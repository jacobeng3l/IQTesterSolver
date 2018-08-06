
public class Coord extends Board{
	// Stores the Row 
	private int x ;
	
	// Stores the Column 
	private int y; 
	
	/**
	 * Converts a position on the Board into a Row and Column format of the board. 
	 * @param pos the position on the board 
	 */
	public Coord(int pos) {
		int current = 0; 
		boolean end = false; 
		for (int i = 0; i < getBoard().length && !end; i++)
			for(int count = 0; count < getBoard()[i].length && !end; count++) {
				if (current == pos) {
					x = i; 
					y = count; 
					end = true; 
				}
				current++; 
			}
	}
	
	/**
	 * Accesses the row of the given position 
	 * @return The Row which the given position was in
	 */
	public int getRow() {
		return x;
	}
	
	/**
	 * Accesses the column of the given position. 
	 * @return The Row which the given position was in
	 */
	public int getCol() {
		return y; 
	}
}
