package asalaz41;

import javiergs.tulip.GitHubHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * GithubUtility pulls the content of a file from a public GitHub url.
 * This utility is used by the FileDelegate.
 * Loads a githubtoken from "resources/config.properties"
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 2
 */

public class GithubUtil {
    private GitHubHandler gitHubHandler;
    private String rootURL;
    private Logger logger;

    public GithubUtil(String rootURL){
        this.rootURL = rootURL;
        try{
            gitHubHandler = new GitHubHandler(loadTokenFromConfig());
        }catch (IOException e){
            e.printStackTrace();
        }
        logger = LoggerFactory.getLogger(GithubUtil.class);
    }

    public Map<String, String> getSourceFiles(){
        Map<String, String> sourceFiles = new HashMap<>();

        for(String url: javaURLList()){
            sourceFiles.put(url, getFileContent(url));
        }
        return sourceFiles;
    }

    public List<String> javaURLList() {
        return urlList().stream()
                .filter(url->url.endsWith(".java"))
                .toList();
    }

    public List<String> urlList(){
        logger.info("loading urls");
        List<String> allFromURL = new ArrayList<>();
        try {
            allFromURL = gitHubHandler.listFilesRecursive(rootURL);
        } catch (Exception e) {
            logger.error("Could not access Repo: {}",rootURL);
            BlackBoard.getInstance().statusLog("Could not access Repo: " + rootURL);
        }
        return allFromURL;
    }

    public String getFileContent(String localPath){
        BlackBoard.getInstance().statusLog("Loading file: " + localPath);
        String path = convertToBlobURL(rootURL,localPath);
        String buffer;
        try{
            buffer = gitHubHandler.getFileContentFromUrl(path);
        }catch (Exception e){
            logger.error("No Files at URL: {}", rootURL);
            buffer = "";
        }
        return buffer;
    }

    //Based on Professors example Assignment 2
    public String convertToBlobURL(String url, String path){
        String blob;
        if(url.contains("/tree/")){
            String[] parts = url.split("/tree/");
            blob = String.format("%s/blob/%s/%s",parts[0] , parts[1].split("/")[0],path);
        }else{
            blob = String.format("%s/blob/main/%s",url,path);
        }
        logger.info("Blob URL: {}", blob);
        return blob;
    }

    private String loadTokenFromConfig() throws IOException {
        Properties properties = new Properties();
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")){
            if(input == null){
                throw new IOException("config.properties not found in classpath.");
            }
            properties.load(input);
            return properties.getProperty("github.token");
        }
    }
}

