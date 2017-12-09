import java.math.BigInteger;

/**
 * Created by Franck on 08.12.2017.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(f(2017));
    }



    static long f(int n) {
        System.out.println(n);

        if (n == 0) {
            return 1;
        }

        if (n == -1) {
            return 0;
        }

        return 8 * f(n-1) - 12 * f(n-2);


    }
}
