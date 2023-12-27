package com.company;

//test kommentar
public class Board {
    private int[] Square;
    private static final int leeresFeld = 0;
     static int[][] distancesToEdge;
    static final int[] richtungen = {8, -8, // unten,oben
            1, -1, // rechts, links
            7, -7,//unten links, oben rechts
            9, -9}; //unten rechts, oben links

    public Board()
    {
        Square = new int[64]; //brett als eindimensionales array (von oben links nach unten rechts) ist später praktisch
        Square[0] = Figur.schwarz * Figur.king;
        Square[63] = Figur.weis * Figur.king;

        distancesToEdge = initialiseDistanceToEdges();
    }

    /**
     *
     * @param figurInt den wert einer Figur aus Farbe * FigurTyp
     * @return den Anfangsbuchstaben des Namen der Figur (klein wenn Figur == schwarz, gross wenn weiß)
     */
    private char figurIntToChar(int figurInt)
    {
        int absolutFigurInt = Math.abs(figurInt);
        int farbe = figurInt / absolutFigurInt;

        return switch (absolutFigurInt) {
            case (Figur.king) -> farbe == Figur.schwarz ? 'k' : 'K';
            case (Figur.pawn) -> farbe == Figur.schwarz ? 'p' : 'P';
            case (Figur.knight) -> farbe == Figur.schwarz ? 'n' : 'N';
            case (Figur.bishop) -> farbe == Figur.schwarz ? 'b' : 'B';
            case (Figur.rook) -> farbe == Figur.schwarz ? 'r' : 'R';
            case (Figur.queen) -> farbe == Figur.schwarz ? 'q' : 'Q';
            default -> 0;
        };
    }

    /**
     * brett auf konsole
     */
    public void gebeBrettAus()
    {
        String zeileString = "";
        for(int i = 0; i< Square.length; i++)
        {

                if(Square[i] == Board.leeresFeld)
                {
                    zeileString += " ";
                }
                else
                {
                    zeileString += figurIntToChar(Square[i]);
                }
                zeileString += " | ";
            if(( (i + 1) % 8 == 0 && i != 0 ) || i == 63) { //Zeilenumbruch wenn i durch 8 teilbar ist -> alle 8 Felder
                System.out.println(zeileString);
                System.out.println("------------------------------");
                zeileString = "";
            }
        }
    }

    /**
     * gibt den Abstand zum Rand von jedem Feld zum Rand in jeder Richtung in einem Array an,
     * diese Information kann so vorgespeichert werden und muss nicht für jede Figur einzeln berechnet werden
     *
     * @return Array mit Abstand zum Rand von jedem Feld zum Rand in jeder Richtung in einem Array
     */
    private int[][] initialiseDistanceToEdges()
    {
        int[][] rdistancesToEdge = new int[64][8];
        for(int zeile = 0;zeile<8;zeile++)
        {
            for (int spalte = 0; spalte < 8; spalte++) {

                int squareIndex = zeile * 8 + spalte;
                int oben, unten, rechts, links, untenlinks, obenrechts, untenrechts, obenlinks;

                oben = zeile;
                unten = 7 - zeile;
                rechts = spalte;
                links = 7 - spalte;

                untenlinks = Math.min(unten, links);
                obenrechts = Math.min(oben,rechts);
                untenrechts = Math.min(unten, rechts);
                obenlinks = Math.min(oben, links);

                int[] distances =  new int[] {oben, unten, rechts, links, untenlinks, obenrechts, untenrechts, obenlinks};
                rdistancesToEdge[squareIndex] = distances;
            }
        }
        return rdistancesToEdge;
    }

    /**
     *
     * @param startPosition startPosition auf dem brett
     * @param figurInt den IntegerWert einer Figur auf dem Brett
     * @return die Moves die gehen würden, wenn keine anderen Figuren auf dem Feld wären
     */
    public static Move[] generateMoves(int startPosition, int figurInt)
    {
        int absolutFigurInt = Math.abs(figurInt);
        if(absolutFigurInt == Figur.queen || absolutFigurInt == Figur.rook || absolutFigurInt == Figur.bishop)
        {
            return generateSlidingPieceMoves(startPosition,absolutFigurInt);
        }
        return null;
    }



    private static Move[] generateSlidingPieceMoves(int startPos, int absolutFigurInt)
    {
        return null;
    }
}
