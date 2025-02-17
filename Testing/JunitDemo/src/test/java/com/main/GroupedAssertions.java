import com.main.JavaOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupedAssertions {

    @Test
    public void multipleAssertions() {
        JavaOperation javaOperations = new JavaOperation();
        Assertions.assertEquals(4, javaOperations.add(3, 1));
        Assertions.assertEquals(25, javaOperations.add(3, 22));
        Assertions.assertEquals(100, javaOperations.add(70, 30));
    }

    @Test
    public void groupAssertionDemo() {
        JavaOperation javaOperations = new JavaOperation();
        Assertions.assertAll(() ->
                        Assertions.assertEquals(5, javaOperations.add(3, 1)),
                () -> Assertions.assertEquals(26, javaOperations.add(3, 22)),
                () -> Assertions.assertEquals(101, javaOperations.add(70, 30))
        );
    }

}
