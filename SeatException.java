package project;

public class SeatException extends Exception
{
    public String toString()
    {
        return "Invalid seat number, enter between 1 and 100";
    }
}
