import java.util.*;

public class OptimalBST {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of book IDs: ");
        int n = sc.nextInt();
        int[] keys = new int[n];
        System.out.print("Enter the book IDs: ");
        for (int i = 0; i < n; i++) {
            keys[i] = sc.nextInt();
        }

        double[] p = new double[n + 1];
        double[] q = new double[n + 1];

        System.out.print("Enter the probabilities of successful searches: ");
        for (int i = 1; i <= n; i++) {
            p[i] = sc.nextDouble();
        }

        System.out.print("Enter the probabilities of unsuccessful searches: ");
        for (int i = 0; i <= n; i++) {
            q[i] = sc.nextDouble();
        }

        System.out.printf("%.4f", optimalBST(p, q, n));
    }

    static double optimalBST(double[] p, double[] q, int n) {
        double[][] e = new double[n + 2][n + 1];
        double[][] w = new double[n + 2][n + 1];

        for (int i = 0; i <= n; i++) {
            e[i + 1][i] = q[i];
            w[i + 1][i] = q[i];
        }

        for (int l = 1; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                e[i][j] = Double.MAX_VALUE;
                w[i][j] = w[i][j - 1] + p[j] + q[j];
                for (int r = i; r <= j; r++) {
                    double t = e[i][r - 1] + e[r + 1][j] + w[i][j];
                    if (t < e[i][j])
                        e[i][j] = t;
                }
            }
        }

        return e[1][n];
    }
}
