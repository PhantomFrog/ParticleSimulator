package no.uib.inf101.patricleSim.elements.particles.solids;
import java.awt.Color;
import no.uib.inf101.patricleSim.grid.Grid;

public class stone extends solidBase {
    public stone(int x, int y, Grid grid) {
        super(x, y, grid);
        this.type = "stone";
        this.isStatic = true; // Stone is static and does not move
        this.color = new Color(128, 128, 128); // Gray color for stone
    }

    //no need for update method, as stone is static and does not move
    
}
