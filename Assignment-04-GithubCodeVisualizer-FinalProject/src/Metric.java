package asalaz41;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Metric holds all the metric relationship properties for a file.
 * These properties are used for the metrics calculation.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class Metric {
    private final List<String> extendsDependents;
    private final List<String> implementsDependents;
    private final List<String> composition;
    private final List<String> aggregation;
    private final List<String> association;
    private final List<String> weakDependencies;
    private final boolean abstractFlag;
    private int inDependencies;
    private final int outDependencies;
    private final Set<String> allOutgoingDep;

    public Metric(boolean abstractFlag,
                  List<String> extendedClasses,
                  List<String> implementedClasses,
                  List<String> composition,
                  List<String> aggregation,
                  List<String> association,
                  List<String> weakDependencies){
        this.abstractFlag = abstractFlag;
        this.extendsDependents = extendedClasses;
        this.implementsDependents = implementedClasses;
        this.composition = composition;
        this.aggregation = aggregation;
        this.association = association;
        this.weakDependencies = weakDependencies;
        this.inDependencies = 0;
        this.allOutgoingDep = new HashSet<>();
        allOutgoingDep.addAll(extendsDependents);
        allOutgoingDep.addAll(implementsDependents);
        allOutgoingDep.addAll(composition);
        allOutgoingDep.addAll(aggregation);
        allOutgoingDep.addAll(association);
        allOutgoingDep.addAll(weakDependencies);
        this.outDependencies = allOutgoingDep.size();
    }

    public int getAbstract(){
        if(abstractFlag){
            return 1;
        }
        return 0;
    }

    public int getInstability() {
        int total = inDependencies + outDependencies;
        if(total == 0){
            return 0;
        }
        return outDependencies/total;
    }

    public List<String> getExtendsDependents() {
        return extendsDependents;
    }

    public List<String> getImplementsDependents() {
        return implementsDependents;
    }

    public void incrementIncomingDependencies() {
        inDependencies++;
    }

    @Override
    public String toString(){
        return String.format("in:%d,out:%d,composition: %s, aggregation: %s, " +
                        "association: %s, weak dependencies: %s\n",
                inDependencies,
                outDependencies,
                composition,
                aggregation,
                association,
                weakDependencies);
    }

    public String relationStringDeclarator(String currentClass){
        if(abstractFlag){
            return "abstract " + currentClass + "\n";
        }
        return "class " + currentClass + "\n";
    }

    public String relationString(String curentClass){
        StringBuilder sb = new StringBuilder();
        for(String className : extendsDependents){
            sb.append(className + " <|-- " + curentClass + "\n");
        }
        for(String className : implementsDependents){
            sb.append(className + " <|.. " + curentClass + "\n");
        }
        for(String className : composition){
            sb.append(curentClass + " *-- " + className + "\n");
        }
        for(String className : aggregation){
            sb.append(curentClass + " o-- " + className + "\n");
        }
        for(String className : association){
            sb.append(curentClass + " --> " + className + "\n");
        }
        for(String className : weakDependencies){
            sb.append(curentClass + " ..> " + className+ "\n");
        }
        return sb.toString();
    }
    public List<String> getAllOutgoingDep() {
        return allOutgoingDep.stream().toList();
    }
}