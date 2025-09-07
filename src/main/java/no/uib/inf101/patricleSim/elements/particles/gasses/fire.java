package no.uib.inf101.patricleSim.elements.particles.gasses;
import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.grid.CellPosition;
import no.uib.inf101.patricleSim.elements.particles.particle;

import java.awt.Color;
import java.util.Random;

public class fire extends gasBase {

    private static final Random random = new Random();
    protected int lifeSpan;

    public fire(int x, int y, Grid grid) {
        super(x, y, grid);
        this.type = "fire";
        this.dispersionRate = 2;
        this.lifeSpan = 10; 

        int baseRed = 255;
        int baseGreen = 120;
        int baseBlue = 63;

        int variation = 10;
        int redVar = random.nextInt(variation * 2) - variation;
        int greenVar = random.nextInt(variation * 2) - variation;

        int red = Math.min(255, Math.max(0, baseRed + redVar));
        int green = Math.min(255, Math.max(0, baseGreen + greenVar));
        
        this.color = new Color(red, green, baseBlue, 200);
    }

    @Override
    public void update() {
        lifeSpan--;
        
        // Remove fire when lifespan is over
        if (lifeSpan <= 0) {
            grid.set(new CellPosition(y, x), null);
            return;
        }

        // Check and spread to neighboring cells
        int[][] neighbors = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        // Shuffle the neighbors array for random spread direction
        for (int i = neighbors.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int[] temp = neighbors[i];
            neighbors[i] = neighbors[j];
            neighbors[j] = temp;
        }

        for (int[] offset : neighbors) {
            int newX = x + offset[0];
            int newY = y + offset[1];
            
            if (newX >= 0 && newX < grid.cols() && newY >= 0 && newY < grid.rows()) {
                particle neighbor = (particle) grid.get(new CellPosition(newY, newX));
                if (neighbor != null && neighbor.isFlammable() && Math.random() < 0.3) {
                    grid.set(new CellPosition(newY, newX), new fire(newX, newY, grid));
                }
            }
        }

        // Move like a gas
        super.update();
    }
}
