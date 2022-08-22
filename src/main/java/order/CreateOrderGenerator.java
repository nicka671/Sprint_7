package order;

import order.OrderColor;

import java.util.ArrayList;

public class CreateOrderGenerator
{

    private static String[] color;

    public static CreateOrder createOrderWithParam(String[] color)
    {
        return new CreateOrder("Nika",
                               "Kurohtina",
                                "Moscow, street",
                             "Novogireevo",
                                 "89161231234",
                                12,
                             "14.01.2002",
                                        color);
    }
}

