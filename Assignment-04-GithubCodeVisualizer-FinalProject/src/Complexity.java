package asalaz41;

/**
 * Complexity holds the files complexity stats.
 *
 * @author Andrea Santos, asalaz41
 * @version 1
 */
public class Complexity {
    private final int size;
    private final int complexity;
    private final int totalSize;

    Complexity(int size, int totalSize, int complexity) {
        this.size = size;
        this.totalSize = totalSize;
        this.complexity = complexity;
    }

    public int getSize() {
        return size;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public int getComplexity() {
        return complexity;
    }
}
