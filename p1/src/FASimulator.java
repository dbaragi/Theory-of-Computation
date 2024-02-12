

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ddh
 */
public class FASimulator {
    
    public static void main(String[] args) {
//        if (args.length != 3) {
//            System.out.println("Usage: java FASimulator <simulation_type> <nfa_file> <input_file>");
//            return;
//        }

        int simulationType = Integer.parseInt(args[0]);
        String nfaFile = args[1];
        String inputFile = args[2];

        try {
            NFA nfa = new NFA();
            nfa.readFromFile(nfaFile);
            
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String input = br.readLine();
            br.close();

            switch (simulationType) {
                case 1:
                    System.out.println(nfa.simulate(input) ? "yes" : "no");
                    break;
                case 2:
                    DFA dfa = new DFA(nfa);
                    System.out.println(dfa.simulate(input) ? "yes" : "no");
                    break;
                case 3:
                    MinimizedDFA minDfa = new MinimizedDFA(new DFA(nfa));
                Set<String> minimizedFinalStates = minDfa.getMinimizedFinalStates();
                String[] results = new String[100]; // Assuming a maximum of 100 lines in the input file
                int currentIndex = 0;
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    
                    while ((input = reader.readLine()) != null) {
                        boolean result = minimizedFinalStates.contains(input);
                        results[currentIndex] = result ? "yes" : "no";
                        currentIndex++;
                        break;
                    }
                    if (currentIndex > 1) {
        System.out.println(results[currentIndex]); // Print the last result
    }
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                    
                case 4:
                     List<DFA> dfasForParallel = Arrays.asList(new DFA(nfa));
                     
                    ParallelDFA pDfa = new ParallelDFA(dfasForParallel);
                    
                    System.out.println(pDfa.simulate(input) ? "yes" : "no");
                    break;
                default:
                    System.out.println("Invalid simulation type");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


