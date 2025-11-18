package asalaz41;

import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * FileUtility is an observable blackboard that uses a GithubUtility to
 * pull files from public repos and stores the data in the FileStat objects.
 * @author Andrea Santos, asalaz41
 * @version 1
 */
public class BlackBoard extends PropertyChangeSupport {
    private static BlackBoard instance;
    public static final String FILE_LOG_PROP = "logFile";
    public static final String FILE_READY_PROP = "cellUpdate";
    public static final String FILE_LOADING_PROP = "loading";
    public static final String FILE_SELECTED_PROP = "selected";
    public static final String FILE_NODE_SELECTED_PROP = "fileNodeSelected";

    private List<FileStat> fStats;
    private List<FileStat> selectedFileStats;

    private BlackBoard(){
        super(new Object());
        fStats = new ArrayList<>();
        selectedFileStats = new ArrayList<>();
    }

    public static BlackBoard getInstance(){
        if(instance == null){
            instance = new BlackBoard();
        }
        return instance;
    }

    public void statusLog(String msg){
        System.out.println(msg);
        firePropertyChange(FILE_LOG_PROP, null, msg);
    }

    public void processURL(String url) {
        GithubUtil ghUtil = new GithubUtil(url);
        FileDelegate fd = new FileDelegate(ghUtil);
        Thread thread = new Thread(fd);
        thread.start();
    }

    public void fileSelected(int currentCell) {
        if(currentCell < 0 || currentCell >= selectedFileStats.size()){
            return;
        }
        FileStat stat = selectedFileStats.get(currentCell);

        statusLog("File Cell Selected: "+ selectedFileStats.get(currentCell).toString());
        firePropertyChange(FILE_SELECTED_PROP, null, stat);
    }

    public void clear() {
        fStats.clear();
        selectedFileStats.clear();
    }

    public void addFileStat(FileStat fileStat) {
        fStats.add(fileStat);
    }

    public void updateStatus(String status) {
        System.out.println("Updating status as: "+ status);
        switch (status){
            case FILE_READY_PROP:
                firePropertyChange(FILE_READY_PROP, null, status);
                break;
            case FILE_LOADING_PROP:
                firePropertyChange(FILE_LOADING_PROP, null, status);
                break;
            default:
                break;
        }
    }

    public List<FileStat> getAllFileStats() {
        return fStats;
    }

    public List<FileStat> getSelectedFileStats(){
        return selectedFileStats;
    }

    public void FolderSelected(String path) {
        selectedFileStats.clear();
        String fileParentPath;
        for(FileStat fileStat : fStats){
            fileParentPath = fileStat.getURL().replace("/"+fileStat.getName(),"");
            if(path.equals(fileParentPath)){
                selectedFileStats.add(fileStat);
            }
        }

        if(selectedFileStats.isEmpty()){
            firePropertyChange(FILE_READY_PROP, null, true);
        }else{
            firePropertyChange(FILE_NODE_SELECTED_PROP, null, selectedFileStats);
        }
    }
}
