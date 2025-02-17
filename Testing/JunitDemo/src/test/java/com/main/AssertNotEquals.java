import com.main.JavaOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AssertNotEquals {

    @Test
    public void assertNotEqualsDemo(){
        JavaOperation javaOperations = new JavaOperation();
        //Assertions.assertNotEquals(9, javaOperations.add(4,5));
        Assertions.assertNotEquals(9, javaOperations.add(4,5), "Assertion add(4,5) failed");
    }
}