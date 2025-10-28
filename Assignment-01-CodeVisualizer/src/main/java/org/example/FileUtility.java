package org.example;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * File Utility is a blackboard that holds the File stats for each file in the selected folder.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class FileUtility {
    private static DrawPanel drawPanel;
    private static FilePanel filePanel;
    private static JList<String> fileList;
    private static FileStat[] fileStat;
    private static int fid = 0;

    public static void SetDrawPanel(JPanel panel) {
        drawPanel = (DrawPanel) panel;
    }

    public static void SetFilePanel(JPanel panel) {
        filePanel = (FilePanel) panel;
    }

    public static void folderSelected() {
        fid = 0;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a folder to open");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            File[] files = selectedDirectory.listFiles(File::isFile);
            if(files == null) {
                return;
            }
            setStats(files);

            String[] fileNames = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                fileNames[i] = files[i].getName();
            }
            fileList = new JList<>(fileNames);
            fileList.setSelectedIndex(fid);

            filePanel.updateFilePanel();
            drawPanel.repaint();
        } else {
            System.out.println("Open operation cancelled by user.");
        }
    }

    public static JList<String> getFileList() {
        return fileList;
    }

    public static void fileSelected(int fd) {
        fid = fd;
        drawPanel.repaint();
    }

    private static void setStats(File[] files){
        fileStat = new FileStat[files.length];
        for(int i = 0; i < files.length; i++){
            fileStat[i] = calculateFileStat(files[i]);
        }
    }

    private static FileStat calculateFileStat(File file) {
        FileStat stat = new FileStat(file.getAbsolutePath());
        String[] keywords = {"if", "for", "while", "switch", "case"};
        Map<String, Integer> keywordCounts = new HashMap<>();
        for (String k : keywords) keywordCounts.put(k, 0);

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            String comments = "//.*";
            String strLiteral = "\".*?\"";
            String wordChars = "[^A-Za-z0-9_@]+";

            while ((line = reader.readLine()) != null) {
                stat.incrementSize();
                line = line.replaceAll(comments,"");
                line = line.replaceAll(strLiteral, "");
                String[] words = line.split(wordChars);

                for(String word : words) {
                    if(keywordCounts.containsKey(word)) {
                        stat.incrementComplexity();
                    }
                    if(word.equals("@author")) {
                        stat.setHasAuthor(true);
                    }
                    if(word.equals("@version")) {
                        stat.setHasVersion(true);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return stat;
    }

    public static int getOverall(){
        if(fileStat == null || fileStat.length==0) {
            return 0;
        }
        return fileStat[fid].getOverall();
    }

    public static int getSize() {
        if(fileStat == null || fileStat.length==0) {
            return 0;
        }
        return fileStat[fid].getSize();
    }

    public static int getComplexity() {
        if(fileStat == null|| fileStat.length==0) {
            return 0;
        }
        return fileStat[fid].getComplexity();
    }
}
