package ai.partB;

/**
 *
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project 1
 * Tutor: Matt De Bono 
 */

//subclass of piece that accounts for vertical players 
public class VerticalPlayer extends GeneralPiece{
	
	public VerticalPlayer(Position pos, char i){
		super(pos,i); 	 
	}
	

//returns an array of the 3 hypothetically possible moves for any vertical piece
//these potential moves are later validated by the Board class 	
	public Position[] hypotheticalMovesV(){
		Position pUp = new Position(this.getPosition().getX(),this.getPosition().getY()-1);
		Position pRight = new Position(this.getPosition().getX()+1,this.getPosition().getY());
		Position pLeft = new Position(this.getPosition().getX()-1,this.getPosition().getY());		
		Position[] pArray = {pUp,pRight,pLeft};
		
		return pArray; 
	}
}

