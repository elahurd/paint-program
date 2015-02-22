

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainPanel extends JPanel {
    
    private PaintPanel paintPanel;
    private SelectorPanel selectorPanel;
    
    /**
     * This is the program's main panel that holds the other panels.
     * 
     * @param selectorPanel Panel to select paint color
     * @param paintPanel Panel to draw in
     */
    public MainPanel(SelectorPanel selectorPanel, PaintPanel paintPanel) {
        
        this.selectorPanel = selectorPanel;
        this.paintPanel = paintPanel;
        
        setPreferredSize(new Dimension(750, 750));
        
        setLayout(new BorderLayout());
        
        add(selectorPanel, BorderLayout.EAST);
        add(paintPanel, BorderLayout.CENTER);
        
    }
    
    /**
     * This is the main method.
     * 
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Paint");
        SelectorPanel selectorPanel = new SelectorPanel();
        PaintPanel paintPanel = new PaintPanel(selectorPanel);
        MainPanel panel = new MainPanel(selectorPanel, paintPanel);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(750, 750);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}
