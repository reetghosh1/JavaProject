package project;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
//import javax.activation.*;

public class Travel
{
    static Booking b[]=new Booking[100];

    static void progress()
    {
        char[] animationChars = new char[]{'|', '/', '-', '\\'};

        for (int i = 0; i <= 100; i++) {
            System.out.print("Processing: " + i + "% " + animationChars[i % 4] + "\r");

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Processing: Done!          ");
    }

    static void send(String e, String a,int x,String p,String q,String f)
    {
        final String username = "ticketproject1@gmail.com";
        final String password = "reet7565";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator()
                {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(username, password);
                    }
                }
                );

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(e)
            );
            message.setSubject("Your Booking with PNR: "+a);
            message.setText("Dear "+p+" "+q+"\n"+"Your booking with PNR: "+a+" has been "+f+"\nSeat no: "+x);

            Transport.send(message);

            //progress();

            System.out.println("Email Sent!!");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    static void addbook() throws IOException
    {
        InputStreamReader read=new InputStreamReader(System.in);
        BufferedReader in=new BufferedReader(read);
        try {
            System.out.println("Choose seat number (between 1 and 100)");
            int x = Integer.parseInt(in.readLine());
            System.out.println();
            if (x < 1 || x > 100) {
                throw new SeatException();
            }
            else if(b[x-1].occ==0)
            {
                int y=x-1;
                System.out.println("Enter first name of Traveller: ");
                b[y].fname=in.readLine();
                System.out.println();
                System.out.println("Enter last name of Traveller: ");
                b[y].lname=in.readLine();
                System.out.println();
                System.out.println("Enter email ID: ");
                b[y].email=in.readLine();
                System.out.println();
                b[y].occ=1;
                progress();
                System.out.println("Booking Confirmed!!");
                System.out.println();
                System.out.println("Your PNR is: "+b[y].id);
                System.out.println();

                send(b[y].email,b[y].id,b[y].seat,b[y].fname,b[y].lname,"confirmed");

            }
            else
            {
                throw new Exception();
            }
        }
        catch(SeatException e)
        {
            System.out.println(e);
        }
        catch(NumberFormatException e)
        {
            System.out.println(e);
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
    }

    static void retrieve(String pnr,String l)
    {
        int k=0;
        for(int i=0;i<100;i++)
        {
            if(((b[i].lname).equalsIgnoreCase(l))&&(b[i].id).equals(pnr))
            {
                if(b[i].occ==1)
                {
                    k=1;
                    System.out.println("Booking found!");
                    System.out.println();
                    System.out.println(b[i].fname + " " + b[i].lname);
                    System.out.println("Seat : " + b[i].seat);
                    System.out.println("PNR : " + b[i].id);
                }
                /*else
                {
                    System.out.println("Booking not Found!!\n");
                    break;
                }*/
            }
            /*else
            {
                System.out.println("Booking not Found!!\n");
                break;
            }*/
        }
        if(k==0)
        {
            System.out.println("Booking not Found!!\n");
        }
    }

    static void delete(String pnr, String l) throws IOException
    {
        int k=0;
        InputStreamReader read=new InputStreamReader(System.in);
        BufferedReader in=new BufferedReader(read);
        for(int i=0;i<100;i++)
        {
            if(((b[i].lname).equalsIgnoreCase(l))&&(b[i].id).equals(pnr))
            {
                k=1;
                System.out.println("Booking found!");
                System.out.println("Are you sure you want to delete?(y/n)");
                char c=(char)in.read();
                if(c=='y'||c=='Y')
                {
                    b[i].occ=0;
                    System.out.println();
                    progress();
                    System.out.println("BOOKING DELETED!!\n");
                    send(b[i].email,b[i].id,b[i].seat,b[i].fname,b[i].lname,"cancelled");
                }
                /*else
                {
                    System.out.println("Booking not Found!!");
                    break;
                }*/
            }
            /*else
            {
                System.out.println("Booking not Found!!\n");
            }*/
        }
        if(k==0)
        {
            System.out.println("Booking not Found!!");
        }
    }

    public static void main(String args[]) throws IOException
    {
        InputStreamReader read=new InputStreamReader(System.in);
        BufferedReader in=new BufferedReader(read);

        System.out.println("-----------------------------------------------BUS RESERVATION SYSTEM-----------------------------------------------");

        for(int i=0;i<100;i++)
        {
            b[i]=new Booking();
        }
        for(int i=0;i<100;i++)
        {
            b[i].seat=i+1;
        }
       while(true) {
            System.out.println("Enter your choice:");
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("1)New Booking\n2)Retrieve Booking\n3)Delete Booking\n4)Exit Program");
            System.out.println();
            int ch=Integer.parseInt(in.readLine());
            switch (ch)
            {
                case 1:
                {
                    System.out.println("------------------------------NEW BOOKING------------------------------");
                    addbook();
                    System.out.println();
                    break;
                }

                case 2:
                {
                    System.out.println("------------------------------RETRIEVE BOOKING------------------------------");
                    System.out.println("Enter last name used in booking: ");
                    String x=in.readLine();
                    System.out.println();
                    System.out.println("Enter 4 digit PNR: ");
                    String y=in.readLine();
                    System.out.println();
                    progress();
                    retrieve(y,x);
                    System.out.println();
                    break;
                }

                case 3:
                {
                    System.out.println("------------------------------DELETE BOOKING------------------------------");
                    System.out.println("Enter last name used in booking: ");
                    String x=in.readLine();
                    System.out.println();
                    System.out.println("Enter 4 digit PNR: ");
                    String y=in.readLine();
                    System.out.println();
                    progress();
                    delete(y,x);
                    System.out.println();
                    break;
                }

                case 4:
                {
                    System.exit(0);
                    break;
                }

                default:
                {
                    System.out.println("Invalid Choice!!");
                    System.out.println();
                }
            }
        }
    }
}
