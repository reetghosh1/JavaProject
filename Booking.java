package project;
import java.io.*;
import java.util.Random;
public class Booking
{
    String fname;
    String lname;
    String email;
    int seat;
    String id;
    int occ;   //1 if occupied, 0 if unoccupied

    Booking() throws IOException
    {
        //InputStreamReader read=new InputStreamReader(System.in);
        //BufferedReader in=new BufferedReader(read);
        //System.out.println("First name of Traveller : ");
        this.fname="";
        //System.out.println("Last name of Traveller : ");
        this.lname="";
        //System.out.println("Email ID: ");
        this.email="";
        //System.out.println("Enter Seat number");
        this.seat=0;
        Random rnd=new Random();
        this.id = String.format("%04d", rnd.nextInt(10000));
        this.occ=0;
    }
}
