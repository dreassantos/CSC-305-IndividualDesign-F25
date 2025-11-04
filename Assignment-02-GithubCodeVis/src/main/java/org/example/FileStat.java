package asalaz41;

/**
 * FileStat contains the information on each file.
 * Data used in FileUtility and by FileUtility obervers.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 2
 */
public class FileStat {
    private String url;
    private int size;
    private int complexity;
    private int totalSize;

    public FileStat(String url, int size, int totalSize, int complexity){
        this.url = url;
        this.size = size;
        this.complexity = complexity;
        this.totalSize = totalSize;
    }

    public int getSize() {
        return size;
    }

    public int getTotalSize(){
        return totalSize;
    }

    public int getComplexity(){
        return complexity;
    }

    public String getURL(){
        return url;
    }

    @Override
    public String toString(){
        return url + "\t[Size:" + size + ", TotalSize:" + totalSize +", Complexity:"+ complexity + "]" ;

    }
}
