import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

class NestedTestsDemo {
    /*
1. All nested class have to be non-static inner classes
2. Nested classes have to be annotated with the help of @Nested annotation
3. There is no depth to the hierarchy of nested classes
4. @BeforeAll and @AfterAll methods won’t work by default in nested
classes since those methods have to be static by default and
static keyword is not allowed inside inner classes in java

How to add @BeforeAll and @AfterAll in nested classes?
You can declare nested classes using @TestInstance(Lifecycle.PER_CLASS)
and make that class as static.


5. Nested classes can have @BeforeEach and @AfterEach methods
*/

    @Test
    public void printHello(){
        System.out.println("Hello outer");
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    static class NestedDemo{
        @BeforeAll
        public static void beforeAllDemo(){
            System.out.println("Before all inner");
        }

        @Test
        public void printHelloInner(){
            System.out.println("Hello inner");
        }
    }

}