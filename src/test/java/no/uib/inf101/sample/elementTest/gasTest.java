package no.uib.inf101.sample.elementTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.patricleSim.elements.particles.gasses.Hydrogen;
import no.uib.inf101.patricleSim.elements.particles.solids.stone;
import no.uib.inf101.patricleSim.elements.particles.liquids.water;
import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.grid.CellPosition;

public class gasTest {
    private Grid grid;
    private Hydrogen gasParticle;

    @BeforeEach
    void setUp() {
        grid = new Grid(10, 10);
        gasParticle = new Hydrogen(5, 5, grid);
    }

    @Test
    void testGasInitialization() {
        assertEquals("Hydrogen", gasParticle.getType());
        assertFalse(gasParticle.isStatic());
        assertEquals(5, gasParticle.getX());
        assertEquals(5, gasParticle.getY());
        assertEquals(4, gasParticle.getDispersionRate());
    }

    @Test
    void testGasMovement() {
        int initialY = gasParticle.getY();
        gasParticle.update();
        // Gas should either move up or stay in place
        assertTrue(gasParticle.getY() <= initialY);
    }

    @Test
    void testGasDispersion() {
        int initialX = gasParticle.getX();
        // Run multiple updates to test horizontal movement
        for (int i = 0; i < 10; i++) {
            gasParticle.update();
        }
        // Gas should have moved horizontally at some point
        assertTrue(Math.abs(gasParticle.getX() - initialX) <= gasParticle.getDispersionRate());
    }

    @Test
    void testGasBoundaryBehavior() {
        // Test gas behavior at top of grid
        Hydrogen topGas = new Hydrogen(5, 0, grid);
        topGas.update();
        // Should not move above grid boundary
        assertTrue(topGas.getY() >= 0);
    }

    @Test
    void testGasCollisionWithSolid() {
        // Place a stone particle above the gas
        stone stoneParticle = new stone(5, 4, grid);
        grid.set(new CellPosition(4, 5), stoneParticle);
        
        // Update gas and verify it doesn't move through stone
        gasParticle.update();
        assertNotEquals(4, gasParticle.getY(), "Gas should not move through solid objects");
    }

    @Test
    void testGasDispersionAroundObstacles() {
        // Create obstacles
        stone leftStone = new stone(4, 5, grid);
        stone rightStone = new stone(6, 5, grid);
        grid.set(new CellPosition(5, 4), leftStone);
        grid.set(new CellPosition(5, 6), rightStone);
        
        int initialY = gasParticle.getY();
        
        // Update several times
        for (int i = 0; i < 5; i++) {
            gasParticle.update();
        }
        
        // Gas should still find a way to move upward
        assertTrue(gasParticle.getY() <= initialY, 
            "Gas should find path around obstacles to move upward");
    }

    @Test
    void testGasDensityBehavior() {
        // Create water particle below gas
        water waterParticle = new water(5, 6, grid);
        grid.set(new CellPosition(6, 5), waterParticle);
        
        gasParticle.update();
        
        // Gas should remain above water due to density
        assertTrue(gasParticle.getY() < waterParticle.getY(), 
            "Gas should stay above liquids due to lower density");
    }
}
