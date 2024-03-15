public class Ticket {
    // Instance variables
    private int row, seat;
    private double price;
    private static Person person;

    // Constructor that takes input variables row,seat,price and person
    public Ticket(int row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getters for the instance variables

    public int getRow() {
        return row;
    }
    public int getSeat() {
        return seat;
    }
    public double getPrice() {
        return price;
    }

    //Method to print all the information from a ticket
    public String print(){

        return ( "Name: " + person.getName()+
      " \nSurname: " + person.getSurname()+
        " \nEmail: " + person.getEmail()+
        "\nRow: " + (row+1)+
       "\nSeat: " + (seat+1)+
      "\nPrice: " + price+"\n-----------------------------------------------------");
    }
}
