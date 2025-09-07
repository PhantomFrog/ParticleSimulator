package no.uib.inf101.patricleSim.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import no.uib.inf101.patricleSim.elements.particles.particle;

public class Grid implements IGrid {
    private final List<List<particle>> grid;
    private final int row;
    private final int col;

    public Grid(int row, int col) {
        this.row = row;
        this.col = col;
        this.grid = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            List<particle> innerList = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                innerList.add(null);
            }
            grid.add(innerList);
        }
    }

    @Override
    public int rows() {
        return row;
    }

    @Override
    public int cols() {
        return col;
    }

    @Override
    public particle get(CellPosition pos) {
        if (!positionIsOnGrid(pos)) {
            throw new IndexOutOfBoundsException("Position " + pos + " is not on grid");
        }
        return grid.get(pos.row()).get(pos.col());
    }

    @Override
    public void set(CellPosition pos, particle value) {
        if (!positionIsOnGrid(pos)) {
            throw new IndexOutOfBoundsException("Position " + pos + " is not on grid");
        }
        grid.get(pos.row()).set(pos.col(), value);
    }

    @Override
    public Iterator<GridCell> iterator() {
        List<GridCell> cellList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                CellPosition pos = new CellPosition(i, j);
                cellList.add(new GridCell(pos, get(pos)));
            }
        }
        return cellList.iterator();
    }

    @Override
    public boolean positionIsOnGrid(CellPosition pos) {
        return pos.row() >= 0 && pos.row() < row && pos.col() >= 0 && pos.col() < col;
    }
}
