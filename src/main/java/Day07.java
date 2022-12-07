import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uitzetter
 */
public class Day07 {

    public static void main(String[] args) {
        new Day07().run();
    }

    private void run() {
        partOne();
        partTwo();
    }

    private void partOne() {
        List<String> input = InputLoader.getInput("day07Input.txt");

        Directory root = mapDirectoryTree(input);

        List<Integer> dirSizesUnder100K = new ArrayList<>();
        getDirSizes(root, -1, dirSizesUnder100K);

        System.out.println(dirSizesUnder100K.stream().reduce(0, Integer::sum));
    }

    private void partTwo() {
        List<String> input = InputLoader.getInput("day07Input.txt");

        Directory root = mapDirectoryTree(input);

        int availableSpace = 70_000_000 - root.getTotalSize();
        int requiredSpace = 30_000_000 - availableSpace;

        List<Integer> dirSizesGreaterThanOrEqual = new ArrayList<>();

        getDirSizes(root, requiredSpace, dirSizesGreaterThanOrEqual);

        System.out.println(dirSizesGreaterThanOrEqual.stream().mapToInt(v -> v).min().getAsInt());
    }

    private Directory mapDirectoryTree(List<String> input) {
        Directory root = new Directory("/");
        Directory currentDir = root;

        for (String line : input) {
            if (isCommand(line)) {
                if (isCd(line)) {
                    String cdDir = getCdDir(line);
                    switch (cdDir) {
                        case ".." -> currentDir = currentDir.getParent();
                        case "/" -> currentDir = root;
                        default ->
                                currentDir = currentDir.getDirectories().stream().filter(d -> d.getName().equals(cdDir)).findFirst().get();
                    }
                }
            } else if (isDirectory(line)) {
                String dirName = getDirName(line);
                Directory directory = new Directory(dirName);
                currentDir.addDirectory(directory);
            } else {
                File file = new File(getFileSize(line));
                currentDir.addFile(file);
                currentDir.addSize(file.getSize());
                Directory parent = currentDir.getParent();
                while (parent != null) {
                    parent.addSize(file.getSize());
                    parent = parent.getParent();
                }
            }
        }

        return root;
    }

    private void getDirSizes(Directory directory, int requiredSpace, List<Integer> sizes) {
        int total = directory.getTotalSize();

        if (requiredSpace == -1) {
            if (total <= 100_000) {
                sizes.add(total);
            }
        } else if (total >= requiredSpace) {
            sizes.add(total);
        }

        for (Directory dir : directory.getDirectories()) {
            getDirSizes(dir, requiredSpace, sizes);
        }
    }

    private int getFileSize(String line) {
        return Integer.parseInt(line.split(" ")[0]);
    }

    private String getDirName(String line) {
        return line.substring(4);
    }

    private boolean isCd(String line) {
        return 'c' == line.charAt(2);
    }

    private String getCdDir(String line) {
        return line.substring(5);
    }

    private boolean isCommand(String line) {
        return '$' == line.charAt(0);
    }

    private boolean isDirectory(String line) {
        return 'd' == line.charAt(0);
    }

}

@Getter
@Setter
@AllArgsConstructor
class File {
    private int size;
}

@Getter
@Setter
class Directory {
    private String name;
    private List<File> files = new ArrayList<>();
    private List<Directory> directories = new ArrayList<>();
    private Directory parent;
    private int totalSize;

    public Directory(String name) {
        this.name = name;
    }

    public void addDirectory(Directory directory) {
        this.directories.add(directory);
        directory.parent = this;
    }

    public void addFile(File file) {
        this.files.add(file);
    }

    public void addSize(int size) {
        this.totalSize += size;
    }
}
