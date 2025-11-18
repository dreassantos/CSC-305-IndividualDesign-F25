package asalaz41;
import javiergs.tulip.GitHubHandler;
import java.util.ArrayList;
/**
 * GithubUtility pulls the content of each file from a public GitHub url.
 * This utility is used by the FileUtility. This is being used as a singleton
 * to not recreate the githubhandler.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */

public class GithubUtil {
    private final String TOKEN = "";
    private GitHubHandler gitHubHandler;
    private String rootURL;

    public GithubUtil(String rootURL){
        this.rootURL = rootURL;
        gitHubHandler = new GitHubHandler(TOKEN);
    }

    public ArrayList<String> urlList(){
        ArrayList<String> allFromURL = new ArrayList<>();
        try {
            allFromURL = (ArrayList<String>) gitHubHandler.listFilesRecursive(rootURL);
        } catch (Exception e) {
            System.out.println("No Files at URL: " + rootURL);
        }
        return allFromURL;
    }

    public String getFileContent(String localPath){
        //String path = rootURL+"/blob/main/"+localPath;
        String path = convertToBlobURL(rootURL,localPath);
        String buffer;
        try{
            buffer = gitHubHandler.getFileContentFromUrl(path);
        }catch (Exception e){
            System.out.println("No Files at URL: " + rootURL);
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
            //TODO: ASK Why are we replacing tree if in the else condition.
            //blob = String.format("%s/%s",url.replace("/tree/","/blob/"), path);
            blob = String.format("%s/blob/main/%s",url,path);
        }
        System.out.println("Blob: "+ blob);
        return blob;
    }
}

