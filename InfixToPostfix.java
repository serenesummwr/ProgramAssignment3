// 672115011 Natthapoom Saengkaew

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfixToPostfix {

    // Node class for the linked list; stores a String token.
    static class Node {
        String data;
        Node next;

        Node(String data) {
            this.data = data;
            this.next = null;
        }
    }

    // Stack class implemented with a linked list; works with String tokens.
    static class Stack {
        Node top;

        public Stack() {
            top = null;
        }

        public void push(String s) {
            Node newNode = new Node(s);
            newNode.next = top;
            top = newNode;
        }

        public String pop() {
            if (isEmpty()) {
                return null;
            }
            String data = top.data;
            top = top.next;
            return data;
        }

        public String peek() {
            if (isEmpty()) {
                return null;
            }
            return top.data;
        }

        public boolean isEmpty() {
            return top == null;
        }
    }

    // Returns true if the token is one of the operators.
    public static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") ||
               token.equals("/") || token.equals("^") || token.equals("==") ||
               token.equals("!=") || token.equals(">") || token.equals("<") ||
               token.equals("&&");
    }

    // Returns the precedence of the operator.
    public static int precedence(String token) {
        if (token.equals("^")) {
            return 3;
        } else if (token.equals("*") || token.equals("/")) {
            return 2;
        } else if (token.equals("+") || token.equals("-")) {
            return 1;
        } else if (token.equals("==") || token.equals("!=") || token.equals(">") || token.equals("<")) {
            return 0;
        } else if (token.equals("&&")) {
            return -1;
        }
        return -1;
    }

    // Tokenizes the input expression using regular expressions.
    public static List<String> tokenize(String exp) {
        List<String> tokens = new ArrayList<>();
        // The regex first matches multi-character operators ("==", "!=", "&&"),
        // then single-character relational operators (">" and "<"),
        // then numbers, variable names, and finally any remaining single-character operator or parenthesis.
        Pattern tokenPattern = Pattern.compile("==|!=|&&|[><]|\\d+|[A-Za-z]+|[+\\-*/^()]");
        Matcher matcher = tokenPattern.matcher(exp);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    // Validate the infix expression:
    // Checks for balanced parentheses and proper ordering of operands/operators.
    public static boolean isValidInfix(String exp) {
        List<String> tokens = tokenize(exp);
        Stack parenStack = new Stack();
        boolean prevIsOperator = false;

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (token.equals("(")) {
                parenStack.push(token);
                prevIsOperator = false;
            } else if (token.equals(")")) {
                if (parenStack.isEmpty()) {
                    return false; // unbalanced parentheses
                }
                parenStack.pop();
                // A closed parenthesis acts like an operand.
                prevIsOperator = false;
            } else if (isOperator(token)) {
                // Expression cannot start with an operator.
                if (i == 0) {
                    return false;
                }
                // Two consecutive operators are not allowed.
                if (prevIsOperator) {
                    return false;
                }
                prevIsOperator = true;
            } else { // Operand
                prevIsOperator = false;
            }
        }
        // Check for unmatched left parentheses.
        if (!parenStack.isEmpty()) {
            return false;
        }
        // Expression should not end with an operator.
        return !prevIsOperator;
    }

    // Convert infix expression to postfix expression using a linked-list-based stack.
    public static String infixToPostfix(String exp) {
        List<String> tokens = tokenize(exp);
        StringBuilder postfix = new StringBuilder();
        Stack stack = new Stack();

        for (String token : tokens) {
            // If token is an operand, add it to the output.
            if (Character.isLetterOrDigit(token.charAt(0))) {
                postfix.append(token).append(" ");
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                // Pop until an opening parenthesis is found.
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.append(stack.pop()).append(" ");
                }
                if (!stack.isEmpty() && stack.peek().equals("(")) {
                    stack.pop(); // Remove the '(' from the stack.
                }
            } else if (isOperator(token)) {
                // For operators, pop operators from the stack based on precedence.
                while (!stack.isEmpty() && isOperator(stack.peek())) {
                    if ((precedence(token) < precedence(stack.peek())) ||
                        (precedence(token) == precedence(stack.peek()) && !token.equals("^"))) {
                        postfix.append(stack.pop()).append(" ");
                    } else {
                        break;
                    }
                }
                stack.push(token);
            }
        }

        // Pop any remaining operators from the stack.
        while (!stack.isEmpty()) {
            String topToken = stack.pop();
            if (topToken.equals("(") || topToken.equals(")")) {
                continue;
            }
            postfix.append(topToken).append(" ");
        }

        return postfix.toString().trim();
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java InfixToPostfix <inputfile>");
            return;
        }

        String filename = args[0];

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int exprCount = 1;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                System.out.println("Expression " + exprCount + ":");
                System.out.println("Infix exp: " + line);

                if (isValidInfix(line)) {
                    System.out.println("Valid");
                    String postfix = infixToPostfix(line);
                    System.out.println("Postfix exp: " + postfix);
                } else {
                    System.out.println("Not-Valid");
                }
                System.out.println();
                exprCount++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
