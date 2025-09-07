package no.uib.inf101.sample.gridTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.grid.CellPosition;
import no.uib.inf101.patricleSim.elements.particles.solids.sand;


public class gridTest {
    
    @Test
    public void testGridCreation() {
        Grid grid = new Grid(10, 10);
        assertEquals(10, grid.rows());
        assertEquals(10, grid.cols());
    }

    @Test 
    public void testSetAndGetParticle() {
        Grid grid = new Grid(10, 10);
        CellPosition pos = new CellPosition(5, 5);
        sand sandParticle = new sand(5, 5, grid);
        
        grid.set(pos, sandParticle);
        assertEquals(sandParticle, grid.get(pos));
    }

    @Test
    public void testEmptyCellIsNull() {
        Grid grid = new Grid(10, 10);
        CellPosition pos = new CellPosition(0, 0);
        assertNull(grid.get(pos));
    }

    @Test
    public void testPositionIsOnGrid() {
        Grid grid = new Grid(10, 10);
        assertTrue(grid.positionIsOnGrid(new CellPosition(0, 0)));
        assertTrue(grid.positionIsOnGrid(new CellPosition(9, 9)));
        assertFalse(grid.positionIsOnGrid(new CellPosition(-1, 0)));
        assertFalse(grid.positionIsOnGrid(new CellPosition(0, 10)));
    }

    @Test
    public void testOutOfBoundsException() {
        Grid grid = new Grid(10, 10);
        CellPosition invalidPos = new CellPosition(10, 10);
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            grid.get(invalidPos);
        });
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            grid.set(invalidPos, new sand(10, 10,grid));
        });
    }
}
