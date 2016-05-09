package com.skinnyChess.boardgenerator;

import com.skinnyChess.ai.SimpleAiPlayerHandler;
//import com.skinnyChess.console.ChessConsole;
import com.skinnyChess.gui.ChessGui;
import com.skinnyChess.logic.ChessGame;
import com.skinnyChess.logic.Piece;

public class Main {

	public static void main(String[] args) {

		// first we create the game
		ChessGame chessGame = new ChessGame();

		// then we create the clients/players
		ChessGui chessGui = new ChessGui(chessGame);
		//ChessConsole chessConsole = new ChessConsole(chessGame);
		SimpleAiPlayerHandler ai1 = new SimpleAiPlayerHandler(chessGame);
		SimpleAiPlayerHandler ai2 = new SimpleAiPlayerHandler(chessGame);

		// set strength of AI
		ai1.maxDepth = 2;
		ai2.maxDepth = 2;

		// then we attach the clients/players to the game
		//chessGame.setPlayer(Piece.COLOR_WHITE, chessGui);
		//chessGame.setPlayer(Piece.COLOR_WHITE, chessConsole);
		//chessGame.setPlayer(Piece.COLOR_WHITE, chessGui);
		//chessGame.setPlayer(Piece.COLOR_BLACK, ai1);
                chessGame.setPlayer(Piece.COLOR_WHITE, chessGui);
		chessGame.setPlayer(Piece.COLOR_BLACK, ai2);

		// in the end we start the game
		new Thread(chessGame).start();
	}
	
}