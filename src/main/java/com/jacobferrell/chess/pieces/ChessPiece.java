package com.jacobferrell.chess.pieces;

import com.jacobferrell.chess.chessboard.*;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

public abstract class ChessPiece {
    public PieceColor color;
    protected int counter;
    public boolean hasMoved;
    public Position position;
    protected ChessBoard board;
    public int rank;

    public ChessPiece(PieceColor color, Position pos, ChessBoard board) {
        this.color = color;
        this.position = pos;
        this.board = board;
        this.hasMoved = false;
    }

    public boolean isValidMove(int x, int y) {
        if (Math.min(x, y) < 0 || Math.max(x, y) > 7) {
            return false;
        }
        Position pos = new Position(x, y);
        if (!board.isSpaceOccupied(pos)) {
            return true;
        }
        ChessPiece otherPiece = board.getPieceAtPosition(pos);
        if (isEnemyPiece(otherPiece)) {
            return true;
        }
        return false;
    }

    public abstract ChessPiece getClone(ChessBoard clonedBoard);

    public abstract char getSymbol();

    public abstract Set<Move> generatePossibleMoves();

    public abstract String getName();

    public void setHasMoved() {
        this.hasMoved = true;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    // Add all possible horizontal moves
    public Set<Move> getHorizontalMoves() {
        Set<Move> possibleMoves = new HashSet<>();
        int y = position.y;
        for (int x = position.x + 1; isValidMove(x, y); x++) {
            Position pos = new Position(x, y);
            possibleMoves.add(new Move(this, pos));
            if (board.isSpaceOccupied(pos)) {
                break;
            }
        }

        for (int x = position.x - 1; isValidMove(x, y); x--) {
            Position pos = new Position(x, y);
            possibleMoves.add(new Move(this, pos));
            if (board.isSpaceOccupied(pos)) {
                break;
            }
        }

        return possibleMoves;
    }

    // Add all possible vertical moves
    public Set<Move> getVerticalMoves() {
        Set<Move> possibleMoves = new HashSet<>();
        int x = position.x;
        for (int y = position.y + 1; isValidMove(x, y); y++) {
            Position pos = new Position(x, y);
            possibleMoves.add(new Move(this, pos));
            if (board.isSpaceOccupied(pos)) {
                break;
            }
        }

        for (int y = position.y - 1; isValidMove(x, y); y--) {
            Position pos = new Position(x, y);
            possibleMoves.add(new Move(this, pos));
            if (board.isSpaceOccupied(pos)) {
                break;
            }
        }

        return possibleMoves;
    }

    // Add all possible diagonal moves
    public Set<Move> getDiagonalMoves() {
        Set<Move> possibleMoves = new HashSet<>();
        for (int x = position.x + 1, y = position.y + 1; isValidMove(x, y); x++, y++) {
            Position pos = new Position(x, y);
            possibleMoves.add(new Move(this, pos));
            if (board.isSpaceOccupied(pos)) {
                break;
            }
        }

        for (int x = position.x + 1, y = position.y - 1; isValidMove(x, y); x++, y--) {
            Position pos = new Position(x, y);
            possibleMoves.add(new Move(this, pos));
            if (board.isSpaceOccupied(pos)) {
                break;
            }
        }

        for (int x = position.x - 1, y = position.y + 1; isValidMove(x, y); x--, y++) {
            Position pos = new Position(x, y);
            possibleMoves.add(new Move(this, pos));
            if (board.isSpaceOccupied(pos)) {
                break;
            }
        }

        for (int x = position.x - 1, y = position.y - 1; isValidMove(x, y); x--, y--) {
            Position pos = new Position(x, y);
            possibleMoves.add(new Move(this, pos));
            if (board.isSpaceOccupied(pos)) {
                break;
            }
        }
        return possibleMoves;
    }

    public void makeMove(Position pos) {
        if (!board.isSpaceOccupied(pos) || isEnemyPiece(board.getPieceAtPosition(pos))) {
            board.setPieceAtPosition(pos, this);
            return;
        }
        ChessPiece rook = board.getPieceAtPosition(pos);
        if (!(rook instanceof Rook)) {
            return;
        }
        King king = board.getPlayerKing(color);
        board.setPieceAtPosition(pos, rook);
        board.setPieceAtPosition(pos, king);
    }

    public void movePiece(Position pos) {
        board.setPieceAtPosition(pos, this);
        position = pos;
        setHasMoved();

    }

    public Set<Move> removeMovesIntoCheck(Set<Move> moves) {
        return moves.stream().filter(move -> {
            return move.isLegal(move.simulateMove());
        }).collect(Collectors.toSet());
    }

    public PieceColor getColor() {
        return this.color;
    }

    public PieceColor getEnemyColor() {
        if (color.equals(PieceColor.WHITE)) {
            return PieceColor.BLACK;
        }
        return PieceColor.WHITE;
    }

    public boolean isEnemyPiece(ChessPiece otherPiece) {
        if (otherPiece.getColor() == getColor()) {
            return false;
        }
        return true;
    }

    public boolean canCastle() {
        if (!(this instanceof Rook)) {
            return false;
        }
        Set<Rook> castleRooks = board.getCastleRooks(color);
        for (Rook rook : castleRooks) {
            if (this.equals(rook)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return getName() + ", " + getColor() + " [" + position.y + ", " + position.x + "]";
    }

    public ChessBoard getBoard() {
        return this.board;
    }

    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }

    /* @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChessPiece)) {
            return false;
        }
        ChessPiece p = (ChessPiece) o;
        return color.equals(p.color) && getName().equals(p.getName()) && position.equals(p.position);
    } */

}
