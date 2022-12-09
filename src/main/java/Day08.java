import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author uitzetter
 */
public class Day08 {

    public static void main(String[] args) {
        new Day08().run();
    }

    private void run() {
        partOne();
        partTwo();
    }

    private void partOne() {
        List<String> input = InputLoader.getInput("day08Input.txt");
        int[][] grid = getGrid(input);
        Set<String> visibleTrees = new HashSet<>();
        int size = grid.length;

        for (int i = 0; i < size; i++) {
            List<Integer> prevTrees = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                int tree = grid[i][j];
                if (prevTrees.stream().allMatch(p -> tree > p))
                    visibleTrees.add("%d-%d".formatted(i, j));

                prevTrees.add(tree);
            }
        }

        for (int i = 0; i < size; i++) {
            List<Integer> prevTrees = new ArrayList<>();
            for (int j = size - 1; j >= 0; j--) {
                int tree = grid[i][j];
                if (prevTrees.stream().allMatch(p -> tree > p))
                    visibleTrees.add("%d-%d".formatted(i, j));

                prevTrees.add(tree);
            }
        }

        for (int i = 0; i < size; i++) {
            List<Integer> prevTrees = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                int tree = grid[j][i];
                if (prevTrees.stream().allMatch(p -> tree > p))
                    visibleTrees.add("%d-%d".formatted(j, i));

                prevTrees.add(tree);
            }
        }

        for (int i = 0; i < size; i++) {
            List<Integer> prevTrees = new ArrayList<>();
            for (int j = size - 1; j >= 0; j--) {
                int tree = grid[j][i];
                if (prevTrees.stream().allMatch(p -> tree > p))
                    visibleTrees.add("%d-%d".formatted(j, i));

                prevTrees.add(tree);
            }
        }

        System.out.println(visibleTrees.size());
    }

    private void partTwo() {
        List<String> input = InputLoader.getInput("day08Input.txt");
        int[][] grid = getGrid(input);
        int size = grid.length;
        Set<Integer> sizes = new HashSet<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int tree = grid[i][j];

                boolean looking = true;
                int distanceLeft = 0;

                int counter = 1;

                while (looking) {
                    if (j - counter >= 0) {
                        int left = grid[i][j - counter];
                        distanceLeft++;
                        if (left >= tree) {
                            looking = false;
                        }
                    } else {
                        looking = false;
                    }
                    counter++;
                }

                looking = true;
                counter = 1;
                int distanceRight = 0;

                while (looking) {
                    if (j + counter < size) {
                        int right = grid[i][j + counter];
                        distanceRight++;
                        if (right >= tree) {
                            looking = false;
                        }
                    } else {
                        looking = false;
                    }
                    counter++;
                }

                looking = true;
                counter = 1;
                int distanceUp = 0;

                while (looking) {
                    if (i - counter >= 0) {
                        int up = grid[i - counter][j];
                        distanceUp++;
                        if (up >= tree) {
                            looking = false;
                        }
                    } else {
                        looking = false;
                    }
                    counter++;
                }


                looking = true;
                counter = 1;
                int distanceDown = 0;

                while (looking) {
                    if (i + counter < size) {
                        int down = grid[i + counter][j];
                        distanceDown++;
                        if (down >= tree) {
                            looking = false;
                        }
                    } else {
                        looking = false;
                    }
                    counter++;
                }

                sizes.add(distanceLeft * distanceRight * distanceUp * distanceDown);

            }
        }

        System.out.println(sizes.stream().mapToInt(a->a).max().getAsInt());
    }

    private int[][] getGrid(List<String> input) {
        int[][] grid = new int[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).length(); j++) {
                grid[i][j] = Integer.parseInt(input.get(i).substring(j, j+1));
            }
        }

        return grid;
    }

}
