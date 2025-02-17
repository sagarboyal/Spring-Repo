import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssertTrueDemo {

    @Test
    public void assertForTrue(){
        String test = "I love java";
        //System.out.println(test.startsWith("J"));
        //Assertions.assertTrue(test.startsWith("J"));
        Assertions.assertTrue(test.startsWith("J"), "Test assertTrue failed");
    }
}
