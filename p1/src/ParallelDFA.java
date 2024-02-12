import java.util.*;

public class ParallelDFA {
    private List<DFA> dfas;

    public ParallelDFA(List<DFA> dfas) {
        this.dfas = dfas;
    }

    public boolean simulate(String input) {
        // A list to keep track of current states for each DFA
        List<Set<String>> currentStates = new ArrayList<>();

        // Initialize each DFA's start state
        for (DFA dfa : dfas) {
            currentStates.add(dfa.getStartState());
        }

        for (char symbol : input.toCharArray()) {
            for (int i = 0; i < dfas.size(); i++) {
                DFA dfa = dfas.get(i);
                Set<String> currentState = currentStates.get(i);
                if (currentState == null) continue; // If current state is null, this DFA has already failed
                Map<Character, Set<String>> transitionMap = dfa.getTransitions().get(currentState);
                if (transitionMap != null && transitionMap.containsKey(symbol)) {
                    Set<String> nextState = transitionMap.get(symbol);
                    currentStates.set(i, nextState);
                } else {
                    // No valid transition for this DFA, so set its current state to null
                    currentStates.set(i, null);
                }
            }
        }

        // Check if any of the DFAs accept the input
        for (int i = 0; i < dfas.size(); i++) {
            DFA dfa = dfas.get(i);
            Set<String> currentState = currentStates.get(i);
            if (currentState != null && dfa.getFinalStates().contains(currentState)) {
                return true;  // If any DFA accepts the input, return true
            }
        }
        return false;  // If none of the DFAs accept the input, return false
    }
}

