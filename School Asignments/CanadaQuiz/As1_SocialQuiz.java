import java.util.Scanner;

public class As1_SocialQuiz {
    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String[] Answers = { "Nunavut", "13", "Thirteen", "450", "5", "Ottawa", "Alberta", "Saskatchewan",
                    "Manitoba" };
            int Counter = 0;

            System.out.println("Enter your name: ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("exit")) {
                scanner.close();
                break;
            }

            System.out.println("Canadian Knowledge Quiz");

            // Q1
            System.out.println("Iqaluit is the capital of which territory?");
            String Ans = scanner.nextLine();
            if (Ans.equalsIgnoreCase(Answers[0])) {
                System.out.println("Correct");
                Counter++;
            } else {
                System.out.println("Incorrect");
            }
            System.out.println("Current Score:" + Counter + "/5");

            // Q2
            System.out.println("How many provinces and territories are there in Canada?");
            Ans = scanner.nextLine();
            if (Ans.toLowerCase().equals(Answers[2].toLowerCase()) || Ans.equals(Answers[1])) {
                System.out.println("Correct");
                Counter++;
            } else {
                System.out.println("Incorrect");
            }
            System.out.println("Current Score:" + Counter + "/5");

            // Q3
            System.out.println("What was Canada's 2017 GDP per capita in US dollars?");
            Ans = scanner.nextLine();
            int digits = 0;
            int tempAns = Integer.parseInt(Ans);
            for (int i = 0; i < 7; i++) {
                tempAns /= 10;
                digits += 1;
                if (tempAns == 0) {
                    break;
                }
            }
            // allows any number that is 5 digits and contains 450, so 45000, 45032 45099
            if (Ans.contains(Answers[3]) && Integer.toString(digits).equals(Answers[4])) {
                System.out.println("Correct");
                Counter++;
            } else {
                System.out.println("Incorrect");
            }
            System.out.println("Current Score:" + Counter + "/5");

            // Q4
            System.out.println("What is the capital of Canada?");
            Ans = scanner.nextLine();

            // allows answers that include both ontario and ottawa, or just ottawa
            if (Ans.toLowerCase().startsWith("ontario") && Ans.toLowerCase().endsWith(Answers[5].toLowerCase())
                    || Ans.equalsIgnoreCase(Answers[5])) {
                System.out.println("Correct");
                Counter++;
            } else {
                System.out.println("Incorrect");
            }
            System.out.println("Current Score:" + Counter + "/5");

            // Q5
            System.out.println("Name one province that is in the 'prairies'.");
            Ans = scanner.nextLine();
            for (int i = 6; i < Answers.length; i++) {
                if (Ans.equalsIgnoreCase(Answers[i])) {
                    System.out.println("Correct");
                    Counter++;
                    break;
                } else if (i == Answers.length) {
                    System.out.println("Incorrect");
                }
            }
            System.out.println("Current Score:" + Counter + "/5");

            // Quiz done
            System.out.println(name + " got " + Counter + "/5");
            System.out.println("This test was marked by Oliver Moser");
            System.out.println("Press enter to continue");

            Ans = scanner.nextLine();
        }
    }
}
