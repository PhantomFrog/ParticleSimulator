package no.uib.inf101.patricleSim.elements.particles.solids;

import java.awt.Color;
import java.util.Random;
import no.uib.inf101.patricleSim.grid.Grid;

//this class can be used for sand, dirt, all non-static solids

public class sand extends solidBase {

    private static final Random random = new Random();

    public sand(int x, int y, Grid grid) {
        super(x, y, grid);
        this.type = "sand";
        this.isStatic = false;
        this.density = 2.5f; // Sand is denser than water (1.0)

        //create random color for sand
        int baseRed = 255;
        int baseGreen = 220;
        int baseBlue = 0;

        int variation = 20;
        int redVar = random.nextInt(variation * 2) - variation;
        int greenVar = random.nextInt(variation * 2) - variation;

        // Ensure colors stay within valid range (0-255)
        int red = Math.min(255, Math.max(0, baseRed + redVar));
        int green = Math.min(255, Math.max(0, baseGreen + greenVar));
        
        this.color = new Color(red, green, baseBlue);
    }

}
