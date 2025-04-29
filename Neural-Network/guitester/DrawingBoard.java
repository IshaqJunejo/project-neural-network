package guitester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawingBoard extends JPanel implements MouseListener, MouseMotionListener {
    private final int pixelSize = 20;
    private final int gap = 3;
    private final int rows = 28;
    private final int cols = 28;
    private double[][] intensity = new double[rows][cols];

    private Color currentColor = Color.BLACK;

    public DrawingBoard() {
        setPreferredSize(new Dimension(cols * (pixelSize + gap), rows * (pixelSize + gap)));
        addMouseListener(this);
        addMouseMotionListener(this);
        clearBoard();
    }

    private void clearBoard() {
        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
                //pixels[y][x] = Color.WHITE;
                intensity[y][x] = 0.0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw pixels
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                float value = (float) clamp(this.intensity[y][x], 0.0, 1.0);
                g.setColor(new Color(value, value, value));
                //g.setColor(pixels[y][x]);
                g.fillRect(x * (pixelSize + gap), y * (pixelSize + gap), pixelSize, pixelSize);
            }
        }

        // Drawing pixel borders
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                g.setColor(Color.GRAY);
                g.drawRect(x * (pixelSize + gap), y * (pixelSize + gap), pixelSize, pixelSize);
            }
        }
    }

    private void drawPixel(int x, int y) {
        int col = x / (pixelSize + gap);
        int row = y / (pixelSize + gap);
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            //pixels[row][col] = currentColor;
            intensity[row][col] = clamp(intensity[row][col] + 0.2, 0.0, 1.0);
        }
        repaint();
    }

    // Mouse events
    public void mousePressed(MouseEvent e) {
        drawPixel(e.getX(), e.getY());
    }

    public void mouseDragged(MouseEvent e) {
        drawPixel(e.getX(), e.getY());
    }

    // Clamp
    private double clamp (double val, double min, double max) {
        if (val > max) {
            return max;
        } else if (val < min) {
            return min;
        } else {
            return val;
        }
    }

    // Unused events
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
}
