

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;


public class SelectorPanel extends JPanel {
    
    private Color selectedColor;
    
    /**
     * This is the panel where the user will select the color to paint with.
     */
    public SelectorPanel() {
      
        
        setPreferredSize(new Dimension(100, 750));
        GridLayout gridLayout = new GridLayout(6, 1);
        setLayout(gridLayout);
        
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.BLACK, Color.YELLOW, Color.ORANGE};
        
        for (int i = 0; i < colors.length; i++) {
            ColorSelector colorSelector = new ColorSelector(colors[i]);
            add(colorSelector, i, 0);
        }
        
        //initial color
        setSelectedColor(Color.BLACK);
    }
    
    public void setSelectedColor(Color color) {
        this.selectedColor = color;
    }
    
    public Color getSelectedColor() {
        return selectedColor;
    }

}
