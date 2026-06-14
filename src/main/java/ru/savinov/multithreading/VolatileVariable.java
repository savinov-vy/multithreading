package ru.savinov.multithreading;

public class VolatileVariable {

    private static volatile int value;

    static class IncrementValue extends Thread {
        @Override
        public void run() {
            while (value < 5) {
                System.out.println("value incrementing: " + ++value);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    static class ChangeListener extends Thread {
        int tmp = 0;

        @Override
        public void run() {
            while (tmp < 5) {
                if (tmp != value) {
                    System.out.println("new value: " + value);
                    tmp = value;
                }
            }
        }
    }

    public static void main(String[] args) {
        ChangeListener incrementListener = new ChangeListener();
        IncrementValue incrementValue = new IncrementValue();
        incrementListener.start();
        incrementValue.start();
        try {
            incrementListener.join();
            incrementValue.join();
        } catch (Exception e) {
        }
    }
}
