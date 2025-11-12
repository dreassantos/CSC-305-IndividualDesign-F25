package asalaz41;

import javax.swing.*;
import java.awt.*;

/***
 * Main creates a frame with a title panel and a start button.
 * The startbutton is controlled by the GameController.
 *
 * @author Andrea Salazar Santos, asalaz41
 * @version 1
 */
public class Main extends JFrame {
    private final String TITLE_BAR_NAME = " Andrea's Game of Life ";
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    public static void main(String[] args) {
        JFrame frame = new Main();
        frame.setTitle("Game of Life");
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public Main() {
        BorderLayout layout = new BorderLayout();
        setLayout(layout);


        JPanel namePanel = buildTitleBar();
        JPanel gameBoardPanel = new GameBoard();

        GameController gameController = new GameController();
        JPanel toolPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(1, 4);
        toolPanel.setLayout(gridLayout);
        JButton startButton = new JButton("Start");
        JButton clearButton = new JButton("Clear");
        JButton nextButton = new JButton("Next");
        JButton stopButton = new JButton("Stop");
        toolPanel.add(startButton);
        toolPanel.add(clearButton);
        toolPanel.add(nextButton);
        toolPanel.add(stopButton);

        startButton.addActionListener(gameController);
        clearButton.addActionListener(gameController);
        nextButton.addActionListener(gameController);
        stopButton.addActionListener(gameController);
        gameBoardPanel.addMouseListener(gameController);

        add(namePanel, BorderLayout.NORTH);
        add(gameBoardPanel, BorderLayout.CENTER);
        add(toolPanel, BorderLayout.SOUTH);
    }

    private void testFonts(){
        System.out.println("To Know the available font family names");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        System.out.println("Getting the font family names");

        // Array of all the fonts available in AWT
        String fonts[] = ge.getAvailableFontFamilyNames();

        // Getting the font family names
        for (String i : fonts) {
            System.out.println(i + " ");
        }
    }

    private JPanel buildTitleBar(){
        JPanel namePanel = new JPanel();
        GridLayout layout = new GridLayout(1, 1);
        namePanel.setLayout(layout);
        namePanel.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT/8));
        namePanel.setBackground(new Color(175, 211, 253));

        JLabel nameLabel = new JLabel(TITLE_BAR_NAME);
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 36));
        nameLabel.setForeground(new Color(80, 115, 170));
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nameLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        namePanel.add(nameLabel);
        return namePanel;
    }
}