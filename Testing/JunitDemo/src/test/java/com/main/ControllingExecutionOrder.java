import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/*
DisplayName → sorts the test methods alphanumerically based on the
display name of the method.

MethodName → sorts the test methods alphanumerically based on the
name of the method and parameters list

OrderAnnotation → sorts the method alphanumerically based on the
@Order annotation specified with the test method

Random → executes tests in Random order
*/

@TestMethodOrder(MethodOrderer.Random.class)
class ControllingExecutionOrder {

    @Test
    @Order(1)
    public void firstTest(){
        System.out.println("First test executed");
    }

    @Test
    @Order(2)
    public void secondTest(){
        System.out.println("Second test executed");
    }

    @Test
    @Order(3)
    public void thirdTest(){
        System.out.println("Third test executed");
    }

    @Test
    @Order(4)
    public void fourthTest(){
        System.out.println("Fourth test executed");
    }
}
