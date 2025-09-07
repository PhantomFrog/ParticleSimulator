package no.uib.inf101.patricleSim.view;

import java.awt.geom.Rectangle2D;
import no.uib.inf101.patricleSim.grid.CellPosition;
import no.uib.inf101.patricleSim.grid.GridDimension;

public class CellPositionToPixelConverter {
    private final Rectangle2D box;
    private final GridDimension gd;
    private final double margin;

    public CellPositionToPixelConverter(Rectangle2D box, GridDimension gd, double margin) {
        this.box = box;
        this.gd = gd;
        this.margin = margin;
    }

    public Rectangle2D getBoundsForCell(CellPosition pos) {
        double cellWidth = (box.getWidth() - (gd.cols() + 1) * margin) / gd.cols();
        double cellHeight = (box.getHeight() - (gd.rows() + 1) * margin) / gd.rows();

        double cellX = box.getX() + margin + pos.col() * (cellWidth + margin);
        double cellY = box.getY() + margin + pos.row() * (cellHeight + margin);

        return new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);
    }
}
