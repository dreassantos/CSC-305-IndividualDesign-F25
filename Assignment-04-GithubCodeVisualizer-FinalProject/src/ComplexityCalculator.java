package asalaz41;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * ComplexityCalculator calculates complexity stats for each sourcefile provided.
 * It is a runnable class that is used by the FileDelegator
 *
 * @author Andrea Santos, asalaz41
 * @version 1
 */
public class ComplexityCalculator implements Runnable {
    Map<String,String> sourceRecords;
    Map<String, Complexity> complexityRecords;
    Logger logger;

    public ComplexityCalculator(Map<String, String> sourceRecords) {
        logger = LoggerFactory.getLogger(Complexity.class);
        this.sourceRecords = sourceRecords;
        this.complexityRecords = new HashMap<>();
    }

    @Override
    public void run() {
        logger.info("Calculating Complexity");
        buildComplexityMap();
    }

    public Complexity getComplexityForPath(String path) {
        if(complexityRecords.containsKey(path)) {
            return complexityRecords.get(path);
        }
        return null;
    }

    private void buildComplexityMap() {
        for (Map.Entry<String,String> sourceRecord : sourceRecords.entrySet()) {
            Complexity complexity = calculateComplexity(sourceRecord.getKey(),sourceRecord.getValue());
            if(complexity != null) {
                complexityRecords.put(sourceRecord.getKey(),complexity);
            }
        }
    }

    private Complexity calculateComplexity(String localPath, String fileContent){
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
            logger.error("Error reading file: {}", localPath);
            return null;
        }
        return (new Complexity(size, totalSize, complexity));
    }
}
