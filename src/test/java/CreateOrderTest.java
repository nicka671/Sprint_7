import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import order.CreateOrder;
import order.CreateOrderGenerator;
import order.OrderClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import  order.OrderColors;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private CreateOrder createOrder;
    private OrderClient orderClient;
    private String[] color;

    @Before
    public void setUp()
    {
        createOrder = CreateOrderGenerator.getDefault();
        orderClient = new OrderClient();
    }

    public CreateOrderTest(String[] color) {
        this.color = color;
    }


    @Parameterized.Parameters
    public static Object[][] makeOrderWithParam() {
        return new Object[][] {
                {OrderColors.ORDER_COLOR_BLACK},
                {OrderColors.ORDER_COLOR_GREY},
                {OrderColors.ORDER_COLOR_EMPTY},
                {OrderColors.ORDER_COLOR_BLACK_AND_GREY}
        };
    }

    @DisplayName("Create orders with different colors")
    @Description("Создаём заказы с разными цветами, их комбинацией и пустым значением")
    @Test
    public void makeOrder()
    {
        int statusCode = orderClient.order(createOrder).extract().statusCode();
        assertEquals("Вернулся некорректный код состояния, должен быть 201", SC_CREATED, statusCode);
        int orderTrack = orderClient.order(createOrder).extract().path("track");
        assertNotNull("Значение параметра \"track\" не должно быть пустым", orderTrack);

    }
}