package no.uib.inf101.patricleSim.grid;

import no.uib.inf101.patricleSim.elements.particles.particle;

public interface IGrid extends GridDimension, Iterable<GridCell> {

    /**
     * Sets the value of a position in the grid
     * 
     * @param pos   the position in which to store the value
     * @param value the particle to store
     * @throws IndexOutOfBoundsException if the position does not exist in the grid
     */
    public void set(CellPosition pos, particle value);

    /**
     * Gets the current value at the given coordinate
     * 
     * @param pos the position to get
     * @return the particle stored at the position
     * @throws IndexOutOfBoundsException if the position does not exist in the grid
     */
    public particle get(CellPosition pos);

    /**
     * Reports whether the position is within bounds for this grid
     * 
     * @param pos position to check
     * @return true if the coordinate is within bounds, false otherwise
     */
    public boolean positionIsOnGrid(CellPosition pos);

}
