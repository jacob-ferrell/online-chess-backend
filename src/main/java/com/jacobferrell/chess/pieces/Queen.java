package com.jacobferrell.chess.pieces;
import com.jacobferrell.chess.chessboard.*;
import java.util.Set;
import java.util.HashSet;

public class Queen extends ChessPiece {
    public String name = "QUEEN";

    public Queen(PieceColor color, Position pos, ChessBoard board) {
        super(color, pos, board);
        this.SYMBOL = color == PieceColor.WHITE ? '♕' : '♛';
        this.rank = 2;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ChessPiece getClone(ChessBoard board) {
        Queen clone = new Queen(color, new Position(position.x, position.y), board);
        clone.hasMoved = hasMoved;
        return clone;
    }

    @Override
    public Set<Move> generatePossibleMoves() {
        Set<Move> possibleMoves = new HashSet<>();
        possibleMoves.addAll(getHorizontalMoves());
        possibleMoves.addAll(getVerticalMoves());
        possibleMoves.addAll(getDiagonalMoves());
        return possibleMoves;
    }
}
