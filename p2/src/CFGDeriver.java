import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Author: Deepti
 * Description: This program implements a CFG  derivator
 *              and explores the possible derivations using a PDA
 */
public class CFGDeriver {

    /**
     * Represents a production rule in the CFG
     */
    static class Rule {
        char variable;
        String derivation;

        public Rule(char variable, String derivation) {
            this.variable = variable;
            this.derivation = derivation;
        }
    }

    /**
     * Represents the CFG
     */
    static class CFG {
        Map<Character, List<String>> rules = new HashMap<>();
        char startSymbol;

        public CFG(char startSymbol) {
            this.startSymbol = startSymbol;
        }

        void addRule(String rule) {
            char variable = rule.charAt(0);
            String derivation = rule.substring(3);
            rules.putIfAbsent(variable, new ArrayList<>());
            rules.get(variable).add(derivation);
        }
    }

    /**
     * Represents the PDA
     */
    static class PDA {
        Stack<Character> stack;
        int inputPos;
        int steps;
        String inputString;
        int boundType;

        public PDA(char startSymbol, String inputString, int boundType) {
            this.stack = new Stack<>();
            this.stack.push(startSymbol);
            this.inputString = inputString;
            this.inputPos = 0;
            this.steps = 0;
            this.boundType = boundType;
        }

        public PDA clone() {
            PDA newPDA = new PDA(' ', this.inputString, this.boundType);
            newPDA.stack = (Stack<Character>) this.stack.clone();
            newPDA.inputPos = this.inputPos;
            newPDA.steps = this.steps;
            return newPDA;
        }
    }

    /**
     * Parses the CFG from a file
     *
     * @param fileName The name of the file containing CFG rules
     * @return The parsed CFG
     * @throws IOException If an I/O error occurs
     */
    static CFG parseCFG(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int numberOfRules = Integer.parseInt(reader.readLine());
        CFG cfg = new CFG('S');

        for (int i = 0; i < numberOfRules; i++) {
            cfg.addRule(reader.readLine());
        }

        reader.close();
        return cfg;
    }

    /**
     * Checks if the PDA has accepted the input string
     *
     * @param pda          The PDA to check
     * @param inputString The input string
     * @return True if accepted, false otherwise
     */
    static boolean isAccepted(PDA pda, String inputString) {
        return pda.stack.isEmpty() && pda.inputPos == inputString.length();
    }

    /**
     * Calculates the bound based on the specified bound type
     *
     * @param boundType    The type of bound
     * @param inputString  The input string
     * @return The calculated bound
     */
    public static int calculateBound(int boundType, String inputString) {
        switch (boundType) {
            case 1:
                return 100;
            case 2:
                return Math.max(inputString.length(), 1);
            case 3:
                int calculatedBound = (2 * inputString.length()) - 1;
                return Math.max(calculatedBound, 1);
            default:
                throw new IllegalArgumentException("Invalid bound type");
        }
    }

    // Global variable to store the maximum step count for specific bound types
    public static int valMax;

    /**
     * Generates the next set of PDAs based on the current PDA and CFG
     *
     * @param cfg         The CFG.
     * @param currentPDA  The current PDA.
     * @return A list of next PDAs.
     */
    static List<PDA> generateNextPDAs(CFG cfg, PDA currentPDA) {
        List<PDA> nextPDAs = new ArrayList<>();

        // Check if the step count bound is exceeded
        if (currentPDA.boundType == 1 && currentPDA.steps >= valMax) {
            return nextPDAs; // Step count bound exceeded
        }

        if (currentPDA.boundType == 2 && currentPDA.steps >= valMax) {
            return nextPDAs; // Step count bound exceeded
        }
        if (currentPDA.boundType == 3 && currentPDA.steps >= 125) {
            return nextPDAs; // Step count bound exceeded
        }

        // No further derivations possible if the stack is empty
        if (currentPDA.stack.isEmpty()) {
            return nextPDAs;
        }

        char stackTop = currentPDA.stack.peek();
        if (Character.isUpperCase(stackTop)) {
            List<String> derivations = cfg.rules.getOrDefault(stackTop, new ArrayList<>());
            for (String derivation : derivations) {
                PDA newPDA = currentPDA.clone();
                newPDA.stack.pop(); // Remove the non-terminal
                for (int i = derivation.length() - 1; i >= 0; i--) {
                    char dChar = derivation.charAt(i);
                    if (dChar != '!') { // Handle non-epsilon transition
                        newPDA.stack.push(dChar);
                    }
                }
                newPDA.steps++;
                nextPDAs.add(newPDA);
            }
        } else if (!currentPDA.stack.isEmpty() && (currentPDA.inputPos < currentPDA.inputString.length() && stackTop == currentPDA.inputString.charAt(currentPDA.inputPos))) {
            PDA newPDA = currentPDA.clone();
            newPDA.stack.pop();
            newPDA.inputPos++;
            newPDA.steps++;
            nextPDAs.add(newPDA);
        }

        return nextPDAs;
    }

    /**
     * Explores possible derivations using the CFG and PDA
     *
     * @param cfg         The CFG
     * @param inputString The input string
     * @param boundType   The type of bound
     * @return True if derivations are possible, false otherwise
     */
    static boolean exploreDerivations(CFG cfg, String inputString, int boundType) {
        if (inputString.equals("!")) {
            inputString = ""; // Treat "!" as an empty string
        }
        List<PDA> pdas = new ArrayList<>();
        pdas.add(new PDA(cfg.startSymbol, inputString, boundType));

        while (!pdas.isEmpty()) {
            PDA currentPDA = pdas.remove(0);
            if (isAccepted(currentPDA, inputString)) {
                return true;
            }
            List<PDA> nextPDAs = generateNextPDAs(cfg, currentPDA);
            pdas.addAll(nextPDAs);
        }

        return false;
    }

    /**
     * 
     *
     * @param args Command-line arguments
     * @throws IOException If an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        // Check if the correct number of command-line arguments is provided
        if (args.length != 3) {
            System.out.println("Usage: java CFGDeriver <boundType> <CFGFile> <InputStringFile>");
            return;
        }

        // Parse command-line arguments
        int boundType = Integer.parseInt(args[0]);
        String cfgFile = args[1];
        String inputStringFile = args[2];

        // Parse CFG from file and read input string
        CFG cfg = parseCFG(cfgFile);
        String inputString = new String(Files.readAllBytes(Paths.get(inputStringFile)));

        // Calculate the maximum step count based on the bound type
        int val = calculateBound(boundType, inputString);
        if (boundType == 1) {
            valMax = 1000;
        } else if (boundType == 2) {
            valMax = Math.max(inputString.length(), 1);
        } else if (boundType == 3) {
            int calculatedBound = (2 * inputString.length()) - 1;
            valMax = Math.max(calculatedBound, 1);
        }

        // Explore derivations and print the result
        boolean canBeDerived = exploreDerivations(cfg, inputString, boundType);
        System.out.println(canBeDerived ? "yes" : "no");
    }
}
