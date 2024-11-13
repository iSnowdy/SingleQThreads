package Build;

import java.util.LinkedList;
import java.util.Queue;

public class ClientQ {
    private final Queue<Client> queue = new LinkedList<Client>();

    // Adds a Client TO the Queue
    // Must be Thread-Safe because multiple threads will access this method
    public synchronized void addToQueue(Client client) {
        queue.add(client);
        System.out.println(client.getName() + " added to the queue.\n" +
                "Queue size now is " + queue.size());
        notifyAll();
    }
    // Removes a Client FROM the Queue
    public synchronized Client removeFromQueue() {
        while (queue.isEmpty()) {
            try {
                wait(); // Make sure we have clients here
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
        // Removes the Client
        Client client = queue.remove();
        System.out.println(client.getName() + " removed from the queue.\n" +
                "Queue size now is " + queue.size());
        return client;
    }
}