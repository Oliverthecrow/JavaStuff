import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class As3_BinarySearchStrings {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> seaCreatures = new ArrayList<>(Arrays.asList("Anglerfish", "ClownFish", "Crab", "Dolphin",
                "Eel", "Jellyfish", "Lobster", "Octopus", "Seahorse", "Whale"));
        ArrayList<String> seaCategory = new ArrayList<>(
                Arrays.asList("Fish", "Fish", "Crustacean", "Mammal", "Fish",
                        "Invertebrate", "Crustacean", "Mollusk", "Fish", "Mammal"));
        while (true) {

            System.out.println(seaCreatures);
            System.out.println("Please choose one sea creature from this list.");

            String chosenCreature = scanner.nextLine();
            if (chosenCreature.equalsIgnoreCase("exit")) {
                scanner.close();
                break;
            }
            int index = Searcher(seaCreatures, chosenCreature);
            if (index >= 0) {
                // this checks to see if the firstletter of seacreatures is also contained
                // within a string containing all vowels
                int vowelCheck = "aieouAIEOU".indexOf(seaCreatures.get(index).charAt(0));
                if (vowelCheck >= 0) {
                    System.out.println(
                            "Found it. An " + seaCreatures.get(index) + " is a " + seaCategory.get(index) + ".");
                } else {
                    System.out.println(
                            "Found it. A " + seaCreatures.get(index) + " is a " + seaCategory.get(index) + ".");
                }
            } else {
                System.out.println("Sorry that animal is not in the list.");
            }

            System.out.println("");
        }
    }

    public static int Searcher(ArrayList<String> seaCreatures, String chosenCreature) {
        for (int i = 0; i < seaCreatures.size(); i++) {
            if (chosenCreature.equalsIgnoreCase(seaCreatures.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
