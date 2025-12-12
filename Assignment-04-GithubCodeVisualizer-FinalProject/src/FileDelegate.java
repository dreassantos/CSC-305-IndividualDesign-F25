package asalaz41;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

/**
 * FileDelegate uses a guihubutility that pulls the contents of a public repo to
 * calculate the  file stats by using a complexity calculator, metric calculator.
 * fileStats are saved to the Blackboard.
 * @author Andrea Santos, asalaz41
 * @version 1
 */
public class FileDelegate implements Runnable{
    private GithubUtil githubUtil;
    private Logger logger;

    public FileDelegate(GithubUtil githubUtil) {
        this.githubUtil = githubUtil;
        logger = LoggerFactory.getLogger(FileDelegate.class);
    }

    @Override
    public void run() {
        BlackBoard.getInstance().clear();

        Map<String, String> sourceRecords = githubUtil.getSourceFiles();

        MetricCalculator metricCalculator = new MetricCalculator(sourceRecords);
        ComplexityCalculator complexityCalculator = new ComplexityCalculator(sourceRecords);

        Thread metricThread = new Thread(metricCalculator);
        Thread complexityThread = new Thread(complexityCalculator);

        metricThread.start();
        complexityThread.start();

        try {
            metricThread.join();
            complexityThread.join();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }

        for(String path : sourceRecords.keySet()) {
            Metric metric = metricCalculator.getMetricForPath(path);
            logger.info("{}:{}",path,metric);
            Complexity complexity = complexityCalculator.getComplexityForPath(path);

            if(metric == null || complexity == null) {
                logger.error("Metric/Complexity Not Found:{}", path);
            }

            BlackBoard.getInstance().addFileStat(new FileStat(path, complexity, metric));
        }
        BlackBoard.getInstance().updateStatus(BlackBoard.FILE_READY_PROP);
    }
}
