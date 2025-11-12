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

public class GithubUtility {
    private static GithubUtility instance;
    private final String TOKEN = "";
    private GitHubHandler gitHubHandler;
    private String rootURL;

    private GithubUtility(){
        gitHubHandler = new GitHubHandler(TOKEN);
    }

    public static GithubUtility getInstance(){
        if(instance == null){
            instance = new GithubUtility();
        }
        return instance;
    }

    public ArrayList<String> urlList(String url){
        rootURL = url;
        ArrayList<String> allFromURL = new ArrayList<>();
        try {
            allFromURL = (ArrayList<String>) gitHubHandler.listFilesRecursive(url);
        } catch (Exception e) {
            System.out.println("No Files at URL: " + url);
        }
        return allFromURL;
    }

    public String getFileContent(String localPath){
        String path = rootURL+"/blob/main/"+localPath;
        String buffer;
        try{
            buffer = gitHubHandler.getFileContentFromUrl(path);
        }catch (Exception e){
            System.out.println("No Files at URL: " + rootURL);
            buffer = "";
        }
        return buffer;
    }
}

