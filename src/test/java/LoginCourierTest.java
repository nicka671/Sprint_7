import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.GenerateCourier;
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
    private Courier courierWithoutPassword;
    private int id;


    @Before
    public void setUp()
    {
        courier = GenerateCourier.getDefault();
        courierWithoutPassword = GenerateCourier.createWithoutPassword();
        courierClient = new CourierClient();
        courierClient.create(courier);
    }
    @After
    public void tearDown()
    {
        courierClient.delete(id);
    }

@Test
        //курьер может авторизоваться; для авторизации нужно передать все обязательные поля; успешный запрос возвращает id.
    public void LoginCourier()
{

    ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
    int loginCode = loginResponse.extract().statusCode();
    assertEquals("Status code is incorrect", SC_OK, loginCode);
    id = loginResponse.extract().path("id");
    assertTrue("id должен быть больше нуля!", id > 0);
   }

    @Test
    public void LoginCourierWithInvalidLogin()
    {

        ValidatableResponse invalidLoginResponse = courierClient.login(CourierCredentials.credentialsWithInvalidLogin(courier));
        int loginCode = invalidLoginResponse.extract().statusCode();
        assertEquals("Status code is incorrect", SC_NOT_FOUND, loginCode);
        String invalidLoginMessage = invalidLoginResponse.extract().path("message");
        assertEquals("Аутентификация выполнена с неверными данными",  "Учетная запись не найдена", invalidLoginMessage);
    }


    @Test
    public void LoginCourierWithoutPassword()
    {

        ValidatableResponse emptyPasswordResponse = courierClient.login(CourierCredentials.credentialsWithoutPassword(courier));
        int loginCode = emptyPasswordResponse.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, loginCode);
        String emptyPasswordMessage = emptyPasswordResponse.extract().path("message");
        assertEquals("Аутентификация выполнена без пароля",  "Недостаточно данных для входа", emptyPasswordMessage);
    }
}

