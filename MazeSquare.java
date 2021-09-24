/**
* MazeSquare represents a single square within a Maze.
* @author Anna Rafferty
*/ 
public class MazeSquare {
    //Wall variables
    private boolean hasTopWall = false;
    private boolean hasRightWall = false;
		
    //Location of this square in a larger maze.
    private int row;
    private int col;
	private boolean isVisited = false;
	private boolean isSolution = false ;
	/**
	*Constructor sets the instance variables of the MazeSquare
	*@parm int c represents the column of the MazeSquare represented
	*@parm int r represents the row of the MazeSquare represented
	*@parm char identifier which represents the letter corresponding to the walls of the square
	* Also sets the the boundaries of the square according to identifier.
	*/
	public MazeSquare(int C, int R, char identifier){
		this.col = C;
		this.row = R;
		if (identifier == '7'){
			hasRightWall = true;
			hasTopWall = true;
		}
		else if (identifier == '|'){
			hasRightWall = true;
		}
		else if (identifier =='_'){
			hasTopWall = true;
		}
	}
	/**
	* sets the square as part of the solution 
	*/
	public void setSolution (){
		this.isSolution = true;
	}
	/**
	* Returns true if the the square is part of the soltuion.
	*/
	public boolean isSolution(){
		return isSolution;
	}
	/**
	* sets the square as not part of the solution when you reach a back end 
	*/
	public void setNotSolution(){
		this.isSolution = false;
	}
	/**
	* sets the square being visited before. 
	*/
	public void setVisited(){
		this.isVisited = true;
	}
	/**
	* Returns true if the square is visited; 
	*/
	public boolean isVisited(){
		return isVisited;
	}
    //Additional instance variables that YOU add:
		
		
    /**
     * Returns true if this square has a top wall.
     */
    public boolean hasTopWall() {
        return hasTopWall;
    }
		
    /**
     * Returns true if this square has a right wall.
     */
    public boolean hasRightWall() {
        return hasRightWall;
    }
		
    /**
     * Returns the row this square is in.
     */
    public int getRow() {
        return row;
    }
		
    /**
     * Returns the column this square is in.
     */
    public int getColumn() {
        return col;
    }
    
    
} 