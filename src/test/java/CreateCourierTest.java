import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.GenerateCourier;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

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


        @After
        public void tearDown()
        {
            courierClient.delete(id);
        }

    @Test
    public void courierCanBeCreatedTest()
    {
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode);
        boolean isCreated = response.extract().path("ok");
        assertTrue("Аккаунт не создан", isCreated);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Курьер не может залогиниться", SC_OK, loginStatusCode);
        id = loginResponse.extract().path("id");
        assertNotNull("ID пустой", id);

    }

    @Test
    public void courierWithoutPassword()
    {
        ValidatableResponse response = courierClient.create(courierWithoutPassword);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode);
        String message400 = response.extract().path("message");
        assertEquals("Введите пароль", "Недостаточно данных для создания учетной записи", message400);
    }

    @Test
    public void courierWithExistingLogin()
    {
        ValidatableResponse response = courierClient.create(courierWithExistedLogin1);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CONFLICT, statusCode);
        String message409 = response.extract().path("message");
        assertEquals("Возможно создать клиентов с одинаковыми логинами", "Этот логин уже используется. Попробуйте другой.", message409);
    }
}
