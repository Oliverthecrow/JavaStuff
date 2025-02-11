package Warehouse;

import java.util.Random;
import java.util.Scanner;

public class As2_Warehouse {
    public static void main(String[] args) {
        // starts the program
        SaleManagment();
    }

    public static void SaleManagment() {
        String[] randomSaleArray = { "Guitar", "Flute", "Banjo", "Drums" };
        // Generates a random int for the index of the above array for a random sale
        // item every time the code is ran
        Random rand = new Random();
        int saleIndex = rand.nextInt(4);
        String itemOnSale = randomSaleArray[saleIndex];

        System.out.println("Oliver's Instrument Inventory Managment System");
        System.out.println("This Months Sale: " + itemOnSale);

        // uses a single scanner to define variables via the user's console
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter type of product EX: 'Guitar'");
        String productType = scanner.nextLine();

        if (productType.equalsIgnoreCase(itemOnSale)) {
            System.out.println("This product is on sale.");
        }

        System.out.println("Enter the product's cost: ");
        /*
         * uses parsedouble and parseint later on, to avoid an error
         * where a nextLine(), thinks the previous answer was inputed by the user
         * which causes it to skip to the next scanner without a real answer
         */
        double cost = Double.parseDouble(scanner.nextLine());

        System.out.println("Enter the brand of the product: ");
        String brandName = scanner.nextLine();

        System.out.println("Enter amount ordered: ");
        int numberInShipment = Integer.parseInt(scanner.nextLine());

        // extra SOUT's add space for easier reading of the summary
        System.out.println();
        System.out.println("Shipment Summary: ");
        System.out.println("Product: " + productType);
        System.out.println("Total cost of shipment: " + cost * numberInShipment);
        System.out.println("This shipment included " + numberInShipment + " from " + brandName);
        if (cost < 50) {
            SaleCost(2, cost, productType, numberInShipment);
        } else {
            SaleCost(1.5, cost, productType, numberInShipment);
        }
        System.out.println();

        if (numberInShipment > 5 && numberInShipment < 10) {
            System.out.println("Back order warning - Watch inventory level carefully.");
        } else if (numberInShipment <= 5) {
            System.out.println("Back order warning - Order from different supplier.");
        }

        System.out.println("Do you want to do another shipment, if so just enter 'y'");
        String answer = scanner.nextLine();

        // reruns function when y is entered
        if (answer.equals("y") || answer.equals("Y")) {
            // RECURSION WOOOOOOOOOOOOOOO
            SaleManagment();
        }
    }

    public static void SaleCost(double markUp, double cost, String productType, int numberInShipment) {
        double SaleCost = cost * markUp;
        System.out.println("Each " + productType + " sells for $" + SaleCost);
        System.out.println("Total Sell Value of Shipment $" + numberInShipment * SaleCost);
    }
}