package com.aspenshore.relish.core;

public class TestUtils {
    public static void attempt(Runnable runnable, int pause, int times) {
        boolean succeeded = false;
        Throwable e = null;
        for (int i = 0; (i < times) && !succeeded; i++) {
            try {
                runnable.run();
                succeeded = true;
            } catch (Throwable e1) {
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                e = e1;
            }
        }
        if (!succeeded) {
            throw new RuntimeException("Failed after several retries: " + e.getMessage(), e);
        }
    }
}
