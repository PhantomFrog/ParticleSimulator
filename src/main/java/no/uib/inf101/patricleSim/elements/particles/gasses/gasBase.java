package no.uib.inf101.patricleSim.elements.particles.gasses;

import no.uib.inf101.patricleSim.elements.particles.particle;
import no.uib.inf101.patricleSim.grid.CellPosition;
import no.uib.inf101.patricleSim.grid.Grid;

//simiar logic to water but up and randomness

public class gasBase extends particle {

    public gasBase(int x, int y, Grid grid) {
        super(x, y, grid);
        this.type = "gas";
        this.dispersionRate = 2;
        this.density = 0.1f; // Default density for gas
        this.isFlammable = false; // Default value, can be overridden in subclasses
    }

    public int getDispersionRate() {
        return dispersionRate;
    }

    // same concepts as the liquid, but with a few changes and upwards movement
    @Override
    public void update() {
        if (!isStatic) {
            // Random chance to move (80% chance)
            if (Math.random() < 0.8) {
                // Try to move up with 90% chance
                if (Math.random() < 0.9 && tryMove(x, y - 1)) {
                    return;
                }

                // If can't move up or didn't try, attempt to disperse horizontally
                if (Math.random() < 0.5) {
                    tryDisperse(-1);
                } else {
                    tryDisperse(1);
                }
            }
        }
    }

    private void tryDisperse(int direction) {
        // Check path up to dispersion rate
        int maxDistance = dispersionRate;
        
        // Check for obstacles in path
        for (int i = 1; i <= maxDistance; i++) {
            int checkX = x + (i * direction);
            
            // Check if position is out of bounds
            if (checkX < 0 || checkX >= grid.cols()) {
                return;
            }
            
            // Check if there's an obstacle in the path
            particle obstacle = grid.get(new CellPosition(y, checkX));
            if (obstacle != null) {
                maxDistance = i - 1; // Can only move up to the obstacle
                break;
            }
        }
        
        // If we can move at least one space
        if (maxDistance > 0) {
            // Try to move to the furthest available position
            tryMove(x + (maxDistance * direction), y);
        }
    }
}
