package asalaz41;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setTitle("Lab-05-MavenGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Main() {
        JPanel panel = new JPanel();
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Assignments", 25);
        dataset.setValue("Labs + Quizes", 25);
        dataset.setValue("Attandance & Participation", 10);
        dataset.setValue("Final Project", 20);
        dataset.setValue("Final Exam", 20);

        PieDataset pieDataset = dataset;

        JFreeChart chart = ChartFactory.createPieChart("Syllabus Grade Percentages", pieDataset, false,true,false);
        chart.setBackgroundPaint(Color.LIGHT_GRAY);
        chart.getPlot().setBackgroundPaint(Color.BLUE);
        chart.getPlot().setOutlineVisible(false);

        ChartPanel chartPanel = new ChartPanel(chart);
        panel.setLayout(new GridLayout(1,1));

        panel.add(chartPanel);
        add(panel);
    }
}
