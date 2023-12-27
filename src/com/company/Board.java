package com.company;


import java.util.ArrayList;


public class Board {
     int[] Square;
    private static final int leeresFeld = 0;
     static int[][] distancesToEdge;
    static final int[] directions = {-8, 8, // oben,unten
            1, -1, // rechts, links
            7, -7,//unten links, oben rechts
            9, -9}; //unten rechts, oben links

    public Board()
    {
        Square = new int[64]; //brett als eindimensionales array (von oben links nach unten rechts) ist später praktisch

        distancesToEdge = getDistanceToEdges();
    }

    /**
     *
     * @param figurInt den wert einer Figur aus Farbe * FigurTyp
     * @return den Anfangsbuchstaben des Namen der Figur (klein wenn Figur == schwarz, gross wenn weiß)
     */
    private  char figurIntToChar(int figurInt)
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
    public  void gebeBrettAus()
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
    private  int[][] getDistanceToEdges()
    {
        int[][] rdistancesToEdge = new int[64][8];
        for(int zeile = 0;zeile<8;zeile++)
        {
            for (int spalte = 0; spalte < 8; spalte++) {

                int squareIndex = zeile * 8 + spalte;
                int oben, unten, rechts, links, untenlinks, obenrechts, untenrechts, obenlinks;

                oben = zeile;
                unten = 7 - zeile;
                rechts = 7 - spalte;
                links = spalte;

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
    public  Move[] generateMoves(int startPosition, int figurInt)
    {
        int absolutFigurInt = Math.abs(figurInt);
        if(absolutFigurInt == Figur.queen || absolutFigurInt == Figur.rook || absolutFigurInt == Figur.bishop)
        {
            return generateSlidingPieceMoves(startPosition,figurInt);
        }
        return null;
    }



    public Move[] generateSlidingPieceMoves(int startPos, int FigurInt)
    {
        ArrayList<Move> moves = new ArrayList<>();
        int absolutFigurInt = Math.abs(FigurInt);


        int anfang = 0;
        int ende = 8;
        if(absolutFigurInt == Figur.rook) //nur oben,unten,rechts,links
        {
            ende = 4;
        }
        else if(absolutFigurInt == Figur.bishop) //nur schraeg
        {
            anfang = 4;
        }


        for(;anfang < ende;anfang++)
        {
            System.out.println("direction: "+directions[anfang]);
            System.out.println("distance: "+distancesToEdge[startPos][anfang]);

            for(int j = 1;j <= distancesToEdge[startPos][anfang];j++) // j=1 damit das Startfeld nicht mitreingenommen wird
            {
                int square = startPos + j * directions[anfang];//aktuellesFeld = Startfeld + Die Anzahl von Schritten in eine Richtung
                if(Square[square] == leeresFeld) //TODO oder wenn ein Feind auf dem Feld ist
                {
                    moves.add(new Move(startPos,square));
                    System.out.println(square);
                }
                else
                {
                    int eigeneFarbe = FigurInt / Math.abs(FigurInt);
                    int farbeAndereFigur = Square[square] / Math.abs(Square[square]);
                    if(eigeneFarbe == farbeAndereFigur)
                    {
                        break;
                    }
                    else
                    {
                        moves.add(new Move(startPos,square));
                        System.out.println(square);
                        break;
                    }
                }

            }
        }
        return moves.toArray(new Move[0]);
    }
}
