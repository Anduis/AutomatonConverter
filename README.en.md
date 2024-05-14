[![es](https://img.shields.io/badge/lang-es-yellow.svg)](./README.md)

## Introduction to Regular Languages and Automaton Theory

Before delving into the specifics of this project, let's take a moment to explore some fundamental concepts in computer science: regular languages and automata theory.

### What are Regular Languages?

In computer science, a regular language is a formal language that can be described by a regular expression. These languages are fundamental in various areas of computer science, including string processing, pattern matching, and compiler design. Regular languages have many practical applications in text processing tasks, such as searching for specific patterns in files or validating user input.

### Automata Theory

Automata theory is the study of abstract machines and computational models called automata, which are used to recognize patterns within strings or sequences of symbols. Automata can be classified into several types based on their computational power, including finite automata (FAs), pushdown automata (PDAs), and Turing machines (TMs).

### Finite Automata (FA)

Finite automata, also known as finite state machines (FSMs), are the simplest type of automata. They consist of a finite set of states, transitions between these states based on input symbols, and designated initial and accepting states. Finite automata are used to recognize regular languages, making them essential tools in theoretical computer science and practical applications.


---

#### Regular Expression to Nondeterministic Finite Automaton (NFA) Conversion
1. **Input**: 
   - The user inputs a regular expression via the console.
   - The alphabet (set of characters) for the regular expression is predefined as "ab".

2. **Thomson Construction (Regex to NFA)**:
   - The regular expression is processed using Thomson's construction algorithm, which converts the regular expression into a nondeterministic finite automaton (NFA).
   - During this process, the regular expression is parsed and transformed into states and transitions in the NFA.

#### Nondeterministic Finite Automaton (NFA) to Deterministic Finite Automaton (DFA) Conversion
1. **Thomson Construction**:
   - The NFA obtained from the regular expression is processed further.
   
2. **Determinization (NFA to DFA)**:
   - The obtained NFA is determinized using a process that involves creating equivalent DFA states.
   - Epsilon transitions are handled during this determinization process.

#### Deterministic Finite Automaton (DFA) to Regular Expression Conversion
1. **DFA to Regular Expression**:
   - After obtaining the DFA, the program aims to convert it back to a regular expression.
   - This is achieved through a process that involves exploring the transitions and states of the DFA to derive the equivalent regular expression.

### Running the Program
1. **Input**:
   - Run the program and input a regular expression via the console.
   
2. **Output**:
   - The program outputs the following:
     - The original regular expression.
     - The corresponding NFA.
     - The determinized DFA.
     - The converted regular expression obtained from the DFA.

### Classes Overview
1. **Main Class**:
   - Contains the main method to execute the program.
   - Takes user input for the regular expression.
   - Instantiates necessary objects and calls relevant methods.

2. **Thomson Class**:
   - Handles the Thomson construction algorithm to convert a regular expression to an NFA.
   - Includes methods for processing regular expressions and constructing NFAs.

3. **Determinista Class**:
   - Performs the determinization process to convert an NFA to a DFA.
   - Implements methods for determinization and DFA-related operations.

### Usage
1. **Compile and Run**:
   - Compile and Run the `Main` class.
   - Input a regular expression when prompted.
   
2. **View Output**:
   - The program will display the NFA, DFA, and the regular expression obtained from the DFA.

### Example
- **Input**: Enter a regular expression, e.g., `((a.b)|(b.a))*`.
- **Output**:
  - Display of the corresponding NFA.
  - Display of the determinized DFA.
  - Display of the regular expression obtained from the DFA.