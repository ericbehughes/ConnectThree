package com.threestones.server.gamestate;

import com.threestones.server.gamestate.ThreeStonesServerGameBoard.CellState;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lyrene Labor
 */
public class ThreeStonesServerGame {

    private ThreeStonesServerGameBoard board;

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());

    //GENERAL BASIC LOGIC FOR OUR MOVE SELECTION
    //DEPENDING ON WHERE WE WANT TO TAKE THIS WE CAN ADD MORE MOVE COMPLEXITY SO THAT IT CHECKS THE AVAILALBE
    //SQUARES THAT WILL BECOME AVAILABLE BASE ON A CERTAIN MOVE AND THE SCORES WHITE OR BLACK CAN SCORE IN THE FUTURE (reading ahead of time)
    public ThreeStonesServerGame() {
        this.board = new ThreeStonesServerGameBoard();
    }

    public void updateBoard(int x, int y) {
        board.getBoardChange(x, y);
    }

    public byte[] determineNextMove() {
        List<ThreeStonesMove> possibleMoves = new ArrayList<ThreeStonesMove>();
        CellState[][] gameBoard = board.getBoard();
        int highestMoveValue = 0;
        ThreeStonesMove bestMove = new ThreeStonesMove();
        //LOOPS THROUGH GAMEBOARD
        for (int i = 0; i < gameBoard[0].length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                //IF CURRENT TILE IS AVAILABLE ADD THE MOVE TO THE LIST
                if (gameBoard[i][j] == CellState.AVAILABLE) {
                    log.debug("determineNextMove CellState.Available");
                    //creates new move with possible values
                    ThreeStonesMove move = new ThreeStonesMove(board.checkForThreeStones(i, j, CellState.WHITE), board.checkForThreeStones(i, j, CellState.BLACK), i, j);
                    move.countNearbyTiles(board.getBoard());
                    possibleMoves.add(move);
                }
            }
        }
        //DETERMINE THE BEST MOVE OUT OF THE LIST
        if (possibleMoves.size() > 1) {
            return ThreeStonesMove.determineBestMove(possibleMoves).toByte();
        } else {
            return possibleMoves.get(0).toByte();
        }
    }

    public void startGame() {
        board.startNewGame();
    }

    private ThreeStonesMove createMove(int x, int y) {

        return new ThreeStonesMove();
    }

    public void setBoard(ThreeStonesServerGameBoard board) {
        this.board = board;
    }

}
