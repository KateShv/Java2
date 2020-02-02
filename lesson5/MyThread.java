package lesson5;

public class MyThread extends Thread {

    private float[] arr;
    private int shift;

    MyThread(float[] arr, int shift) {
        this.arr = arr;
        this.shift = shift;
        start();
    }

    @Override
    public void run() {
        for (int i = 0; i < arr.length; i++)
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i + shift) / 5f) * Math.cos(0.2f + (i + shift) / 5f) * Math.cos(0.4f + (i + shift) / 2f));
    }

}