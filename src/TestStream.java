import java.util.*;

import static java.util.stream.Collectors.*;

public class TestStream {

    public static void main(String[] args) {

        TestStream testStream = new TestStream();

        int[] values = new Random()
                .ints(5,1,10)
                .toArray();

        System.out.println("Исходный массив:\n" + Arrays.toString(values));
        System.out.println("Число из уникальных цифр:\n" + testStream.minValue(values) + "\n");

        List<Integer> integers = new Random()
                .ints(5, 0, 10)
                .boxed()
                .toList();

        System.out.println("Исходный массив:\n" + integers.toString());
        System.out.println("Массив из чисел в зависимости от суммы: \n" + testStream.oddEven(integers));
    }

    private int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, x) -> acc * 10 + x);
    }

    private List<Integer> oddEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> oddEvenIntegers = integers.stream()
                .collect(partitioningBy(x -> x % 2 == 0));
        return oddEvenIntegers.get(false).size() % 2 != 0 ?
                oddEvenIntegers.get(false) : oddEvenIntegers.get(true);
    }
}
