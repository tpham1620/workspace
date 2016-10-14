/**
 * @author Tan Pham
 *	Game interface.
 * Control the game and players to take turn to play
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Pentago {
	
	static Player white;
	static Player black;
	Board board;
	Player winner;
	boolean isTie = false;
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public Pentago() {
		board = new Board();
	}
	
	
	static void teller(String message) {
			System.out.println(message);
	}
	
	public void setPlayer (Player player) {
		if (player.isWhite()) {
			white = player;
		} else {
			black = player;
		}
	}
	
	
	/*
	 * control the game.
	 * players take turn to move
	 */
	boolean playerMove(Player player) {
		Movement move = null;
		boolean moveOK = false;
		while (!moveOK) {
			teller("-------------------------------------------------");
			teller(player + " turn!");
			move = player.move(board);
			moveOK = board.checkCoordinate(move.coords);
			if (!moveOK) {
				teller("Invalid Move from " + player + ": " + move);
			}
		}
		board.move(move);
		teller(player.name + " made move: " + move.getBlock() + "/" + (move.getUnit()+1) + " " + (move.getTwistBlock() + 1) + move.twist);
		teller("Nodes expanded for this move: " + player.getNodesExpanded());
		player.setNodesExpanded(0);
		teller(board.toString());
		if (board.detectWin()) {
			teller("Win detected");
			return true;
		}
		return false;
	}
	
	/*
	 * start game.
	 */
	public void play() {
		teller("Starting Game.");
		teller(board.toString());
		for (int i=0;i<18;i++) {
			if (playerMove(white)) {
				break;
			}
			if (playerMove(black)) {
				break;
			}
		}
		if (board.isWon) {
			Block.Unit winnerU = board.getWinnerUnit();
			teller("Winner is " + winnerU);
			teller(board.toString());
			if (winnerU == Block.Unit.BLACK) {
				winner = black;
			} else {
				winner = white;
			}
		} else {
			//We have a tie
			teller("There is no Winner in this game.");
			isTie = true;
		}
	}
	
	/*
	 * main method.
	 * declaration game interface.
	 */
	public static void main(String[] args) {
		boolean successfull = false;
		String p1 = null, p2 = null, n1 = "", n2 = "";
		Player player1, player2;
		Pentago game = new Pentago();
		
		while(!successfull){			
			try {
				teller("Please select role for player 1: \nH for Human, A for AI with Minmax algorithm, P for AI with pruning alpha-beta algorithm.");
				p1 = br.readLine();
				teller("Player 1 name?");
				n1 = br.readLine();
				teller("Please select role for player 1: \nH for Human, A for AI with Minmax algorithm, P for AI with pruning alpha-beta algorithm.");
				p2 = br.readLine();
				teller("Player 2 name?");
				n2 = br.readLine();
				successfull = true;
			} catch (IOException e) {
				teller("Unknown Input. \nRestart!");
			}
		}
		
		if(p1.equalsIgnoreCase("h")){
			player1 = new HumanPlayer(n1);
			
		}else if(p1.equalsIgnoreCase("a")){
			player1 = new MiniMaxPlayer(n1);
		}else{
			player1 = new AlphaBetaPlayer(n1);
		}
		
		if(p2.equalsIgnoreCase("h")){
			player2 = new HumanPlayer(n2);
			
		}else if(p2.equalsIgnoreCase("a")){
			player2 = new MiniMaxPlayer(n2);
		}else{
			player2 = new AlphaBetaPlayer(n2);
		}
		
		Random rd = new Random();
		if(rd.nextBoolean() == true){
			player1.setWhite(true);
			player2.setWhite(false);
		}else{
			player2.setWhite(true);
			player1.setWhite(false);
		}
		
		game.setPlayer(player1);
		game.setPlayer(player2);
		
		teller("Player 1 name: " + n1);
		teller("Player 2 name: " + n2);
		teller("Player 1 token: " + player1.getUnit().toString());
		teller("Player 2 token: " + player2.getUnit().toString());
		teller("Player go first (random): " + white.name);
		
		game.play();
		
	}

}
