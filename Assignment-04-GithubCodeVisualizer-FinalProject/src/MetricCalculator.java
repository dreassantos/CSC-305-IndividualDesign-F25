package asalaz41;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/***
 * MetricCalculator calculates the metrics for all source files into metric objects.
 * This is a runnable class used by the FileDelegator.
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class MetricCalculator implements Runnable {
    private final Map<String, String> sourceRecords;
    private final Map<String, Metric> metricRecords;
    private final Logger logger;

    MetricCalculator(Map<String,String> sourceRecords){
        this.logger = LoggerFactory.getLogger(MetricCalculator.class);
        this.sourceRecords = sourceRecords;
        this.metricRecords = new HashMap<>();
    }

    @Override
    public void run() {
        logger.info("Calculating Metrics");
        buildMetricMap();
        calculateIncomingMetrics();
    }

    public Metric getMetricForPath(String path) {
        if(metricRecords.containsKey(path)){
            return metricRecords.get(path);
        }
        return null;
    }

    private void buildMetricMap(){
        for(Map.Entry<String, String> record : sourceRecords.entrySet()){
            String path = record.getKey();
            String parentPath = path.substring(0, path.lastIndexOf('/'));
            String fileContents = record.getValue();

            CompilationUnit compilationUnit = getCompilationUnit(fileContents);
            if(compilationUnit == null){
                logger.error("Skipping {}, could not parse",path);
                continue;
            }

            ClassOrInterfaceDeclaration declaration = getTopClassDeclaration(path, compilationUnit);
            if(declaration == null){
                logger.error("No top-level class declaration found in{}", path);
                continue;
            }

            Metric metric = buildMetric(path, parentPath, declaration);
            metricRecords.put(path, metric);
        }
    }

    private CompilationUnit getCompilationUnit(String fileContents){
        JavaParser parser = new JavaParser();
        ParseResult<CompilationUnit> result = parser.parse(fileContents);
        return result.getResult().orElse(null);
    }

    private ClassOrInterfaceDeclaration getTopClassDeclaration(String path, CompilationUnit compilationUnit){
        String className = path.substring(path.lastIndexOf("/")+1).replace(".java","");

        for(ClassOrInterfaceDeclaration declaration: compilationUnit.findAll(ClassOrInterfaceDeclaration.class)) {
            if (!declaration.getNameAsString().equals(className)) {
                continue;
            }
            return declaration;
        }
        return null;
    }

    private Metric buildMetric(String path, String parentPath,ClassOrInterfaceDeclaration declaration){
        logger.info("Analyzing Class Metrics:{}", declaration.getNameAsString());

        boolean abstractFlag = computeAbstractMetric(declaration);

        List<String> extendedDependents = computeBaseClassFromClassTypes(parentPath, declaration.getExtendedTypes());
        List<String> implementedDependents = computeBaseClassFromClassTypes(parentPath,
                                                                                declaration.getImplementedTypes());
        List<String> compositionDependents = computeCompositionDependents(parentPath, declaration);
        List<String> aggregationDependents = computeAggregationDependents(parentPath, declaration, extendedDependents);
        List<String> associationDependents = computeAssociationDependents(parentPath, declaration);
        List<String> weakDependents = computeParametricDependents(parentPath, declaration);

        return new Metric(
                abstractFlag,
                extendedDependents,
                implementedDependents,
                compositionDependents,
                aggregationDependents,
                associationDependents,
                weakDependents
        );
    }

    private boolean computeAbstractMetric(ClassOrInterfaceDeclaration declaration){
        return declaration.isInterface() || declaration.isAbstract();
    }

    private List<String> computeBaseClassFromClassTypes(String parentPath,
                                                        NodeList<ClassOrInterfaceType> dependentTypes) {
        List<String>inPackage = new ArrayList<>();
        for(ClassOrInterfaceType dependentType: dependentTypes){
            String className = dependentType.getNameAsString();
            if(isInPackage(className, parentPath)){
                inPackage.add(className);
            }
        }
        return inPackage;
    }

    private Boolean isInPackage(String className, String parentPath){
        String fullPath = parentPath + "/" + className + ".java";
        return sourceRecords.containsKey(fullPath);
    }

    private List<String> computeCompositionDependents(String parentPath, ClassOrInterfaceDeclaration declaration){
        List<String> result = new ArrayList<>();
        for(VariableDeclarator variable: getGlobalVarDeclarations(parentPath, declaration)){
            if(variable.getInitializer().isPresent() && !result.contains(variable.getTypeAsString())){
                result.add(variable.getTypeAsString());
            }
        }
        return result;
    }

    private List<String> computeAggregationDependents(String parentPath,
                                                      ClassOrInterfaceDeclaration declaration,
                                                      List<String>extendedDependents){
        List<String> result = new ArrayList<>();
        for(VariableDeclarator variable: getGlobalVarDeclarations(parentPath, declaration)){
            String className = variable.getTypeAsString();
            if(!variable.getInitializer().isPresent()
                    && !result.contains(className)
                    && !extendedDependents.contains(className)){
                result.add(className);
            }
        }
        return result;
    }

    private List<String> computeAssociationDependents(String parentPath, ClassOrInterfaceDeclaration declaration){
        List<String> result = new ArrayList<>();
        for(VariableDeclarator variable: getMethodAndConstructorVariables(parentPath, declaration)){
            String className = declaration.getNameAsString();
            String typeName = variable.getTypeAsString();
            if(!typeName.equals(className) && !result.contains(variable.getTypeAsString())){
                result.add(typeName);
            }
        }
        return result;
    }

    private List<String> computeParametricDependents(String parentPath, ClassOrInterfaceDeclaration declaration){
        List<String> result = new ArrayList<>();
        for(Parameter parameter : getMethodParameterVariable(parentPath, declaration)){
            String paramTypeName = parameter.getTypeAsString();
            String className = declaration.getNameAsString();
            if(paramTypeName.equals(className) || result.contains(paramTypeName)){
                continue;
            }
            result.add(paramTypeName);
        }
        return result;
    }

    private List<Parameter> getMethodParameterVariable(String parentPath, ClassOrInterfaceDeclaration declaration){
        List<Parameter> parameters = new ArrayList<>();
        for(MethodDeclaration method : declaration.getMethods()){
            for(Parameter parameter : method.getParameters()){
                if(parameter.getType().isPrimitiveType() || !isInPackage(parameter.getTypeAsString(), parentPath)){
                    continue;
                }
                parameters.add(parameter);
            }
        }
        return parameters;
    }

    private List<VariableDeclarator> getMethodAndConstructorVariables(String parentPath,
                                                                      ClassOrInterfaceDeclaration declaration){
        List<VariableDeclarationExpr> variableDeclarationExprs = new ArrayList<>();
        List<VariableDeclarator> variables = new ArrayList<>();

        for(ConstructorDeclaration constructor: declaration.getConstructors()){
            variableDeclarationExprs.addAll(constructor.getBody().findAll(VariableDeclarationExpr.class));
        }

        for (MethodDeclaration method : declaration.getMethods()) {
            if(method.getBody().isPresent()){
                variableDeclarationExprs.addAll(method.getBody().get().findAll(VariableDeclarationExpr.class));
            }
        }

        for(VariableDeclarationExpr expr: variableDeclarationExprs){
            for(VariableDeclarator variable : expr.getVariables()) {
                String classType = variable.getTypeAsString();
                if(variable.getType().isPrimitiveType() || !isInPackage(classType, parentPath)){
                    continue;
                }
                variables.add(variable);
            }
        }
        return variables;
    }

    private List<VariableDeclarator> getGlobalVarDeclarations(String parentPath,
                                                              ClassOrInterfaceDeclaration declaration) {
        List<VariableDeclarator> result = new ArrayList<>();
        for(FieldDeclaration field : declaration.getFields()){
            for(VariableDeclarator variable : field.getVariables()) {
                String classType = variable.getTypeAsString();
                if(variable.getType().isPrimitiveType() || !isInPackage(classType, parentPath)){
                    continue;
                }
                result.add(variable);
            }
        }
        return result;
    }

    private void calculateIncomingMetrics(){
        for(Map.Entry<String, Metric> metricsRecord : metricRecords.entrySet()){

            String path = metricsRecord.getKey();
            String parentPath = path.substring(0, path.lastIndexOf("/"));

            Metric metric = metricsRecord.getValue();
            List<String> outgoingClassDependencies = metric.getAllOutgoingDep();

            for(String outClass : outgoingClassDependencies){
                String outClassPath = parentPath + "/" + outClass + ".java";
                if(metricRecords.containsKey(outClassPath)){
                    metricRecords.get(outClassPath).incrementIncomingDependencies();
                }
            }
        }
    }
}
