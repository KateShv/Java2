package lesson5;

import java.util.Arrays;

public class Main {

    private static int size = 10000000;
    private static int half = size / 2;

    private static void calc(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
        }
    }

    private static void calcWithMyThread(float[] arr) {
        float[] temp1 = new float[half];
        float[] temp2 = new float[half];
        System.arraycopy(arr, 0, temp1, 0, half);
        System.arraycopy(arr, half, temp2, 0, half);
        try {
            new MyThread(temp1, 0).join();
            new MyThread(temp2, half).join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Поток был кем-то прерван:\n" + e);
            //или interrupt(); если все же нужно завершить
        }
        System.arraycopy(temp1, 0, arr, 0, half);
        System.arraycopy(temp2, 0, arr, half, half);
    }

    public static void main(String[] args) {

        float[] arr1 = new float[size];
        Arrays.fill(arr1, 1f);
        long start = System.nanoTime();
        calc(arr1);
        long deltaTime = System.nanoTime() - start;
        System.out.println("В один поток, время выполнения: " + deltaTime * 0.000000001f + " сек.");

        float[] arr2 = new float[size];
        Arrays.fill(arr2, 1f);
        start = System.nanoTime();
        calcWithMyThread(arr2);
        deltaTime = System.nanoTime() - start;
        System.out.println("В два потока, время выполнения: " + deltaTime * 0.000000001f + " сек.");

        //временно, для проверки корректно ли подсчитывается обоими способами
        System.out.println(Arrays.equals(arr1, arr2));

        //примерная разница
        //В один поток, время выполнения: 1.6693492 сек.
        //В два потока, время выполнения: 1.5906918 сек.
        //в итоге в два потока не особо то быстрее

    }

}
