package no.uib.inf101.patricleSim.elements.particles.liquids;
import java.awt.Color;
import no.uib.inf101.patricleSim.grid.Grid;

public class water extends liquidBase {
    public water(int x, int y, Grid grid) {
        super(x, y, grid);
        this.type = "water";
        this.color = new Color(4, 118, 208);
        this.density = 1.0f; 
        this.dispersionRate = 5; 
    }

}
