package guitester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawingBoard extends JPanel implements MouseListener, MouseMotionListener {
    private final int pixelSize = 16;
    private final int gap = 4;
    private final int rows = 28;
    private final int cols = 28;
    private final int brushSize = 2;
    private final double brushIntensity = 0.4;
    private double[][] intensity = new double[rows][cols];

    private Color currentColor = Color.BLACK;

    public DrawingBoard() {
        setPreferredSize(new Dimension(cols * (pixelSize + gap), rows * (pixelSize + gap)));
        addMouseListener(this);
        addMouseMotionListener(this);
        clearBoard();
    }

    public void clearBoard() {
        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
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

    private void drawBrush (int x, int y) {
        int brushX = x / (pixelSize + gap);
        int brushY = y / (pixelSize + gap);

        for (int dx = -brushSize; dx <= brushSize; dx++) {
            for (int dy = -brushSize; dy <= brushSize; dy++) {
                int pixelX = brushX + dx;
                int pixelY = brushY + dy;

                if (pixelX >= 0 && pixelX < cols && pixelY >= 0 && pixelY < rows) {
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    if (distance <= brushSize) {
                        intensity[pixelY][pixelX] = intensity[pixelY][pixelX] + (1.0 - distance / brushSize) * brushIntensity;
                        intensity[pixelY][pixelX] = clamp(intensity[pixelY][pixelX], 0.0, 1.0);
                    }
                }
            }
        }
        repaint();
    }

    // Getter Function for intensity
    public double[] getInput() {
        double[] input = new double[rows * cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                input[y * cols + x] = intensity[y][x];
            }
        }
        return input;
    }

    // Mouse events
    public void mousePressed(MouseEvent e) {
        drawBrush(e.getX(), e.getY());
    }

    public void mouseDragged(MouseEvent e) {
        drawBrush(e.getX(), e.getY());
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