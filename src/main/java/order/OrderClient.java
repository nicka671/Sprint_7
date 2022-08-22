package order;
import courier.RestClient;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient
{
    private static final String ORDER_PATH = "api/v1/orders";

    public static ValidatableResponse order(CreateOrder createOrder)
{
        return given()
        .spec(getBaseSpec())
        .body(createOrder)
        .when()
        .post(ORDER_PATH)
        .then();
}

}
