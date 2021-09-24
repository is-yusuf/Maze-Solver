import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
/**
* Maze represents a maze that can be navigated. The maze
* should indicate its start and end squares, and where the
* walls are. 
*
* Eventually, this class will be able to load a maze from a
* file, and solve the maze.
* The starter code has part of the implementation of load, but
* it does not read and store the information about where the walls of the maze are.
*
*/
public class Maze { 
	// 2-d ArrayList to contain objects of type MazeSquare
	List<ArrayList<MazeSquare>> nestedList = new ArrayList<ArrayList<MazeSquare>>();
	MysteryStackImplementation<MazeSquare> stack;
	
	
	int currentrow;
	int currentcol;
	
	String line;

    //Number of rows in the maze.
    private int numRows;
    
    //Number of columns in the maze.
    private int numColumns;
    
    //Grid coordinates for the starting maze square
    private int startRow;
    private int startColumn;
    
    //Grid coordinates for the final maze square
    private int finishRow;
    private int finishColumn;
    
    //**************YOUR CODE HERE******************
    //You'll likely want to add one or more additional instance variables
    //to store the squares of the maze
    
    /**
     * Creates an empty maze with no squares.
     */
    public Maze() {
        ; //You can add any code you need to initialize instance 
          //variables you've added.
    } 
    
    /**
     * Loads the maze that is written in the given fileName.
     * Returns true if the file in fileName is formatted correctly
     * (meaning the maze could be loaded) and false if it was formatted
     * incorrectly (meaning the maze could not be loaded). The correct format
     * for a maze file is given in the assignment description. Ways 
     * that you should account for a maze file being incorrectly
     * formatted are: one or more squares has a descriptor that doesn't
     * match  *, 7, _, or | as a descriptor; the number of rows doesn't match
     * what is specified at the beginning of the file; or the number of
     * columns in any row doesn't match what's specified at the beginning
     * of the file; or the start square or the finish square is outside of
     * the maze. You can assume that the file does start with the number of
     * rows and columns.
     * 
     */

    public boolean load(String fileName) { 
		
		inputFile = new File(fileName);
		scanner = null;
		try {
			scanner = new Scanner(inputFile);
		}	
		catch (FileNotFoundException e) {
			System.err.println(e);
			System.exit(1);
		}

		// manually reads the first 3 lines and sets the corresponding variables 
		line = scanner.nextLine();

		
		numColumns = Integer.parseInt(line.substring(0,1));
		
		numRows = Integer.parseInt(line.substring(2,3));
		
		line = scanner.nextLine();

		startColumn = Integer.parseInt(line.substring(0,1));
		
		startRow = Integer.parseInt(line.substring(2,3));
		
		line = scanner.nextLine();

		finishColumn = Integer.parseInt(line.substring(0,1));
		
		finishRow = Integer.parseInt(line.substring(2,3));

		line = scanner.nextLine();


		// fills the outer ArrayList with inner ArrayLists
		for (int i = 0; i < numRows+1 ; i++){
			nestedList.add(new ArrayList<MazeSquare>());
		}

		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numColumns; j++){
				// stores the character corresponding to the square in a variable tempChar
				char tempChar = line.substring(j,j+1).charAt(0);
				// checks if the entry is correct or not. 
				checkValidSquare(tempChar);
				// if the character is valid, creates a new MazeSquare object and adds it to the inner ArrayList of the nestedList
				MazeSquare tempMazeSquare = new MazeSquare(j, i, tempChar);
				nestedList.get(j).add(tempMazeSquare); 
			}
			if (scanner.hasNextLine()){
				line = scanner.nextLine();
			}
			
		}
	
      	return true;//Remove this and load the maze from the file
    } 

	/**
	* Checks if the entry is valid for making the square or not.
	*@param char type variable
	*exits the program if the entry is invalid
	*/

	private void checkValidSquare (char toCheck){
		if (toCheck=='7' || toCheck=='|' || toCheck=='*' || toCheck=='_'){
			return;
		}
		else{
			System.out.println("Invalid input for square! ");
			System.exit(1);
		}
	}
    
    /**
     * Returns true if number is greater than or equal to lower bound
     * and less than upper bound. 
     * @param number
     * @param lowerBound
     * @param upperBound
     * @return true if lowerBound ≤ number < upperBound
     */
    private static boolean isInRange(int number, int lowerBound, int upperBound) {
        return number < upperBound && number >= lowerBound;
    }
    
    /**
     * Prints the maze with the start and finish squares marked. Does
     * not include a solution.
     */
    public void print() {
        //We'll print off each row of squares in turn.
        for(int row = 0; row < numRows; row++) {
            
            //Print each of the lines of text in the row
            for(int charInRow = 0; charInRow < 4; charInRow++) {
                //Need to start with the initial left wall.
                if(charInRow == 0) {
                    System.out.print("+");
                } else {
                    System.out.print("|");
                }
                
                for(int col = 0; col < numColumns; col++) {
                    MazeSquare curSquare = this.getMazeSquare(row, col);
                    if(charInRow == 0) {
                        //We're in the first row of characters for this square - need to print
                        //top wall if necessary.
                        if(curSquare.hasTopWall()) {
                            System.out.print(getTopWallString());
                        } else {
                            System.out.print(getTopOpenString());
                        }
                    } else if(charInRow == 1 || charInRow == 3) {
                        //These are the interior of the square and are unaffected by
                        //the start/final state.
                        if(curSquare.hasRightWall()) {
                            System.out.print(getRightWallString());
                        } else {
                            System.out.print(getOpenWallString());
                        }
                    } else {
                        //We must be in the second row of characters.
                        //This is the row where start/finish should be displayed if relevant
                        
                        //Check if we're in the start or finish state
                        if(startRow == row && startColumn == col) {
                            System.out.print("  S  ");
                        } else if(finishRow == row && finishColumn == col) {
                            System.out.print("  F  ");
                        }
						
						else if (curSquare.isSolution()){
							System.out.print("  *  ");
						}
						else {
                            System.out.print("     ");
                        }
                        if(curSquare.hasRightWall()) {
                            System.out.print("|");
                        } else {
                            System.out.print(" ");
                        }
                    } 
                }
                
                //Now end the line to start the next
                System.out.print("\n");
            }           
        }
        
        //Finally, we have to print off the bottom of the maze, since that's not explicitly represented
        //by the squares. Printing off the bottom separately means we can think of each row as
        //consisting of four lines of text.
        printFullHorizontalRow(numColumns);
    }
    
    /**
     * Prints the very bottom row of characters for the bottom row of maze squares (which is always walls).
     * numColumns is the number of columns of bottom wall to print.
     */
    private static void printFullHorizontalRow(int numColumns) {
        System.out.print("+");
        for(int row = 0; row < numColumns; row++) {
            //We use getTopWallString() since bottom and top walls are the same.
            System.out.print(getTopWallString());
        }
        System.out.print("\n");
    }
    
    /**
     * Returns a String representing the bottom of a horizontal wall.
     */
    private static String getTopWallString() {
        return "-----+";
    }
    
    /**
     * Returns a String representing the bottom of a square without a
     * horizontal wall.
     */
    private static String getTopOpenString() {
        return "     +";
    }
    
    /**
     * Returns a String representing a left wall (for the interior of the row).
     */
    private static String getRightWallString() {
        return "     |";
    }
    
    /**
     * Returns a String representing no left wall (for the interior of the row).
     */
    private static String getOpenWallString() {
        return "      ";
    }
    
    /**
     * Implement me! This method should return the MazeSquare at the given 
     * row and column. The line "return null" is added only to make the
     * code compile before this method is implemented. Delete that line and
     * replace it with your own code.
     */
    public MazeSquare getMazeSquare(int row, int col) {
        //**************YOUR CODE HERE******************
        return nestedList.get(col).get(row);
    }

	/**
	*Moves one square to the left.
	*/
	private void moveLeft (){
		getMazeSquare(currentrow, currentcol-1).setVisited();
		stack.push (getMazeSquare(currentrow, currentcol-1));
		stack.peek().setSolution();
	}
	/**
	*Moves one square to the right.
	*/
	private void moveRight(){
		getMazeSquare(currentrow, currentcol+1).setVisited();
		stack.push (getMazeSquare(currentrow, currentcol+1));
		stack.peek().setSolution();
	}
	/**
	*Moves one square up.
	*/
	private void moveUp(){
		getMazeSquare(currentrow-1, currentcol).setVisited();
		stack.push (getMazeSquare(currentrow-1, currentcol));
		stack.peek().setSolution();
	}
	/**
	*Moves one square down.
	*/
	private void moveDown(){
		getMazeSquare(currentrow+1, currentcol).setVisited();
		stack.push (getMazeSquare(currentrow+1, currentcol));
		stack.peek().setSolution();
	}
	/**
	*Checks if the square to the right is valid or not.
	*/
	private boolean checkRight(){
		return isInRange(currentcol +1 , 0, numColumns) &&
				!stack.peek().hasRightWall() && !getMazeSquare(currentrow, currentcol+1).isVisited();
	}
	/**
	*Checks if the square to the right is valid or not.
	*/
	private boolean checkUp(){
		return isInRange(currentrow -1 , 0, numRows) &&
			!stack.peek().hasTopWall() 
			&&!getMazeSquare(currentrow -1, currentcol).isVisited();
	}
	/**
	*Checks if the square to the left is valid or not.
	*/
	private boolean checkLeft(){
		return isInRange(currentcol-1, 0, numColumns)
			&&!getMazeSquare(currentrow, currentcol -1).hasRightWall() && 
			!getMazeSquare(currentrow , currentcol-1 ).isVisited();
	}
	/**
	*Checks if the square down is valid or not.
	*/
	private boolean checkDown (){
		return isInRange(currentrow +1 , 0, numRows) &&
			!getMazeSquare(currentrow+1, currentcol).hasTopWall() 
			&&!getMazeSquare(currentrow +1, currentcol).isVisited();
	}


	/**
	* gets the solution of the maze and edits the MazeSquares representing part of the solution 
	*
	*
	*/
	public void getSolution(){
		stack = new MysteryStackImplementation<MazeSquare>();
		stack.push (getMazeSquare(startRow, startColumn));
		getMazeSquare(startRow, startColumn).setVisited();

		while (! stack.isEmpty()){
			currentrow = stack.peek().getRow();
			currentcol = stack.peek().getColumn();

			if (currentrow == finishRow && currentcol == finishColumn) {
				return;
			}
			
			else if (checkRight()){
					moveRight();
			}
			else if (checkUp()){
				moveUp();
			}
			else if (checkLeft()){
				moveLeft();
			}

			else if (checkDown()){
				moveDown();
			}
			else{
				stack.peek().setNotSolution();
				stack.pop();
			}
			}
			System.out.println("The maze is unsolvable!");
		}    

    /**
     * You should modify main so that if there is only one
     * command line argument, it loads the maze and prints it
     * with no solution. If there are two command line arguments
     * and the second one is --solve,
     * it should load the maze, solve it, and print the maze
     * with the solution marked. No other command lines are valid.
     */ 
    public static void main(String[] args) {
		if (args.length < 1){
			System.out.println ("Missing an argument.");
			System.exit(1);
		}
		Maze firstMaze = new Maze ();
		firstMaze.load(args[0]);
		if (args.length > 1 && args[1].equals("--solve")){
			firstMaze.getSolution();
			firstMaze.print();
		}
		else {
			firstMaze.print();
		}
    } 
}