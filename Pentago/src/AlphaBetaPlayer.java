/**
 * @author Tan Pham
 * An AI player with implementation of minimax alpha beta pruning algorithm.
 * The AI will look at DEPTH step ahead to decide the best move.
 */

import java.util.Set;


public class AlphaBetaPlayer extends Player{
	
	private static final int DEPTH = 2;

	public AlphaBetaPlayer(String name) {
		super(name);
	}
	

	@Override
	public Movement move(Board board) {
		Board copy = board.copy();
		boolean myPlayer = this.isWhite();
		return alphabeta(copy, myPlayer,DEPTH , Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/*
	 * alpha beta pruning algorithm.
	 */
	private Movement alphabeta(Board board, boolean player, int depth, int alpha, int beta){
		Set<Movement> nextMoves = this.getPossibleMoves(board);
		
		int score = 0;
		Movement bestMove = (Movement) nextMoves.toArray()[0];

		if(nextMoves.isEmpty()||depth == 0){
			score = evaluate(board);
			bestMove.setMoveScore(score);
			return bestMove;
		}else{
			for (Movement move: nextMoves){
				board.move(move);
				if(player == this.isWhite()){
					score = alphabeta(board, !player, depth -1, alpha, beta).getMoveScore();
					if(score>alpha){
						alpha = score;
						bestMove = move;
					}
				}else{
					score = alphabeta(board, !player, depth-1, alpha, beta).getMoveScore();
					if(score<beta){
						beta = score;
						bestMove = move;
					}
				}
				//undo move
				board.undoMove(move);
				//cut off
				if(alpha>=beta) break;
				setNodesExpanded(getNodesExpanded() + 1);
			}
			bestMove.setMoveScore(player == this.isWhite()? alpha: beta);
			return bestMove;
		}
		
	}

}
