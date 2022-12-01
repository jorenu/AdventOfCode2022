import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author uitzetter
 */
public class Day01 {

    public static final String EMPTY = "";

    public static void main(String[] args) {
        new Day01().run();
    }

    private void run() {
        List<String> input = InputLoader.getInput("day01Input.txt");
        partOne(input);
        partTwo(input);
    }

    private void partOne(List<String> input) {
        System.out.println(getTotalCaloriesSortedDesc(input).get(0));
    }

    private void partTwo(List<String> input) {
        List<Integer> totalCaloriesSortedDesc = getTotalCaloriesSortedDesc(input);
        System.out.println(totalCaloriesSortedDesc.get(0) + totalCaloriesSortedDesc.get(1) + totalCaloriesSortedDesc.get(2));
    }

    private List<Integer> getTotalCaloriesSortedDesc(List<String> input) {
        int totalOfOne = 0;
        List<Integer> totalOfAll = new ArrayList<>();

        for (String line : input) {
            if (isEmpty(line)) {
                totalOfAll.add(totalOfOne);
                totalOfOne = 0;
                continue;
            }
            totalOfOne += Integer.parseInt(line);
        }

        return totalOfAll.stream().sorted(Comparator.reverseOrder()).toList();
    }

    private boolean isEmpty(String line) {
        return EMPTY.equals(line);
    }
}
