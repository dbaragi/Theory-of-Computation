Last login: Sun Dec 10 14:55:03 on ttys002
(base) Deepti@ajiths-MacBook-Air ~ % ssh deeptigururajbar@onyx.boisestate.edu
deeptigururajbar@onyx.boises[d[de[dee[deep[dee[deep[dee[de[[[[[d[d[[d[[[[[[deeptigururajbar@onyx ~]$ ls
CFGDeriver               DB_project  msandbox.ssh  sandboxes
CFGprojectfinalanalysis  Dump.sql    opt           schema.sql
CS510_finalproj.zip      ml-1m       p1            TC4
CS535-resources          model.pdf   README.docx
[deeptigururajbar@onyx ~]$ cd p1
[deeptigururajbar@onyx p1]$ ls
DFA.class           MinimizedDFA.java  README.md
DFA.java            NFA.class          TC3
FASimulator.class   NFA.java           TOC-p1_report.pdf
FASimulator.java    ParallelDFA.class
MinimizedDFA.class  ParallelDFA.java
[deeptigururajbar@onyx p1]$ vi README.md







# Project 2 : Context-Free Languages

* Author : Deepti Gururaj Baragi

## Overview
                                                        1,47          Top
This project constructs and simulates pushdown automata to determine string derivability by a context-free grammar. It explores derivation bounds, employs two grammar sets for verification and performance analysis, and implements systematic steps in CFGDeriver.java. Testing includes correctness validation and performance assessment, offering insights into grammar performance under different bounds.

If an input string can be derived by the CFG -> prints yes
If an input string can't be derived by the CFG -> prints no 

## Submitted Files

- src file - containing .class files, tests, evals folders and CFGDeriver.java
- CFGDeriver.java (in src) - explores derivations of CFG using PDA as per the project requirement.
- tests folder (in src) - contains CFG encoding files (tc.txt) and input files(in.txt)
-evals folder (in src) - contains CFG encoding files (LGb.txt, Lcnf.txt) and input files (eval.txt)
- CFGDeriver.pdf - project report containing implementation approach and r
esults.

## Compiling and executing

Login to the onyx and cd p2
 
Under that, follow the steps given here:

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
