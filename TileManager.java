/* Homework 1  -- Jiayi Wang, CSE 143 A, Spring 2017 */
/**
 * TileManager is a class that works with ArrayList to
 * Enable interactions with a graphic set of Tile objects.
 */
import java.util.*;
import java.awt.*;

public class TileManager {
	private ArrayList<Tile> tileList;   // A list to store and maintain Tile objects as a class field
	public TileManager() {
		tileList = new ArrayList<Tile>();
	}
	/**
	 * Add the given tile to the end of the tiles list
	 * @param rect given Tile object, assumed to be not null, otherwise invalid
	 */
	public void addTile(Tile rect) {
		if (rect == null) {
			throw new IllegalArgumentException("Tile cannot be null!");
		}
		tileList.add(rect);
	}
	/**
	 * Draw the list of Tiles on the drawing panel from start to end
	 * @param g Graphics object, assumed to be not null, otherwise invalid
	 */
	public void drawAll(Graphics g) {
		if (g == null) {
			throw new IllegalArgumentException("Graphic element cannot be null!");
		}
		for (int i = 0; i < tileList.size(); i++) {
			tileList.get(i).draw(g);
		}
	}
	/**
	 * Move the topmost tile that covers the clicked position to top of the list
	 * @param x the x position of the user click, assumed to be less than window width
	 * @param y the y position of the user click, assumed to be less than window height
	 */
	public void raise(int x, int y) {
		Tile tile = removeTile(x, y);
		if (tile.getX() != -1) {  // has tile that covers that x/y position
			tileList.add(tile);  // add the tile to list end
		}
	}
	
	// Move the topmost tile that covers the clicked position to top of the list
	public void lower(int x, int y) {
		Tile tile = removeTile(x, y);
		if (tile.getX() != -1) {  // has tile that covers that x/y position
			tileList.add(0, tile);  // add the tile to list start
		}
	}
	
	// Remove the topmost tile that covers the clicked position to top of the list
	public void delete(int x, int y) {
		removeTile(x, y);
	}
	
	// Remove all the tiles that covers the clicked position to top of the list
	public void deleteAll(int x, int y) {
		Tile tile = removeTile(x, y);
		while (tile.getX() != -1) {  // if the list still has tiles covering that position
			tile = removeTile(x, y);
		}
	}
	/**
	 * Reorder the tiles in the list into a random order
	 * Move all tiles to a new position on the screen
	 * @param width width of the drawing panel
	 * @param height height of the drawing panel
	 */
	public void shuffle(int width, int height) {
		Collections.shuffle(tileList);
		Random rand = new Random();
		for (int i = 0; i < tileList.size(); i++) {  // loop through all tiles
			Tile tmp = tileList.get(i);
			int tileHeight = tmp.getHeight();
			int tileWidth = tmp.getWidth();
			int randomX = rand.nextInt(width - tileWidth);  // generate a valid random x/y position
			int randomY = rand.nextInt(height - tileHeight);
			tmp.setX(randomX);  // set the new random position to the tile
			tmp.setY(randomY);
		}
	}
	/**
	* Return the topmost tile to be removed
	* Return the default tile if no match tiles found
	*/
	private Tile removeTile(int x, int y) {
		Tile tile = new Tile(-1, -1, -1, -1, new Color(0,0,0));   // make a default tile object
		// search from the end of the tileList
		for (int i = tileList.size() - 1; i >= 0; i--) {
			Tile tmp = tileList.get(i);
			if (helper(x, y, tmp)) {  // if tile covers the given x/y position
				tile = tileList.get(i);
				tileList.remove(i);
				return tile;  // immediately return the topmost tile
			}
		}
		return tile;  // no tiles cover that position
	}
	/**
	 * Return a boolean to indicate whether the given position is covered in the area of a tile
	 * @param tile a Tile object in the tiles list
	 */
	private boolean helper(int x, int y, Tile tile) {
		boolean isCovered = false;
		int xLeft = tile.getX();
		int xRight = xLeft + tile.getWidth();
		int yUpper = tile.getY();
		int yLower = yUpper + tile.getHeight();
		// if covered in the tile area, return true
		if (x >= xLeft && x < xRight && y >= yUpper && y < yLower) {
			isCovered = true;
		}
		return isCovered;
	}
}
