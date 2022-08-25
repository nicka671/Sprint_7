import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.GenerateCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class LoginCourierTest
{
    private Courier courier;
    private CourierClient courierClient;
    private int id;


    @Before
    public void setUp()
    {
        courier = GenerateCourier.getDefault();
        courierClient = new CourierClient();
        courierClient.create(courier);
    }
    @After
    public void tearDown()
    {
        courierClient.delete(id);
    }

    @DisplayName("Login Courier")
    @Description("В случае успешного логина сервер возвращает 200 и id > 0")
    @Test
    public void loginCourier()
    {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginCode = loginResponse.extract().statusCode();
        assertEquals("Код состояния должен быть 200", SC_OK, loginCode);
        id = loginResponse.extract().path("id");
        assertTrue("id должен быть больше нуля", id > 0);
    }

    @DisplayName("Login Courier With Invalid Login")
    @Description("При логине с неверным паролем сервер возвращает 404 \"Учетная запись не найдена\"")
    @Test
    public void loginCourierWithInvalidLogin()
    {
        ValidatableResponse invalidLoginResponse = courierClient.login(CourierCredentials.credentialsWithInvalidLogin(courier));
        int loginCode = invalidLoginResponse.extract().statusCode();
        assertEquals("Код состояния должен быть 404", SC_NOT_FOUND, loginCode);
        String invalidLoginMessage = invalidLoginResponse.extract().path("message");
        assertEquals("Аутентификация выполнена с неверным логином",  "Учетная запись не найдена", invalidLoginMessage);
    }

    @DisplayName("Login Courier Without Password")
    @Description("При логине без пароля сервер возвращает 400 \"Недостаточно данных для входа\"")
    @Test
    public void loginCourierWithoutPassword()
    {

        ValidatableResponse emptyPasswordResponse = courierClient.login(CourierCredentials.credentialsWithoutPassword(courier));
        int loginCode = emptyPasswordResponse.extract().statusCode();
        assertEquals("Код состояния должен быть 400", SC_BAD_REQUEST, loginCode);
        String emptyPasswordMessage = emptyPasswordResponse.extract().path("message");
        assertEquals("Аутентификация выполнена без пароля",  "Недостаточно данных для входа", emptyPasswordMessage);
    }
}