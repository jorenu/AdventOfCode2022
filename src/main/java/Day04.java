import java.util.ArrayList;
import java.util.List;

/**
 * @author uitzetter
 */
public class Day04 {

    public static void main(String[] args) {
        new Day04().run();
    }

    private void run() {
        partOne();
        partTwo();
    }

    private void partOne() {
        List<String> input = InputLoader.getInput("day04Input.txt");

        int answer = 0;

        for (String line : input) {
            String[] pair = line.split(",");

            String[] elf1 = pair[0].split("-");
            String[] elf2 = pair[1].split("-");

            List<Integer> elf1Sections = getSections(elf1);
            List<Integer> elf2Sections = getSections(elf2);

            if (elf1Sections.containsAll(elf2Sections) || elf2Sections.containsAll(elf1Sections)) {
                answer++;
            }
        }

        System.out.println(answer);
    }

    private void partTwo() {
        List<String> input = InputLoader.getInput("day04Input.txt");

        int answer = 0;

        for (String line : input) {
            String[] pair = line.split(",");

            String[] elf1 = pair[0].split("-");
            String[] elf2 = pair[1].split("-");

            boolean overlapAtAll = false;

            for (int i = Integer.parseInt(elf1[0]); i <= Integer.parseInt(elf1[1]); i++) {
                for (int j = Integer.parseInt(elf2[0]); j <= Integer.parseInt(elf2[1]); j++) {
                    if (i == j) {
                        overlapAtAll = true;
                    }
                }
            }

            if (overlapAtAll) {
                answer++;
            }
        }

        System.out.println(answer);
    }

    private List<Integer> getSections(String[] elf) {
        List<Integer> sections = new ArrayList<>();

        for (int i = Integer.parseInt(elf[0]); i <= Integer.parseInt(elf[1]); i++) {
            sections.add(i);
        }

        return sections;
    }

}
