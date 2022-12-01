import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author uitzetter
 */
public class Day01 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        new Day01().run();
    }

    private void run() throws IOException, URISyntaxException {
        partOne();
        partTwo();
    }

    private void partOne() throws IOException, URISyntaxException {
        Path inputPath = Paths.get(ClassLoader.getSystemClassLoader().getResource("day01Input.txt").toURI());
        List<String> input = Files.readAllLines(inputPath);
        Elf elf = new Elf();
        List<Elf> elves = new ArrayList<>();

        for (String line : input) {
            if ("".equals(line)) {
                elves.add(elf);
                elf = new Elf();
                continue;
            }
            elf.calories += Integer.parseInt(line);
        }

        List<Integer> highestCaloriesSorted = elves.stream().map(e -> e.calories).sorted(Comparator.reverseOrder()).toList();

        System.out.println(highestCaloriesSorted.get(0));
    }

    private void partTwo() throws IOException, URISyntaxException {
        Path inputPath = Paths.get(ClassLoader.getSystemClassLoader().getResource("day01Input.txt").toURI());
        List<String> input = Files.readAllLines(inputPath);
        Elf elf = new Elf();
        List<Elf> elves = new ArrayList<>();

        for (String line : input) {
            if ("".equals(line)) {
                elves.add(elf);
                elf = new Elf();
                continue;
            }
            elf.calories += Integer.parseInt(line);
        }

        List<Integer> highestCaloriesSorted = elves.stream().map(e -> e.calories).sorted(Comparator.reverseOrder()).toList();

        System.out.println(highestCaloriesSorted.get(0) + highestCaloriesSorted.get(1) + highestCaloriesSorted.get(2));
    }
}

class Elf {
    public int calories;
}
