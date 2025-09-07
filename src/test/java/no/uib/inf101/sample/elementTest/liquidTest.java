package no.uib.inf101.sample.elementTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.patricleSim.elements.particles.liquids.liquidBase;
import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.grid.CellPosition;

public class liquidTest {
    private Grid grid;
    private liquidBase liquid;
    
    @BeforeEach
    void setUp() {
        grid = new Grid(10, 10);
        liquid = new liquidBase(5, 5, grid);
    }
    
    @Test
    void testLiquidInitialization() {
        assertEquals("liquid", liquid.getType());
        assertEquals(5, liquid.getX());
        assertEquals(5, liquid.getY());
        assertFalse(liquid.isStatic());
    }
    
    @Test
    void testLiquidFalls() {
        liquid.update();
        assertEquals(6, liquid.getY()); // Should fall down one position
        assertEquals(5, liquid.getX()); // X position should remain the same
    }
    

    
    @Test
    void testDispersionRate() {
        assertEquals(3, liquid.getDispersionRate());
    }

    @Test
    void testLiquidMaxDispersion() {
        // Place liquid and let it disperse
        for (int i = 0; i < 10; i++) {
            liquid.update();
        }
        
        // Check that liquid hasn't dispersed beyond its dispersion rate
        int dispersalDistance = Math.abs(liquid.getX() - 5);
        assertTrue(dispersalDistance <= liquid.getDispersionRate(), 
            "Liquid shouldn't disperse beyond its dispersion rate");
    }

    @Test
    void testLiquidBoundaryBehavior() {
        // Place liquid at edge of grid
        liquidBase edgeLiquid = new liquidBase(0, 5, grid);
        grid.set(new CellPosition(5, 0), edgeLiquid);
        
        edgeLiquid.update();
        
        // Ensure liquid stays within grid bounds
        assertTrue(edgeLiquid.getX() >= 0, "Liquid should not move beyond left boundary");
        assertTrue(edgeLiquid.getX() < grid.cols(), "Liquid should not move beyond right boundary");
    }
}
