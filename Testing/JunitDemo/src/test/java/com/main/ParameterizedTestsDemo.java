import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

public class ParameterizedTestsDemo {

    @ParameterizedTest
    @ValueSource(strings = {"abc","xyz",""})
    public void checkBlanks(String value){
        Assertions.assertTrue(StringUtils.isBlank(value));
    }

    @ParameterizedTest(name = "Check blanks {0}")
    @ValueSource(strings = {"abc","xyz",""})
    public void checkBlanksWithPlaceholder(String value){
        Assertions.assertTrue(StringUtils.isBlank(value));
    }

    @ParameterizedTest(name = "Check blanks {0}")
    @ValueSource(strings = {"abc","xyz"})
    @EmptySource
    public void checkBlanksWithPlaceholder1(String value){
        Assertions.assertTrue(StringUtils.isBlank(value));
    }

    @ParameterizedTest(name = "Check blanks {0}")
    @ValueSource(strings = {"abc","xyz"})
    @NullSource
    public void checkBlanksWithPlaceholderNull(String value){
        Assertions.assertTrue(StringUtils.isBlank(value));
    }

    @ParameterizedTest(name = "Check blanks {0}")
    @ValueSource(strings = {"abc","xyz"})
    @NullAndEmptySource
    public void checkBlanksWithPlaceholderNullAndEmpty(String value){
        Assertions.assertTrue(StringUtils.isBlank(value));
    }
}
