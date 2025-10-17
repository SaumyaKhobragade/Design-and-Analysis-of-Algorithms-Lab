import java.util.Arrays;

public class MaxSubarraySum {
    static class Result {
        int sum;
        int start;
        int end;

        Result(int sum, int start, int end) {
            this.sum = sum;
            this.start = start;
            this.end = end;
        }
    }

    public static void findMaxSubarray(int[] arr, int constraint) {
        if (arr == null || arr.length == 0 || constraint <= 0) {
            System.out.println("No subarray exists.\n");
            return;
        }

        Result res = findMaxSubarray(arr, 0, arr.length - 1, constraint);

        if (res.sum == Integer.MIN_VALUE) {
            System.out.println("Array: " + Arrays.toString(arr));
            System.out.println("Constraint: " + constraint);
            System.out.println("No subarray exists.\n");
        } else {
            int[] subarray = Arrays.copyOfRange(arr, res.start, res.end + 1);
            System.out.println("Array: " + Arrays.toString(arr));
            System.out.println("Constraint: " + constraint);
            System.out.println("Best Subarray: " + Arrays.toString(subarray) + ". Sum = " + res.sum + "\n");
        }
    }

    public static Result findMaxSubarray(int[] arr, int left, int right, int constraint) {
        if (left > right) return new Result(Integer.MIN_VALUE, -1, -1);

        if (left == right) {
            if (arr[left] <= constraint) {
                return new Result(arr[left], left, left);
            } else {
                return new Result(Integer.MIN_VALUE, -1, -1); // not valid
            }
        }

        int mid = (left + right) / 2;

        Result leftRes = findMaxSubarray(arr, left, mid, constraint);
        Result rightRes = findMaxSubarray(arr, mid + 1, right, constraint);
        Result crossRes = findMaxCrossing(arr, left, mid, right, constraint);

        Result best = leftRes;
        if (rightRes.sum > best.sum) best = rightRes;
        if (crossRes.sum > best.sum) best = crossRes;

        return best;
    }

    private static Result findMaxCrossing(int[] arr, int left, int mid, int right, int constraint) {
        int sum = 0;
        int bestLeftSum = Integer.MIN_VALUE, bestLeftIdx = -1;

        for (int i = mid; i >= left; i--) {
            sum += arr[i];
            if (sum <= constraint && sum > bestLeftSum) {
                bestLeftSum = sum;
                bestLeftIdx = i;
            }
        }

        sum = 0;
        int bestRightSum = Integer.MIN_VALUE, bestRightIdx = -1;

        for (int i = mid + 1; i <= right; i++) {
            sum += arr[i];
            if (bestLeftSum != Integer.MIN_VALUE && bestLeftSum + sum <= constraint
                    && bestLeftSum + sum > bestRightSum) {
                bestRightSum = bestLeftSum + sum;
                bestRightIdx = i;
            }
        }

        if (bestRightSum == Integer.MIN_VALUE) {
            if (bestLeftSum == Integer.MIN_VALUE) {
                return new Result(Integer.MIN_VALUE, -1, -1);
            }
            return new Result(bestLeftSum, bestLeftIdx, mid);
        }

        return new Result(bestRightSum, bestLeftIdx, bestRightIdx);
    }

    private static int[] generateLargeArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i + 1;
        return arr;
    }

    public static void main(String[] args) {
        findMaxSubarray(new int[]{2, 1, 3, 4}, 5);
        findMaxSubarray(new int[]{2, 2, 2, 2}, 4);
        findMaxSubarray(new int[]{1, 5, 2, 3}, 5);
        findMaxSubarray(new int[]{6, 7, 8}, 5);
        findMaxSubarray(new int[]{1, 2, 3, 2, 1}, 5);
        findMaxSubarray(new int[]{1, 1, 1, 1, 1}, 4);
        findMaxSubarray(new int[]{4, 2, 3, 1}, 5);
        findMaxSubarray(new int[]{}, 10);
        findMaxSubarray(new int[]{1, 2, 3}, 0);
//        findMaxSubarray(generateLargeArray(100000), 1000000000);
    }
}
