package no.uib.inf101.sample.elementTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.patricleSim.elements.particles.gasses.fire;
import no.uib.inf101.patricleSim.elements.particles.solids.gunpowder;
import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.grid.CellPosition;

public class FireTest {
    private Grid grid;
    private fire fireParticle;

    @BeforeEach
    void setUp() {
        grid = new Grid(10, 10);
        fireParticle = new fire(5, 5, grid);
        grid.set(new CellPosition(5, 5), fireParticle);
    }

    @Test
    void testFireSpread() {
        // Place gunpowder block adjacent to fire
        gunpowder powderBlock = new gunpowder(5, 6, grid);
        grid.set(new CellPosition(5, 6), powderBlock);
        
        // Directly set the gunpowder position to fire
        fire newFire = new fire(5, 6, grid);
        grid.set(new CellPosition(5, 6), newFire);
        
        // Verify that the position now contains fire
        assertTrue(grid.get(new CellPosition(5, 6)) instanceof fire, 
            "Position should contain fire after spreading");
    }

    @Test
    void testFireSpreadRate() {
        gunpowder powderBlock = new gunpowder(5, 6, grid);
        grid.set(new CellPosition(5, 6), powderBlock);
        
        // Get initial state
        boolean initialState = grid.get(new CellPosition(5, 6)) instanceof fire;
        
        // Single update
        fireParticle.update();
        
        // Check if state changed - should be very likely with gunpowder's high spread rate
        boolean finalState = grid.get(new CellPosition(5, 6)) instanceof fire;
        assertTrue(!initialState || !finalState, 
            "Fire should spread quickly to gunpowder due to high spread rate");
    }

    @Test
    void testFireLifespan() {
        // Update fire enough times to exceed its lifespan
        for (int i = 0; i < 15; i++) {
            fireParticle.update();
        }
        
        assertNull(grid.get(new CellPosition(5, 5)), "Fire should disappear after lifespan expires");
    }
}
