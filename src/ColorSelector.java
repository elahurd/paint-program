

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;


@SuppressWarnings("serial")
public class ColorSelector extends JComponent {
    
    private Color color;
    private final int width = 50;
    private final int height = 50;
    
    /**
     * ColorSelector constructor.
     * 
     * @param color Color of the color selector 
     */
    public ColorSelector(Color color) {
        
        this.color = color;
        setPreferredSize(new Dimension(width, height));
        
        MouseInputAdapter mia = new MouseInputAdapter() {
            public void mousePressed(MouseEvent event) {
                if (event.getX() >= getBounds().getX() + ((getWidth() - width) / 2) &&
                    event.getX() <= getBounds().getX() + getWidth() - ((getWidth() - width) / 2) &&
                    event.getY() >= getParent().getBounds().getY() + ((getHeight() - height) / 2) &&
                    event.getY() <= getParent().getBounds().getY() + getHeight() - ((getHeight() - height) / 2)) {
          
                    ((SelectorPanel)getParent()).setSelectedColor(color);
                    ((SelectorPanel)getParent()).getSelectedColor();
                } 
            }
        };
        
        addMouseListener(mia);
    }
    
    /**
     * Color selector paints itself.
     */
    public void paintComponent(Graphics graphics) {
        
        Graphics2D g2d = (Graphics2D)graphics;
        g2d.setColor(color);
        
        //increase selector size to show which color is being used
        if (((SelectorPanel)getParent()).getSelectedColor() == color) {
            g2d.fillRect(width / 2 - 10, height / 2 - 10, width + 20, height + 20);
        } else {
            g2d.fillRect(width / 2, height / 2, width, height);
        }
        
        repaint();
        revalidate();
    }
    
    /**
     * This returns this color of the color selector.
     * 
     * @return the color of the color selector
     */
    public Color getColor() {
        return color;
    }

}
