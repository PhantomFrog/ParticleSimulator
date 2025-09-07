package no.uib.inf101.patricleSim.elements.particles.solids;
import no.uib.inf101.patricleSim.grid.Grid;
import java.awt.Color;
import java.util.Random;

public class gunpowder extends solidBase {
    private static final Random random = new Random();

    public gunpowder(int x, int y, Grid grid) {
        super(x, y, grid);
        this.type = "gunpowder";
        
        int baseGrey = 50;
        int variation = 15;
        int colorVar = random.nextInt(variation * 2) - variation;
        
        // Ensure colors stay within valid range (0-255)
        int grey = Math.min(255, Math.max(0, baseGrey + colorVar));
        this.color = new Color(grey, grey, grey);
        
        this.spreadRate = 1; //instantaneous spread
        this.isFlammable = true;
        this.density = 2.5f; // Higher density than water
    }
}
