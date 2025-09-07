package no.uib.inf101.patricleSim.elements.particles.solids;

import no.uib.inf101.patricleSim.elements.particles.particle;
import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.grid.CellPosition;

public class solidBase extends particle {
    
    public solidBase(int x, int y, Grid grid) {
        super(x, y, grid);
        this.type = "solid";
        this.isStatic = false;
    }

    // rules for the solid particle, split into static or not
    // if not static, try to move down, if not possible, try to move diagonally down left or right
    @Override
    public void update() {
        if (!isStatic) {
            // Try moving down
            if (tryMove(x, y + 1)) {
                return;
            }
            
            // If can't move down, try diagonal moves
            if (tryMove(x - 1, y + 1)) {
                return;
            }
            if (tryMove(x + 1, y + 1)) {
                return;
            }
            
            // Only become static if at bottom or on top of another solid
            if (y + 1 >= grid.rows()) {
                isStatic = true;
                return;
            }
            
            particle below = grid.get(new CellPosition(y + 1, x));
            if (below != null && below.isSolid()) {
                isStatic = true;
            }
        }
    }

}
