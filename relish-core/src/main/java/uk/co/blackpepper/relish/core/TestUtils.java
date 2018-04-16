package uk.co.blackpepper.relish.core;

/**
 * The type Test utils.
 */
public class TestUtils {
    private TestUtils() {
        // Should not be instantiated
    }

    /**
     * Attempt.
     *
     * @param runnable the runnable
     * @param pause    the pause
     * @param times    the times
     */
    public static void attempt(Runnable runnable, int pause, int times) {
        boolean succeeded = false;
        Throwable e = null;
        for (int i = 0; (i < times) && !succeeded; i++) {
            try {
                runnable.run();
                succeeded = true;
            } catch (Exception e1) {
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                }
                e = e1;
            }
        }
        if (!succeeded) {
            assert e != null;
            throw new IllegalStateException("Failed after several retries: " + e.getMessage(), e);
        }
    }
}
