public class SelfAssessment1 {
    public static void findingSquareRoot(int num) {
        long start = System.nanoTime();
        float x = num;
        float y = 1;

        double e = 0.000001;
        while (x - y > e) {
            x = (x + y) / 2;
            y = num / x;
        }

        System.out.println(x);

        long end = System.nanoTime();
        System.out.printf("Task D - Time (Babylonian Method) - Time: %.6f ms\n", (end - start) / 1000000f);

        start = System.nanoTime();
        x = 0;
        y = num;
        float mid;

        double ans = 0.0;

        while (x <= y) {
            mid = (x + y) / 2f;

            if (mid * mid == num) {
                ans = mid;
                break;
            }

            if (mid * mid < num) {
                x = mid + 1;
                ans = mid;
            } else {
                y = mid - 1;
            }
        }

        double increment = 0.1;
        for (int i = 0; i < 4; i++) {
            while (ans * ans <= num) {
                ans += increment;
            }

            ans = ans - increment;
            increment = increment / 10;
        }

        end = System.nanoTime();
        System.out.printf("Task D - Time (Binary Search): %.6f ms\n", (end - start) / 1000000f);
    }

    public static void main(String[] args) {
        System.out.println("\n===== TASK D: Finding Square Root =====");
        findingSquareRoot(28826161);
        findingSquareRoot(100000);
    }
}
