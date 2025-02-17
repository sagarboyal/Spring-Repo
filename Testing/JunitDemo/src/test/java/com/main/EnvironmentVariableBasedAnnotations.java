import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

class EnvironmentVariableBasedAnnotations {
     /*
    @EnabledIfEnvironmentVariable
    @DisabledIfEnvironmentVariable
    */

    @Test
    @EnabledIfEnvironmentVariable(named = "USER", matches = "faisals")
    public void matchesUserName(){
        System.out.println("User name matched");
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "USER", matches = "faisals")
    public void matchesUserNameDisabled(){
        System.out.println("User name matched disabled");
    }

}