import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author uitzetter
 */
public class Day09 {

    public static void main(String[] args) {
        new Day09().run();
    }

    private void run() {
        partOne();
        partTwo();
    }

    private void partOne() {
        List<String> input = InputLoader.getInput("day09Input.txt");

        Coordinate H = new Coordinate();
        Coordinate T = new Coordinate();

        Set<Coordinate> tailHistory = new HashSet<>();
        tailHistory.add(T.copy());

        for (String instruction : input) {
            char direction = instruction.charAt(0);
            int distance = Integer.parseInt(instruction.substring(2));

            for (int i = 0; i < distance; i++) {

                H.move(direction);

                if (!T.isAdjacent(H)) {
                    T.moveToward(H);
                }

                tailHistory.add(T.copy());
            }
        }

        System.out.println(tailHistory.size());
    }

    private void partTwo() {
        List<String> input = InputLoader.getInput("day09Input.txt");

        Coordinate H = new Coordinate();
        Coordinate _1 = new Coordinate();
        Coordinate _2 = new Coordinate();
        Coordinate _3 = new Coordinate();
        Coordinate _4 = new Coordinate();
        Coordinate _5 = new Coordinate();
        Coordinate _6 = new Coordinate();
        Coordinate _7 = new Coordinate();
        Coordinate _8 = new Coordinate();
        Coordinate _9 = new Coordinate();

        Set<Coordinate> tailHistory = new HashSet<>();
        tailHistory.add(_9.copy());

        for (String instruction : input) {
            char direction = instruction.charAt(0);
            int distance = Integer.parseInt(instruction.substring(2));

            for (int i = 0; i < distance; i++) {

                H.move(direction);

                follow(_1, H);
                follow(_2, _1);
                follow(_3, _2);
                follow(_4, _3);
                follow(_5, _4);
                follow(_6, _5);
                follow(_7, _6);
                follow(_8, _7);
                follow(_9, _8);

                tailHistory.add(_9.copy());
            }
        }

        System.out.println(tailHistory.size());
    }

    private void follow(Coordinate follower, Coordinate followee) {
        if (!follower.isAdjacent(followee)) {
            follower.moveToward(followee);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    static class Coordinate {
        private int x, y;

        public Coordinate copy() {
            return new Coordinate(x, y);
        }

        public void move(char direction) {
            switch (direction) {
                case 'L' -> x--;
                case 'R' -> x++;
                case 'U' -> y++;
                case 'D' -> y--;
            }
        }

        public boolean isAdjacent(Coordinate other) {
            return (x == other.x && y == other.y) || (Math.abs(x - other.x) == 1 && Math.abs(y - other.y) == 1)
                    || (Math.abs(x - other.x) == 1 && y == other.y) || (x == other.x && Math.abs(y - other.y) == 1);
        }

        public void moveToward(Coordinate other) {
            if (x < other.x) {
                x++;
            }
            if (x > other.x) {
                x--;
            }
            if (y < other.y) {
                y++;
            }
            if (y > other.y) {
                y--;
            }
        }
    }

}
