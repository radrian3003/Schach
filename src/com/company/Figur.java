package com.company;

public class Figur {
    // Figur kann an einem int erkannt werden denn: Farbe * FigurTyp
    static final int schwarz = -1;
    static final int weis = 1;


    static final int pawn = 1;
    static final int knight = 2;
    static final int bishop = 3;
    static final int rook = 4;
    static final int queen = 5;
    static final int king = 6;



    /**
     *
     * @param startPosition startPosition auf dem brett
     * @param figurInt den IntegerWert einer Figur auf dem Brett
     * @return die Moves die gehen würden, wenn keine anderen Figuren auf dem Feld wären
     */
    public static Move[] generateMoves(int startPosition, int figurInt)
    {
        int absolutFigurInt = Math.abs(figurInt);
        if(absolutFigurInt == queen || absolutFigurInt == rook || absolutFigurInt == bishop)
        {

        }
        return null;
    }



    private static Move[] generateSlidingPieceMoves(int startPos, int figurInt)
    {
        return null;
    }



}
