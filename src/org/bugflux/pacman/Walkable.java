package org.bugflux.pacman;

public interface Walkable {
	public static enum PositionType { WALL, HALL };
	public static enum Direction { UP, DOWN, LEFT, RIGHT };
	
	/**
	 * Move in the designated direction, if possible.
	 * 
	 * @param w The walker that wishes to move.
	 * @return the new Coordinates of the walker.
	 */
	public abstract Coord move(Walker w, Direction d);
	
	/**
	 * Add a Walker to a specified row,column coordinate.
	 * 
	 * @param w The Walker.
	 * @param r The row index.
	 * @param c The column index.
	 */
	public abstract void addWalker(Walker w, Coord c);

	/**
	 * Checks if a given position is valid for this walkable space.
	 * 
	 * @param c The position's coordinates
	 * @return true if the position is valid, false otherwise.
	 */
	public abstract boolean validPosition(Coord c);

	/**
	 * Checks if a given position is free, i.e., doesn't have walkers there.
	 * @param c The position's coordinates.
 	 * @return true if the position is free, false otherwise.
	 */
	public abstract boolean isFree(Coord c);
	
	
	/**
	 * Checks if a given position is a Hall (or Wall!). Halls can be walked through, Walls cannot.
	 * @param c The position's coordinates.
	 * @return true if the position is Hall, false otherwise.
	 */
	public abstract boolean isHall(Coord c);
	
	/**
	 * Calculate the resulting coordinates from moving from a specified position to a given direction.
	 * These coordinates aren't necessarily Free, Hall or even Valid!
	 * 
	 * @param c The position to move from.
	 * @param d The position to move to.
	 * @return the new Coordinates.
	 */
	public abstract Coord newCoord(Coord c, Direction d);
}
