import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class Theatre {
    static Scanner input = new Scanner(System.in);
    private final static ArrayList<Ticket> tickets = new ArrayList<>();   //this stores all the Ticket objects, using private final because variable is only accessible within the class & tickets immutable
    static int[] row1 = new int[12];  //row1 has 12 seats
    static int[] row2 = new int[16];  //row2 has 16 seats
    static int[] row3 = new int[20];  //row3 has 20 seats
    static int[][] allRows = {row1, row2, row3};  //an array with all rows
    public static void main(String[] args) {
        System.out.println("Welcome to the New Theatre");
        //Setting all the seats to 0 at the start of program (reference : https://www.geeksforgeeks.org/arrays-fill-java-examples/)
        Arrays.fill(row1, 0);
        Arrays.fill(row2, 0);
        Arrays.fill(row3, 0);

        while(true){
            try{
                System.out.println("""
                -----------------------------------------------------
                Please select an option:
                1) Buy a ticket
                2) Print seating area
                3) Cancel ticket
                4) List available seats
                5) Save to file
                6) Load from file
                7) Print ticket information and total price
                8) Sort tickets by price
                0) Quit
                -----------------------------------------------------""");
                System.out.print("Enter option: ");
                int option = input.nextInt();
                //Enhanced switch -The main advantage of this syntax is that we don’t need a break statement to avoid the default fall-through.
                //reference : https://docs.oracle.com/en/java/javase/13/language/switch-expressions.html
                switch (option) {      //this is used to run the representing method when the corresponding number is entered
                    case 0 -> {System.out.println("Exiting program"); System.exit(0);}
                    case 1 -> buy_ticket(allRows, tickets);
                    case 2 -> print_seating_area(allRows);
                    case 3 -> cancel_ticket(allRows, tickets);
                    case 4 -> show_available(allRows);
                    case 5 -> save(allRows);
                    case 6 -> load();
                    case 7 -> show_tickets_info(tickets);
                    case 8 -> sort_tickets(tickets);
                    default -> System.out.println("Invalid option, please try again.");
                }
        } catch (InputMismatchException e){        //catch the exception when input is not an integer
                System.out.println("Integer input required.");
                input.next();
            }
        }
    }
    public static void buy_ticket(int[][] allRows, ArrayList<Ticket> tickets) {
        //Ask for the person's information
        System.out.print("Enter your name: ");
        String name = input.next();
        System.out.print("Enter your surname: ");
        String surname = input.next();
        System.out.print("Enter you email: ");
        String email = input.next();

        while (true){
            int row;   //row number to be booked
            while (true) {
                System.out.print("Enter row number (1-3): ");
                row = input.nextInt() - 1; // setting index to 0

                // checking for row number validation
                if (row < 0 || row >= 3) {
                    System.out.println("This row number does not exist. Please select 1-3.");
                } else {
                    break; // break out of the loop if input is valid
                }
            }
            int seat;   //seat number to be booked
            while (true) {
                System.out.print("Enter seat number (1-12, 1-16 or 1-20 depending on row): ");
                seat = input.nextInt() - 1; //setting index to 0

                if (seat < 0 || seat >= allRows[row].length) {     //checking for seat number validation
                    System.out.println("This seat number does not exist. Please select 1-12, 1-16 or 1-20 depending on row");
                } else if (allRows[row][seat] == 1) {
                    System.out.println("This seat is already occupied. Please check for another seat.");
                } else {
                    break;
                }
            }
            if (allRows[row][seat] == 0) { //marking seat as occupied
                allRows[row][seat] = 1;
                System.out.println("""
                    -----------------------------------------------------
                    Price Details:
                    Row 1 = £20.0
                    Row 2 = £25.0
                    Row 3 = £30.0
                    -----------------------------------------------------""");
                double price;
                while (true) {
                    System.out.print("Enter price: £");
                    try {
                        price = input.nextDouble();
                    } catch (InputMismatchException e) {      //catch the exception when input is not a double
                        System.out.println("Invalid price format. Please enter a valid price for the selected row.");
                        input.nextLine();    //consume the invalid input to avoid an infinite loop
                        continue;
                    }
                    System.out.println("-----------------------------------------------------");
                    double[] rowPrices = {20.0, 25.0, 30.0}; //an array of prices for each row
                    double correctPrice = rowPrices[row];
                    if (price != correctPrice) {      //checking for price validation for selected row
                        System.out.println("Invalid price. Please enter the correct price for the selected row.");
                    } else {
                        break;
                    }
                }
                Person person = new Person(name, surname, email);    //Create a new person object with the specified name, surname, and email.
                Ticket ticket = new Ticket(row, seat, price, person);     //create a new ticket object with the given information
                tickets.add(ticket);      //Add the ticket to the array list of tickets
                System.out.println("Ticket of Seat [" + (row + 1) + "]-[" + (seat + 1) + "] bought successfully.");
            }
            //Ask the user if they want to buy more tickets
            while (true) {
                System.out.print("Do you want to buy more tickets? (yes or no): ");
                String answer = input.next();
                if (answer.equals("yes")) {
                    break;   //exit the loop if the answer is "yes"
                } else if (answer.equals("no")) {
                    return;   //exit the method if the answer is "no"
                } else {
                    System.out.println("Invalid input. Please enter either 'yes' or 'no'.");
                }
            }
        }
    }
    public static void print_seating_area(int[][] allRows) {
        System.out.print("""
                      ***********
                      *  STAGE  *
                      ***********
                """);
        for (int i = 0; i < allRows.length; i++) {
            for (int j = 2*(2-i); j >= 0 ; j--){   //this creates a triangular shape with progressively decreasing spaces for consecutive rows
                System.out.print(" ");
            }
            for (int j = 0; j < allRows[i].length; j++) {
                int middle = allRows[i].length/2;
                if (j == middle){
                    System.out.print("\t");
                }
                if (allRows[i][j] == 1) {
                    System.out.print("X");    //displays "X" for sold seats
                } else {
                    System.out.print("O");    //displays "O" for available seats
                }
            }
            System.out.println();
        }
    }
    public static void cancel_ticket(int[][] allRows,ArrayList<Ticket> tickets) {
        int rowToCancel;   //row number to be cancelled
        while (true){
            System.out.print("Enter row number to be cancelled: ");
            rowToCancel = input.nextInt() - 1; //setting index to 0

            //checking for row number validation
            if (rowToCancel < 0 || rowToCancel >= 3) {
                System.out.println("Invalid row number. Please try again.");
            } else {
                break;
            }
        }
        int seatToCancel;   //seat number to be cancelled
        while (true){
            System.out.print("Enter seat number to be cancelled: ");
            seatToCancel = input.nextInt() - 1;  //setting index to  0

            //checking for seat number validation
            if (seatToCancel < 0 || seatToCancel >= allRows[rowToCancel].length) {
                System.out.println("Invalid seat number. Please try again.");
            }else {
                break;
            }
        }
        if (allRows[rowToCancel][seatToCancel] == 0) {   //check if the specified seat is already available
            System.out.println("Ticket of seat [" + (rowToCancel + 1) + "][" + (seatToCancel + 1) +"] is not found, this seat is already available.");
        }
        //marking seat as cancelled
        if (allRows[rowToCancel][seatToCancel] == 1){
            allRows[rowToCancel][seatToCancel] = 0;
            for (int i = 0; i < tickets.size(); i++) {
                Ticket ticket = tickets.get(i);
                if ((ticket.getRow() == rowToCancel) && (ticket.getSeat() == seatToCancel)) {
                    tickets.remove(i);
                    System.out.println("Ticket cancelled successfully of the seat [" + (rowToCancel + 1) + "]-[" + (seatToCancel + 1) + "].");
                }
            }
        }
    }
    public static void show_available(int[][] allRows) {
        for (int i = 0; i < allRows.length; i++) {
            System.out.print("Seats available in row " + (i + 1) + ": ");
            for (int j = 0; j < allRows[i].length; j++) {
                if (allRows[i][j] == 0) {    //checks for available seat
                    System.out.print((j + 1) + ",");
                }
            }
            System.out.println("\b.");  //"\b" is used to remove the trailing comma from the end of the list.
        }
    }
    public static void save(int[][] allRows) {
        try {
            FileWriter writer = new FileWriter("data_file.txt");  //this will overwrite any existing data in the file.
            for (int i = 0; i < allRows.length; i++) {
                for (int j = 0; j < allRows[i].length; j++) {
                    if (allRows[i][j] == 0) {    //checks for available seat
                        writer.write("0" + " ");
                    } else {      //checks for booked seats
                        writer.write("1" + " ");
                    }
                }
                writer.write("\n");
            }
            writer.close();    //close the file writer
            System.out.println("Data is saved in the file data_file.txt successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the data: ");
            e.printStackTrace();  //for debugging purposes
        }
    }
    public static void load() {
        try {
            File file = new File("data_file.txt");
            Scanner reader = new Scanner(file);  //this will read from the file
            for (int i = 0; i < allRows.length; i++){
                for (int j = 0; j < allRows[i].length; j++){
                    allRows[i][j] = reader.nextInt();
                }
            }
            // Print the loaded data as arrays
            System.out.println("Loaded data:");
            for (int i = 0; i < allRows.length; i++) {
                System.out.println(Arrays.toString(allRows[i]));   //print the contents of each row as a string
            }
            System.out.println("Successfully loaded");
            reader.close();    //Close the scanner object "reader"

        } catch (IOException e) {
            System.out.println("An error occurred while loading the data: ");
            e.printStackTrace();
        }
    }
    public static void show_tickets_info(ArrayList<Ticket> tickets) {
        double totalPrice = 0.0;   //total price of all booked tickets
        System.out.println("List of Tickets:");
        System.out.println("-----------------------------------------------------");
        for (Ticket ticket : tickets) {
            System.out.println(ticket.print());
            totalPrice += ticket.getPrice();
        }
        System.out.println("Total price of all tickets: £" + totalPrice);
    }
    public static void sort_tickets(ArrayList<Ticket> tickets) {
        //used the selection sort algorithm to sort in ascending order of price
        int n = tickets.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (tickets.get(j).getPrice() < tickets.get(minIndex).getPrice()) {
                    minIndex = j;
                }
            }
            Ticket temp_ticket = tickets.get(i);
            tickets.set(i, tickets.get(minIndex));
            tickets.set(minIndex, temp_ticket);
        }
        System.out.println("List of sorted tickets by price:" + "\n-----------------------------------------------------");
        for (Ticket ticket : tickets) {
            System.out.println(ticket.print());
        }
    }
}






