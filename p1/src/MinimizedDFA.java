import java.util.*;

public class MinimizedDFA {
    private DFA dfa;
    private Set<Set<String>> partitions;
    private Map<String, Set<String>> stateToPartition;
    private DFA minimizedDfa;
     private Set<String> minimizedFinalStates; 

    public MinimizedDFA(DFA dfa) {
        this.dfa = dfa;
        this.partitions = new HashSet<>();
        this.stateToPartition = new HashMap<>();
        this.minimizedFinalStates = new HashSet<>(); 
        constructMinimizedDFA();
    }

    public DFA getMinimizedDFA() {
        return minimizedDfa;
    }

     public Set<String> getMinimizedFinalStates() {  // Change this line
        return minimizedFinalStates;
    }

    private void constructMinimizedDFA() {
        minimizedDfa = new DFA();

        // Minimized states are the refined partitions
        minimizedDfa.setStates(partitions);

        // Assigning start state
        minimizedDfa.setStartState(stateToPartition.get(dfa.getStartState()));

        // Constructing transitions for minimized DFA
        Map<Set<String>, Map<Character, Set<String>>> minimizedTransitions = new HashMap<>();
        for (Set<String> stateSet : partitions) {
            Map<Character, Set<String>> transitionMap = new HashMap<>();
            for (char symbol : dfa.getAlphabet()) {
                String representativeState = stateSet.iterator().next();
                if (dfa.getTransitions().containsKey(representativeState)) {  // Added this
                    Set<String> targetStates = dfa.getTransitions().get(representativeState).get(symbol);
                    if (targetStates != null && !targetStates.isEmpty()) {
                        Set<String> minimizedTargetState = stateToPartition.get(targetStates.iterator().next());
                        transitionMap.put(symbol, minimizedTargetState);
                    }
                }
            }
            minimizedTransitions.put(stateSet, transitionMap);
        }
        minimizedDfa.setTransitions(minimizedTransitions);

        // Assigning final states
        Set<Set<String>> minimizedFinalStates = new HashSet<>();
        for (Set<String> finalState : dfa.getFinalStates()) {
            if (stateToPartition.containsKey(finalState.iterator().next())) {
                minimizedFinalStates.add(stateToPartition.get(finalState.iterator().next()));
            }
        }
    }
}

