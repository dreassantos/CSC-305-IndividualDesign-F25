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
    private Complexity complexity;
    private Metric metric;

    public FileStat(String url, Complexity complexity, Metric metric){
        this.url = url;
        this.complexity = complexity;
        this.metric = metric;
    }

    public Complexity getComplexityRecord(){
        return complexity;
    }

    public Metric getMetricRecord(){
        return metric;
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

    @Override
    public String toString(){
        return String.format("%s\t[Size: %d, TotalSize: %d, Complexity: %d \nMetrics[%s, %s]",
                getFileName(), complexity.getSize(), complexity.getTotalSize(), complexity.getComplexity(), metric.getExtendsDependents(),metric.getImplementsDependents());
    }
}

