package no.uib.inf101.patricleSim.engine;

import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.grid.CellPosition;
import no.uib.inf101.patricleSim.elements.particles.particle;
import javax.swing.Timer;

/**
    * SimulationEngine is responsible for running the simulation at a fixed frame rate.
    * It updates the state of particles in the grid and handles the simulation loop.
*/
public class SimulationEngine {
    private static final int FPS = 60; // can be adjusted for performance
    private static final int FRAME_TIME = 1000 / FPS; // milliseconds per frame
    private final Timer timer;
    private boolean isRunning;
    private final Grid grid;

    public SimulationEngine(Grid grid) {
        this.grid = grid;
        this.isRunning = false;
        this.timer = new Timer(FRAME_TIME, e -> tick());
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            timer.start();
        }
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
            timer.stop();
        }
    }

    private void tick() {
        for (int row = grid.rows() - 1; row >= 0; row--) {
            for (int col = 0; col < grid.cols(); col++) {
                CellPosition pos = new CellPosition(row, col);
                particle p = grid.get(pos);
                if (p != null && !p.isStatic()) {
                    p.update();
                }
            }
        }
    }
}