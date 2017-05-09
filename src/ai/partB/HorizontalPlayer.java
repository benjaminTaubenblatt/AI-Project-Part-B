package ai.partB;

/**
 *
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project 1
 * Tutor: Matt De Bono 
 */

//subclass of piece that accounts for horizontal players 
//the benefit to subclassing is we can use instanceOf to verify the type before we apply move 

public class HorizontalPlayer extends GeneralPiece{
	
	public HorizontalPlayer(Position pos, char i){
		super(pos,i);		 
	}
	
	
	//returns an array of the 3 hypothetically possible moves for any horizontal piece
	//these potential moves are later validated by the Board class 
	public Position[] hypotheticalMovesH(){
		Position pUp = new Position(this.getPosition().getX(),this.getPosition().getY()-1);
		Position pRight = new Position(this.getPosition().getX()+1,this.getPosition().getY());
		Position pDown = new Position(this.getPosition().getX(),this.getPosition().getY()+1);		
		Position[] pArray = {pUp,pRight,pDown};
		
		return pArray; 
	}
}
