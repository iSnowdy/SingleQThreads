package Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Supermarket {
    private final String NAME;
    private HashMap<String, Integer> results = new HashMap<>();
    private final ClientQ clientQ = new ClientQ();
    private final Cron cron;

    public int clientsLeft;
    private boolean isFinished = false;

    public Supermarket(String name, Cron cron) {
        this.NAME = name;
        this.cron = cron;
    }

    public void simulate(int cashiers, int clients) {
        clientsLeft = clients; // Extract
        List<Thread> cashierThreads = new ArrayList<>();
        List<Thread> clientThreads = new ArrayList<>();
        // Cashier Threads
        for (int i = 0; i < cashiers; i++) {
            Thread cashier = new Thread(new Cashier(i + 1, this));
            cashierThreads.add(cashier);
            cashier.start();
        }
        // Client Threads
        for (int i = 0; i < clients; i++) {
            Thread client = new Thread(new Client(i + 1, this));
            clientThreads.add(client);
            client.start();
        }

        for (Thread thread : clientThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Now that all clients have been processed, print result
        printResults();
        for (Thread thread : cashierThreads) {
            // Done with the cashiers because we are closing!
            thread.interrupt();
        }
        this.cron.shutDown();
    }

    public synchronized void addClient(String client, int expenses) {
        this.results.put(client, expenses);
    }

    public synchronized void decreaseClientCounter() {
        this.clientsLeft--;
        System.out.println("Clients left inside the supermarket: " + this.clientsLeft);
    }

    public void printResults() {
        int totalEarnings = 0;
        System.out.println("---------------------------");
        for (String client: this.results.keySet()) {
            totalEarnings += this.results.get(client);
            System.out.println(client + " spent: " + this.results.get(client) + "€");
        }
        System.out.println("***************************");
        System.out.println("Total revenue: " + totalEarnings + "€");
        System.out.println("---------------------------");
    }

    public String getNAME() {
        return NAME;
    }

    public ClientQ getClientQ() {
        return this.clientQ;
    }
}