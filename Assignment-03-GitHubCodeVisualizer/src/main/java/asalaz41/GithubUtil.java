package asalaz41;

import javiergs.tulip.GitHubHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/**
 * GithubUtility pulls the content of a file from a public GitHub url.
 * This utility is used by the FileDelegate. This is being used as a singleton
 * to not recreate the githubhandler.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */

public class GithubUtil {
    private final String TOKEN = "";
    private GitHubHandler gitHubHandler;
    private String rootURL;
    private Logger logger;

    public GithubUtil(String rootURL){
        this.rootURL = rootURL;
        gitHubHandler = new GitHubHandler(TOKEN);
        logger = LoggerFactory.getLogger(GithubUtil.class);
    }

    public ArrayList<String> urlList(){
        ArrayList<String> allFromURL = new ArrayList<>();
        try {
            allFromURL = (ArrayList<String>) gitHubHandler.listFilesRecursive(rootURL);
        } catch (Exception e) {
            logger.error("Could not access Repo: " + rootURL);
            BlackBoard.getInstance().statusLog("Could not access Repo: " + rootURL);
        }
        return allFromURL;
    }

    public String getFileContent(String localPath){
        String path = convertToBlobURL(rootURL,localPath);
        String buffer;
        try{
            buffer = gitHubHandler.getFileContentFromUrl(path);
        }catch (Exception e){
            logger.error("No Files at URL: " + rootURL);
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
        logger.info("Blob URL: " + blob);
        return blob;
    }
}

