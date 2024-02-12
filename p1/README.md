# Project 1 : Exploring Efficiency of FA Models

* Author : Deepti Gururaj Baragi

## Overview

The "Exploring Efficiency of FA Models" project is a Java-based implementation for assessing the efficiency of finite state automata (FAs) in the context of string parsing and validation. The project aims to convert Non-Deterministic Finite Automaton (NFA) definitions into various FA models, including NFAs, Deterministic Finite Automata (DFAs), and minimized DFAs. It focuses on implementing string tracing algorithms, determinization, and minimization techniques. 

## Submitted Files

- NFA.java: Java source code for Non-Deterministic Finite Automaton (NFA) implementation.
- DFA.java: Java source code for Deterministic Finite Automaton (DFA) implementation.
- MinimizedDFA.java: Java source code for Minimized DFA implementation.
- ParallelDFA.java: Java source code for Parallel DFA implementation.
- FASimulator.java: Java source code for the main simulation program.
- TC3/tests/: Folder containing test and evaluation files.

## Compiling and Running

1. **Log in to Onyx**: Open a terminal and log in to your Onyx account.

2. **Navigate to Your Project Directory**: Use the `cd` command to navigate to the project directory. My project is located in a directory called `p1`, you can navigate there with:

```
cd p1

```

*  Compile Java Files: Compile the Java source code files for the project by running the following command. This will generate the necessary compiled files.

```
javac NFA.java DFA.java MinimizedDFA.java ParallelDFA.java FASimulator.java

```

* Run Simulations: To run simulations, use the java command, followed by the FASimulator class, the simulation type, the path to the NFA file, and the path to the input file. Replace <simulation_type>, <nfa_file>, and <input_file> with the specific values for the test case. 

* For NFA simulation:

```
java FASimulator 1 TC3/tests/tc0.txt TC3/tests/in0_1.txt

```

* For DFA simulation:

```
java FASimulator 2 TC3/tests/tc0.txt TC3/tests/in0_1.txt

```

* For Minimized DFA simulation:

```
java FASimulator 3 TC3/tests/tc0.txt TC3/tests/in0_1.txt

```

* For Parallel DFA simulation:

```
java FASimulator 4 TC3/tests/tc0.txt TC3/tests/in0_1.txt

```

3. Repeat for Different Test Cases: Repeat the simulation for different test cases by modifying the paths to the NFA and input files as needed. For each simulation type (1, 2, 3, or 4),  evaluate the efficiency and correctness of the FA models.

The general usage for running simulations is:

```
 java FASimulator <simulation_type> <nfa_file> <input_file>

```

Please note that the evaluation and test files are located in the "TC3" folder inside the "p1" directory. 
