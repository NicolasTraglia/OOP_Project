package backend;
import windowInterface.MyInterface;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
//TODO :
// FIX ISSUE WITH STEP METHOD
// currently, the step method works but doesnt detect correctly some cells for unknown reasons.
//
/*
 *  Note : if you use an import in another class you will need to add
 *  the import lines on top of the file of the other class.
 */
// Examples of useful imports :
// import java.util.LinkedList;
// import java.util.ArrayList;
// import java.util.Random;

public class Simulator extends Thread {

	private MyInterface mjf;
	private boolean stopFlag;
	private boolean pauseFlag;
	private int loopDelay;
	private boolean loopingState;
	private int[][] mainGrid = new int[100][100];
	//TODO : add declaration of additional attributes here

	public Simulator(MyInterface mjfParam) {
		mjf = mjfParam;
		stopFlag=false;
		pauseFlag=false;
		loopDelay = 150;
	    loopingState = false;
		//TODO : add other attribute initialization here

	}
	
	/**
	 * getter of the width of the simulated world
	 * @return the number of columns in the grid composing the simulated world
	 */
	public int getWidth() {
		//TODO : correct return
		return mainGrid.length;
	}

	/**
	 * getter of the height of the simulated world
	 * @return the number of rows in the grid composing the simulated world
	 */
	public int getHeight() {
		//TODO : correct return
		return mainGrid[0].length;
	}
	
	//dont touch this -----------------
	public void run() {
		//WARNING : Do not modify this.
		/*Exception : 
		 *if everything you have to do works and you have a backup... 
		 *have fun editing this if you want to enhance it!
		 *But be sure to take into account that this class inherits from Thread
		 *You might want to check documentation online about the Thread class
		 *But do not hesitate to email me any questions 
		*/
		int stepCount=0;
		while(!stopFlag) {
			stepCount++;
			makeStep();
			mjf.update(stepCount);
			try {
				Thread.sleep(loopDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(pauseFlag && !stopFlag) {
				try {
					Thread.sleep(loopDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
	//dont touch this -----------------

	public void makeStep(){
		int[][] newBoard = new int[getWidth()][getHeight()];
		for (int i=0; i<getWidth();i++){
		for (int j=0;j<getHeight();j++){
		int count = count_Cell_Alive(i, j);
		if (count == 2){
		newBoard[i][j] = getCell(i, j);
		} else if (count==3) {
		newBoard[i][j] = 1;
		} 
		}
		}
		mainGrid = newBoard;
		}
		/*
		*Counts the number of alive cells neighboring the cell located in (x, y)
		*@param x coordinate x horizontal (int)
		*@param y coordinate y vertical (int)
		*/
		private int count_Cell_Alive(int x, int y){
			int count = 0; //number of alive cells detected
			for (int i=x-1; i<=x+1; i++){ //x grid loop
				for (int j=y-1; j<=y+1; j++){ //y grid loop
					if (i!=x || j!=y){
						
						if(loopingState) {
							int nI=i;
							int nJ=j;
							if(i<0) {
								nI=getWidth()-1;
							}
							else if(i>=getWidth()) {
								nI=0;
							}
							if(j<0) {
								nJ=getHeight()-1;
							}
							else if(j>=getHeight()) {
								nJ=0;
							}
							
							if(getCell(nI,nJ)==1) {
								count+=1;
							}
						}
						
						else if (i>=0 && j>=0 && i<getWidth() && j<getHeight()) { //if not a bound
							if (getCell(i, j) !=0) {
							count++;
							}
						}
					}
				}
			}
			return count;
		}
	
	/**
	 * Stops simulation by raising the stop flag used in the run method
	 */
	public void stopSimu() {
		//TODO : set stopFlag to true
		stopFlag = true;		
	}

	/**
	 * Toggles Pause of simulation 
	 * by raising or lowering the pause flag used in the run method
	 */
	public void togglePause() {
		//TODO : change value of boolean attribute pauseFlag
		// from false to true, or from true to false
		pauseFlag =!pauseFlag; 
	}
	
	/**
	 * Changes content value of the Cell at the coordinates specified in arguments
	 * 
	 * 
	 * @param x coordinate on the x-axis (horizontal)
	 * @param y coordinate on the y-axis (vertical)
	 */
	public void toggleCell(int x, int y) {
		//TODO : change the value of the cell at coordinates (x,y)
		/*
		 * Note : the value of the cell is NOT a boolean, it is an integer.
		 * O means dead, 1 means alive...
		 * But the GUI can also print properly more values than that.
		 * You might want to use this for the going further section.
		 */
		if(true) { //For debugging purposes. true or false.
			System.out.println("Cell toggled at x="+x+", y= "+y);
		}
		if (mainGrid[x][y]==1) {
			mainGrid[x][y]=0;
		}
		else {
			mainGrid[x][y]=1;
		}
	}
	/**
	 * get the value of a cell at coordinates
	 * @param x coordinate
	 * @param y coordinate
	 * @return the value of the cell
	 */
	public int getCell(int x, int y) {
		//TODO implement proper return
		return mainGrid[x][y];
	}

	/**
	 * set the value of a cell at coordinates
	 * @param x coordinate
	 * @param y coordinate
	 * @param val the value to set inside the cell
	 */
	public void setCell(int x, int y, int val) {
		//TODO implement
		mainGrid[x][y]=val;
	}

	/**
	 * Each String in the returned array represents a [row/column]
	 * 
	 * @return an array of Strings representing the simulated world's state
	 */
	public String[] getFileRepresentation() {
		//TODO : implement
		String placeholder = new String();
		String[] output= new String[getWidth()-1];
		for(int i=0;i==getWidth();i++) {
			placeholder ="";
			for(int j=0;j==getHeight();j++) {
				placeholder += mainGrid[i][j]+";";
			}
			output[i]=placeholder;
		}
		return output;
	}
	/**
	 * Populates a [row/column] indicated by the given coordinate
	 * using its String representation
	 * 
	 * @param y the y coordinate of the row/column to populate
	 * @param fileLine the String line representing the row
	 */
	public void populateLine(int coord, String fileLine) {
		//TODO : implement and correct the comment
		// As you have to choose row OR column depending on your implementation
		String placeholder = fileLine.replace(";","");
		if(coord<getHeight()) {
			for(int i=0;i<getWidth();i++){
				mainGrid[i][coord]=Character.getNumericValue(placeholder.charAt(i));
			}
		}
	}
	
	/**
	 * populates world with randomly living cells
	 * 
	 * @param chanceOfLife the probability, expressed between 0 and 1, 
	 * that any given cell will be living
	 */
	public void generateRandom(float chanceOfLife) {
		//TODO implement
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; i < getHeight();j++) {
				Random randI = new Random();
				int myRandInt = randI.nextInt(100);
		        myRandInt = myRandInt+1;
				if(myRandInt>=chanceOfLife){
					mainGrid[i][j]=1;
				}
			}
		}
		
	}
	
	/**
	 * Checks if the borders are looping
	 * 
	 * @return true if the borders are looping, false otherwise
	 */
	public boolean isLoopingBorder() {
		return loopingState;
	}
	
	/**
	 * Toggle the looping of borders, activating or deactivating it
	 * depending on the present state
	 */
	public void toggleLoopingBorder() {
		loopingState =! loopingState;
	}
	
	/**
	 * Setter for the delay between steps of the simulation
	 * @param delay in milliseconds
	 */
	public void setLoopDelay(int delay) {
		//TODO : implement
		loopDelay =delay;
	}
}
