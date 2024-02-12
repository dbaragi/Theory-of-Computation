# Project 2 : Context-Free Languages

* Author : Deepti Gururaj Baragi

## Overview
                                                       
This project constructs and simulates pushdown automata to determine string derivability by a context-free grammar. It explores derivation bounds, employs two grammar sets for verification and performance analysis, and implements systematic steps in CFGDeriver.java. Testing includes correctness validation and performance assessment, offering insights into grammar performance under different bounds.

If an input string can be derived by the CFG -> prints yes
If an input string can't be derived by the CFG -> prints no 

## Submitted Files

- src file - containing .class files, tests, evals folders and CFGDeriver.java
- CFGDeriver.java (in src) - explores derivations of CFG using PDA as per the project requirement.
- tests folder - contains CFG encoding files (tc.txt) and input files(in.txt)
-evals folder - contains CFG encoding files (LGb.txt, Lcnf.txt) and input files (eval.txt)


## Compiling and executing


To compile the code use the command below:

```
javac CFGDeriver.java

```

To execute the code use the command below:

General structure:

```
java CFGDeriver <boundtype> <encodingfile> <inputfile> 

```

For running test cases(tc.txt with in.txt):

```
java CFGDeriver 1 tests/tc1.txt tests/in1_1.txt

```

For running evals(LGb.txt or Lcnf.txt with  eval.txt):

```
java CFGDeriver 1 evals/L1Gb.txt evals/eval1_1.txt

```

```
java CFGDeriver 3 evals/L1Gcnf.txt evals/eval1_1.txt

```
Note:
-  use bounds 1 and 2 with LGb.txt files
- use bound 3 with Lcnf.txt file
   
There are some cases where input takes more than 30 seconds to run, in that case kill the process using ctrl + c.
