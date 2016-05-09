package com.skinnyChess.boardgenerator;

import java.util.Arrays;
import com.skinnyChess.logic.ChessGame;

/**
 * ChessBoard class, Strongly inspired by Logic Crazy chess tutorial
 * @author CHUx MyKeL
 */
public class ChessBoard 
{
    public static void newStandardChessGame()
    {
        long W_KING=0L,W_QUEEN=0L,W_ROOK=0L,W_BISHOP=0L,W_KNIGHT=0L,W_PAW_KNIGHT=0L,B_KING=0L,B_QUEEN=0L,B_ROOK=0L,B_BISHOP=0L,B_KNIGHT=0L,B_PAW_KNIGHT=0L;
        
        String [][] board= {
            {"r","n","b","q","k","b","n","r",},
            {"p","p","p","p","p","p","p","p",},
            {" "," "," "," "," "," "," "," ",},
            {" "," "," "," "," "," "," "," ",},
            {" "," "," "," "," "," "," "," ",},
            {" "," "," "," "," "," "," "," ",},
            {"P","P","P","P","P","P","P","P",},
            {"R","N","B","Q","K","B","N","R",}};

        generateBitboards(board,W_KING,W_QUEEN,W_ROOK,W_BISHOP,W_KNIGHT,W_PAW_KNIGHT,B_KING,B_QUEEN,B_ROOK,B_BISHOP,B_KNIGHT,B_PAW_KNIGHT);
    }
    public static void newChess960() {
        long W_KING=0L,W_QUEEN=0L,W_ROOK=0L,W_BISHOP=0L,W_KNIGHT=0L,W_PAW_KNIGHT=0L,B_KING=0L,B_QUEEN=0L,B_ROOK=0L,B_BISHOP=0L,B_KNIGHT=0L,B_PAW_KNIGHT=0L;
        String board[][]={
            {" "," "," "," "," "," "," "," "},
            {"p","p","p","p","p","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {"P","P","P","P","P","P","P","P"},
            {" "," "," "," "," "," "," "," "}};
        //step 1:
        int random1=(int)(Math.random()*8);
        board[0][random1]="b";
        board[7][random1]="B";
        //step 2:
        int random2=(int)(Math.random()*8);
        while (random2%2==random1%2) {
            random2=(int)(Math.random()*8);
        }
        board[0][random2]="b";
        board[7][random2]="B";
        //step 3:
        int random3=(int)(Math.random()*8);
        while (random3==random1 || random3==random2) {
            random3=(int)(Math.random()*8);
        }
        board[0][random3]="q";
        board[7][random3]="Q";
        //step 4:
        int random4a=(int)(Math.random()*5);
        int counter=0;
        int loop=0;
        while (counter-1<random4a) {
            if (" ".equals(board[0][loop])) {counter++;}
            loop++;
        }
        board[0][loop-1]="n";
        board[7][loop-1]="N";
        int random4b=(int)(Math.random()*4);
        counter=0;
        loop=0;
        while (counter-1<random4b) {
            if (" ".equals(board[0][loop])) {counter++;}
            loop++;
        }
        board[0][loop-1]="n";
        board[7][loop-1]="N";
        //step 5:
        counter=0;
        while (!" ".equals(board[0][counter])) {
            counter++;
        }
        board[0][counter]="r";
        board[7][counter]="R";
        while (!" ".equals(board[0][counter])) {
            counter++;
        }
        board[0][counter]="k";
        board[7][counter]="K";
        while (!" ".equals(board[0][counter])) {
            counter++;
        }
        board[0][counter]="r";
        board[7][counter]="R";
        generateBitboards(board,W_KING,W_QUEEN,W_ROOK,W_BISHOP,W_KNIGHT,W_PAW_KNIGHT,B_KING,B_QUEEN,B_ROOK,B_BISHOP,B_KNIGHT,B_PAW_KNIGHT);
    }

    static void generateBitboards(String [][]board,long W_KING,long W_QUEEN,long W_ROOK,long W_BISHOP,long W_KNIGHT,long W_PAWN,long B_KING,long B_QUEEN,long B_ROOK,long B_BISHOP,long B_KNIGHT,long B_PAWN) 
    {
        long boardScanner=1L;
        for (int i=0; i<64; i++)
        {
            switch(board [i/8][i%8])
            {
                case "K":   W_KING+=boardScanner;
                    break;
                case "Q":   W_QUEEN+=boardScanner;
                    break;
                case "R":   W_ROOK+=boardScanner;
                    break;
                case "B":   W_BISHOP+=boardScanner;
                    break;
                case "N":   W_KNIGHT+=boardScanner;
                    break;
                case "P":   W_PAWN+=boardScanner;
                    break;
                case "k":   B_KING+=boardScanner;
                    break;
                case "q":   B_QUEEN+=boardScanner;
                    break;
                case "r":   B_ROOK+=boardScanner;
                    break;
                case "b":   B_BISHOP+=boardScanner;
                    break;
                case "n":   B_KNIGHT+=boardScanner;
                    break;
                case "p":   B_PAWN+=boardScanner;
                    break;
            }
            boardScanner*=2;
        }
        drawArray(W_KING,W_QUEEN,W_ROOK,W_BISHOP,W_KNIGHT,W_PAWN,B_KING,B_QUEEN,B_ROOK,B_BISHOP,B_KNIGHT,B_PAWN);
        ChessGame.WP=W_PAWN; ChessGame.WN=W_KNIGHT; ChessGame.WB=W_BISHOP;
        ChessGame.WR=W_ROOK; ChessGame.WQ=W_QUEEN; ChessGame.WK=W_KING;
        ChessGame.BP=B_PAWN; ChessGame.BN=B_KNIGHT; ChessGame.BB=B_BISHOP;
        ChessGame.BR=B_ROOK; ChessGame.BQ=B_QUEEN; ChessGame.BK=B_KING;
    }
    public static void drawArray(long W_KING,long W_QUEEN,long W_ROOK,long W_BISHOP,long W_KNIGHT,long W_PAWN,long B_KING,long B_QUEEN,long B_ROOK,long B_BISHOP,long B_KNIGHT,long B_PAWN) {
        String chessBoard[][]=new String[8][8];
        for (int i=0;i<64;i++) {
            chessBoard[i/8][i%8]=" ";
        }
        for (int i=0;i<64;i++) {
            if (((W_PAWN>>i)&1)==1) {chessBoard[i/8][i%8]="P";}
            if (((W_KNIGHT>>i)&1)==1) {chessBoard[i/8][i%8]="N";}
            if (((W_BISHOP>>i)&1)==1) {chessBoard[i/8][i%8]="B";}
            if (((W_ROOK>>i)&1)==1) {chessBoard[i/8][i%8]="R";}
            if (((W_QUEEN>>i)&1)==1) {chessBoard[i/8][i%8]="Q";}
            if (((W_KING>>i)&1)==1) {chessBoard[i/8][i%8]="K";}
            if (((B_PAWN>>i)&1)==1) {chessBoard[i/8][i%8]="p";}
            if (((B_KNIGHT>>i)&1)==1) {chessBoard[i/8][i%8]="n";}
            if (((B_BISHOP>>i)&1)==1) {chessBoard[i/8][i%8]="b";}
            if (((B_ROOK>>i)&1)==1) {chessBoard[i/8][i%8]="r";}
            if (((B_QUEEN>>i)&1)==1) {chessBoard[i/8][i%8]="q";}
            if (((B_KING>>i)&1)==1) {chessBoard[i/8][i%8]="k";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
    }
}