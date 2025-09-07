package no.uib.inf101.patricleSim;

import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.view.GridView;
import no.uib.inf101.patricleSim.engine.SimulationEngine;
import no.uib.inf101.patricleSim.engine.SimulationController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //recomended size for my performence, however if you want you can change it.
        Grid grid = new Grid(150, 200); 


        // Create controller before GridView
        SimulationController controller = new SimulationController(grid);

        // Pass controller to GridView
        GridView gridView = new GridView(grid, controller);

        SimulationEngine engine = new SimulationEngine(grid);
        engine.start();

        // Set up the JFrame
        JFrame frame = new JFrame("Particle Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gridView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        Timer repaintTimer = new Timer(1000/60, e -> gridView.repaint());
        repaintTimer.start();

        frame.addKeyListener(controller);
        frame.setFocusable(true);
        frame.requestFocus();  
    }
}
