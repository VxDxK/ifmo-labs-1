package org.vdk;

public class Task1 {
    public static double expandSin(double x, int n) {
        double result = 0;

        for (int i = 0; i < n; i++) {
            double term = Math.pow(-1, i) * Math.pow(x, 2 * i + 1) / factorial(2 * i + 1);
            result += term;
        }
        return result;
    }

    private static long factorial(int num) {
        if (num <= 1) {
            return 1;
        }
        return num * factorial(num - 1);
    }
}
