package Build;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cron {
    private final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);
    public int counter;

    public Cron() {
        this.counter = 0;
    }

    public void startTimer() {
        System.out.println(Colours.ANSI_CYAN + "Starting Timer..."+ Colours.ANSI_RESET);
        final Runnable RUNNABLE = new Runnable() {
            int count = getCounter();
            @Override
            public void run() {
                count++;
                setCounter(count);
            }
        };
        SCHEDULER.scheduleAtFixedRate(RUNNABLE,0, 1, TimeUnit.SECONDS);
    }

    private int getCounter() {
        return this.counter;
    }
    private void setCounter(int a) {
        this.counter = a;
    }

    public void shutDown() {
        SCHEDULER.shutdown();
        System.out.println(Colours.ANSI_CYAN + "Stopping Timer...");
        System.out.println("Execution time: " + getCounter() + "s" + Colours.ANSI_RESET);
    }
}