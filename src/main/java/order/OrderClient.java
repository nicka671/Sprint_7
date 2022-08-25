package order;
import courier.RestClient;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient
{
    private static final String ORDER_PATH = "api/v1/orders";

    public static ValidatableResponse order(CreateOrder order)
    {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    // ручка get все запросы
    public static ValidatableResponse getAllOrders()
    {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

}