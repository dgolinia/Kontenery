public class Train {
    public static final Train TRAIN = new Train();
    public static final Object LOCK = new Object();
    private static boolean isLocked;

    public int maxTrainQuantityOfContainers;

    private Train() {
        maxTrainQuantityOfContainers = 10;
    }

    private Thread create30sThread() {
        return new Thread(() -> {
            synchronized (LOCK) {
                try {
                    Thread.sleep(30_000);
                    maxTrainQuantityOfContainers = 10;
                    isLocked = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadTrain() {
        if (maxTrainQuantityOfContainers <= 1) {
            System.out.println("Wait for 30s");
            create30sThread().start();
            isLocked = true;
        } else {
            maxTrainQuantityOfContainers--;
        }
    }

    public static boolean isLocked() {
        return isLocked;
    }
}