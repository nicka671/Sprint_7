import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.GenerateCourier;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

import io.qameta.allure.junit4.DisplayName;

public class CreateCourierTest {

    private Courier courier;
    private Courier courierWithoutPassword;
    private Courier courierWithExistedLogin;
    private Courier courierWithExistedLogin1;
    private CourierClient courierClient;

    private int id;

    @Before
    public void setUp()
    {
        courier = GenerateCourier.getDefault();
        courierClient = new CourierClient();
        courierWithoutPassword = GenerateCourier.createWithoutPassword();
        courierWithExistedLogin = GenerateCourier.existedLogin();
        courierWithExistedLogin1 = GenerateCourier.existedLogin1();
    }

    @DisplayName("Check if courier can be created")
    @Description("При создании курьера возвращается 201 True, а при последующем логине - непустой id")
    @Test
    public void courierCanBeCreatedTest()
    {
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        assertEquals("Код состояния должен быть 201", SC_CREATED, statusCode);
        boolean isCreated = response.extract().path("ok");
        assertTrue("Аккаунт курьера не создан", isCreated);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Код состояния должен быть 200", SC_OK, loginStatusCode);
        id = loginResponse.extract().path("id");
        assertNotNull("ID пустой", id);
        courierClient.delete(id);
    }

    @DisplayName("Courier without password")
    @Description("При создании курьера без пароля возвращается 400 \"Недостаточно данных для создания учетной записи\"")
    @Test
    public void courierWithoutPassword()
    {
        ValidatableResponse response = courierClient.create(courierWithoutPassword);
        int statusCode = response.extract().statusCode();
        assertEquals("Вернулся некорректный код состояния, должен быть 400", SC_BAD_REQUEST, statusCode);
        String message400 = response.extract().path("message");
        assertEquals("При создании учётной записи на сервер был передан пустой пароль", "Недостаточно данных для создания учетной записи", message400);
    }

    @DisplayName("Courier With Existing Login")
    @Description("При создании курьера с уже существующим логином возвращается 409 \"Этот логин уже используется. Попробуйте другой.\"")
    @Test
    public void courierWithExistingLogin()
    {
        ValidatableResponse response = courierClient.create(courierWithExistedLogin1);
        int statusCode = response.extract().statusCode();
        assertEquals("Вернулся некорректный код состояния, должен быть 409", SC_CONFLICT, statusCode);
        String message409 = response.extract().path("message");
        assertEquals("Был создан новый курьер с уже существующим логином", "Этот логин уже используется. Попробуйте другой.", message409);
    }
}