package asalaz41;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDelegate implements Runnable{
    GithubUtil githubUtil;

    public FileDelegate(GithubUtil githubUtil) {
        this.githubUtil = githubUtil;
    }

    @Override
    public void run() {
        BlackBoard.getInstance().clear();
        List<String> localPaths = githubUtil.urlList();
        for (String localPath : localPaths) {
            if(!localPath.endsWith(".java")) {
                BlackBoard.getInstance().statusLog("localPath excluded: " + localPath + " - not a Java file");
                continue;
            }
            FileStat fileStat = calculateFileStat(localPath);
            BlackBoard.getInstance().addFileStat(fileStat);
        }
        BlackBoard.getInstance().updateStatus(BlackBoard.FILE_READY_PROP);
    }

    private FileStat calculateFileStat(String urlPath){
        String[] keywords = {"if", "for", "while", "switch", "case"};
        Map<String, Integer> keywordCounts = new HashMap<>();
        for (String k : keywords) keywordCounts.put(k, 0);

        String fileContent = githubUtil.getFileContent(urlPath);
        if(fileContent == null){
            BlackBoard.getInstance().statusLog("Could not find contents of");
            return null;
        }
        BlackBoard.getInstance().statusLog("Calculating File Stats: " + urlPath);
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
            e.printStackTrace();
        }

        FileStat stat = new FileStat(urlPath, size, totalSize, complexity);

        return stat;
    }
}
