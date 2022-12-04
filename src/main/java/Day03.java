import java.util.ArrayList;
import java.util.List;

/**
 * @author uitzetter
 */
public class Day03 {

    private static final int lowercaseOffset = 'a' - 1;
    private static final int uppercaseOffset = 'A' - 27;

    public static void main(String[] args) {
        new Day03().run();
    }

    private void run() {
        partOne();
        partTwo();
    }

    private void partOne() {
        List<String> input = InputLoader.getInput("day03Input.txt");

        int answer = 0;

        for (String rucksack : input) {
            int amountOfItems = rucksack.length();

            char[] compartment1 = rucksack.substring(0, amountOfItems / 2).toCharArray();
            char[] compartment2 = rucksack.substring(amountOfItems / 2, amountOfItems).toCharArray();

            char commonItem = (char) -1;

            for (char itemC1 : compartment1) {
                for (char itemC2 : compartment2) {
                    if (itemC1 == itemC2) {
                        commonItem = itemC1;
                    }
                }
            }

            answer += getPriority(commonItem);

        }

        System.out.println(answer);
    }

    private void partTwo() {
        List<String> input = InputLoader.getInput("day03Input.txt");

        int answer = 0;

        for (int i = 0; i < input.size(); i++) {
            if (i % 3 == 0) {
                char[] elf1Items = input.get(i).toCharArray();
                char[] elf2Items = input.get(i + 1).toCharArray();
                char[] elf3Items = input.get(i + 2).toCharArray();

                char badgeItem = (char) -1;

                for (char item1 : elf1Items) {
                    for (char item2 : elf2Items) {
                        for (char item3 : elf3Items) {
                            if (item1 == item2 && item1 == item3) {
                                badgeItem = item1;
                            }
                        }
                    }
                }

                answer += getPriority(badgeItem);

            }
        }

        System.out.println(answer);
    }

    private int getPriority(char character) {
        return Character.isUpperCase(character) ? character - uppercaseOffset : character - lowercaseOffset;
    }

}
