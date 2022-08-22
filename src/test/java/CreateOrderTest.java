import io.restassured.response.ValidatableResponse;
import order.CreateOrder;
import order.CreateOrderGenerator;
import order.OrderClient;
import order.OrderColor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final String[] color;


    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] makeOrderWithParam() {
        return new Object[][] {
                {OrderColor.ORDER_COLOR_GREY},
                {OrderColor.ORDER_COLOR_BLACK},
                {OrderColor.ORDER_COLOR_EMPTY},
                {OrderColor.ORDER_COLOR_GREY_AND_BLACK}
        };
    }

    @Test
    public void makeOrder() {
        CreateOrder order = CreateOrderGenerator.createOrderWithParam(color);
        int statusCode = OrderClient.order(order).extract().statusCode();
        assertEquals("сообщение", SC_CREATED, statusCode);

    }
}

//почему 500 ошибка, нужна помощь :((