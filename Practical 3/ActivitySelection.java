import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ActivitySelection {
    public static void activitySelectionFinish(int[] start, int[] end, int[] profit) {
        int maxAct = 0;
        int maxProfit = 0;
        ArrayList<Integer> ans = new ArrayList<>();

        maxAct = 1;
        ans.add(0);
        maxProfit = profit[0];
        int lastEnd = end[0];
        for (int i = 1; i < start.length; i++) {
            if (start[i] >= lastEnd) {
                maxAct++;
                ans.add(i);
                lastEnd = end[i];
                maxProfit += profit[i];
            }
        }

        System.out.println("===== Finish Time Based =====");
        System.out.println("Maximum Activities: " + maxAct);
        System.out.println("Maximum Profit: " + maxProfit);
        for (int i : ans) {
            System.out.print("A" + (i + 1) + " ");
        }
        System.out.println();
    }

    public static void activitySelectionStart(int[] start, int[] end, int[] profit) {
        int n = start.length;
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;

        Arrays.sort(idx, Comparator.comparingInt(i -> start[i]));

        int maxAct = 1;
        int maxProfit = profit[idx[0]];
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(idx[0]);
        int lastEnd = end[idx[0]];

        for (int i = 1; i < n; i++) {
            if (start[idx[i]] >= lastEnd) {
                maxAct++;
                ans.add(idx[i]);
                lastEnd = end[idx[i]];
                maxProfit += profit[idx[i]];
            }
        }

        System.out.println("===== Start Time Based =====");
        System.out.println("Maximum Activities: " + maxAct);
        System.out.println("Maximum Profit: " + maxProfit);
        for (int i : ans) {
            System.out.print("A" + (i + 1) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] start = {1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
        int[] end = {4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};
        int[] profit = {10, 15, 14, 12, 20, 30, 32, 28, 30, 40, 45};

        activitySelectionFinish(start, end, profit);
        activitySelectionStart(start, end, profit);
    }
}
