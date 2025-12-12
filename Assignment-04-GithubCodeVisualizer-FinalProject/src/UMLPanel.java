package asalaz41;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
/**
 * UMLPanel creates a panel with a UML graphic.
 * Graphic is generated from the PLANTUML library
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class UMLPanel extends JPanel  {

    private BufferedImage image;

    public UMLPanel(String umlSource) {
        setBackground(Color.white);
        try{
            SourceStringReader reader = new SourceStringReader(umlSource);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            reader.outputImage(os, new FileFormatOption(FileFormat.PNG));
            os.close();

            image = javax.imageio.ImageIO.read(
                    new java.io.ByteArrayInputStream(os.toByteArray())
            );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(image != null){
            g.drawImage(image, 0, 0, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if(image != null ){
            return new Dimension(image.getWidth(), image.getHeight());
        }
        return super.getPreferredSize();
    }
}
