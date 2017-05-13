package ai.partB;

/**
 *
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project 1
 * Tutor: Matt De Bono 
 */
import aiproj.slider.Move;
import java.util.ArrayList; 

 //piece is the parent class of all game pieces, contains a position object for its location 
 //board is made up of pieces 
public class abstract_Player {
	protected BoardObj my_board; //it doesn't need to see the actual board representation 
	protected int dimension;
	protected char player; 

	public abstract_Player(int dimension, BoardObj board, char player){
		this.dimension = dimension;
		this.my_board = my_board;
		this.player = player; 
	}

	public ArrayList<Move> getPotentialMoves(BoardObj root, char player)
	{
		ArrayList<Move> moves = new ArrayList<Move>(); 
		for(int i=0; i<dimension; i++)
		{
			for(int j=0; j<dimension; j++)
			{
				if(root.getPiece(i,j) instanceof VerticalPiece && player=='V')
				{
					VerticalPiece piece = (VerticalPiece) root.getPiece(i,j);
					Move[] hyp_movs = piece.hypotheticalMovesV(); 
					for(Move move: hyp_movs)
					{
						Position pos = new Position(0,0);  //java is so annoying 
						if(move.d == Move.Direction.UP)
						{
							pos = new Position(move.i, move.j-1); 
						}
						else if (move.d == Move.Direction.DOWN)
						{
							pos = new Position(move.i, move.j+1); 
						}
						else if(move.d== Move.Direction.LEFT)
						{
							pos = new Position(move.i-1, move.j);
						}
						else if(move.d == Move.Direction.RIGHT)
						{
							pos = new Position(move.i+1, move.j); 
						}
						if(root.checkVerticalSpot(pos))
						{
							moves.add(move); 
						}
					}
				}

                else if(root.getPiece(i,j) instanceof HorizontalPiece && player=='H')
                {
					HorizontalPiece piece = (HorizontalPiece) root.getPiece(i,j);
					Move[] hyp_movs = piece.hypotheticalMovesH(); 
					for(Move move: hyp_movs)
					{
						Position pos = new Position(0,0);  //java is so annoying 
						if(move.d == Move.Direction.UP)
						{
							pos = new Position(move.i, move.j-1); 
						}
						else if (move.d == Move.Direction.DOWN)
						{
							pos = new Position(move.i, move.j+1); 
						}
						else if(move.d== Move.Direction.LEFT)
						{
							pos = new Position(move.i-1, move.j);
						}
						else if(move.d == Move.Direction.RIGHT)
						{
							pos = new Position(move.i+1, move.j); 
						}
						if(root.checkHorizontalSpot(pos))
						{
							moves.add(move); 
						}
					}

                }
			}
		}
		return(moves);
	}

	//our evaluation function is to minimize the sum over our pieces of (the number of moves to go off the board + number of blocked spaces)
	//these 2 components to our featuers will likely need different weights, tbd later 
	public int evaluate(BoardObj curr_board, char player)
	{
		int evaluate = 0; 
		for(int i =0; i<curr_board.getDimension(); i++)
		{
			for(int j=0; j<curr_board.getDimension(); j++)
			{
				if (player=='V' && curr_board.getPiece(i,j) instanceof VerticalPiece)
				{
					int comp_1 = i+1;  //how many spaces til it goes off the board 
					//check rest of that column to see how many pieces are blocking this piece 
					int comp_2 = 0; 
					for(int n=0; n<curr_board.getDimension();n++)
					{
						if(!(curr_board.getPiece(n,i) instanceof BlankSpace)) //ie there is something blocking our piece
						{
							comp_2++; 
						}
					}
					evaluate += comp_1+comp_2; 
				}
				else if (player=='H' && curr_board.getPiece(i,j) instanceof HorizontalPiece)
				{
					int comp_1 = j+1;  //how many spaces til it goes off the board 
					//check rest of that column to see how many pieces are blocking this piece 
					int comp_2 = 0; 
					for(int n=0; n<curr_board.getDimension(); n++)
					{
						if(!(curr_board.getPiece(i,n) instanceof BlankSpace)) //ie there is something blocking our piece
						{
							comp_2++; 
						}
					}
					evaluate += comp_1+comp_2; 

				}
			}
		}

		return(evaluate); 
	}

	public void update(Move move, BoardObj board, char player)
    {
		if(player=='V')
		{
        //GeneralPiece curr_pos = super.my_board.getPiece(move.i, move.j); 
        	if(move.d == Move.Direction.UP)
        	{
            	board.setPiece(move.i, move.j-1, new VerticalPiece(new Position(move.i, move.j-1),'H'));
        	}
        	else if(move.d==Move.Direction.DOWN)
        	{
            	board.setPiece(move.i, move.j+1, new VerticalPiece(new Position(move.i, move.j-1),'H'));
       		}
        	else if(move.d==Move.Direction.LEFT)
        	{
            	board.setPiece(move.i-1, move.j, new VerticalPiece(new Position(move.i, move.j-1),'H'));
        	}
        	else if(move.d==Move.Direction.RIGHT)
        	{
            	board.setPiece(move.i+1, move.j, new VerticalPiece(new Position(move.i, move.j-1),'H'));
        	}
			board.setPiece(move.i, move.j, new BlankSpace(new Position(move.i, move.j), '+'));		
    	}
		else if(player=='H')
		{
			if(move.d == Move.Direction.UP)
        	{
            	board.setPiece(move.i, move.j-1, new VerticalPiece(new Position(move.i, move.j-1),'V'));
        	}
        	else if(move.d==Move.Direction.DOWN)
        	{
            	board.setPiece(move.i, move.j+1, new VerticalPiece(new Position(move.i, move.j-1),'V'));
       		}
        	else if(move.d==Move.Direction.LEFT)
        	{
            	board.setPiece(move.i-1, move.j, new VerticalPiece(new Position(move.i, move.j-1),'V'));
        	}
        	else if(move.d==Move.Direction.RIGHT)
        	{
            	board.setPiece(move.i+1, move.j, new VerticalPiece(new Position(move.i, move.j-1),'V'));
        	}
			board.setPiece(move.i, move.j, new BlankSpace(new Position(move.i, move.j), '+'));
		}
		else
		{
			System.out.println("player symbol error");
		}
	}


    public Move move()
    {
		ArrayList<Move> level_one = getPotentialMoves(my_board, player); 
        return(level_one[0]); 
    }
	
	/*public String[][] create_board_representation(String board)//create a board representation from String 
	{
		String [][] arr_board;
		String board_nospace = board.replaceAll("\\s+",""); //delete all white space
		String lines[] = board_nospace.split("\\r?\\n"); //split into array based on skipline characters
		for(int i=0; i<lines.length; i++)
		{
			for(int j=0; i<lines[i].length();i++)
			{
				arr_board[i][j] = lines[i].charAt(j); 
			}
		} 
		return arr_board;
	}*/
	
	
}
