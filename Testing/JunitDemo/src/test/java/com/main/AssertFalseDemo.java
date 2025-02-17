import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssertFalseDemo {

    @Test
    public void assertForFalse(){
        String test = "I love java";
        //Assertions.assertFalse(test.startsWith("J"));
        Assertions.assertFalse(test.startsWith("I"), "Test assertFalse failed");
    }
}
