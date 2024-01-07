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
        else if(absolutFigurInt == Figur.knight)
        {
            return generateKnightMoves(startPosition,figurInt);
        }
        return null;
    }

    /**
     * generiert die möglichen Moves für Laeufer, Turm, Dame wenn
     * @param startPos aktuelle Position der Figur und
     * @param FigurInt Farbe + Art der Figur bekannt sind
     * @return Array an allen möglichen Moves
     */
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


            for(int j = 1;j <= distancesToEdge[startPos][anfang];j++) // j=1 damit das Startfeld nicht mitreingenommen wird
            {
                int square = startPos + j * directions[anfang];//aktuellesFeld = Startfeld + Die Anzahl von Schritten in eine Richtung
                if(Square[square] == leeresFeld)
                {
                    moves.add(new Move(startPos,square));

                }
                else
                {
                    int eigeneFarbe = FigurInt / Math.abs(FigurInt);
                    int farbeAndereFigur = Square[square] / Math.abs(Square[square]);
                    if(eigeneFarbe == farbeAndereFigur) // wenn eigene Farbe auf Feld
                    {
                        break;
                    }
                    else // wenn Gegner auf Feld
                    {
                        moves.add(new Move(startPos,square));

                        break;
                    }
                }

            }
        }
        return moves.toArray(new Move[0]);
    }

    public Move[] generateKnightMoves(int startPos, int FigurInt)
    {
        int eigeneFarbe = FigurInt / Math.abs(FigurInt);
        ArrayList<Move> moves = new ArrayList<>();

        int[] knightDirections = new int[]{-16 + 1, -16 - 1, // 3 nach oben, 1 nach links/rechts
                                            2 - 8, 2 + 8,    // 3 nach rechts, 1 nach oben/unten
                                            16 + 1, 16 - 1,  // 3 nach unten, 1 nach links/rechts
                                            -2 - 8, -2 + 8   // 3 nach links, 1 nach oben/unten
        };
        int[][] necessaryDistancestoEdge =  new int[][]{{2,0,1,0}, {2,0,0,1}, // kann man bestimmt auch berechnen, hab aber keine Lust das zu machen und so geht das aufjedenfall schneller
                                                        {1,0,2,0}, {0,1,2,0},
                                                        {0,2,1,0}, {0,2,0,1},
                                                        {1,0,0,2}, {0,1,0,2}};

        outerloop: for (int i = 0; i < knightDirections.length ; i++) {
            for (int j = 0; j < 4; j++) {
                if(necessaryDistancestoEdge[i][j] > distancesToEdge[startPos][j]) // wenn in irgendeine Richtung nicht genuegend Platz ist
                {
                    continue outerloop; // wird der Move geskippt
                }}

                int neuersquare = startPos + knightDirections[i];

                // wenn das neue Feld leer ist oder ein Feind drauf ist, ist der Move okay,
                // wenn eine eigene Figur auf dem Feld ist, geht der Move nicht und muss dementsprechend auch nicht hinzugefuegt werden
                if(Square[neuersquare] == leeresFeld || eigeneFarbe != Square[neuersquare] / Math.abs(Square[neuersquare]))
                {
                    moves.add(new Move(startPos,neuersquare));
                    System.out.println(neuersquare);

                }



        }
        return moves.toArray(new Move[0]);
    }
}
