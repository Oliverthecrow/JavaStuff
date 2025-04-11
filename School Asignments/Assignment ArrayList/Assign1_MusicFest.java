
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Assign1_MusicFest {
    public static void main(String[] args) {
        ArrayList<String> comptetitors = new ArrayList<>(Arrays.asList("John", "Tim", "Cloobert",
                "Johnathan 2", "Crazy Dave", "Crazy Steve", "Steve", "Yuh", "John 3", "Crazy Steve 2"));
        ArrayList<String> genres = new ArrayList<>(Arrays.asList("Rock", "Pop", "Classical", "Rock", "Metal",
                "Rock", "Pop", "Classical", "Metal", "Yodeling"));
        ArrayList<Integer> scores = new ArrayList<>(10);
        // randomly asigns a random score from 0 to 100 for the scores arraylist
        for (int i = 0; i < 10; i++) {
            int random = (int) (Math.random() * 101);
            scores.add(random);
        }

        // prints out data of each competitor
        System.out.printf("%-20s %-15s %-5s%n", "Competitor:", "Genre:", "Score:");
        for (int i = 0; i < comptetitors.size(); i++) {
            System.out.printf("%-20s %-15s %-5d%n",
                    comptetitors.get(i), genres.get(i), scores.get(i));
        }

        while (true) {
            // seperation println
            System.out.println("");

            // A
            System.out.println("Competitors who played Classical:");
            for (int i = 0; i < genres.size(); i++) {
                if (genres.get(i).equalsIgnoreCase("classical")) {
                    System.out.println(comptetitors.get(i));
                }
            }

            // seperation println
            System.out.println("");

            // B
            int total = 0;
            for (int i = 0; i < scores.size(); i++) {
                total += scores.get(i);
            }
            total /= scores.size();
            System.out.println("Average score for all competitors: " + total);

            // seperation println
            System.out.println("");

            // C
            System.out.println("Competitors with a score greater than 80:");
            System.out.printf("%-20s %-5s%n", "Competitor:", "Score:");
            int count = 0;
            for (int i = 0; i < comptetitors.size(); i++) {
                if (scores.get(i) >= 80) {
                    System.out.printf("%-20s %-5d%n", comptetitors.get(i), scores.get(i));
                    count++;
                }
            }
            if (count == 0) {
                System.out.println("No competitor got a score above 80");
            }

            // seperation println
            System.out.println("");

            // D
            // numbers can only be positive so this works
            int maxScore = -1;
            for (int i = 0; i < comptetitors.size(); i++) {
                if (scores.get(i) > maxScore) {
                    maxScore = scores.get(i);
                }
            }
            ArrayList<Integer> indexArray = new ArrayList<>();
            for (int i = 0; i < scores.size(); i++) {
                if (maxScore == scores.get(i)) {
                    indexArray.add(i);
                }
            }
            System.out.println("Highest Score");
            System.out.printf("%-20s %-5s%n", "Competitor:", "Score:");
            for (int i = 0; i < indexArray.size(); i++) {
                if (scores.get(indexArray.get(i)) == maxScore) {
                    System.out.printf("%-20s %-5d%n", comptetitors.get(indexArray.get(i)),
                            scores.get(indexArray.get(i)));
                }
            }
            // this prints out all the people with the highest score, just in case multiple
            // people have the highest score

            Scanner scanner = new Scanner(System.in);
            System.out.println("What Competitors score do you wish to change");
            String name = scanner.nextLine();
            // ends loop if name entered is exit
            if (name.equalsIgnoreCase("exit")) {
                scanner.close();
                break;
            }

            int nameIndex = 0; // will not cause a problem cause we are assuming that the user WILL input a
                               // correct
                               // name
            for (int i = 0; i < comptetitors.size(); i++) {
                if (name.equalsIgnoreCase(comptetitors.get(i))) {
                    nameIndex = i;
                }
            }

            System.out.println("What Score should " + name + " have now");
            int newScore = Integer.parseInt(scanner.nextLine());

            scores.set(nameIndex, newScore);
            System.out.println("*****************Updating Stats*****************");
        }
    }
}
