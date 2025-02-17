import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParameterizedMethod {

    @ParameterizedTest
    @MethodSource("stringParameters")
    public void useMethodSourceString(String parameter){
        System.out.println(parameter);
    }

    static Stream<String> stringParameters(){
        return Stream.of("Audi","Ferrari","Tesla");
    }

    //Primitive types
    @ParameterizedTest
    @MethodSource("intParameters")
    public void useMethodSourceInt(int parameter){
        System.out.println(parameter);
    }

    static IntStream intParameters(){
        return IntStream.range(0,5);
    }

}
