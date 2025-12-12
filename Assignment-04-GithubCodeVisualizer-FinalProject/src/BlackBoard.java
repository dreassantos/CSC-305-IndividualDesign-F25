package asalaz41;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Blackboard manages the fileStats resource alerting listeners
 * whenever files are changed.
 *
 * @author Andrea Santos, asalaz41
 * @version 2
 */
public class BlackBoard extends PropertyChangeSupport {
    private static BlackBoard instance;

    public static final String FILE_LOG_PROP = "logStatus";
    public static final String FILE_READY_PROP = "cellUpdate";
    public static final String FILE_LOADING_PROP = "loading";
    public static final String FILE_NODE_SELECTED_PROP = "nodeSelected";

    private List<FileStat> fStats;
    private List<FileStat> selectedFileStats;
    private Logger logger;

    private BlackBoard(){
        super(new Object());
        fStats = new ArrayList<>();
        selectedFileStats = new ArrayList<>();
        logger = LoggerFactory.getLogger(BlackBoard.class);
    }

    public static BlackBoard getInstance(){
        if(instance == null){
            instance = new BlackBoard();
        }
        return instance;
    }

    public void statusLog(String msg){
        firePropertyChange(FILE_LOG_PROP, null, msg);
    }

    public void processURL(String url) {
        statusLog("Processing URL: " + url);
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

        logger.info("File Cell Selected: "+ stat);
        statusLog("File Cell Selected: "+ stat);
    }

    public void clear() {
        fStats.clear();
        selectedFileStats.clear();
    }

    public void addFileStat(FileStat fileStat) {
        fStats.add(fileStat);
    }

    public void updateStatus(String status) {
        switch (status){
            case FILE_READY_PROP:
                statusLog("Files Analyzed");
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
            fileParentPath = fileStat.getURL().replace("/"+fileStat.getFileName(),"");
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
