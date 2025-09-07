package no.uib.inf101.patricleSim.view;

import java.awt.Color;
import no.uib.inf101.patricleSim.elements.particles.particle;

public class ColorTheme {
    private static final Color EMPTY_COLOR = Color.BLACK;

    public Color getCellColor(Object cell) {
        if (cell instanceof particle) {
            return ((particle) cell).getColor();
        }
        // Default color for empty cells or non-particle objects
        return EMPTY_COLOR;
    }
}
