import java.util.Scanner;

public class As1_99Bottles {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String preferedDrink = "";
        String color = "\u001B[96m";

        System.out.println(color + "How old are you:");
        int age = Integer.parseInt(scanner.nextLine());

        if (age >= 18) {
            System.out.println("What drink do you prefer between Pop or Beer:");
            preferedDrink = scanner.nextLine();
        }

        scanner.close();

        if (age < 10) {
            bottlesOnTheWall("milk");
        } else if (age >= 10 && age <= 17) {
            bottlesOnTheWall("pop");
        } else if (preferedDrink.equalsIgnoreCase("beer")) {
            bottlesOnTheWall("beer");
        } else if (preferedDrink.equalsIgnoreCase("pop")) {
            bottlesOnTheWall("pop");
        }
        // if nothing is entered plays milk on the wall
        else {
            bottlesOnTheWall("milk");
        }
    }

    public static void bottlesOnTheWall(String bottleType) {
        String colorReset = "\u001B[0m";
        for (int i = 99; i > -1; i--) {
            if (i != 1 && i >= 99) {
                System.out.println(i + " bottles of " + bottleType + " on the wall");
                System.out.println(i + " bottles on the wall");
                System.out.println("If one of those bottles were to fall,");
            } else if (i != 1) {
                System.out.println(i + " bottles of " + bottleType + " on the wall.");
                if (i != 0) {
                    System.out.println(i + " bottles on the wall");
                    System.out.println("If one of those bottles were to fall,");
                }
            } else {
                System.out.println(i + " bottle of " + bottleType + " on the wall");
                System.out.println(i + " bottle on the wall");
                System.out.println("If one of those bottles were to fall,");
            }
        }
        System.out.println(colorReset);
    }
}