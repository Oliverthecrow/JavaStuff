
import java.time.LocalDate;
import java.util.Scanner;

class As3_Airfare {
    public static void main(String[] args) {
        int numTravelers = 0;
        boolean isMember;
        String city;
        String ticketclass;
        Scanner scanner = new Scanner(System.in);
        String day;
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();

        double eachTicket = 0;

        System.out.println("How many travelers in your party?");
        numTravelers = Integer.parseInt(scanner.nextLine());

        System.out.println("Are you a member of CoupAir? (true or false)");
        isMember = Boolean.parseBoolean(scanner.nextLine());

        System.out.println("Where are you travelling?");
        city = scanner.nextLine();

        System.out.println("What class of ticket (first, business, economy)");
        ticketclass = scanner.nextLine();

        System.out.println("What day is the flight");
        day = scanner.nextLine();

        eachTicket = destinationAmount(city);
        eachTicket += classAmount(ticketclass, numTravelers);
        eachTicket += dayAmount(day);
        eachTicket -= isMember(isMember, scanner);
        eachTicket *= datePriceEffector(month);
        System.out.println("Each ticket is $" + eachTicket);
    }// run

    // EXTRA OPTION
    public static double datePriceEffector(int month) {
        if (month > 3 || month < 9) {
            return 1.25; // greater price because of winter travel
        } else {
            return 1.0; // no change
        }
    }

    public static double isMember(boolean member, Scanner scanner) {
        if (member) {
            return 50.0;
        } else {
            System.out.println("Would you like to become a member to save 50$ on flights (yes or no)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                System.out.println("Thank you");
                return 50.0;
            } else {
                return 0.0;
            }
        }
    }

    public static double dayAmount(String day) {
        if (day.equalsIgnoreCase("saturday") || day.equalsIgnoreCase("sunday")) {
            return 100.0;
        } else if (day.equalsIgnoreCase("friday")) {
            return 80.0;
        } else {
            return 40.0;
        }
    }

    public static double classAmount(String ticketclass, int numTravelers) {
        if (ticketclass.equalsIgnoreCase("economy")) {
            return 100.0 * numTravelers;
        } else if (ticketclass.equalsIgnoreCase("business")) {
            return 400.0 * numTravelers;
        } else {
            return 700.0 * numTravelers;
        }
    }// classamount

    public static double destinationAmount(String city) {
        String[] destinations = { "Toronto", "Cancun", "Calgary", "Vancouver", "Saskatoon", "Winnipeg", "Montreal" };
        // distances from edmonton to "destination"
        int[] distances = { 3467, 6201, 299, 1159, 560, 1000, 3300 };
        double price = 50 + (0.1 * distances[search(destinations, city)]);
        System.out.println("Destination amount " + price);
        return price;
    }// destinationAmount

    public static int search(String[] arr, String searchTerm) {
        for (int i = 0; i < arr.length; i++) {
            if (searchTerm.equalsIgnoreCase(arr[i])) {
                return i;
            }
        }
        return -1;
    }// search
}// class