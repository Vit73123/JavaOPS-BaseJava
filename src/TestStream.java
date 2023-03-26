import java.util.Arrays;

public class TestStream {

    public static void main(String[] args) {

        TestStream testStream = new TestStream();

        System.out.println(testStream.minValue(new int[]{9, 3, 2, 1, 1, 4, 3, 7}));
    }

    private int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (acc, x) -> acc * 10 + x);
    }
}
