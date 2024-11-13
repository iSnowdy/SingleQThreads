package Run;

import Build.Cron;
import Build.Supermarket;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        args = new String[]{"5", "5"}; // Override args
        if (!validateArgs(args)) {
            System.err.println("Invalid CLI");
            System.exit(1);
        }

        Cron cron = new Cron();
        cron.startTimer();

        int N = Integer.parseInt(args[0]); // Cashiers
        int M = Integer.parseInt(args[1]); // Clients

        Supermarket supermarket = new Supermarket("Eroski", cron);
        supermarket.simulate(N, M);
    }

    private static boolean validateArgs(String[] args) {
        if (args.length != 2) return false;
        if (!(args[0].matches("^\\d+$") && args[1].matches("^\\d+$"))) return false;
        return true;
    }
}