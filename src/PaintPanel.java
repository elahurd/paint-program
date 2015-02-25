

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;


/**
 * This is the panel where the user's drawing occurs.
 * 
 * @author Evan LaHurd
 */
public class PaintPanel extends JPanel implements KeyListener {
    
    private SelectorPanel selectorPanel;
    
    private int leftBound;
    private int rightBound;
    private int bottomBound;
    private int topBound;
    
    private boolean entering;
    private boolean rightReleased;
    
    private ArrayList<Point2D> pointArray;
    
    private HashMap<Path2D.Double, Color> pathMap;
    private Path2D.Double selectedPath;
    
    private Path2D.Double rightPath;
    
    private int strokeWidth;
    
    private Color color;
    
    private SigerRecognizer siger;

    /**
     * This is the panel that the user will paint in.
     * 
     * @param selectorPanel Reference to the panel where the user can select a color to use
     */
    public PaintPanel(SelectorPanel selectorPanel) {
        
        siger = new SigerRecognizer();
        
        this.selectorPanel = selectorPanel; 
        
        setPreferredSize(new Dimension(650, 750));
        
        color = new Color(0, 0, 0);
        
        entering = false;
        rightReleased = true;
        
        pointArray = new ArrayList<Point2D>();
        
        pathMap = new HashMap<Path2D.Double, Color>();
        rightPath = new Path2D.Double();
        
        rightPath = new Path2D.Double();
        
        strokeWidth = 3;

        MouseInputAdapter mia = new MouseInputAdapter() {

            public void mousePressed(MouseEvent event) { 
                if (event.getX() >= leftBound && event.getX() <= rightBound && event.getY() <= bottomBound && event.getY() >= topBound) {
                    if (SwingUtilities.isRightMouseButton(event)) {
                        rightPath.moveTo(event.getX(), event.getY());
                        rightReleased = false;
                        pointArray.add(rightPath.getCurrentPoint());
                    } else {
                        Path2D.Double path = new Path2D.Double();
                        selectedPath = path;
                        if (color == null) {
                            pathMap.put(path, new Color(0, 0, 0));
                        } else {
                            pathMap.put(path, color);
                        }
                        selectedPath.moveTo(event.getX(), event.getY());
                    }
                } 
            }

            public void mouseDragged(MouseEvent event) {
          
                if (event.getX() >= leftBound && event.getX() <= rightBound && event.getY() <= bottomBound && event.getY() >= topBound) {
                    if (SwingUtilities.isRightMouseButton(event)) {
                        rightReleased = false;
                        rightPath.lineTo(event.getX(), event.getY());
                        pointArray.add(rightPath.getCurrentPoint());
                    } else {
                        if (entering) {
                            selectedPath.moveTo(event.getX(), event.getY());
                        } else if (selectedPath != null) { 
                            selectedPath.lineTo(event.getX(), event.getY());
                        }
                        entering = false;
                    }
                } else {
                    entering = true;
                }
            }
            
            public void mouseReleased(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    SigerRecognizer.PatternIndex index = siger.matchToTemplates(siger.buildDirectionVector(pointArray));
                    switch (index) {
                        case RED:
                            selectorPanel.setSelectedColor(Color.RED);
                            break;
                        case GREEN:
                            selectorPanel.setSelectedColor(Color.GREEN);
                            break;
                        case BLUE:
                            selectorPanel.setSelectedColor(Color.BLUE);
                            break;
                        case BLACK:
                            selectorPanel.setSelectedColor(Color.BLACK);
                            break;
                        case YELLOW:
                            selectorPanel.setSelectedColor(Color.YELLOW);
                            break;
                        case ORANGE:
                            selectorPanel.setSelectedColor(Color.ORANGE);
                            break;
                        case CLEAR:
                            clearCanvas();
                            break;
                        case NONE:
                            break;
                        default:
                            break;
                    }
                }
                
                rightReleased = true;
                rightPath = new Path2D.Double();
                pointArray.clear();
            }
        };
        
        addMouseListener(mia);
        addMouseMotionListener(mia);
        addKeyListener(this);
        setFocusable(true);
    }

    /**
     * This is where the panel paints itself.
     * 
     * @param graphics The graphics object
     */
    public void paintComponent(Graphics graphics) {
    
        Graphics2D g2d = (Graphics2D)graphics;
        super.paintComponent(g2d);
        
        setBackground(new Color(255, 255, 255));
        
        setColor(selectorPanel.getSelectedColor());
        g2d.setStroke(new BasicStroke(strokeWidth));
    
        leftBound = this.getBounds().x;
        rightBound = this.getBounds().x + this.getWidth();
        topBound = this.getBounds().y;
        bottomBound = this.getBounds().y + this.getHeight();
        
        for (Entry<Path2D.Double, Color> entry : pathMap.entrySet()) {
            g2d.setColor(entry.getValue());
            g2d.draw(entry.getKey());
        }
        
        if (!rightReleased) {
            g2d.setColor(new Color(255, 0, 0));
            g2d.draw(rightPath);
        }
        
        repaint();
        revalidate();
    }
    
    /**
     * This checks for key presses.
     * 
     * @param event The event of the key being pressed
     */
    public void keyTyped(KeyEvent event) {
        if (event.getKeyChar() == 'c') {
            clearCanvas();
        }
    }
    
    /**
     * This clears the canvas of any drawings.
     */
    private void clearCanvas() {
        for (Entry<Path2D.Double, Color> entry : pathMap.entrySet()) {
            entry.getKey().reset();
        }
        pathMap.clear();
        repaint();
        revalidate();
    }
    
    /**
     * Sets the active color to the specified color.
     * 
     * @param color The color to set the active color to.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    //unimplemented method
    public void keyPressed(KeyEvent event) {}
    
    //unimplemented method
    public void keyReleased(KeyEvent event) {}

    

}
