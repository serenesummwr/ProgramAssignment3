# Infix to Postfix Converter

This Java project converts an infix expression to its corresponding postfix expression using a linked-list-based stack.

---

## Features

- **Tokenization:**  
  Supports numbers, variable names, and multi-character operators such as `==`, `!=`, and `&&`, as well as arithmetic and relational operators.

- **Validation:**  
  Checks for balanced parentheses and prevents invalid sequences like consecutive operators or expressions starting/ending with an operator.

- **Conversion:**  
  Uses a custom linked-list-based stack to convert valid infix expressions to their postfix equivalents.

- **File Input:**  
  Reads expressions from a text file, processes each line, and outputs the original infix expression along with its postfix conversion.

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK):** Version 8 or higher is required.
- A text editor or IDE for editing the code (e.g., VSCode, IntelliJ IDEA).

## Usage

Compile the Java file:
```
javac InfixToPostfix.java
```

Run the application with an input file containing infix expressions:
```
java InfixToPostfix <inputfile>
```
Each non-empty line in the input file is processed as a separate expression.

## Example

Assuming `input1.csv` contains:
```
(3 + 4) * 5
a + b * c
```

Run:
```
java InfixToPostfix /Users/natthapoom/Desktop/input1.csv
```

Output:
```
Expression 1:
Infix exp: (3 + 4) * 5
Valid
Postfix exp: 3 4 + 5 *

Expression 2:
Infix exp: a + b * c
Valid
Postfix exp: a b c * +
```
## Author

672115011 Natthapoom Saengkaew

## License

This project is provided without any warranty. Feel free to modify and enhance it as needed.


