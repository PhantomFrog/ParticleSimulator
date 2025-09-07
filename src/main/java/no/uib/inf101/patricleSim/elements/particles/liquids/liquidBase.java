package no.uib.inf101.patricleSim.elements.particles.liquids;

import no.uib.inf101.patricleSim.elements.particles.particle;
import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.grid.CellPosition;

public class liquidBase extends particle {

    public liquidBase(int x, int y, Grid grid) {
        super(x, y, grid);
        this.type = "liquid";
        this.dispersionRate = 3; // Default dispersion rate
    }

    public int getDispersionRate() {
        return dispersionRate;
    }

    //water is similar to solid, main diffrence is 
    //that it never stops looking for a place to go, and it can move left or right

    @Override
    public void update() {
        if (!isStatic) {
            // Step 1: Try to move down
            if (tryMove(x, y + 1)) {
                return;
            }

            //step 2: try to move diagonally down left or right
            if (tryMove(x - 1, y + 1)) {
                return;
            }
            if (tryMove(x + 1, y + 1)) {
                return;
            }

            // Step 3: Try to disperse left or right
            if (Math.random() < 0.5) {
                // Try left first
                tryDisperse(-1);
            } else {
                // Try right first
                tryDisperse(1);
            }
        }
    }

    //dispertion is meant to make it flow quicker and more "natural"
    protected void tryDisperse(int direction) {
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
