import java.util.LinkedList;

/**
 * A class which holds the board of the IQ Tester Game. 
 * @author Jacob Engelbrecht 
 * @since 08-02-2018
 * @version 1.0.0
 */
public class Board {
	// Size of the diagonal, should work with any size board 
	int diagonal = 5; 
	
	// Variables used to keep track of the size of the board, based off of diagonal 
	static int tot; 
	
	// This stores the board as a double array, but it is not a square 
	private Boolean[][] board = new Boolean[diagonal][]; 
	
	// Holds the number of elements currently on the board 
	private int elements; 
	
	// Holds the original peg removed from the board 
	private int seed; 
	
	/**
	 * Creates the board based off of the value of the diagonal with one peg missing. 
	 */
	public Board() {
		// populates the diagonal side of the board
		for(int i = 0; i < diagonal; i++)
			getBoard()[i] = new Boolean[i + 1]; 
		
		// populates tot through a summation 
		tot = add(diagonal); 
		
		elements = (tot - 1); 
		
		// Randomly chooses seed of the board 
		seed = (int) (Math.random() * tot); 

		//Populate the board 
		int current = 0; 		
		for (int i = 0; i < getBoard().length; i++)
			for(int count = 0; count < getBoard()[i].length; count++) {
				if (current == seed)
					getBoard()[i][count] = false; 
				else
					getBoard()[i][count] = true; 
				current++; 
			}
	}
	
	/***
	 * Returns the value at a given location called from counting left to right, top to bottom 
	 * @param pos The location of the value which is desired 
	 * @return The boolean at a location 
	 * @throws Exception Caused by the request of a number outside of the size of the board 
	 */
	public boolean get(int pos) throws Exception {
		if(pos > (tot - 1) || pos < 0)
			throw new Exception(); 
		Coord loc = new Coord(pos); 
		return board[loc.getRow()][loc.getCol()];  
	}
	
	/**
	 * Alters the value at a certain location on the board. 
	 * @param pos The position on the board which is to be changed 
	 * @param b The boolean value which the position on the board should be set to 
	 * @throws Exception Caused by the request of a number outside of the size of the board
	 */
	public void set(int pos, boolean b) throws Exception {
		if(pos > (tot - 1) || pos < 0)
			throw new Exception(); 
		Coord loc = new Coord(pos); 
		board[loc.getRow()][loc.getCol()] = b; 
	}
	
	/**
	 * Checks to see if it is legal to jump between two values. 
	 * @param start The first value to be checked if it can be swapped. 
	 * @param end The other value used to be moved
	 * @return A boolean value on whether or not it is legal to make the switch 
	 * @throws Exception Caused by the request of a number outside of the size of the board
	 */
	public boolean check(int start, int end) throws Exception {
		if(start < 0 || start > (tot - 1) || end < 0 || end > (tot - 1) || start == end)
			return false; 
		Coord sta = new Coord(start); 
		Coord fin = new Coord(end); 
		int difRow = sta.getRow() - fin.getRow(); 
		int difCol = sta.getCol() - fin.getCol(); 
		if(difRow == 2) {
			if(difCol == 0)
				if(board[sta.getRow() - 1][sta.getCol()])
					if(get(start) != get(end))
						return true; 
			if(difCol == 2)
				if(board[sta.getRow() - 1][sta.getCol() - 1])
					if(get(start) != get(end))
						return true; 
		}
		if(difRow == -2) {
			if(difCol == 0)
				if(get(start) != get(end))
					if(board[sta.getRow() + 1][sta.getCol()])
						return true; 
			if(difCol == -2)
				if(board[sta.getRow() + 1][sta.getCol() + 1])
					return true; 
		}
		if(difRow == 0) {
			if(difCol == -2)
				if(get(start) != get(end))
					if(board[sta.getRow()][sta.getCol() + 1])
						return true; 
			if(difCol == 2)
				if(board[sta.getRow()][sta.getCol() - 1])
					return true; 
		}
		return false; 
	}
	
	/**
	 * Checks to see if it is possible to move between the two points, and does so if possible 
	 * @param start The primary point of the swap 
	 * @param end The other point of the movement
	 * @return True or false based on whether the move was legal and successful 
	 * @throws Exception Caused by the request of a number outside of the size of the board
	 */
	public boolean move(int start, int end) throws Exception {
		if(start < 0 || start > (tot - 1) || end < 0 || end > (tot - 1) || start == end || get(start) == get(end))
			return false; 
		elements--; 
		Coord sta = new Coord(start); 
		Coord fin = new Coord(end); 
		int difRow = sta.getRow() - fin.getRow(); 
		int difCol = sta.getCol() - fin.getCol(); 
		if(difRow == 2) {
			if(difCol == 0)
				if(board[sta.getRow() - 1][sta.getCol()])
					if(get(start) != get(end)) {
						board[sta.getRow() - 1][sta.getCol()] = false; 
						set(start, !get(start));  
						set(end, !get(end));
						return true; 
					}
			if(difCol == 2)
				if(board[sta.getRow() - 1][sta.getCol() - 1])
					if(get(start) != get(end)) {
						board[sta.getRow() - 1][sta.getCol() - 1] = false; 
						set(start, !get(start));  
						set(end, !get(end)); 
						return true; 
					}
		}
		if(difRow == -2) {
			if(difCol == 0)
				if(get(start) != get(end))
					if(board[sta.getRow() + 1][sta.getCol()]) {
						board[sta.getRow() + 1][sta.getCol()] = false; 
						set(start, !get(start));  
						set(end, !get(end)); 
						return true; 
					}
			if(difCol == -2)
				if(board[sta.getRow() + 1][sta.getCol() + 1]) {
					board[sta.getRow() + 1][sta.getCol() + 1] = false; 
					set(start, !get(start));  
					set(end, !get(end)); 
					return true; 
				}
		}
		if(difRow == 0) {
			if(difCol == -2)
				if(get(start) != get(end))
					if(board[sta.getRow()][sta.getCol() + 1]) {
						board[sta.getRow()][sta.getCol() + 1] = false; 
						set(start, !get(start));  
						set(end, !get(end)); 
						return true; 
					}
			if(difCol == 2)
				if(board[sta.getRow()][sta.getCol() - 1]) {
					board[sta.getRow()][sta.getCol() - 1] = false; 
					set(start, !get(start));  
					set(end, !get(end)); 
					return true; 
				}
		}
		elements++; 
		return false; 
	}
	
	/**
	 * Prints the board in its current state. 
	 */
	public void print() {
		System.out.println(toString());
	}
	
	/**
	 * Converts the current board to a string which can easily be printed 
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append("\t\t\t\t\t ^\n");
		for(int row = 0; row < diagonal; row++) {
			for(int i = row; i < diagonal; i++)
				sb.append("\t");
			for(int i = 0; i < (6 * row); i++)
				sb.append(" ");
			sb.append("/");
			for(int i = 0; i <= row; i++) {
				if(i > 0)
					sb.append("   ");
				if(getBoard()[row][i])
					sb.append("1");
				else
					sb.append("0");
			}
			sb.append("\\\n");
		}
		return sb.toString(); 
	}
	
	/**
	 * Returns the board in its current state
	 * @return The double boolean array which represents the board 
	 */
	public Boolean[][] getBoard() {
		return board;
	}
	
	/**
	 * Returns the number of pegs still on the board
	 * @return An integer representing the number of pegs on the board 
	 */
	public int getElements() {
		return elements; 
	}
	
	/**
	 * Returns the seed of the first removed piece
	 * @return The position representing the first removed peg 
	 */
	public int getSeed() {
		return seed; 
	}
	
	/**
	 * A summation program 
	 * @param num The number at the top of the summation equation 
	 * @return The sum from zero the the number 
	 */
	public int add(int num) {
		int total = 0; 
		for(int i = 1; i <= num; i++)
			total += i; 
		return total; 
	}
	
	/**
	 * Plays a certain number of games until it succeeds at winning one. Needs optimization. 
	 * @throws Exception If an element outside the board is attempted to be accessed 
	 */
	public static void play() throws Exception {
		Board b = new Board();
		while(b.getElements() != 1) {
			b = new Board(); 
			LinkedList<Integer> list = new LinkedList<>(); 
			// Guess 1000 moves in hopes that it plays an entire game 
			for(int i = 0; i < 1000; i++) {
				int guess = (int) (Math.random() * tot); 
				int guess2 = (int) (Math.random() * tot);
				if (b.move(guess, guess2)) {
					list.add(guess);
					list.add(guess2); 
					b.print();
				}
			}
			if(b.getElements() == 1) {
				System.out.println(b.getSeed()); 
				System.out.println(list.toString()); 
			}
			b.print(); 
			System.out.println(b.getElements());
		}
	}
	
	/**
	 * The running method which calls play 
	 * @param args Unused in the program 
	 * @throws Exception Caused by accessing an element outside of the board 
	 */
	public static void main(String[] args) throws Exception {
		play(); 
		System.out.println("Done");
	}
	
}
