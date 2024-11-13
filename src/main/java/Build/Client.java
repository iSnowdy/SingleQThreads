package Build;

import java.util.Random;

public class Client implements Runnable {
    private final int ID;
    private final Supermarket supermarket;
    private final Random random = new Random();
    private final int amountOfProducts;


    public Client(int ID, Supermarket supermarket) {
        this.ID = ID;
        this.supermarket = supermarket;
        this.amountOfProducts = amountOfProducts();
    }

    @Override
    public void run() {
        try {
            // 1 to 3 seconds scaled to the amount of products bought
            // or 1 second if they did not buy anything
            int shoppingTime = (random.nextInt(2000) + 1000) + this.amountOfProducts * 1000;
            Thread.sleep(shoppingTime);

            if (this.amountOfProducts > 0) {
                System.out.println("Client " + this.ID + " is ready to pay!");
                supermarket.getClientQ().addToQueue(this);
            } else {
                System.out.println(Colours.ANSI_PURPLE + "Client " + this.ID + " did not buy any products!" + Colours.ANSI_RESET);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // Each product will add an extra second to the shopping time
    private int amountOfProducts() {
        // 0 to 20 products
        return random.nextInt(21);
    }

    public int getID() {
        return this.ID;
    }
    public String getName() {
        return "Client " + this.ID;
    }

    // Expenses are scaled by the amount of products bought
    public int getExpenses() {
        // 10 to 100€ scaled by the amount of products for the price of each one
        return amountOfProducts() * random.nextInt(101) + 10;
    }

    public int getTimeInLine() {
        return amountOfProducts() * random.nextInt(101) + 10;
    }
}