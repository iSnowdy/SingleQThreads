package Build;

public class Cashier implements Runnable {
    private final int ID;
    private final Supermarket supermarket;

    public Cashier(int ID, Supermarket supermarket) {
        this.ID = ID;
        this.supermarket = supermarket;
    }

    @Override
    public void run() {
        while (!(Thread.currentThread().isInterrupted())) {
            try {
                // Withdraw a Client FROM the Queue
                Client client = supermarket.getClientQ().removeFromQueue();
                if (client != null) {
                    supermarket.decreaseClientCounter();
                    System.out.println(Colours.ANSI_GREEN + "Cashier " + this.ID + " is processing " + client.getName() + Colours.ANSI_RESET);
                    // Time in line
                    //Thread.sleep(2000);
                    String name = client.getName();
                    int expenses = client.getExpenses();
                    Thread.sleep(client.getTimeInLine());
                    supermarket.addClient(name, expenses);
                    System.out.println(Colours.ANSI_RED + "Client " + client.getID() + " has paid " + expenses + "€" + Colours.ANSI_RESET);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Colours.ANSI_YELLOW + "Cashier " + this.ID + " is closing");
            }
        }
    }
}