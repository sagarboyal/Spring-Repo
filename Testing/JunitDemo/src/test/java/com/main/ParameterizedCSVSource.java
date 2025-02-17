import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class ParameterizedCSVSource {
    @ParameterizedTest
    @CsvSource({
            "Audi,55",
            "Tesla,99",
            "Ferrari,101"
    })
    public void csvSourceMethod(String car, int quantity) {
        System.out.println(car + " : " + quantity);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/java/resources/input.csv")
    void csvSourceMethodFromFile(String car, int quantity) {
        System.out.println(car + " : " + quantity);
    }
}
