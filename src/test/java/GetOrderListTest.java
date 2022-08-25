import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.GenerateCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class GetOrderListTest
{
    int id;
    private Courier courierOrderList;
    private CourierClient courierClient;

    private OrderClient orderClient;

    @Before
    public void setUp()
    {
        courierOrderList = GenerateCourier.getOrderListCourier();
        courierClient = new CourierClient();
        orderClient = new OrderClient();
    }

    @After
    public void tearDown()
    {
        courierClient.delete(id);
    }

    @DisplayName("Get Order List Test")
    @Description("Проверяем, что при получении списка всех заказов приходит код состояния 200 и что список заказов не пустой")
    @Test
    public void getOrderListTest()
    {
        courierClient.create(courierOrderList);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courierOrderList));
        id = loginResponse.extract().path("id");
        ValidatableResponse response = orderClient.getAllOrders();
        int statusCode = response.extract().statusCode();
        assertEquals("Код состояния должен быть 200", SC_OK, statusCode);
        ArrayList<String> orderBody = response.extract().path("orders");
        boolean isNotEmpty = orderBody!=null && !orderBody.isEmpty();
        assertTrue("Список заказов пустой", isNotEmpty);
    }
}