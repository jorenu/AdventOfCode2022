import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author uitzetter
 */
public class Day10 {

    public static void main(String[] args) {
        new Day10().run();
    }

    private void run() {
        partOne();
        partTwo();
    }

    private void partOne() {
        List<String> input = InputLoader.getInput("day10Input.txt");

        int cycles = 0;
        int X = 1;
        Stack<Integer> pendingAddx = new Stack<>();

        List<Integer> results = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            String cmd = input.get(i);
            cycles++;

            if (cycles == 20 || cycles == 60 || cycles == 100 || cycles == 140 || cycles == 180 || cycles == 220) {
                results.add(cycles * X);
            }

            if (cmd.charAt(0) == 'n') {
                continue;
            }

            if (!pendingAddx.isEmpty()) {
                int amount = pendingAddx.pop();
                X += amount;
            } else {
                pendingAddx.add(Integer.parseInt(cmd.split(" ")[1]));
                i--;
            }


        }

        System.out.println(results.stream().mapToInt(a -> a).sum());
    }

    private void partTwo() {
        List<String> input = InputLoader.getInput("day10Input.txt");

        int cycles = 0;
        Stack<Integer> pendingAddx = new Stack<>();

        Map<Integer, StringBuilder> crtRows = new HashMap<>();
        int spriteStart = 0;
        int row = 0;

        for (int i = 0; i < input.size(); i++) {
            String cmd = input.get(i);
            cycles++;

            StringBuilder sb = new StringBuilder();
            if (crtRows.get(row) != null) {
                sb = crtRows.get(row);
            } else {
                crtRows.put(row, sb);
            }

            if ((cycles - row * 40) - 1 < spriteStart || (cycles - row * 40) - 1 > spriteStart + 2) {
                sb.append(".");
            } else {
                sb.append("#");
            }

            if (cycles != 0 && cycles % 40 == 0) {
                row++;
            }

            if (cmd.charAt(0) == 'n') {
                continue;
            }

            if (!pendingAddx.isEmpty()) {
                int amount = pendingAddx.pop();
                spriteStart += amount;
            } else {
                pendingAddx.add(Integer.parseInt(cmd.split(" ")[1]));
                i--;
            }


        }

        for (StringBuilder crtRow : crtRows.values()) {
            System.out.println(crtRow);
        }

    }

}
