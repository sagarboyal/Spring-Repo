import com.main.JavaOperation;
import org.junit.jupiter.api.*;

class BeforeAllAfterAll {

    JavaOperation javaOperations;

    @BeforeAll
    public static void openDatabase(){
        System.out.println("Open database connection");
    }

    @AfterAll
    public static void closeDatabase(){
        System.out.println("Close database connection");
    }

    @BeforeEach
    public void init(){
        javaOperations = new JavaOperation();
        // all init tasks
        System.out.println("Initialization done");
    }

    @AfterEach
    public void cleanUp(){
        System.out.println("Cleanup done");
    }

    @Test
    void addTest(){
        // open database
        System.out.println("First test .. using Database");
        // close database
    }

    @Test
    void multiplyTest(){
        // open database
        System.out.println("Second test .. using Database");
        // close database
    }
}