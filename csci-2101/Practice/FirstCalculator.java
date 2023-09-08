package Practice;
import java.util.Scanner;

public class FirstCalculator {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to a simple calculator. Type \"q\" to quit.");

        while (true) {
            if (!scan.hasNextDouble()) {
                String userInput = scan.next();
                if (userInput.equals("q")) {
                    System.out.println("Thank you for using!");
                    break;
                } else {
                    System.out.println("I do not understand!");
                    continue;
                }
            }

            double operandOne = scan.nextDouble();
            String operator = scan.next();
            double operandTwo = scan.nextDouble();
            
            double result = 0;
            if (operator.equals("+")) {
                result = (operandOne + operandTwo);
            }
            else if (operator.equals("-")) {
                result = (operandOne - operandTwo);
            }
            else if (operator.equals("*")) {
                result = (operandOne * operandTwo);
            }
            else if (operator.equals("/")) {
                result = (operandOne / operandTwo);
            }
            else {
                System.out.println("Invalid operator!");
            }

            System.out.println(result);

        }

        scan.close();
    }
}
