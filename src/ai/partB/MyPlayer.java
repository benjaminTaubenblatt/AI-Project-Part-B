package ai.partB;

import aiproj.slider.Move;
import java.util.Scanner; 

public class MyPlayer implements aiproj.slider.SliderPlayer {
	private BoardObj myBoard; 
	private char playerType;

	public MyPlayer(){
		
	}
	
	public static String extractBadChar(String s){
		String newS = "";
		
		for(int j=0; j<s.length(); j++){
			if((int)s.charAt(j) == 32){ //if the character is a white space
				continue;
			}else{
				newS = newS + s.charAt(j);
			}
		}
		
		return newS;
	}

	
	public void init(int dimension, String board, char player){
		int counter = 0;
		GeneralPiece[][] myWorld = new GeneralPiece[dimension][dimension];
		Scanner sc = new Scanner(board);
		int x = 0;
		while(counter < dimension){
			String temp = extractBadChar(sc.nextLine()); //get first line of input to make game board 
			for(int y=0; y<temp.length(); y++){
				// Horizontal Player
				if(temp.charAt(y) == 'H'){
					Position p = new Position(x,y);
					HorizontalPlayer h = new HorizontalPlayer(p,'H');
					myWorld[x][y] = h;
				// Blank Space	
				}else if(temp.charAt(y) == '+'){
					Position p = new Position(x,y);
					BlankSpace bs = new BlankSpace(p,'+');
					myWorld[x][y] = bs;
				// Block Character
				}else if(temp.charAt(y) == 'B'){
					Position p = new Position(x,y);
					Block bl = new Block(p,'B');
					myWorld[x][y] = bl;
				// Vertical Player 
				}else if(temp.charAt(y) == 'V'){
					Position p = new Position(x,y);
					VerticalPlayer v = new VerticalPlayer(p,'V');
					myWorld[x][y] = v;
				}else if(temp.charAt(y) == '\n'){
					break;
				}else if(counter > dimension){
					break;
				}
			
			}
			x++;
			counter++;
		}
		sc.close();
		/*for (int row = 0; row < dimension; row++) {
	        for (int column = 0; column < myWorld[row].length; column++) {
	            System.out.print(myWorld[row][column].getI() + " ");
	        }
	        System.out.println();
	    }
        System.out.println(); */
        this.myBoard = new BoardObj(dimension,myWorld); //create our board object 
        this.playerType = player; //initiate type  

	}
			
	
	
	public void update(Move move){}
	
	public Move move(){
		Move.Direction d = Move.Direction.DOWN; //just making it compile
		Move m = new Move(1,1,d);
		return m;
	}
	
}
