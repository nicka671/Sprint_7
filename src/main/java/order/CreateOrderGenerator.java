package order;

import java.util.ArrayList;

public class CreateOrderGenerator
{

     static String[] colors;


    public static CreateOrder getDefault()
    {
        return new CreateOrder(
                "Nika",
                "Kurohtina",
                "Moscow, street",
                5,
                "+79161231234",
                12,
                "14-01-2002",
                "Very useful comment",
                colors);
    }
}