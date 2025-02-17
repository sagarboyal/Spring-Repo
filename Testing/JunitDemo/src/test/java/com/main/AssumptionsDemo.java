import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class AssumptionsDemo {

    @Test
    public void assumptionDemo(){
        Assumptions.assumeTrue("test".startsWith("t"));
        System.out.println("Assumption Demo 1");
        Assumptions.assumeFalse("false".startsWith("q"));
        System.out.println("Assumption Demo");
        Assertions.assertTrue("test".startsWith("f"));
    }

    @Test
    public void assumptionIfElseDemo(){
        if ("test".startsWith("f"))
            System.out.println("Passed");
        else System.out.println("Fail");
    }
}
