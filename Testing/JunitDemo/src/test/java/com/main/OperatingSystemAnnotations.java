import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

class OperatingSystemAnnotations {

    @Test
    @EnabledOnOs(OS.MAC)
    public void printHello(){
        System.out.println("Hello");
    }

    @Test
    @DisabledOnOs({OS.LINUX, OS.WINDOWS})
    public void printHelloPython(){
        System.out.println("Hello Python");
    }

}