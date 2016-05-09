package com.skinnyChess.logic;

import java.util.ArrayList;
import java.util.List;

import com.skinnyChess.boardgenerator.ChessBoard;

import com.skinnyChess.console.ChessConsole;

public class ChessGame implements Runnable{
	
        public static long WP=0L,WN=0L,WB=0L,WR=0L,WQ=0L,WK=0L,BP=0L,BN=0L,BB=0L,BR=0L,BQ=0L,BK=0L;
	
        public int gameState = GAME_STATE_WHITE;
	public static final int GAME_STATE_WHITE = 0;
	public static final int GAME_STATE_BLACK = 1;
	public static final int GAME_STATE_END_BLACK_WON = 2;
	public static final int GAME_STATE_END_WHITE_WON = 3;

	// 0 = bottom, size = top
	public List<Piece> pieces = new ArrayList<Piece>();
	private List<Piece> capturedPieces = new ArrayList<Piece>();

	private MoveValidator moveValidator;
	private IPlayerHandler blackPlayerHandler;
	private IPlayerHandler whitePlayerHandler;
	private IPlayerHandler activePlayerHandler;

	/**
	 * initialize game
	 */
	public ChessGame(){
            
                this.moveValidator = new MoveValidator(this);
                newGame();
                // Create and place pieces
                for (int i=0;i<64;i++) 
                {
                    int color=0, type=0, xpos=(i%8) , ypos=(i/8);
                    switch(xpos)
                    {
                        case 0:
                            xpos = Piece.COLUMN_A;
                            break;
                        case 1:
                            xpos = Piece.COLUMN_B;
                            break;
                        case 2:
                            xpos = Piece.COLUMN_C;
                            break;
                        case 3:
                            xpos = Piece.COLUMN_D;
                            break;
                        case 4:
                            xpos = Piece.COLUMN_E;
                            break;
                        case 5:
                            xpos = Piece.COLUMN_F;
                            break;
                        case 6:
                            xpos = Piece.COLUMN_G;
                            break;
                        case 7:
                            xpos = Piece.COLUMN_H;
                            break;
                    }
                    switch(ypos)
                    {
                        case 0:
                            ypos = Piece.ROW_8;
                            break;
                        case 1:
                            ypos = Piece.ROW_7;
                            break;
                        case 2:
                            ypos = Piece.ROW_6;
                            break;
                        case 3:
                            ypos = Piece.ROW_5;
                            break;
                        case 4:
                            ypos = Piece.ROW_4;
                            break;
                        case 5:
                            ypos = Piece.ROW_3;
                            break;
                        case 6:
                            ypos = Piece.ROW_2;
                            break;
                        case 7:
                            ypos = Piece.ROW_1;
                            break;
                    }
                    if (((WK>>i)&1)==1) 
                    {
                        color=Piece.COLOR_WHITE; 
                        type=Piece.TYPE_KING;
                        createAndAddPiece(color, type, ypos, xpos);
                    }
                    else if (((BK>>i)&1)==1)
                    {
                        color=Piece.COLOR_BLACK; 
                        type=Piece.TYPE_KING;
                        createAndAddPiece(color, type, ypos, xpos);
                    }
                    else if (((WQ>>i)&1)==1)
                        {
                        color=Piece.COLOR_WHITE; 
                        type=Piece.TYPE_QUEEN;
                        createAndAddPiece(color, type, ypos, xpos);
                    }
                    else if (((BQ>>i)&1)==1) 
                        {
                        color=Piece.COLOR_BLACK; 
                        type=Piece.TYPE_QUEEN;
                        createAndAddPiece(color, type, ypos, xpos);
                    }
                    else if (((WR>>i)&1)==1) 
                        {
                        color=Piece.COLOR_WHITE; 
                        type=Piece.TYPE_ROOK;
                        createAndAddPiece(color, type, ypos, xpos);
                    }
                    else if (((BR>>i)&1)==1) 
                        {
                        color=Piece.COLOR_BLACK; 
                        type=Piece.TYPE_ROOK;
                        createAndAddPiece(color, type, ypos, xpos);
                    }
                    else if (((WB>>i)&1)==1) 
                        {
                        color=Piece.COLOR_WHITE; 
                        type=Piece.TYPE_BISHOP;
                        createAndAddPiece(color, type, ypos, xpos);
                    }
                    else if (((BB>>i)&1)==1) 
                        {
                        color=Piece.COLOR_BLACK; 
                        type=Piece.TYPE_BISHOP;
                        createAndAddPiece(color, type, ypos, xpos);
                    }
                    else if (((WN>>i)&1)==1) 
                        {
                        color=Piece.COLOR_WHITE; 
                        type=Piece.TYPE_KNIGHT;
                        createAndAddPiece(color, type, ypos, xpos);
                    }
                    else if (((BN>>i)&1)==1) 
                        {
                        color=Piece.COLOR_BLACK; 
                        type=Piece.TYPE_KNIGHT;
                        createAndAddPiece(color, type, ypos, xpos);
                    }
                    else if (((WP>>i)&1)==1) 
                        {
                        color=Piece.COLOR_WHITE; 
                        type=Piece.TYPE_PAWN;
                        createAndAddPiece(color, type, ypos, xpos);
                    }
                    else if (((BP>>i)&1)==1) 
                        {
                        color=Piece.COLOR_BLACK; 
                        type=Piece.TYPE_PAWN;
                        createAndAddPiece(color, type, ypos, xpos);
                    }
                    //createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_3, Piece.COLUMN_A);
                }   
		/*
		// create and place pieces
		// rook, knight, bishop, queen, king, bishop, knight, and rook
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_A);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, Piece.ROW_1, Piece.COLUMN_B);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, Piece.ROW_1, Piece.COLUMN_C);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_QUEEN, Piece.ROW_1, Piece.COLUMN_D);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KING, Piece.ROW_1, Piece.COLUMN_E);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, Piece.ROW_1, Piece.COLUMN_F);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, Piece.ROW_1, Piece.COLUMN_G);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_H);
		
		// pawns
		int currentColumn = Piece.COLUMN_A;
		for (int i = 0; i < 8; i++) {
			createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, Piece.ROW_2, currentColumn);
			currentColumn++;
		}

		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_A);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, Piece.ROW_8, Piece.COLUMN_B);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, Piece.ROW_8, Piece.COLUMN_C);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_QUEEN, Piece.ROW_8, Piece.COLUMN_D);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KING, Piece.ROW_8, Piece.COLUMN_E);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, Piece.ROW_8, Piece.COLUMN_F);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, Piece.ROW_8, Piece.COLUMN_G);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_H);
		
		// pawns
		currentColumn = Piece.COLUMN_A;
		for (int i = 0; i < 8; i++) {
			createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_7, currentColumn);
			currentColumn++;
		}*/
	}
        	
	/**
	 * set the client/player for the specified piece color
	 * @param pieceColor - color the client/player represents 
	 * @param playerHandler - the client/player
	 */
	public void setPlayer(int pieceColor, IPlayerHandler playerHandler){
		switch (pieceColor) {
			case Piece.COLOR_BLACK: this.blackPlayerHandler = playerHandler; break;
			case Piece.COLOR_WHITE: this.whitePlayerHandler = playerHandler; break;
			default: throw new IllegalArgumentException("Invalid pieceColor: "+pieceColor);
		}
	}

	/**
	 * start main game flow
	 */
	public void startGame(){
		// check if all players are ready
		System.out.println("ChessGame: waiting for players");
		while (this.blackPlayerHandler == null || this.whitePlayerHandler == null){
			// players are still missing
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		}
		
		// set start player
		this.activePlayerHandler = this.whitePlayerHandler;
		
		// start game flow
		System.out.println("ChessGame: starting game flow");
		while(!isGameEndConditionReached()){
			waitForMove();
			swapActivePlayer();
		}
		
		System.out.println("ChessGame: game ended");
		ChessConsole.printCurrentGameState(this);
		if(this.gameState == ChessGame.GAME_STATE_END_BLACK_WON){
			System.out.println("Black won!");
			
		}else if(this.gameState == ChessGame.GAME_STATE_END_WHITE_WON){
			System.out.println("White won!");
			
		}else{
			throw new IllegalStateException("Illegal end state: "+this.gameState);
		}
	}

	/**
	 * swap active player and change game state
	 */
	private void swapActivePlayer() {
		if( this.activePlayerHandler == this.whitePlayerHandler ){
			this.activePlayerHandler = this.blackPlayerHandler;
		}else{
			this.activePlayerHandler = this.whitePlayerHandler;
		}
		
		this.changeGameState();
	}

	/**
	 * Wait for client/player move and execute it.
	 * Notify all clients/players about successful execution of move.
	 */
	private void waitForMove() {
		Move move = null;
		// wait for a valid move
		do{
			move = this.activePlayerHandler.getMove();
			try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
			if( move != null && this.moveValidator.isMoveValid(move, false) ){
				break;
			}else if( move != null && !this.moveValidator.isMoveValid(move,true)){
				System.out.println("provided move was invalid: "+move);
				
				ChessConsole.printCurrentGameState(this);
				move=null;
				System.exit(0);
			}
		}while(move == null);
		
		//execute move
		boolean success = this.movePiece(move);
		if(success){
			this.blackPlayerHandler.moveSuccessfullyExecuted(move);
			this.whitePlayerHandler.moveSuccessfullyExecuted(move);
		}else{
			throw new IllegalStateException("move was valid, but failed to execute it");
		}
	}

	/**
	 * create piece instance and add it to the internal list of pieces
	 * 
	 * @param color on of Pieces.COLOR_..
	 * @param type on of Pieces.TYPE_..
	 * @param row on of Pieces.ROW_..
	 * @param column on of Pieces.COLUMN_..
	 */
	public void createAndAddPiece(int color, int type, int row, int column) {
		Piece piece = new Piece(color, type, row, column);
		this.pieces.add(piece);
	}

	/**
	 * Move piece to the specified location. If the target location is occupied
	 * by an opponent piece, that piece is marked as 'captured'. If the move
	 * could not be executed successfully, 'false' is returned and the game
	 * state does not change.
	 * 
	 * @param move to execute
	 * @return true, if piece was moved successfully
	 */
	public boolean movePiece(Move move) {
		//set captured piece in move
		// this information is needed in the undoMove() method.
		move.capturedPiece = this.getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);
		
		Piece piece = getNonCapturedPieceAtLocation(move.sourceRow, move.sourceColumn);

		// check if the move is capturing an opponent piece
		int opponentColor = (piece.getColor() == Piece.COLOR_BLACK ? Piece.COLOR_WHITE
				: Piece.COLOR_BLACK);
		if (isNonCapturedPieceAtLocation(opponentColor, move.targetRow, move.targetColumn)) {
			// handle captured piece
			Piece opponentPiece = getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);
			this.pieces.remove(opponentPiece);
			this.capturedPieces.add(opponentPiece);
			opponentPiece.isCaptured(true);
		}

		// move piece to new position
		piece.setRow(move.targetRow);
		piece.setColumn(move.targetColumn);

		// reset game state
		//if(piece.getColor() == Piece.COLOR_BLACK){
		//	this.gameState = ChessGame.GAME_STATE_BLACK;
		//}else{
		//	this.gameState = ChessGame.GAME_STATE_WHITE;
		//}
		
		return true;
	}
	
	/**
	 * Undo the specified move. It will also adjust the game state appropriately.
	 * @param move
	 */
	public void undoMove(Move move){
		Piece piece = getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);
		
		piece.setRow(move.sourceRow);
		piece.setColumn(move.sourceColumn);
		
		if(move.capturedPiece != null){
			move.capturedPiece.setRow(move.targetRow);
			move.capturedPiece.setColumn(move.targetColumn);
			move.capturedPiece.isCaptured(false);
			this.capturedPieces.remove(move.capturedPiece);
			this.pieces.add(move.capturedPiece);
		}
		
		if(piece.getColor() == Piece.COLOR_BLACK){
			this.gameState = ChessGame.GAME_STATE_BLACK;
		}else{
			this.gameState = ChessGame.GAME_STATE_WHITE;
		}
	}

	/**
	 * check if the games end condition is met: One color has a captured king
	 * 
	 * @return true if the game end condition is met
	 */
	private boolean isGameEndConditionReached() {
		for (Piece piece : this.capturedPieces) {
			if (piece.getType() == Piece.TYPE_KING ) {
				return true;
			} else {
				// continue iterating
			}
		}

		return false;
	}

	/**
	 * returns the first piece at the specified location that is not marked as
	 * 'captured'.
	 * 
	 * @param row one of Piece.ROW_..
	 * @param column one of Piece.COLUMN_..
	 * @return the first not captured piece at the specified location
	 */
	public Piece getNonCapturedPieceAtLocation(int row, int column) {
		for (Piece piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column) {
				return piece;
			}
		}
		return null;
	}

	/**
	 * Checks whether there is a piece at the specified location that is not
	 * marked as 'captured' and has the specified color.
	 * 
	 * @param color one of Piece.COLOR_..
	 * @param row one of Piece.ROW_..
	 * @param column on of Piece.COLUMN_..
	 * @return true, if the location contains a not-captured piece of the
	 *         specified color
	 */
	boolean isNonCapturedPieceAtLocation(int color, int row, int column) {
		for (Piece piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column
					&& piece.getColor() == color) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether there is a non-captured piece at the specified location
	 * 
	 * @param row one of Piece.ROW_..
	 * @param column on of Piece.COLUMN_..
	 * @return true, if the location contains a piece
	 */
	boolean isNonCapturedPieceAtLocation(int row, int column) {
		for (Piece piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return current game state (one of ChessGame.GAME_STATE_..)
	 */
	public int getGameState() {
		return this.gameState;
	}

	/**
	 * @return the internal list of pieces
	 */
	public List<Piece> getPieces() {
		return this.pieces;
	}

	/**
	 * switches the game state depending on the current board situation.
	 */
	public void changeGameState() {

		// check if game end condition has been reached
		//
		if (this.isGameEndConditionReached()) {

			if (this.gameState == ChessGame.GAME_STATE_BLACK) {
				this.gameState = ChessGame.GAME_STATE_END_BLACK_WON;
			} else if(this.gameState == ChessGame.GAME_STATE_WHITE){
				this.gameState = ChessGame.GAME_STATE_END_WHITE_WON;
			}else{
				// leave game state as it is
			}
			return;
		}

		switch (this.gameState) {
			case GAME_STATE_BLACK:
				this.gameState = GAME_STATE_WHITE;
				break;
			case GAME_STATE_WHITE:
				this.gameState = GAME_STATE_BLACK;
				break;
			case GAME_STATE_END_WHITE_WON:
			case GAME_STATE_END_BLACK_WON:// don't change anymore
				break;
			default:
				throw new IllegalStateException("unknown game state:" + this.gameState);
		}
	}
	
	/**
	 * @return current move validator
	 */
	public MoveValidator getMoveValidator(){
		return this.moveValidator;
	}
        
        public static void newGame()
        {
            ChessBoard.newStandardChessGame();
            //ChessBoard.newChess960();
        }

	@Override
	public void run() {
		this.startGame();
	}

}
