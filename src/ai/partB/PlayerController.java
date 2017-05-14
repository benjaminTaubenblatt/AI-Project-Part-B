package ai.partB;

/**
 *
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project 1
 * Tutor: Matt De Bono 
 */
import aiproj.slider.Move;
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List; 

 //piece is the parent class of all game pieces, contains a position object for its location 
 //board is made up of pieces 
public class PlayerController {
	protected BoardObj my_board; //it doesn't need to see the actual board representation 
	protected int dimension;
	protected char player; 
    protected char opponent;

	public PlayerController(int dimension, BoardObj board, char player){
		this.dimension = dimension;
		this.my_board = board;
		this.player = player; 
        if(player=='H')
        {
            this.opponent='V';
        }
        else
        {
            this.opponent='H';
        }
	}

	public ArrayList<Move> getPotentialMoves(BoardObj root, char p)
	{
		ArrayList<Move> moves = new ArrayList<Move>(); 
		for(int i=0; i<dimension; i++)
		{
			for(int j=0; j<dimension; j++)
			{
				if(root.getPiece(i,j) instanceof VerticalPiece && p=='V')
				{
					VerticalPiece piece = (VerticalPiece) root.getPiece(i,j);
					Move[] hyp_movs = piece.hypotheticalMovesV(); 
					for(Move move: hyp_movs)
					{
						Position pos = new Position(0,0);  //java is so annoying 
						if(move.d == Move.Direction.UP)
						{
							pos = new Position(move.i, move.j+1); 
						}
						else if (move.d == Move.Direction.DOWN)
						{
							pos = new Position(move.i, move.j-1); 
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

                else if(root.getPiece(i,j) instanceof HorizontalPiece && p=='H')
                {
					HorizontalPiece piece = (HorizontalPiece) root.getPiece(i,j);
					Move[] hyp_movs = piece.hypotheticalMovesH(); 
					for(Move move: hyp_movs)
					{
						Position pos = new Position(0,0);  //java is so annoying 
						if(move.d == Move.Direction.UP)
						{
							pos = new Position(move.i, move.j+1); 
						}
						else if (move.d == Move.Direction.DOWN)
						{
							pos = new Position(move.i, move.j-1); 
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
	public int evaluate(BoardObj curr_board, char p)
	{
		int evaluate = 0; 
		for(int i =0; i<curr_board.getDimension(); i++)
		{
			for(int j=0; j<curr_board.getDimension(); j++)
			{
				if (p=='V' && curr_board.getPiece(i,j) instanceof VerticalPiece)
				{
					int comp_1 = dimension-j;  //how many spaces til it goes off the board 
					//check rest of that column to see how many pieces are blocking this piece 
					int comp_2 = 0; 
					for(int n=0; n<curr_board.getDimension();n++)
					{
						if(!(curr_board.getPiece(i,n) instanceof BlankSpace)) //ie there is something blocking our piece
						{
							comp_2++; 
						}
					}
					evaluate += comp_1+comp_2; 
				}

				else if (p=='H' && curr_board.getPiece(i,j) instanceof HorizontalPiece)
				{
					int comp_1 = dimension-i;  //how many spaces til it goes off the board 
					//check rest of that column to see how many pieces are blocking this piece 
					int comp_2 = 0; 
					for(int n=0; n<curr_board.getDimension(); n++)
					{
						if(!(curr_board.getPiece(n,i) instanceof BlankSpace)) //ie there is something blocking our piece
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

	public void update(Move move, BoardObj board, char p)
    {
		if(p=='V')
		{
        //GeneralPiece curr_pos = super.my_board.getPiece(move.i, move.j); 
        	if(move.d == Move.Direction.UP)
        	{
            	board.setPiece(move.i, move.j+1, new HorizontalPiece(new Position(move.i, move.j+1),'H'));
        	}
        	else if(move.d==Move.Direction.DOWN)
        	{
            	board.setPiece(move.i, move.j-1, new HorizontalPiece(new Position(move.i, move.j-1),'H'));
       		}
        	else if(move.d==Move.Direction.LEFT)
        	{
            	board.setPiece(move.i-1, move.j, new HorizontalPiece(new Position(move.i-1, move.j),'H'));
        	}
        	else if(move.d==Move.Direction.RIGHT)
        	{
                if(move.i+1>=dimension) //if their player went off the board 
                {}
                else
                {
            	    board.setPiece(move.i+1, move.j, new HorizontalPiece(new Position(move.i+1, move.j),'H'));
                }
        	}
			board.setPiece(move.i, move.j, new BlankSpace(new Position(move.i, move.j), '+'));		
    	}
		else if(p=='H')
		{
			if(move.d == Move.Direction.UP)
        	{
                if(move.j+1 >= dimension)  //if their player went off the board 
                {}
                else
                {
            	    board.setPiece(move.i, move.j+1, new VerticalPiece(new Position(move.i, move.j+1),'V'));
                }
        	}
        	else if(move.d==Move.Direction.DOWN)
        	{
            	board.setPiece(move.i, move.j-1, new VerticalPiece(new Position(move.i, move.j-1),'V'));
       		}
        	else if(move.d==Move.Direction.LEFT)
        	{
            	board.setPiece(move.i-1, move.j, new VerticalPiece(new Position(move.i-1, move.j),'V'));
        	}
        	else if(move.d==Move.Direction.RIGHT)
        	{
            	board.setPiece(move.i+1, move.j, new VerticalPiece(new Position(move.i+1, move.j),'V'));
        	}
			board.setPiece(move.i, move.j, new BlankSpace(new Position(move.i, move.j), '+'));
		}
		else
		{
			System.out.println("player symbol error");
		}
	}

    public void generate_Tree(TreeNode<BoardObj> b, char p, int n)
    {
        if(n==0)
        {
            return; 
        }
        ArrayList<Move> moves = getPotentialMoves(b.data, p); 
        if(moves.isEmpty())//end branch of the tree 
        {
            return; 
        }
        //b.eval = evaluate(b.data, p);
        for(Move m: moves){
		    TreeNode<BoardObj> temp= new TreeNode<BoardObj>(b.data.getDimension(), b.data.getWorld()); 
			update(m, temp.data, p);
			b.addChild(temp);
            generate_Tree(b, p, n-1); 
        }
    }

    public Move move()
    {
		ArrayList<Move> level_one = getPotentialMoves(my_board, player); 
        TreeNode<BoardObj> root = new TreeNode<BoardObj>(my_board);
        //root.eval = evaluate(my_board, player); 
        generate_Tree(root,player);
        
        //TreeNode<BoardObj> current = root; 

       /* int i=0; 
        //if there is a layer with no children 
        while(i<5 && !(current.children.isEmpty()))
        {
            for(TreeNode<BoardObj> c : current.children){
                if(c.getLevel()%2 == 0){
                    generate_Tree(c,player);
                } else {
                    generate_Tree(c,opponent);
                }
            }
            current = c; 
            i++; 
        }*/

        update(level_one.get(0), my_board, opponent); 
        System.out.println("Player:" + player); 
        System.out.println("" + level_one.get(0).i +  "," + level_one.get(0).j + "," + level_one.get(0).d); 
        if(level_one.isEmpty())
        {
            return(null); 
        }
        return(level_one.get(0)); 
    }
	
	public String[][] create_board_representation(String board)//create a board representation from String 
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
	}
	
	
}
