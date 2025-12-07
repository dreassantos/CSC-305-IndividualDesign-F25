package asalaz41;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FileDelegate uses a guihubutility that pulls the contents of a public repo to
 * calculate the fle stats saved to the Blackboard.
 * @author Andrea Santos, asalaz41
 * @version 1
 */
public class FileDelegate implements Runnable{
    private GithubUtil githubUtil;
    private Map<String, int[]> metrics = new HashMap<>();
    private Logger logger;

    public FileDelegate(GithubUtil githubUtil) {
        this.githubUtil = githubUtil;
        logger = LoggerFactory.getLogger(FileDelegate.class);
    }

    @Override
    public void run() {
        BlackBoard.getInstance().clear();

        List<String> urlList = githubUtil.urlList();
        if(urlList.isEmpty()){
            return;
        }

        List<String> localPaths = new ArrayList<>();
        for(String localPath : urlList) {
            if(!localPath.endsWith(".java")) {
                logger.warn(String.format("localPath excluded: %s :not a Java file", localPath));
                continue;
            }
            localPaths.add(localPath);
            metrics.put(localPath, new int[]{0,0,0});
        }

        for (String localPath : localPaths) {
            logger.info("localPath: " + localPath);
            BlackBoard.getInstance().statusLog("Analyzing: "+ localPath);

            String fileContents = githubUtil.getFileContent(localPath);

            calculateMetrics(localPath, fileContents);
            FileStat fileStat = calculateFileStat(localPath,fileContents);

            BlackBoard.getInstance().addFileStat(fileStat);
        }

        BlackBoard.getInstance().addFileMetrics(metrics);
        BlackBoard.getInstance().updateStatus(BlackBoard.FILE_READY_PROP);
    }

    private FileStat calculateFileStat(String localPath, String fileContent){
        String[] keywords = {"while","for", "if", "case", "goto"};
        Map<String, Integer> keywordCounts = new HashMap<>();

        for (String k : keywords) keywordCounts.put(k, 0);
        int size = 0;
        int totalSize = 0;
        int complexity = 0;

        try(BufferedReader reader = new BufferedReader(new StringReader(fileContent))){
            String line;
            String comments = "//.*";
            String strLiteral = "\".*?\"";
            String wordChars = "[^A-Za-z0-9_@]+";

            while ((line = reader.readLine()) != null) {
                totalSize++;
                if(line.equals("")){
                    continue;
                }
                size++;
                line = line.replaceAll(comments,"");
                line = line.replaceAll(strLiteral, "");

                String[] words = line.split(wordChars);
                for(String word : words) {
                    if(keywordCounts.containsKey(word)) {
                        complexity++;
                    }
                }
            }
        }catch (IOException e){
            logger.error("Error reading file: " + localPath);
            e.printStackTrace();
        }
        return (new FileStat(localPath,size, totalSize, complexity));
    }

    private void calculateMetrics(String localPath, String contents){
        JavaParser parser = new JavaParser();
        ParseResult<CompilationUnit> result = parser.parse(contents);

        if(result.getResult().isEmpty()){
            logger.error("Could not parse"+ localPath);
            result.getProblems().forEach(System.out::println);
            return;
        }

        CompilationUnit cu = result.getResult().get();

        int abstractMetric = 0;
        String parentPath = localPath.substring(0, localPath.lastIndexOf('/'));
        for(ClassOrInterfaceDeclaration declaration: cu.findAll(ClassOrInterfaceDeclaration.class)){
            logger.info("Analyzing Class: "+ declaration.getNameAsString());

            if(declaration.isInterface() || declaration.isAbstract()){
                abstractMetric = 1;
            }

            List<String> extended = declaration.getExtendedTypes()
                    .stream()
                    .map(t->t.getNameAsString())
                    .collect(Collectors.toList());
            logger.info("Extended Types: "+ extended);

            List<String> implemented = declaration.getImplementedTypes()
                    .stream()
                    .map(t->t.getNameAsString())
                    .collect(Collectors.toList());
            logger.info("Implemented Types: "+ implemented);


            int outMetric = 0;
            for(String extClass : extended){
                outMetric += incRelatedClassInMetric(parentPath, extClass);
            }

            for(String impleClass : implemented){
                outMetric += incRelatedClassInMetric(parentPath, impleClass);
            }

            setCurClassMetrics(localPath, abstractMetric, outMetric);
        }
    }

    private int incRelatedClassInMetric(String parentPath, String className){
        String path = parentPath + "/" + className +".java";
        int inIndex = 1;
        if(metrics.containsKey(path)){
            metrics.get(path)[inIndex]+=1;
            return 1;
        }
        return 0;
    }

    private void setCurClassMetrics(String classPath, int abstractMetric, int outMetric){
        int abstractIndex = 0;
        int outIndex = 2;
        if(metrics.containsKey(classPath)){
            int[] metric = metrics.get(classPath);
            metric[abstractIndex] = abstractMetric;
            metric[outIndex]= outMetric;
        }
    }
}
