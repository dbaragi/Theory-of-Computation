


import java.io.*;
import java.util.*;

public class NFA {
    private Set<String> states;
    private Set<String> finalStates;
    private String startState;
    private Map<String, Map<Character, List<String>>> transitions;

    public NFA() {
        this.states = new HashSet<>();
        this.finalStates = new HashSet<>();
        this.transitions = new HashMap<>();
    }

    public Set<String> getStates() {
        return states;
    }

    public void setStates(Set<String> states) {
        this.states = states;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(Set<String> finalStates) {
        this.finalStates = finalStates;
    }

    public String getStartState() {
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public Map<String, Map<Character, List<String>>> getTransitions() {
        return transitions;
    }

    public void setTransitions(Map<String, Map<Character, List<String>>> transitions) {
        this.transitions = transitions;
    }
    
    public Set<Character> getAlphabet() {
    Set<Character> alphabet = new HashSet<>();
    for (Map<Character, List<String>> map : transitions.values()) {
        alphabet.addAll(map.keySet());
    }
    return alphabet;
}

    public void readFromFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        // Reading final states
        String line = br.readLine();
        for (String state : line.split("\\s+")) {
            finalStates.add(state);
        }
        // Reading start state
        startState = br.readLine().trim();
        // Reading other states
        line = br.readLine();
        for (String state : line.split("\\s+")) {
            states.add(state);
        }
        // Combining all states
        states.addAll(finalStates);
        states.add(startState);
        // Reading transitions
        line = br.readLine();
        for (String transition : line.split("\\s+")) {
            String fromState = Character.toString(transition.charAt(0));
            char symbol = transition.charAt(1);
            String toState = Character.toString(transition.charAt(2));
            transitions.putIfAbsent(fromState, new HashMap<>());
            transitions.get(fromState).putIfAbsent(symbol, new ArrayList<>());
            transitions.get(fromState).get(symbol).add(toState);
        }
        br.close();
    }

    public boolean simulate(String input) {
    Set<String> currentStates = new HashSet<>();
    currentStates.add(startState);
    currentStates = epsilonClosure(currentStates);
    for (char symbol : input.toCharArray()) {
        Set<String> nextStates = new HashSet<>();
        for (String state : currentStates) {
            if (transitions.containsKey(state) && transitions.get(state).containsKey(symbol)) {
                nextStates.addAll(transitions.get(state).get(symbol));
            }
        }
        currentStates = epsilonClosure(nextStates);
    }
    for (String state : currentStates) {
        if (finalStates.contains(state)) {
            return true;
        }
    }
    return false;
}

public Set<String> epsilonClosure(Set<String> states) {
        Set<String> closure = new HashSet<>(states);
        boolean changed;
        do {
            Set<String> newStates = new HashSet<>(closure);
            for (String state : closure) {
                if (transitions.containsKey(state) && transitions.get(state).containsKey('e')) {
                    newStates.addAll(transitions.get(state).get('e'));
                }
            }
            changed = closure.size() != newStates.size();
            closure = newStates;
        } while (changed);
        return closure;
    }

}



