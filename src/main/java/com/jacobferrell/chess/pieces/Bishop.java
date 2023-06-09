package com.jacobferrell.chess.pieces;
import com.jacobferrell.chess.chessboard.*;
import java.util.Set;


public class Bishop extends ChessPiece {
    public String name = "BISHOP";

    public Bishop(PieceColor color, Position pos, ChessBoard board) {
        super(color, pos, board);
        this.SYMBOL = color == PieceColor.WHITE ? '♗' : '♝';
        this.rank = 4;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ChessPiece getClone(ChessBoard board) {
        Bishop clone = new Bishop(color, new Position(position.x, position.y), board);
        clone.hasMoved = hasMoved;
        return clone;
    }

    @Override
    public Set<Move> generatePossibleMoves() {
        return getDiagonalMoves();
    }

}
