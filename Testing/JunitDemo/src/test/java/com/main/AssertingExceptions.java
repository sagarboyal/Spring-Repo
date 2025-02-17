import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AssertingExceptions {
    /*
    assertThrows(expectedType,executable);
    */

    @Test
    public void assertionException() {
        Exception exception = Assertions.assertThrows(ArithmeticException.class, () -> {
            int a = 10 / 0;
            System.out.println("Hello");
        });
        System.out.println(exception.toString());
    }

    @Test
    public void assertionExceptionSubType() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            int a = 10 / 0;
        });
        System.out.println(exception.toString());
    }
}