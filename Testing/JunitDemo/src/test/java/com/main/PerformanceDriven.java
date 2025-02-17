import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
@Timeout(6)
public class PerformanceDriven {

    @Test
    @Timeout(3)
    public void timeOutTesting1() throws InterruptedException {
        Thread.sleep(5000);
    }

    @Test
    @Timeout(value = 6000, unit = TimeUnit.MILLISECONDS)
    public void timeOutTesting2() throws InterruptedException {
        Thread.sleep(5000);
    }

    @Test
    public void timeOutTesting3() throws InterruptedException {
        Thread.sleep(5000);
    }

    @Test
    public void timeOutTesting4(){
        Assertions.assertTimeout(Duration.ofMillis(3000), () ->
        {
            Thread.sleep(4000);
        });
    }
}
