import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Pattern;

/**
 * @author uitzetter
 */
public class Day11 {

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static void main(String[] args) {
        new Day11().run();
    }

    private void run() {
        partOne();
        partTwo();
    }

    private void partOne() {
        List<String> input = InputLoader.getInput("exampleInput.txt");

        Map<Integer, Monkey> monkeys = parseMonkeys(input);

        for (int i = 0; i < 20; i++) {
            for (Monkey monkey : monkeys.values()) {
                List<BigInteger> itemsToRemove = new ArrayList<>();
                for (BigInteger item : monkey.getItems()) {
                    BigInteger og = item;
                    monkey.setAmountInspected(monkey.getAmountInspected() + 1);
                    switch (monkey.getAction()) {
                        case ADD -> item = (monkey.getActionLeft() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionLeft())).add(monkey.getActionRight() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionRight()));
                        case SUB -> item = (monkey.getActionLeft() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionLeft())).subtract(monkey.getActionRight() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionRight()));
                        case MUL -> item = (monkey.getActionLeft() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionLeft())).multiply(monkey.getActionRight() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionRight()));
                        default -> item = (monkey.getActionLeft() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionLeft())).divide(monkey.getActionRight() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionRight()));
                    }

                    item = item.divide(BigInteger.valueOf(3));

                    if (item.mod(BigInteger.valueOf(monkey.getTest())).equals(BigInteger.ZERO)) {
                        monkeys.get(monkey.getMonkeyTrue()).getItems().add(item);
                        itemsToRemove.add(og);
                    } else {
                        monkeys.get(monkey.getMonkeyFalse()).getItems().add(item);
                        itemsToRemove.add(og);
                    }
                }
                monkey.getItems().removeAll(itemsToRemove);
            }
        }

        List<Long> amounts = monkeys.values().stream().map(Monkey::getAmountInspected).sorted(Comparator.reverseOrder()).toList();

        System.out.println(amounts.get(0) * amounts.get(1));

    }

    private void partTwo() {
        List<String> input = InputLoader.getInput("day11Input.txt");

        Map<Integer, Monkey> monkeys = parseMonkeys(input);

        long modProd = 1;
        for (Monkey monkey : monkeys.values()) {
            modProd *= monkey.getTest();
        }

        for (int i = 0; i < 10_000; i++) {
            for (Monkey monkey : monkeys.values()) {
                List<BigInteger> itemsToRemove = new ArrayList<>();
                for (BigInteger item : monkey.getItems()) {
                    BigInteger og = item;
                    monkey.setAmountInspected(monkey.getAmountInspected() + 1);
                    switch (monkey.getAction()) {
                        case ADD -> item = (monkey.getActionLeft() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionLeft())).add(monkey.getActionRight() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionRight()));
                        case SUB -> item = (monkey.getActionLeft() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionLeft())).subtract(monkey.getActionRight() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionRight()));
                        case MUL -> item = (monkey.getActionLeft() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionLeft())).multiply(monkey.getActionRight() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionRight()));
                        default -> item = (monkey.getActionLeft() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionLeft())).divide(monkey.getActionRight() == Integer.MIN_VALUE ? item : BigInteger.valueOf(monkey.getActionRight()));
                    }

                    if (item.mod(BigInteger.valueOf(monkey.getTest())).equals(BigInteger.ZERO)) {
                        monkeys.get(monkey.getMonkeyTrue()).getItems().add(item.mod(BigInteger.valueOf(modProd)));
                        itemsToRemove.add(og);
                    } else {
                        monkeys.get(monkey.getMonkeyFalse()).getItems().add(item.mod(BigInteger.valueOf(modProd)));
                        itemsToRemove.add(og);
                    }

                }
                monkey.getItems().removeAll(itemsToRemove);

            }
        }

        List<Long> amounts = monkeys.values().stream().map(Monkey::getAmountInspected).sorted(Comparator.reverseOrder()).toList();

        System.out.println(amounts.get(0) * amounts.get(1));
    }

    private Map<Integer, Monkey> parseMonkeys(List<String> input) {
        Map<Integer, Monkey> monkeys = new HashMap<>();
        Monkey currentMonkey = null;
        int currentMonkeyId = -1;

        for (String line : input) {
            if (line.length() == 0) continue;
            if (line.charAt(0) == 'M') {
                currentMonkeyId++;
                currentMonkey = new Monkey();
                currentMonkey.setId(currentMonkeyId);
                monkeys.put(currentMonkeyId, currentMonkey);
            } else if (line.charAt(2) == 'S') {

                for (String item : line.substring(line.indexOf(":") + 1).trim().split(", ")) {
                    currentMonkey.getItems().add(BigInteger.valueOf(Long.parseLong(item)));
                }

            } else if (line.charAt(2) == 'O') {
                String[] operation;
                String relevantLine = line.substring(line.indexOf("=") + 1);

                if (relevantLine.contains("+")) {
                    operation = relevantLine.split("\\+");
                    currentMonkey.setAction(Action.ADD);
                } else if (relevantLine.contains("-")) {
                    operation = relevantLine.split("-");
                    currentMonkey.setAction(Action.SUB);
                } else if (relevantLine.contains("*")) {
                    operation = relevantLine.split("\\*");
                    currentMonkey.setAction(Action.MUL);
                } else {
                    operation = relevantLine.split("/");
                    currentMonkey.setAction(Action.DIV);
                }

                currentMonkey.setActionLeft(isNumeric(operation[0].trim()) ? Integer.parseInt(operation[0].trim()) : Integer.MIN_VALUE);
                currentMonkey.setActionRight(isNumeric(operation[1].trim()) ? Integer.parseInt(operation[1].trim()) : Integer.MIN_VALUE);

            } else if (line.charAt(2) == 'T') {
                currentMonkey.setTest(Integer.parseInt(line.substring(line.indexOf("y") + 1).trim()));
            } else if (line.contains("true")) {
                currentMonkey.setMonkeyTrue(Integer.parseInt(line.substring(line.indexOf("y") + 1).trim()));
            } else if (line.contains("false")) {
                currentMonkey.setMonkeyFalse(Integer.parseInt(line.substring(line.indexOf("y") + 1).trim()));
            }
        }

        return monkeys;
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

}

@Getter
@Setter
class Monkey {
    private int id;
    private Queue<BigInteger> items = new LinkedList<>();
    private Action action;
    private int actionLeft;
    private int actionRight;
    private int monkeyTrue;
    private int monkeyFalse;
    private int test;
    private long amountInspected = 0;
}

enum Action {
    MUL,
    DIV,
    ADD,
    SUB;
}
