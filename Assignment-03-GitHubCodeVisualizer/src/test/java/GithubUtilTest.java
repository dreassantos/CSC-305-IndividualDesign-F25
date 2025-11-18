import asalaz41.GithubUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GithubUtilTest {
    @Test
    void blobTestWithoutTree(){
        String testRepo = "https://github.com/CSC3100/Pacman";
        String testLocal = "src/Maze.java";
        String exp = "https://github.com/CSC3100/Pacman/blob/main/src/Maze.java";
        String result;

        GithubUtil util = new GithubUtil(testRepo);
        result = util.convertToBlobURL(testRepo,testLocal);

        assertEquals(exp,result);
    }

    @Test
    void blobTestWithTree(){
        String testRepo = "https://github.com/CSC3100/Pacman/tree/main/src";
        String testLocal = "src/Maze.java";
        String exp = "https://github.com/CSC3100/Pacman/blob/main/src/Maze.java";
        String result;

        GithubUtil util = new GithubUtil(testRepo);
        result = util.convertToBlobURL(testRepo,testLocal);

        assertEquals(exp,result);
    }

    @Test
    void blobTestWithDeepTree(){
        String testRepo =  "https://github.com/CSC3100/App-Paint/tree/main/src/main/java/javiergs/paint/alpha";
        String testLocal = "src/main/java/javiergs/paint/alpha/DrawPanel.java";
        String exp = "https://github.com/CSC3100/App-Paint/blob/main/src/main/java/javiergs/paint/alpha/DrawPanel.java";
        String result;

        GithubUtil util = new GithubUtil(testRepo);
        result = util.convertToBlobURL(testRepo,testLocal);

        assertEquals(exp,result);
    }

    @Test
    void urlListNoTree(){
        String testRepo = "https://github.com/CSC3100/Pacman";
        GithubUtil util = new GithubUtil(testRepo);
        ArrayList<String> urls = util.urlList();
        for (String url : urls){
            System.out.println(url);
        }
    }

    @Test
    void urlListWTree(){
        String testRepo = "https://github.com/CSC3100/Pacman/tree/main/src";
        GithubUtil util = new GithubUtil(testRepo);
        ArrayList<String> urls = util.urlList();
        for (String url : urls){
            System.out.println(url);
        }
    }

    @Test
    void urlListWithDeepTree(){
        String testRepo = "https://github.com/CSC3100/App-Paint/tree/main/src/main/java/javiergs/paint/alpha";
        GithubUtil util = new GithubUtil(testRepo);
        ArrayList<String> urls = util.urlList();
        for (String url : urls){
            System.out.println(url);
        }
    }

    @Test
    void getFileContentTestWithoutTree(){
        String testRepo = "https://github.com/CSC3100/Pacman";
        String testLocal = "src/Maze.java";

        GithubUtil util = new GithubUtil(testRepo);
        String result = util.getFileContent(testLocal);
        System.out.println(result);
    }

    @Test
    void getFileContentWithTree(){
        String testRepo =  "https://github.com/CSC3100/App-Paint/tree/main/src/main/java/javiergs/paint/alpha";
        String testLocal = "src/main/java/javiergs/paint/alpha/DrawPanel.java";
        String exp = "https://github.com/CSC3100/App-Paint/blob/main/src/main/java/javiergs/paint/alpha/DrawPanel.java";

        GithubUtil util = new GithubUtil(testRepo);
        String result = util.getFileContent(testLocal);
        System.out.println(result);
    }
}
