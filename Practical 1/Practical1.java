import java.util.Random;

public class Practical1 {
    public static void linearSearch(float[] temperature, float[] pressure) {
        long start = System.nanoTime();

        float minTemp = temperature[0];
        for (float temp : temperature)
            if (temp < minTemp)
                minTemp = temp;

        float maxPressure = pressure[0];
        for (float p : pressure)
            if (p > maxPressure)
                maxPressure = p;

        long end = System.nanoTime();
        System.out.printf("Task A (Linear Search) - Time: %.6f ms\n", (end - start) / 1000000f);
        System.out.println("Min Temperature: " + minTemp);
        System.out.println("Max Pressure: " + maxPressure);
    }

    public static void pairwiseComparison(float[] temperature, float[] pressure) {
        long start = System.nanoTime();

        float minTemp = Float.MAX_VALUE;
        for (int i = 0; i < temperature.length; i++) {
            boolean isMin = true;
            for (int j = 0; j < temperature.length; j++) {
                if (temperature[i] > temperature[j]) {
                    isMin = false;
                    break;
                }
            }
            if (isMin) {
                minTemp = temperature[i];
                break;
            }
        }

        float maxPressure = Float.MIN_VALUE;
        for (int i = 0; i < pressure.length; i++) {
            boolean isMax = true;
            for (int j = 0; j < pressure.length; j++) {
                if (pressure[i] < pressure[j]) {
                    isMax = false;
                    break;
                }
            }
            if (isMax) {
                maxPressure = pressure[i];
                break;
            }
        }

        long end = System.nanoTime();
        System.out.printf("Task B (Pairwise) - Time: %.6f ms\n", (end - start) / 1000000f);
        System.out.println("Min Temperature: " + minTemp);
        System.out.println("Max Pressure: " + maxPressure);
    }

    public static void linearAndBinarySearch(float[] temperature, float[] pressure) {
        int n = temperature.length;
        float[] tempSorted = new float[n];
        float startValue = 20;
        float step = (50 - 20) / (float)n;

        for (int i = 0; i < n; i++) {
            tempSorted[i] = startValue + i * step;
        }

        long start = System.nanoTime();
        int indexLinear = -1;
        for (int i = 0; i < n; i++) {
            if (tempSorted[i] >= 30) {
                indexLinear = i;
                break;
            }
        }
        long end = System.nanoTime();
        System.out.printf("Task C (Linear Search) - Time: %.6f ms, Index: %d\n", (end - start) / 1000000f, indexLinear);

        start = System.nanoTime();
        int low = 0, high = n - 1, indexBinary = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (tempSorted[mid] >= 30) {
                indexBinary = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        end = System.nanoTime();
        System.out.printf("Task C (Binary Search) - Time: %.6f ms, Index: %d\n", (end - start) / 1000000f, indexBinary);
    }

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
        int n = 1000000;

        SensorData data = new SensorData(n);

        System.out.println("===== TASK A: Linear Search =====");
        linearSearch(data.temperature, data.pressure);

        System.out.println("\n===== TASK B: Pairwise Comparison =====");
        pairwiseComparison(data.temperature, data.pressure);

        System.out.println("\n===== TASK C: Linear and Binary Search =====");
        linearAndBinarySearch(data.temperature, data.pressure);

        System.out.println("\n===== TASK D: Finding Square Root =====");
        findingSquareRoot(2147395600);
    }
}

class SensorData {
    float[] temperature;
    float[] pressure;

    SensorData(int n) {
        temperature = new float[n];
        pressure = new float[n];
        Random ran = new Random();

        for (int i = 0; i < n; i++) {
            temperature[i] = -20 + ran.nextFloat() * (50 + 20);
            pressure[i] = 950 + ran.nextFloat() * 100;
        }
    }
}
