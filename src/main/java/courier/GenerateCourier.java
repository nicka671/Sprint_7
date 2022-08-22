package courier;

import courier.Courier;

public class GenerateCourier
{
    public static Courier getDefault() {
        return new Courier("me33fefwww", "666361", "nika51");
    }

    public static Courier existedLogin() {
        return new Courier("help818", "7777", "nika7");
    }

    public static Courier existedLogin1() {
        return new Courier("help818", "788888", "nika8");
    }

    public static Courier createWithoutPassword() {
        return new Courier("you88", "nika5");
    }

    }
