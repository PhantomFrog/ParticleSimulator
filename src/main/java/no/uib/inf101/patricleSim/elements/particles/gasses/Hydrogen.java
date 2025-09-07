package no.uib.inf101.patricleSim.elements.particles.gasses;
import no.uib.inf101.patricleSim.grid.Grid;
import java.awt.Color;

public class Hydrogen extends gasBase {
    public Hydrogen(int x, int y, Grid grid) {
        super(x, y, grid);
        this.type = "Hydrogen";
        this.color = new Color(255, 255, 255, 90); 
        this.dispersionRate = 4;
        this.density = 0.07f; // Density of hydrogen gas
        this.spreadRate = 0.6; 
        this.isFlammable = true;
    }
}
