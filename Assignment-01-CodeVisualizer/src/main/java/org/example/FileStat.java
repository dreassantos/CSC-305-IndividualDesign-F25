package org.example;
/**
 * FileStat contains the information on each file.
 * Data used in FileUtility.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class FileStat {
    private String name;
    private int size;
    private int overall;
    private int complexity;
    private boolean hasAuthor;
    private boolean hasVersion;

    public FileStat(String name){
        this.name = name;
        this.size = 0;
        this.complexity = 0;
        this.overall = 0;
        this.hasAuthor = false;
        this.hasVersion = false;
    }

    public void setHasAuthor(boolean hasAuthor) {
        this.hasAuthor = hasAuthor;
    }

    public void setHasVersion(boolean hasVersion) {
        this.hasVersion = hasVersion;
    }

    public int getSize() {
        return size;
    }

    public int getOverall() {
        if(hasAuthor&&hasVersion){
            overall = 1;
        }
        return overall;
    }

    public int getComplexity(){
        return complexity;
    }

    public void incrementSize() {
        size++;
    }

    public void incrementComplexity() {
        complexity++;
    }
}
