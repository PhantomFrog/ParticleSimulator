package no.uib.inf101.patricleSim.view;

import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.engine.SimulationController;
import no.uib.inf101.patricleSim.grid.CellPosition;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.MouseAdapter;

public class GridView extends JPanel {
    private static final int OUTER_MARGIN = 20;
    private static final int INNER_MARGIN = 0;
    private static final int DEFAULT_SIZE = 1000;
    private static final Color BORDER_COLOR = new Color(20, 20, 20);
    private static final Color DEFAULT_CELL_COLOR = Color.GRAY;
    
    private final Grid grid;
    private final ColorTheme colorTheme;
    private final SimulationController controller;
    private final BasicStroke defaultStroke;
    private final StartScreen startScreen;
    
    public GridView(Grid grid, SimulationController controller) {
        this.grid = grid;
        this.colorTheme = new ColorTheme();
        this.controller = controller;
        this.defaultStroke = new BasicStroke(1.0f);
        this.startScreen = new StartScreen();
        
        initializePanel();
        setupMouseListeners();
    }
    
    private void initializePanel() {
        setPreferredSize(new Dimension(DEFAULT_SIZE, DEFAULT_SIZE));
        setOpaque(true);
        setBackground(Color.BLACK);
    }
    
    private void setupMouseListeners() {
        MouseAdapter mouseAdapter = controller.getMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        configureGraphics(g2);
        
        Rectangle2D bounds = calculateGridBounds();
        controller.setConverter(bounds);
        
        if (controller.isShowingStartScreen()) {
            startScreen.draw(g2, bounds);
            return;
        }
        
        drawGrid(g2, bounds);
    }

    private void configureGraphics(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(defaultStroke);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }

    private Rectangle2D calculateGridBounds() {
        int width = getWidth();
        int height = getHeight();
        int cellSize = Math.min(
            (width - 2 * OUTER_MARGIN) / grid.cols(),
            (height - 2 * OUTER_MARGIN) / grid.rows()
        );
        
        int actualWidth = cellSize * grid.cols();
        int actualHeight = cellSize * grid.rows();
        int xOffset = (width - actualWidth) / 2;
        int yOffset = (height - actualHeight) / 2;
        
        return new Rectangle2D.Double(xOffset, yOffset, actualWidth, actualHeight);
    }

    private void drawGrid(Graphics2D g2, Rectangle2D bounds) {
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(bounds, grid, INNER_MARGIN);
        
        for (int row = 0; row < grid.rows(); row++) {
            for (int col = 0; col < grid.cols(); col++) {
                drawCell(g2, new CellPosition(row, col), converter);
            }
        }
    }

    private void drawCell(Graphics2D g2, CellPosition pos, CellPositionToPixelConverter converter) {
        Rectangle2D cellBounds = converter.getBoundsForCell(pos);
        Color cellColor = getCellColor(pos);
        
        // Draw fill
        setAlphaComposite(g2, cellColor);
        g2.setColor(cellColor);
        g2.fill(cellBounds);
        

        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(BORDER_COLOR);
        g2.draw(cellBounds);
    }

    private Color getCellColor(CellPosition pos) {
        try {
            return colorTheme.getCellColor(grid.get(pos));
        } catch (IllegalArgumentException e) {
            return DEFAULT_CELL_COLOR;
        }
    }

    private void setAlphaComposite(Graphics2D g2, Color color) {
        float alpha = color.getAlpha() / 255.0f;
        g2.setComposite(alpha < 1.0f 
            ? AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)
            : AlphaComposite.SrcOver);
    }
}
