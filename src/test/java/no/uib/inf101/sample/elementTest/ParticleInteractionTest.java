package no.uib.inf101.sample.elementTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.patricleSim.elements.particles.liquids.water;
import no.uib.inf101.patricleSim.elements.particles.liquids.oil;
import no.uib.inf101.patricleSim.elements.particles.solids.sand;
import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.grid.CellPosition;

/**
 * Tests for particle interactions and behaviors
 */
public class ParticleInteractionTest {
    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid(10, 10);
    }

    @Test
    void testWaterOilInteraction() {
        water waterParticle = new water(3, 3, grid);
        oil oilParticle = new oil(3, 4, grid);
        
        grid.set(new CellPosition(3, 3), waterParticle);
        grid.set(new CellPosition(4, 3), oilParticle);
        
        waterParticle.update();
        
        // Verify initial conditions
        assertTrue(waterParticle.getDensity() > oilParticle.getDensity());
        
        // Check if particles have moved correctly
        assertNull(grid.get(new CellPosition(3, 3)), "Original water position should be empty");
        assertEquals(waterParticle, grid.get(new CellPosition(4, 3)), "Water should have moved down");
    }

    @Test
    void testSandWaterInteraction() {
        sand sandParticle = new sand(3, 3, grid);
        water waterParticle = new water(3, 4, grid);
        
        grid.set(new CellPosition(3, 3), sandParticle);
        grid.set(new CellPosition(4, 3), waterParticle);
        
        sandParticle.update();
        
        assertNull(grid.get(new CellPosition(3, 3)), "Original sand position should be empty");
        assertEquals(sandParticle, grid.get(new CellPosition(4, 3)), "Sand should have moved down");
    }


    @Test
    void testLiquidSideways() {
        water water1 = new water(3, 3, grid);
        grid.set(new CellPosition(3, 3), water1);
        
        water1.update();
        
        // Water should try to move down first
        assertNull(grid.get(new CellPosition(3, 3)), "Original position should be empty");
        assertEquals(water1, grid.get(new CellPosition(4, 3)), "Water should move down");
    }

    @Test
    void testDensityDisplacement() {
        sand sandParticle = new sand(3, 3, grid);
        water waterParticle = new water(3, 4, grid);
        
        grid.set(new CellPosition(3, 3), sandParticle);
        grid.set(new CellPosition(4, 3), waterParticle);
        
        sandParticle.update();
        
        // Sand should displace water due to higher density
        assertEquals(sandParticle, grid.get(new CellPosition(4, 3)), "Sand should move to water's position");
        assertTrue(grid.get(new CellPosition(3, 3)) == waterParticle || 
                  grid.get(new CellPosition(4, 2)) == waterParticle || 
                  grid.get(new CellPosition(4, 4)) == waterParticle, 
                  "Water should be displaced to a nearby position");
    }

    @Test
    void testParticleDensityBehavior() {
        sand sandParticle = new sand(5, 5, grid);
        water waterParticle = new water(5, 6, grid);
        oil oilParticle = new oil(5, 7, grid);
        
        grid.set(new CellPosition(5, 5), sandParticle);
        grid.set(new CellPosition(6, 5), waterParticle);
        grid.set(new CellPosition(7, 5), oilParticle);
        
        assertTrue(sandParticle.getDensity() > waterParticle.getDensity(), 
            "Sand should be denser than water");
        assertTrue(waterParticle.getDensity() > oilParticle.getDensity(), 
            "Water should be denser than oil");
    }
}
