import java.util.Scanner;

public class FinancialPrediction {

    public static double calFutureValue(double initialAmount, double annualGrowthRate, int years) {
        if (years == 0) {
            return initialAmount;
        }
        double previousValue = calFutureValue(initialAmount, annualGrowthRate, years - 1);
        return previousValue * (1 + annualGrowthRate);
    }

    public static double computeFutureValueIterative(double initialAmount, double annualGrowthRate, int years) {
        double futureAmount = initialAmount;
        for (int i = 0; i < years; i++) {
            futureAmount *= (1 + annualGrowthRate);
        }
        return futureAmount;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the initial amount: ");
        double initialAmount = scanner.nextDouble();

        System.out.print("Enter the annual growth rate (as a decimal): ");
        double annualGrowthRate = scanner.nextDouble();

        System.out.print("Enter the number of years into the future: ");
        int years = scanner.nextInt();

        double futureValueRecursive = calFutureValue(initialAmount, annualGrowthRate, years);
        double futureValueIterative = computeFutureValueIterative(initialAmount, annualGrowthRate, years);

        System.out.printf("The future value after %d years (recursive) is: %.2f%n", years, futureValueRecursive);
        System.out.printf("The future value after %d years (iterative) is: %.2f%n", years, futureValueIterative);
    }
}
