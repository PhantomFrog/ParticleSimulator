package no.uib.inf101.patricleSim.elements.particles.liquids;

import java.awt.Color;
import no.uib.inf101.patricleSim.grid.Grid;


public class oil extends liquidBase {
    
    public oil(int x, int y, Grid grid) {
        super(x, y, grid);
        this.color = new Color(20, 13, 11); // Brown color for oil
        this.density = 0.9f; // Less dense than water (1.0)
        this.spreadRate = 0.4;
        this.isFlammable = true; 
        this.type = "oil"; 
        this.dispersionRate = 4; 
    }

    @Override
    public void update() {
        if (!isStatic) {
            // Try to move down first
            if (tryMove(x, y + 1)) {
                return;
            }

            // Try diagonal movement
            if (tryMove(x - 1, y + 1)) {
                return;
            }
            if (tryMove(x + 1, y + 1)) {
                return;
            }

            // Try horizontal dispersion
            if (Math.random() < 0.5) {
                tryDisperse(-1);
            } else {
                tryDisperse(1);
            }
        }
    }
}
