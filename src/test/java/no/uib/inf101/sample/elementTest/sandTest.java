package no.uib.inf101.sample.elementTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.patricleSim.elements.particles.solids.sand;
import no.uib.inf101.patricleSim.grid.Grid;

public class sandTest {
    private Grid grid;
    private sand sandParticle;

    @BeforeEach
    void setUp() {
        grid = new Grid(10, 10);
        sandParticle = new sand(5, 5, grid);
    }

    @Test
    void testSandInitialization() {
        assertEquals("sand", sandParticle.getType());
        assertFalse(sandParticle.isStatic());
        assertEquals(5, sandParticle.getX());
        assertEquals(5, sandParticle.getY());
    }

    @Test
    void testSandFalling() {
        sandParticle.update();
        assertEquals(5, sandParticle.getX());
        assertEquals(6, sandParticle.getY());
    }

    @Test
    void testSandBlockedBecomesStatic() {
        // Place a sand particle at the bottom of the grid
        sand bottomSand = new sand(5, 9, grid);
        bottomSand.update();
        assertTrue(bottomSand.isStatic());
    }


}
