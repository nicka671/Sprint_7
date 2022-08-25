package courier;

public class Generator
{

    public static String generateNew() {
        String[] randomArr = {"d", "s", "m", "c", "e", "o", "k", "n", "l", "i", "v"};
        String randomStr = null;
        for (int i = 0; i < 6; i++) {
            String one = randomArr[(int) (Math.random() * 10)];
            randomStr = randomStr + one;
        }

        return randomStr;

    }

}
