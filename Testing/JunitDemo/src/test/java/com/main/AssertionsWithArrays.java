import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AssertionsWithArrays {

    @Test
    public void assertsWithArraysDemo() {
        int a[] = new int[]{2, 2, 3}; // object 1
        int b[] = new int[]{1, 2, 3}; // object 2
        //Assertions.assertEquals(a, b);
        //Assertions.assertArrayEquals(a, b);
        Assertions.assertArrayEquals(a, b, "Comparison of arrays failed");
    }
}