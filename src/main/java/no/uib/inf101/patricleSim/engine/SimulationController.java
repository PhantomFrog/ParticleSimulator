package no.uib.inf101.patricleSim.engine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import no.uib.inf101.patricleSim.elements.particles.particle;
import no.uib.inf101.patricleSim.elements.particles.solids.sand;
import no.uib.inf101.patricleSim.elements.particles.solids.stone;
import no.uib.inf101.patricleSim.elements.particles.liquids.water;
import no.uib.inf101.patricleSim.elements.particles.gasses.Hydrogen;
import no.uib.inf101.patricleSim.elements.particles.liquids.oil;
import no.uib.inf101.patricleSim.elements.particles.gasses.fire;
import no.uib.inf101.patricleSim.elements.particles.solids.wood;
import no.uib.inf101.patricleSim.elements.particles.solids.gunpowder;

import no.uib.inf101.patricleSim.grid.CellPosition;
import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.view.CellPositionToPixelConverter;

/**
 * SimulationController handles user input and manages the simulation state.
 * It allows users to interact with the simulation grid using mouse and keyboard events.
 */

public class SimulationController implements KeyListener {
    private Grid grid;
    private CellPositionToPixelConverter converter;
    private CellPosition currentMouseCell;
    private CellPosition lastMouseCell;  
    private static final int MARGIN = 2;
    private int ballSize = 2; 
    private boolean showStartScreen = true;

   //enum, compact and easy to use

    public enum ParticleType {
        DELETE('0', (x, y, g) -> null),
        SAND('1', (x, y, g) -> new sand(x, y, g)),
        WATER('2', (x, y, g) -> new water(x, y, g)),
        HYDROGEN('3', (x, y, g) -> new Hydrogen(x, y, g)),
        OIL('4', (x, y, g) -> new oil(x, y, g)),
        GUNPOWDER('5', (x, y, g) -> new gunpowder(x, y, g)),
        FIRE('6', (x, y, g) -> new fire(x, y, g)),
        WOOD('7', (x, y, g) -> new wood(x, y, g)),
        STONE('8', (x, y, g) -> new stone(x, y, g));

        private final char key;
        private final ParticleFactory factory;

        ParticleType(char key, ParticleFactory factory) {
            this.key = key;
            this.factory = factory;
        }

        public static ParticleType fromKey(char key) {
            for (ParticleType type : values()) {
                if (type.key == key) return type;
            }
            return SAND; // default
        }
    }

    @FunctionalInterface
    interface ParticleFactory {
        particle create(int x, int y, Grid grid);
    }

    private ParticleType currentType = ParticleType.SAND;

    public SimulationController(Grid grid) {
        this.grid = grid;
        this.currentMouseCell = null;
        this.lastMouseCell = null;
    }

    /**
     * Set the converter for translating between pixels and grid cells
     */
    public void setConverter(Rectangle2D box) {
        this.converter = new CellPositionToPixelConverter(box, grid, MARGIN);
    }

    /**
     * Get the current cell position that the mouse is over
     */
    public CellPosition getCurrentMouseCell() {
        return currentMouseCell;
    }

    /**
     * Creates and returns a MouseAdapter for handling mouse events
     */
    public MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (showStartScreen) return;
                updateMouseCell(e.getPoint().x, e.getPoint().y);
                lastMouseCell = currentMouseCell;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (showStartScreen) return;
                CellPosition previousCell = currentMouseCell;
                updateMouseCell(e.getPoint().x, e.getPoint().y);
                
                if (currentMouseCell != null && !currentMouseCell.equals(previousCell)) {
                    if (lastMouseCell == null) {
                        lastMouseCell = previousCell;
                    }
                    drawLine(lastMouseCell, currentMouseCell);
                    lastMouseCell = currentMouseCell;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (showStartScreen) return;
                updateMouseCell(e.getPoint().x, e.getPoint().y);
                if (currentMouseCell != null) {
                    spawnParticleBall(currentMouseCell, ballSize);
                    lastMouseCell = currentMouseCell;
                }
            }
        };
    }

    /**
     * Updates the current mouse cell position based on mouse coordinates
     */
    private void updateMouseCell(int mouseX, int mouseY) {
        if (converter == null) return;

        // Check each cell to find which one contains the mouse coordinates
        for (int row = 0; row < grid.rows(); row++) {
            for (int col = 0; col < grid.cols(); col++) {
                CellPosition pos = new CellPosition(row, col);
                Rectangle2D cellBounds = converter.getBoundsForCell(pos);

                // Convert mouse coordinates to component coordinates
                Point mousePoint = new Point(mouseX, mouseY);

                if (cellBounds.contains(mousePoint)) {
                    currentMouseCell = pos;
                    return;
                }
            }
        }
        currentMouseCell = null;
    }

    private void spawnParticle(CellPosition pos) {
        if (grid.positionIsOnGrid(pos)) {
            if (currentType == ParticleType.DELETE) {
                grid.set(pos, null);  // Remove particle
            } else if (grid.get(pos) == null) {  // Only spawn if position is empty
                particle newParticle = currentType.factory.create(pos.col(), pos.row(), grid);
                grid.set(pos, newParticle);
            }
        }
    }

    private void spawnParticleBall(CellPosition center, int radius) {
        for (int dy = -radius; dy <= radius; dy++) {
            for (int dx = -radius; dx <= radius; dx++) {
                // Check if point is within circle using distance formula
                if (dx*dx + dy*dy <= radius*radius) {
                    CellPosition pos = new CellPosition(center.row() + dy, center.col() + dx);
                    spawnParticle(pos);
                }
            }
        }
    }

    private void drawLine(CellPosition start, CellPosition end) {
        if (start == null || end == null) return;

        int x1 = start.col(), y1 = start.row();
        int x2 = end.col(), y2 = end.row();

        int xDiff = x1 - x2;
        int yDiff = y1 - y2;
        boolean xDiffIsLarger = Math.abs(xDiff) > Math.abs(yDiff);

        int xModifier = xDiff < 0 ? 1 : -1;
        int yModifier = yDiff < 0 ? 1 : -1;

        int longerSideLength = Math.max(Math.abs(xDiff), Math.abs(yDiff));
        int shorterSideLength = Math.min(Math.abs(xDiff), Math.abs(yDiff));
        float slope = (shorterSideLength == 0 || longerSideLength == 0) ? 0 : ((float) shorterSideLength / longerSideLength);

        for (int i = 0; i <= longerSideLength; i++) {
            int shorterSideIncrease = Math.round(i * slope);
            int currentX, currentY;
            
            if (xDiffIsLarger) {
                currentX = x1 + (i * xModifier);
                currentY = y1 + (shorterSideIncrease * yModifier);
            } else {
                currentX = x1 + (shorterSideIncrease * xModifier);
                currentY = y1 + (i * yModifier);
            }

            CellPosition currentPos = new CellPosition(currentY, currentX);
            if (grid.positionIsOnGrid(currentPos)) {
                spawnParticleBall(currentPos, ballSize);
            }
        }
    }

    public void keyTyped(KeyEvent e) {
        //blir ikke brukt
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (showStartScreen) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                showStartScreen = false;
                return;
            }
            return;
        }

        char keyChar = e.getKeyChar();
        if (keyChar >= '0' && keyChar <= '9') {
            currentType = ParticleType.fromKey(keyChar);
        }
        
        // Add arrow key handling
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            ballSize = Math.min(10, ballSize + 1); // Cap at size 10
        } else if (keyCode == KeyEvent.VK_DOWN) {
            ballSize = Math.max(0, ballSize - 1); // Minimum size 1
        } else if (keyCode == KeyEvent.VK_DELETE) {
            // Reset the entire grid
            for (int row = 0; row < grid.rows(); row++) {
                for (int col = 0; col < grid.cols(); col++) {
                    grid.set(new CellPosition(row, col), null);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // yabadabadoodoo
    }

    public ParticleType getCurrentType() {
        return currentType;
    }

    public int getBallSize() {
        return ballSize;
    }

    public boolean isShowingStartScreen() {
        return showStartScreen;
    }
}


//  lil penguin of good luck
//     __
// -=(o '.
//    '.-.\
//    /|  \\
//    '|  ||
//     _\_):,_