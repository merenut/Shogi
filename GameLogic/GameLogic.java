package GameLogic;

import Game.Board;
import Game.Game;
import Game.Piece;

public class GameLogic {
	public static boolean handleMove(Game refGame, Game defactoGame) 
	{	
		int oldX = -1, oldY = -1, newX = -1, newY = -1;
		
		//old board
		Board bOld = refGame.getBoard();
		//old pieces
		Piece[][] pOld = bOld.getGameBoard();
		//new board
		Board bNew = defactoGame.getBoard();
		//new pieces
		Piece[][] pNew = bNew.getGameBoard();
		
		int[][] move = defactoGame.getMoveSet();
		
		oldX = move[0][1];
		oldY = move[0][0];
		newX = move[1][1];
		newY = move[1][0];
		
		
		if(pOld[newY][newX] != null)
		{
			defactoGame.addCapturedPiece(pOld[newY][newX]);
		}
		
		if(oldX < 0 || oldY < 0 || newX < 0 || newY < 0)
		{
			System.out.println("Something went wrong");
			return false;
		}
		
		
		//get moveset
		Integer[][] moveset = pOld[oldY][oldX].getMoveset();
		
		for(Integer[] set : moveset)
		{
			int xdir = set[1];
			int ydir = set[0];
			int d = set[2];
			
			
			if(((set[0] * set[2]) + oldY) == newY && ((set[1] * set[2]) + oldX) == newX)
			{
				
				return true;
			}
		}
		
		
		return false;
		
		
	}
	
	public static boolean winCheck(Game defactoGame)
	{
		Piece[][] board = defactoGame.getBoard().getGameBoard();
		
		int x = defactoGame.getMoveSet()[1][1];
		int y = defactoGame.getMoveSet()[1][0];
		
		Piece lastMoved = board[y][x];
		
		String dir = lastMoved.getDirection();
		
		
		for(Piece p : defactoGame.getCaptured())
		{
			if(p.getId().equals("King"))
			{
				//check direction
				if(!dir.equals(p.getDirection()))
				{
					System.out.println("Win");
				}
				
				return true;
			}
		}
		
		return true;
	}
	
	public static boolean handlePromotion(Game refGame, Game defactoGame) 
	{
		
		int[] ploc = defactoGame.getPromotionLocation();
		
		int y = ploc[0];
		int x = ploc[1];
		
		Piece[][] pieces = defactoGame.getBoard().getGameBoard();
		
		Piece prom = pieces[y][x];
		prom.promote();
		
		String dir = prom.getDirection();
		
		switch(dir)
		{
		case "up":
			if(y <= 2)
			{
				return true;
			}
			break;
		case "down":
			if(y >= 6 )
			{
				return true;
			}
			break;
		}
		
		return false;
	}
	public static Game applyMove(Game game) {
		return game; 
	}
	public static Game applyPromotion(Game game) {
		return game; 
	}
	
}
