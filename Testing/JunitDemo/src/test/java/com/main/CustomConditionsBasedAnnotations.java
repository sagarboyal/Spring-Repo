import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;

class CustomConditionsBasedAnnotations {

    /*
    @EnabledIf
    @DisabledIf
    */

    @Test
    @EnabledIf("checkIfUserValid")
    public void validateUser(){
        System.out.println("User is valid");
    }

    @Test
    @DisabledIf("checkIfUserValid")
    public void validateUserDisabled(){
        System.out.println("User is valid disabled");
    }

    public boolean checkIfUserValid(){
       // some code to check if user is valid
        return true;
    }

}