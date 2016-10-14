/**
 * @author Tan Pham
 * An AI player with implementation of minimax algorithm.
 * The AI will look at DEPTH step ahead to decide the best move.
 */

import java.util.Set;



public class MiniMaxPlayer extends Player {

	private static final int DEPTH = 2;

	public MiniMaxPlayer(String name) {
		super(name);
	}

	
	/*
	 * (non-Javadoc)
	 * @see Player#move(Board)
	 */
	@Override
	public Movement move(Board board) {
		Board copy = board.copy();
		boolean myPlayer = this.isWhite();
		return minimax(copy, myPlayer, DEPTH);
	}

	/*
	 * Minimax algorithm.
	 */
	private Movement minimax(Board board, boolean player, int depth){
		Set<Movement> nextMoves = this.getPossibleMoves(board);
		int bestScore = (player == this.isWhite())? Integer.MIN_VALUE: Integer.MAX_VALUE;
		int currtScore;
		Movement bestMove = (Movement) nextMoves.toArray()[0];

		if(nextMoves.isEmpty()||depth == 0){
			bestScore = evaluate(board);
		}else{
			for (Movement move: nextMoves){
				board.move(move);
				if(player == this.isWhite()){
					currtScore = minimax(board, !player, depth -1).getMoveScore();
					if(currtScore>bestScore){
						bestScore = currtScore;
						bestMove = move;
					}
				}else{
					currtScore = minimax(board, !player, depth-1).getMoveScore();
					if(currtScore<bestScore){
						bestScore = currtScore;
						bestMove = move;
					}
				}
				//undo move
				board.undoMove(move);
				setNodesExpanded(getNodesExpanded() + 1);
			}
		}
		bestMove.setMoveScore(bestScore);
		return bestMove;
	}

}
