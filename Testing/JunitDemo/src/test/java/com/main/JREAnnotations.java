import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

class JREAnnotations {

    /*
    @EnabledOnJre
    @DisabledOnJre
    @EnabledForJreRange
    @DisabledForJreRange
    */

    @Test
    @EnabledOnJre(JRE.JAVA_13)
    public void printHelloJre(){
        System.out.println("Hello Jre");
    }

    @Test
    @DisabledOnJre({JRE.JAVA_13, JRE.JAVA_8})
    public void printHelloJreDisabled(){
        System.out.println("Hello Jre");
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_9, max = JRE.JAVA_14)
    public void printJava9to14(){
        System.out.println("Hi");
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_10)
    public void printJavaMin10(){
        System.out.println("Hello");
    }

    @Test
    @EnabledForJreRange(max = JRE.JAVA_14)
    public void printJavaMax14(){
        System.out.println("Hello");
    }

    //DisabledForJRERange
    @Test
    @DisabledForJreRange(min = JRE.JAVA_9, max = JRE.JAVA_14)
    public void printJavaDisabled9to14(){
        System.out.println("Hello");
    }

    @Test
    @DisabledForJreRange(min = JRE.JAVA_9)
    public void printJavaDisabled9(){
        System.out.println("Hello");
    }

    @Test
    @DisabledForJreRange(max = JRE.JAVA_13)
    public void printJavaDisabled13(){
        System.out.println("Hello");
    }
}