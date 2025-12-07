package asalaz41;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * FilePanel creates a panel with a scroll panel that displays a filetree.
 * it uses a Controller to manage the user input.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class FilePanel extends JPanel implements PropertyChangeListener {
    private JScrollPane scrollPane;
    private Controller controller;
    private JTree tree;

    public FilePanel(Controller controller) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 600));

        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(10, 0));

        add(scrollPane, BorderLayout.CENTER);
        add(spacer, BorderLayout.WEST);

        this.controller = controller;
        BlackBoard.getInstance().addPropertyChangeListener(this);
    }

    public void updateFilePanel(ArrayList<FileStat> fileStats){
        Map<String, DefaultMutableTreeNode> nodeMap = new HashMap<>();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        nodeMap.put("",root);

        for(FileStat fileStat : fileStats) {
            String[] parts = fileStat.getURL().split("/+");

            StringBuilder currentPathSB = new StringBuilder();
            DefaultMutableTreeNode parent = root;

            for(String part: parts){
                if(part.isEmpty()){
                    continue;
                }

                if(!currentPathSB.isEmpty()){
                    currentPathSB.append("/");
                }
                currentPathSB.append(part);

                String fullPath = currentPathSB.toString();

                DefaultMutableTreeNode node = nodeMap.get(fullPath);
                if(node == null){
                    node = new DefaultMutableTreeNode(part);
                    parent.add(node);
                    nodeMap.put(fullPath, node);
                }
                parent = node;
            }
        }

        tree = new JTree(root);
        tree.addMouseListener(controller);
        controller.setRoot(tree);

        expandTree(tree, new TreePath(root));
        tree.setRootVisible(false);
        scrollPane.setViewportView(tree);
    }

    private void expandTree(JTree tree, TreePath parent){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getLastPathComponent();

        for (int i = 0; i < node.getChildCount(); i++) {
            TreeNode child = node.getChildAt(i);
            TreePath path = parent.pathByAddingChild(child);
            expandTree(tree, path);
        }

        tree.expandPath(parent);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case BlackBoard.FILE_READY_PROP:
                updateFilePanel((ArrayList<FileStat>) BlackBoard.getInstance().getAllFileStats());
                break;
            case BlackBoard.FILE_LOADING_PROP:
                scrollPane.setViewportView(null);
            default:
        }
    }
}
