import java.util.ArrayList;

public class MainConcurrency {

    public static final int THREADS_NUMBER = 10_000;
    private static int counter;
//    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ", " +
                    Thread.currentThread().getState());
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName() + ", " +
//                        Thread.currentThread().getState());
//                }
//
//            private void inc() {
//                synchronized (this) {
//                    counter++;
//                }
//            }
//        });

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
//                   inc();
                   mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

//        Thread.sleep(500);
        System.out.println(counter);

//        new MainConcurrency().inc();
    }

//    private static void inc() {
//    private static synchronized void inc() {
//    private void inc() {
    private synchronized void inc() {

//        synchronized (LOCK) {
//        synchronized (MainConcurrency.class) {
        double a = Math.sin(13.);
//        try {
//            synchronized (this) {
                counter++;
//                wait();
//                notify();
//                notifyAll();
//                readFile
//                ...
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}