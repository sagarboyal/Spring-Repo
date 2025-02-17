import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

class RepeatedTests {

    //Normal repeated test
    @RepeatedTest(5)
    public void repeatedHelloOneParam(){
        System.out.println("Hello Param");
    }


    //Repeated test with custom display name
    @RepeatedTest(value = 5, name = "Executing repetition {currentRepetition} " +
            "of {totalRepetitions}")
    public void repeatedHello(){
        System.out.println("Hello");
    }

    //Repeated test with custom display name
    @RepeatedTest(value = 5, name = "{displayName} {currentRepetition} " +
            "of {totalRepetitions}")
    @DisplayName("RepeatDemo:")
    public void customDisplayName(){
        System.out.println("Hello");
    }

    @RepeatedTest(value = 3, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("Test!")
    public void customDisplayNameLong(){
        System.out.println("Repeat demo");
    }

    @RepeatedTest(3)
    public void repetitionInfoDemo(RepetitionInfo repetitionInfo){
        System.out.println("Current repetition : " + repetitionInfo.getCurrentRepetition());
        System.out.println("Total repetition : " + repetitionInfo.getTotalRepetitions());
    }
}