package com.company;
import java.util.Comparator;

class Sort implements Comparator<Token>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Token a, Token b)
    {
        return a.getRepeat()-b.getRepeat();
    }
}
class Sort1 implements Comparator<Bigramm>
{
    public int compare(Bigramm a, Bigramm b)
    {
        return b.getRepeat()-a.getRepeat();
    }
}