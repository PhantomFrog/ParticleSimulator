package no.uib.inf101.patricleSim.elements.particles;

import java.awt.Color;
import no.uib.inf101.patricleSim.grid.Grid;
import no.uib.inf101.patricleSim.grid.CellPosition;


public abstract class particle {
    // Position coordinates
    protected int x;
    protected int y;

    // Color of the particle
    protected Color color; 
    
    // Basic properties
    protected boolean isStatic;
    protected String type;
    protected boolean isFlammable;
    // Fire spread rate
    protected double spreadRate;
    protected int dispersionRate;
    protected float density; 
    // Reference to the grid
    protected Grid grid;

    /**
     * Constructor for creating a new particle
     * 
     * @param x Initial x position
     * @param y Initial y position
     * @param grid Reference to the grid
     */
    public particle(int x, int y, Grid grid) {
        this.x = x;
        this.y = y;
        this.isStatic = false;
        this.isFlammable = false;
        this.color = new Color(255,255,255,255); // Default color
        this.grid = grid;
    }

    // Getters and setters

    /**
     * Gets the X coordinate of the particle.
     * 
     * @return The X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y coordinate of the particle.
     * 
     * @return The Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the position of the particle.
     * 
     * @param x The new X coordinate
     * @param y The new Y coordinate
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks if the particle is static.
     * 
     * @return true if the particle is static, false otherwise
     */
    public boolean isStatic() {
        return isStatic;
    }

    /**
     * Gets the type of the particle.
     * 
     * @return The type of the particle
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the color of the particle.
     * 
     * @return The color of the particle
     */
    public Color getColor() {
        return color;
    }

    /**
     * Checks if the particle is solid.
     * 
     * @return true if the particle is solid, false otherwise
     */
    public boolean isSolid() {
        return type != null && (type.equals("solid") || type.equals("stone") || type.equals("wood"));
    }

    /**
     * Checks if the particle is liquid.
     * 
     * @return true if the particle is liquid, false otherwise
     */
    public boolean isLiquid() {
        return type != null && (type.equals("liquid") || type.equals("water") || type.equals("oil"));
    }

    /**
     * Checks if the particle is gas.
     * 
     * @return true if the particle is gas, false otherwise
     */
    public boolean isGas() {
        return type != null && (type.equals("gas") || type.equals("hydrogen") || type.equals("fire"));
    }

    /**
     * Checks if the particle is flammable.
     * 
     * @return true if the particle is flammable, false otherwise
     */
    public boolean isFlammable() {
        return isFlammable;
    }

    /**
     * Gets the fire spread rate of the particle.
     * 
     * @return The fire spread rate
     */
    public double getSpreadRate() {
        return spreadRate;
    }

    /**
     * Gets the dispersion rate of the particle.
     * 
     * @return The dispersion rate
     */
    public int getDispersionRate() {
        return dispersionRate;
    }

    /**
     * Gets the density of the particle.
     * 
     * @return The density
     */
    public float getDensity() {
        return density;
    }

    // Abstract methods that must be implemented by subclasses

    /**
     * Updates the state of the particle.
     * This method must be implemented by subclasses.
     */
    public abstract void update();

    /**
     * Attempts to move the particle to a new position.
     * 
     * @param newX The target X coordinate
     * @param newY The target Y coordinate
     * @return true if movement was successful, false otherwise
     */
    protected boolean tryMove(int newX, int newY) {
        if (newX < 0 || newX >= grid.cols() || newY < 0 || newY >= grid.rows()) {
            return false;
        }
        
        particle other = (particle) grid.get(new CellPosition(newY, newX));
        if (other == null) {
            grid.set(new CellPosition(y, x), null);
            grid.set(new CellPosition(newY, newX), this);
            this.x = newX;
            this.y = newY;
            return true;
        } else if (other.isLiquid() && this.density > other.density) {
            int[] dx = {-1, 1, 0};  // Check left, right, and up
            int[] dy = {0, 0, -1};
            
            for (int i = 0; i < dx.length; i++) {
                int dispX = newX + dx[i];
                int dispY = newY + dy[i];
                
                if (dispX >= 0 && dispX < grid.cols() && dispY >= 0 && dispY < grid.rows()) {
                    particle dispSpot = (particle) grid.get(new CellPosition(dispY, dispX));
                    if (dispSpot == null) {
                        // Move the liquid to the free space
                        grid.set(new CellPosition(dispY, dispX), other);
                        other.setPosition(dispX, dispY);
                        // Move this particle to the liquid's original position
                        grid.set(new CellPosition(y, x), null);
                        grid.set(new CellPosition(newY, newX), this);
                        this.x = newX;
                        this.y = newY;
                        return true;
                    }
                }
            }
            
            // If no displacement possible, just swap positions
            grid.set(new CellPosition(y, x), other);
            grid.set(new CellPosition(newY, newX), this);
            other.setPosition(x, y);
            this.x = newX;
            this.y = newY;
            return true;
        }
        return false;
    }
}