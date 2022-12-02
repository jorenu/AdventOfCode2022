import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author uitzetter
 */
public class Day02 {

    private static final String ROCK_THEM = "A";
    private static final String PAPER_THEM = "B";
    private static final String SCISSORS_THEM = "C";

    private static final String ROCK_ME = "X";
    private static final String PAPER_ME = "Y";
    private static final String SCISSORS_ME = "Z";

    private static final int ROCK_SCORE = 1;
    private static final int PAPER_SCORE = 2;
    private static final int SCISSORS_SCORE = 3;

    private static final int DRAW_SCORE = 3;
    private static final int WIN_SCORE = 6;

    private static final Map<String, String> winMap = new HashMap<>();

    static {
        winMap.put(ROCK_THEM, PAPER_ME);
        winMap.put(PAPER_THEM, SCISSORS_ME);
        winMap.put(SCISSORS_THEM, ROCK_ME);
    }

    private static final Map<String, String> loseMap = new HashMap<>();

    static {
        loseMap.put(ROCK_THEM, SCISSORS_ME);
        loseMap.put(PAPER_THEM, ROCK_ME);
        loseMap.put(SCISSORS_THEM, PAPER_ME);
    }

    private static final Map<String, String> drawMap = new HashMap<>();

    static {
        drawMap.put(ROCK_THEM, ROCK_ME);
        drawMap.put(PAPER_THEM, PAPER_ME);
        drawMap.put(SCISSORS_THEM, SCISSORS_ME);
    }

    private static final Map<String, Integer> scoreMap = new HashMap<>();

    static {
        scoreMap.put(ROCK_ME, ROCK_SCORE);
        scoreMap.put(PAPER_ME, PAPER_SCORE);
        scoreMap.put(SCISSORS_ME, SCISSORS_SCORE);
    }

    private static final Map<String, Map<String, String>> translationMap = new HashMap<>();

    static {
        translationMap.put(ROCK_ME, loseMap);
        translationMap.put(PAPER_ME, drawMap);
        translationMap.put(SCISSORS_ME, winMap);
    }

    public static void main(String[] args) {
        new Day02().run();
    }

    private void run() {
        List<String> input = InputLoader.getInput("day02Input.txt");
        partOne(input);
        partTwo(input);
    }

    private void partOne(List<String> input) {
        int score = calculateScore(input);
        System.out.println(score);
    }

    private void partTwo(List<String> input) {

        input = input.stream()
                .map(s -> {
                    String them = s.substring(0, 1);
                    String me = s.substring(2, 3);
                    return s.replace(me, translationMap.get(me).get(them));
                })
                .toList();

        int score = calculateScore(input);
        System.out.println(score);
    }

    private int calculateScore(List<String> input) {
        int score = 0;

        for (String line : input) {
            String[] segments = line.split(" ");

            String them = segments[0];
            String me = segments[1];

            score += scoreMap.get(me);

            if (draw(them, me)) {
                score += DRAW_SCORE;
            } else if (won(them, me)) {
                score += WIN_SCORE;
            }
        }

        return score;
    }

    private boolean draw(String them, String me) {
        return drawMap.get(them).equals(me);
    }

    private boolean won(String them, String me) {
        return winMap.get(them).equals(me);
    }

}
