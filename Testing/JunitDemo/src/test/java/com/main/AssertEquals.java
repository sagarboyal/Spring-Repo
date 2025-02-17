import com.main.JavaOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AssertEquals {

    @Test
    public void assertAlternative() {
        JavaOperation javaOperations = new JavaOperation();
        if (javaOperations.add(4, 5) == 8)
            System.out.println("Test passed");
        else
            System.out.println("Test failed");
    }

    @Test
    public void assertDemo() {
        JavaOperation javaOperations = new JavaOperation();
        int actual = javaOperations.add(4, 5);
        Assertions.assertEquals(8, actual);
        Assertions.assertEquals(8, actual, "Assertion add(4,5) failed");
    }
}