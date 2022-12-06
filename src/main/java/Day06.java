import java.util.HashSet;
import java.util.Set;

/**
 * @author uitzetter
 */
public class Day06 {

    public static void main(String[] args) {
        new Day06().run();
    }

    private void run() {
        partOne();
        partTwo();
    }

    private void partOne() {
        char[] input = InputLoader.getInput("day06Input.txt").get(0).toCharArray();
        System.out.println(getAnswerForLenghth(input, 4));
    }

    private void partTwo() {
        char[] input = InputLoader.getInput("day06Input.txt").get(0).toCharArray();
        System.out.println(getAnswerForLenghth(input, 14));
    }

    private int getAnswerForLenghth(char[] input, int length) {
        Set<Character> charSet = new HashSet<>();

        for (int i = 0; i < input.length - length; i++) {
            for (int j = 0; j < length; j++) {
                charSet.add(input[i + j]);
            }

            if (charSet.size() == length) {
                return i + length;
            }

            charSet.clear();
        }

        return -1;
    }
}
