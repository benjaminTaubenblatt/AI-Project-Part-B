package ai.partB;

/**
 *
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project 1
 * Tutor: Matt De Bono 
 */
import aiproj.slider.Move;

//subclass of piece that accounts for vertical players 
public class VerticalPiece extends GeneralPiece{
	
	public VerticalPiece(Position pos, char i){
		super(pos,i); 	 
	}
	

//returns an array of the 3 hypothetically possible moves for any vertical piece
//these potential moves are later validated by the Board class 	
	public Move[] hypotheticalMovesV(){
        Move mUp = new Move(this.getPosition().getX(), this.getPosition().getY(), Move.Direction.UP);
        Move mLeft = new Move(this.getPosition().getX(), this.getPosition().getY(), Move.Direction.LEFT);
        Move mRight = new Move(this.getPosition().getX(), this.getPosition().getY(), Move.Direction.RIGHT);

		Move[] mArray = {mUp, mLeft, mRight};
		return mArray; 
	}

}

