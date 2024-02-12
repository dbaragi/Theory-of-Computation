import java.util.*;

public class DFA {
    private Set<Set<String>> states;  // States of DFA (each state is a set of NFA states)
    private Set<Set<String>> finalStates; // Final states of DFA
    private Set<String> startState;   // Start state of DFA
    private Map<Set<String>, Map<Character, Set<String>>> transitions;  // Transitions of DFA
    private NFA nfa;  // The NFA from which the DFA is derived
    private Set<Character> alphabet;

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public DFA(NFA nfa) {
        this.states = new HashSet<>();
        this.finalStates = new HashSet<>();
        this.transitions = new HashMap<>();
        this.nfa = nfa;
        this.alphabet = nfa.getAlphabet();

        convertFromNFA();
    }

    public DFA() {
        this.states = new HashSet<>();
        this.finalStates = new HashSet<>();
        this.transitions = new HashMap<>();
    }

    public Set<Set<String>> getStates() {
        return states;
    }

    public Set<Set<String>> getFinalStates() {
        return finalStates;
    }

    public Set<String> getStartState() {
        return startState;
    }

    public Map<Set<String>, Map<Character, Set<String>>> getTransitions() {
        return transitions;
    }
public void setStates(Set<Set<String>> states) {
    this.states = states;
}

public void setTransitions(Map<Set<String>, Map<Character, Set<String>>> transitions) {
    this.transitions = transitions;
}

public void setStartState(Set<String> startState) {
    this.startState = startState;
}

    private void convertFromNFA() {
    // Initialize DFA start state from the epsilon closure of the NFA start state
    Set<String> initialState = nfa.epsilonClosure(new HashSet<>(Arrays.asList(nfa.getStartState())));
    states.add(initialState);
    startState = initialState;

    Queue<Set<String>> queue = new LinkedList<>();
    queue.add(initialState);

    while (!queue.isEmpty()) {
        Set<String> currentDFAState = queue.poll();
        for (char symbol : nfa.getAlphabet()) {
            if (symbol == 'e') continue; // skip epsilon transitions

            Set<String> nextDFAState = new HashSet<>();
            for (String nfaState : currentDFAState) {
                if (nfa.getTransitions().containsKey(nfaState) && nfa.getTransitions().get(nfaState).containsKey(symbol)) {
                    nextDFAState.addAll(nfa.getTransitions().get(nfaState).get(symbol));
                }
            }
            
            nextDFAState = nfa.epsilonClosure(nextDFAState); // Take epsilon closure

            if (!states.contains(nextDFAState) && !nextDFAState.isEmpty()) {
                states.add(nextDFAState);
                queue.add(nextDFAState);
            }

            transitions.putIfAbsent(currentDFAState, new HashMap<>());
            transitions.get(currentDFAState).put(symbol, nextDFAState);

            // Check if any state in the nextDFAState set is a final state in the NFA
            if (nextDFAState.stream().anyMatch(nfa.getFinalStates()::contains)) {
                finalStates.add(nextDFAState);
            }
        }
    }
}


    public boolean simulate(String input) {
        Set<String> currentState = startState;
        for (char symbol : input.toCharArray()) {
            if (transitions.get(currentState) != null && transitions.get(currentState).get(symbol) != null) {
                currentState = transitions.get(currentState).get(symbol);
            } else {
                // No valid transition
                return false;
            }
        }
        return finalStates.contains(currentState);
    }
}

