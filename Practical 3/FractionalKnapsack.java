import java.util.Arrays;
import java.util.Comparator;

public class FractionalKnapsack {
    public static double ratioLoading(int[] profit, int[] weight, int W) {
        double[][] ratio = new double[profit.length][2];
        for (int i = 0; i < profit.length; i++) {
            ratio[i][0] = i;
            if (weight[i] == 0) {
                ratio[i][1] = Double.POSITIVE_INFINITY;
            } else {
                ratio[i][1] = profit[i] / (double) weight[i];
            }
        }

        Arrays.sort(ratio, Comparator.comparingDouble(o -> o[1]));

        int capacity = W;
        double finalValue = 0;
        for (int i = ratio.length - 1; i >= 0; i--) {
            int idx = (int)ratio[i][0];
            if (weight[idx] == 0) {
                finalValue += profit[idx];
                continue;
            }
            if (capacity >= weight[idx]) {
                finalValue += profit[idx];
                capacity -= weight[idx];
            } else {
                finalValue += (ratio[i][1] * capacity);
                break;
            }
        }

        return finalValue;
    }

    public static double minWeightLoading(int[] profit, int[] weight, int W) {
        double[][] weights = new double[profit.length][2];
        for (int i = 0; i < profit.length; i++) {
            weights[i][0] = i;
            weights[i][1] = weight[i];
        }

        Arrays.sort(weights, Comparator.comparingDouble(o -> o[1]));

        int capacity = W;
        double finalValue = 0;
        for (int i = 0; i < weights.length; i++) {
            int idx =(int)weights[i][0];
            if (weight[idx] == 0) {
                finalValue += profit[idx];
                continue;
            }
            if (capacity >= weight[idx]) {
                finalValue += profit[idx];
                capacity -= weight[idx];
            } else {
                finalValue += (profit[idx] / (double) weight[idx]) * capacity;
                break;
            }
        }

        return finalValue;
    }

    public static double maxProfitLoading(int[] profit, int[] weight, int W) {
        double[][] profits = new double[profit.length][2];
        for (int i = 0; i < profit.length; i++) {
            profits[i][0] = i;
            profits[i][1] = profit[i];
        }

        Arrays.sort(profits, Comparator.comparingDouble(o -> o[1]));

        int capacity = W;
        double finalValue = 0;
        for (int i = profits.length - 1; i >= 0; i--) {
            int idx =(int)profits[i][0];
            if (weight[idx] == 0) {
                finalValue += profit[idx];
                continue;
            }
            if (capacity >= weight[idx]) {
                finalValue += profit[idx];
                capacity -= weight[idx];
            } else {
                finalValue += (profit[idx] / (double) weight[idx]) * capacity;
                break;
            }
        }

        return finalValue;
    }

    public static void main(String[] args) {
        int[] profit = {360, 83, 59, 130, 431, 67, 230, 52, 93, 125, 670, 892, 600, 38, 48, 147, 78, 256,
                63, 17, 120, 164, 432, 35, 92, 110, 22, 42, 50, 323, 514, 28, 87, 73, 78, 15, 26, 78, 210,
                36, 85, 189, 274, 43, 33, 10, 19, 389, 276, 312};
        int[] weight = {7, 0, 30, 22, 80, 94, 11, 81, 70, 64, 59, 18, 0, 36, 3, 8, 15, 42, 9, 0, 42, 47,
                52, 32, 26, 48, 55, 6, 29, 84, 2, 4, 18, 56, 7, 29, 93, 44, 71, 3, 86, 66, 31, 65, 0, 79,
                20, 65, 52, 13};
        int W = 850;

        System.out.println("Total Profit using Profit by Weight ratio: " + ratioLoading(profit, weight, W));
        System.out.println("Total Profit using Minimum Weight Loading: " + minWeightLoading(profit, weight, W));
        System.out.println("Total Profit using Maximum Profit Loading: " + maxProfitLoading(profit, weight, W));
    }
}
