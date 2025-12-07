package asalaz41;

/**
 * FileStat contains the information on each file.
 * Data used in BlackBoard and by Blackboard obervers.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 3
 */
public class FileStat {
    private String url;
    private int size;
    private int complexity;
    private int totalSize;

    private int abstractMetric;
    private double instabilityMetric;

    public FileStat(String url, int size, int totalSize, int complexity){
        this.url = url;
        this.size = size;
        this.complexity = complexity;
        this.totalSize = totalSize;
        this.abstractMetric = 0;
        this.instabilityMetric = 0;
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

    public String getFileName(){
        return url.substring(url.lastIndexOf("/")+1);
    }

    public String getClasName(){
        return url.substring(url.lastIndexOf('/') + 1).replace(".java", "");
    }

    public int getAbstractMetric() {
        return abstractMetric;
    }

    public double getInstabilityMetric() {
        return instabilityMetric;
    }

    public void setMetric(int abstractMetric, double instabilityMetric){
        this.abstractMetric = abstractMetric;
        this.instabilityMetric = instabilityMetric;
    }

    @Override
    public String toString(){
        return url + "\t[Size:" + size + ", TotalSize:" + totalSize +", Complexity:"+ complexity + "]" ;
    }
}

