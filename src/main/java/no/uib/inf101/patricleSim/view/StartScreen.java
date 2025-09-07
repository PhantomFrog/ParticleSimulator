package no.uib.inf101.patricleSim.view;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;

public class StartScreen {
    public void draw(Graphics2D g2d, Rectangle2D bounds) {
        // Semi-transparent black background
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.fill(bounds);

        // Draw title
        g2d.setColor(Color.WHITE);
        Font titleFont = new Font("Algerian", Font.PLAIN , 48);
        g2d.setFont(titleFont);
        drawCenteredString(g2d, "Particle Simulator", bounds, bounds.getY() + 100);

        // Draw instructions
        Font textFont = new Font("Algerian", Font.PLAIN, 20);
        g2d.setFont(textFont);
        String[] instructions = {
            "Controls:",
            "1: Sand",
            "2: Water",
            "3: Hydrogen",
            "4: Oil",
            "5: Gunpowder",
            "6: Fire",
            "7: Wood",
            "8: Stone",
            "0: Eraser",
            "DELETE: Reset everything",
            "",
            " up/down arrow: Adjust brush size",
            "",
            "Press SPACE to start"
        };

        float y = (float) bounds.getY() + 200;
        for (String line : instructions) {
            drawCenteredString(g2d, line, bounds, y);
            y += 30;
        }
    }

    private void drawCenteredString(Graphics2D g2d, String text, Rectangle2D bounds, double y) {
        FontMetrics metrics = g2d.getFontMetrics();
        double x = bounds.getX() + (bounds.getWidth() - metrics.stringWidth(text)) / 2;
        g2d.drawString(text, (float) x, (float) y);
    }
}
