package asalaz41;

import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * FileUtility is an observable blackboard that uses a GithubUtility to
 * pull files from public repos and stores the data in the FileStat objects.
 * @author Andrea Santos, asalaz41
 * @version 1
 */
public class FileUtility extends PropertyChangeSupport {
    private static FileUtility instance;
    private ArrayList<String> urlList;
    private FileStat[] fileStats;

    public static final String FILE_CELL_PROP = "cellUpdate";
    public static final String FILE_SELECTED_PROP = "selected";
    public static final String FILE_LOG_PROP = "logFile";

    private FileUtility(){super(new Object());}

    public static FileUtility getInstance(){
        if(instance == null){
            instance = new FileUtility();
        }
        return instance;
    }
    private void statusLOG(String msg){
        System.out.println(msg);
        firePropertyChange(FILE_LOG_PROP, null, msg);
    }
    public void processURL(String url) {
        ArrayList<String> localPaths = GithubUtility.getInstance().urlList(url);
        urlList = new ArrayList<>();
        for(String localPath: localPaths){
            if(localPath.endsWith(".java")){
                urlList.add(localPath);
            }else{
                statusLOG("localPath excluded: " + localPath + " - not a Java file");
            }
        }
        setStats(urlList);
        firePropertyChange(FILE_CELL_PROP, null, fileStats);

        if(urlList.size() == 0){
            statusLOG("Processing URL: No paths at URL: " + url);
        }else{
            statusLOG("Files Loaded: " + urlList.size());
        }
    }

    private void setStats(ArrayList<String> urlList){
        fileStats = new FileStat[urlList.size()];
        for(int i = 0; i < urlList.size(); i++){
            fileStats[i] = calculateFileStat(urlList.get(i));
        }
    }

    private FileStat calculateFileStat(String urlPath){
        String[] keywords = {"if", "for", "while", "switch", "case"};
        Map<String, Integer> keywordCounts = new HashMap<>();
        for (String k : keywords) keywordCounts.put(k, 0);

        String fileContent = GithubUtility.getInstance().getFileContent(urlPath);
        if(fileContent == null){
            statusLOG("Could not get file content: "+ urlPath);
            return null;
        }

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

    public void fileSelected(int currentCell) {
        statusLOG("File Selected " + fileStats[currentCell].toString());
        firePropertyChange(FILE_SELECTED_PROP, null, fileStats[currentCell].getURL());
    }
}
