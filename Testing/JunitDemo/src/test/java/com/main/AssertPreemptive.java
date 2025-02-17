import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.Duration;

public class AssertPreemptive {
    @Test
    public void timeOutTesting4(){
        Assertions.assertTimeout(Duration.ofMillis(3000), () ->
        {
            Thread.sleep(4000);
        });
    }

    @Test
    public void timeOutPreemptive(){
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(3000), () ->
        {
            Thread.sleep(4000);
        });
    }
}
