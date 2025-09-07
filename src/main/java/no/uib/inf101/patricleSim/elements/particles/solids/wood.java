package no.uib.inf101.patricleSim.elements.particles.solids;
import java.awt.Color;
import no.uib.inf101.patricleSim.grid.Grid;


public class wood extends solidBase {
    public wood(int x, int y, Grid grid) {
        super(x, y, grid);
        this.type = "wood";
        isFlammable = true; 
        this.spreadRate = 0.2; 
        this.isStatic = true; 
        this.color = new Color(85, 52, 43); // Brown color for wood
    }
    
}
