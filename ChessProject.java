/*
* Robert Roe 17138752
* Introduction to Artificial Intelligence (BSHCSD4)
* Software Development Stream
* x17138752@student.ncirl.ie
*
*
*/

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;

public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {

    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;

    private int xAdjustment;
    private int yAdjustment;
    private int startX;
    private int startY;
    private int initialX;
    private int initialY;

    private int landingX;
    private int landingY;
    private int xMovement;
    private int yMovement;

    private boolean validMove;
    private boolean success;

    private String pieceName;

    MouseEvent currentEvent;

    JPanel panels;
    JLabel pieces;

    Boolean whiteMove;


    public ChessProject() {
    	//sets dimension of chess board
        Dimension boardSize = new Dimension(600, 600);
        whiteMove = true;
        this.setTitle("Whites Turn");
        //  Use a Layered Pane for this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            chessBoard.add(square);

            int row = (i / 8) % 2;
            if (row == 0){
                square.setBackground(i % 2 == 0 ? Color.white : Color.gray);
        	}//end if
            else{
                square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
            }//end else
        }//end for

        //Setting up the Initial Chess board.
        //Loop to get all pawns
        for (int i = 8; i < 16; i++) {
            pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("WhitePawn.png")));
            panels = (JPanel) chessBoard.getComponent(i);
            panels.add(pieces);
        }//end for
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("WhiteRook.png")));
        panels = (JPanel) chessBoard.getComponent(0);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("WhiteKnight.png")));
        panels = (JPanel) chessBoard.getComponent(1);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("WhiteKnight.png")));
        panels = (JPanel) chessBoard.getComponent(6);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("WhiteBishop.png")));
        panels = (JPanel) chessBoard.getComponent(2);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource( "WhiteBishop.png")));
        panels = (JPanel) chessBoard.getComponent(5);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("WhiteKing.png")));
        panels = (JPanel) chessBoard.getComponent(3);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("WhiteQueen.png")));
        panels = (JPanel) chessBoard.getComponent(4);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("WhiteRook.png")));
        panels = (JPanel) chessBoard.getComponent(7);
        panels.add(pieces);
        //Loop to get all pawns
        for (int i = 48; i < 56; i++) {
            pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("BlackPawn.png")));
            panels = (JPanel) chessBoard.getComponent(i);
            panels.add(pieces);
        }//end for
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("BlackRook.png")));
        panels = (JPanel) chessBoard.getComponent(56);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("BlackKnight.png")));
        panels = (JPanel) chessBoard.getComponent(57);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("BlackKnight.png")));
        panels = (JPanel) chessBoard.getComponent(62);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("BlackBishop.png")));
        panels = (JPanel) chessBoard.getComponent(58);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("BlackBishop.png")));
        panels = (JPanel) chessBoard.getComponent(61);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("BlackKing.png")));
        panels = (JPanel) chessBoard.getComponent(59);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("BlackQueen.png")));
        panels = (JPanel) chessBoard.getComponent(60);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("BlackRook.png")));
        panels = (JPanel) chessBoard.getComponent(63);
        panels.add(pieces);
    }//end ChessProjct

    //This method checks if there is a piece present on a particular square.
    private Boolean piecePresent(int x, int y) {
        Component c = chessBoard.findComponentAt(x, y);
        if (c instanceof JPanel) {
            return false;
        }//end if
        else {
            return true;
        }//end else
    }//end PiecePresent

    private Boolean piecePresent() {
        return piecePresent(currentEvent.getX(), currentEvent.getY());
    }//end piecePresent

    //Checks if black piece is the opponent
    private Boolean checkWhiteOpponent(int newX, int newY) {
        return checkOpponent("Black", newX, newY);
    }//end checkWhiteOpponent
	//Checks if white piece is the opponent
    private Boolean checkBlackOpponent(int newX, int newY) {
        return checkOpponent("White", newX, newY);
    }//end checkBlackOpponent

	//method to check opponents colour
    private boolean checkOpponent(String colour, int newX, int newY) {
        Boolean opponent;
        Component c1 = chessBoard.findComponentAt(newX, newY);
        JLabel awaitingPiece = (JLabel) c1;
        String tmp1 = awaitingPiece.getIcon().toString();
        if (((tmp1.contains(colour)))) {
            opponent = true;
        }//end if
        else {
            opponent = false;
        }//end else
        return opponent;
    }//end checkOpponent

    //Press Mouse method
    public void mousePressed(MouseEvent e) {
        chessPiece = null;
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
        if (c instanceof JPanel){
            return;
        }//end if
        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel) c;

        String tmp = chessPiece.getIcon().toString();
        pieceName = new File(tmp).getName();
        pieceName = pieceName.substring(0, (pieceName.length() - 4));

        //Turn System to make sure white goes first. Checks if the Pieces name matches with who's turn it is.
        if (whiteMove) {
            if (!pieceName.contains("White")) {
                JOptionPane.showMessageDialog(null, "White's Turn. Select a White Piece");
                return;
            }//end if
        }//end if
        else{
            if (!pieceName.contains("Black")) {
                JOptionPane.showMessageDialog(null, "Black's Turn. Select a Black Piece");
                return;
            }//end if
        }//end else

        initialX = e.getX();
        initialY = e.getY();
        startX = (e.getX() / 75);
        startY = (e.getY() / 75);
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
    }//end mousePressed

    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null) return;
        chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }//end mouseDragged

    //Mouse Released
    public void mouseReleased(MouseEvent e) {
        currentEvent = e;
        if (chessPiece == null){
        	return;
    	}//end if
        whiteMove = !whiteMove;

        chessPiece.setVisible(false);
        success = false;
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
        String tmp = chessPiece.getIcon().toString();
        pieceName = new File(tmp).getName();
        pieceName = pieceName.substring(0, (pieceName.length() - 4));
        validMove = false;

		//prints x and y position of each piece movement
        System.out.println("X and Y: (" + e.getX() + "," + e.getY() + ").");
        landingX = (e.getX()/75);
        landingY  = (e.getY()/75);
        xMovement = Math.abs((e.getX()/75)-startX);
        yMovement = Math.abs((e.getY()/75)-startY);

        String title = whiteMove ? "Whites Turn" : "Blacks Turn";
        this.setTitle(title);


		//Switch to call methods of each method for each piece's movement also checks if the move was valid
        switch (pieceName) {
            case "WhitePawn":
            case "BlackPawn":
                movePawn();
                break;
            case "WhiteKnight":
            case "BlackKnight":
                moveKnight();
                break;
            case "WhiteBishop":
            case "BlackBishop":
                moveBishop();
                break;
            case "BlackRook":
            case "WhiteRook":
                moveRook();
                break;
            case "BlackQueen":
            case "WhiteQueen":
                moveQueen();
                break;
            case "BlackKing":
            case "WhiteKing":
                moveKing();
                break;
            default:
                validMove = false;
                break;
        }//end switch


		//If checks puts piece back where it was if the move made is not valid.
        if (!validMove) {
            PieceBack();
        }//end if
        else {
            if (success) {
                String colour = pieceName.contains("White") ? "White" : "Black";
                int location = (colour.equals("White")) ? 56 + (e.getX() / 75) : (e.getX() / 75);;
                //Options for user to pick
                Object [] options = {"Pawn", "Rook", "Knight", "Bishop","Queen"};

                JOptionPane pane = new JOptionPane("Select a piece to transform into", JOptionPane.QUESTION_MESSAGE);
                pane.setWantsInput(true);
                pane.setSelectionValues(options);
                pane.setInitialSelectionValue(options[0]);
                JDialog dialog = pane.createDialog(layeredPane, "Success");
                dialog.setVisible(true);
                String selectedPiece = pane.getInputValue().toString();
                //if else's to ensure the transformed pawn image is chnged and how it moves is changed
                if (c instanceof JLabel) {
                    Container parent = c.getParent();
                    parent.remove(0);
                    pieces = new JLabel(new ImageIcon(ChessProject.class.getResource(colour + selectedPiece + ".png")));
                    parent = (JPanel) chessBoard.getComponent(location);
                    parent.add(pieces);
                    pieces.setVisible(true);
                    parent.validate();
                    parent.repaint();
                }//end if
                else {
                    Container parent = (Container) c;
                    pieces = new JLabel(new ImageIcon(ChessProject.class.getResource(colour + selectedPiece + ".png")));
                    parent = (JPanel) chessBoard.getComponent(location);
                    parent.add(pieces);
                    pieces.setVisible(true);
                    parent.validate();
                    parent.repaint();
                }//end else
            }//end if
            else {
                if (c instanceof JLabel) {
                    Container parent = c.getParent();
                    parent.remove(0);
                    parent.add(chessPiece);
                }//end if
                else {
                    Container parent = (Container) c;
                    parent.add(chessPiece);
                }//end else

                //makes piece visible again
                chessPiece.setVisible(true);
            }//end else
        }//end else
    }// end mouseReleased

    //Method for pawn movement
    private void movePawn() {
        if ((landingX < 0 || landingX > 7) || (landingY < 0 || landingY > 7)) {
            validMove = false;
            return;
        }//end if

        Boolean whitePawn = pieceName.contains("White");
        if (whitePawn) {
            if (landingY < startY) {
                validMove = false;
                return;
            }//end if
        }//end if
        else {
            if (landingY > startY) {
                validMove = false;
                return;
            }//end if
        }//end else
        Boolean startCondition = whitePawn ? startY == 1 : startY == 6;
        Boolean directionCondition = whitePawn ? startY < landingY : startY > landingY;

        //On the first move, pawn can move 2 spaces.
        //Pawn can only move forward (White = Y+, Black = Y-)
        if (startCondition) {
            if ((yMovement == 1 || yMovement == 2) && directionCondition && xMovement == 0) {
                if (yMovement == 2) {
                    if ((!piecePresent(currentEvent.getX(), (currentEvent.getY()))) &&
                       (!piecePresent(currentEvent.getX(), (currentEvent.getY() + 75)))) {
                        validMove = true;
                    }//end if
                    else {
                        validMove = false;
                    }//end else
                }//end if
                else {
                    if ((!piecePresent(currentEvent.getX(), (currentEvent.getY())))) {
                        validMove = true;
                    }//end uf
                    else {
                        validMove = false;
                    }//end else
                }//end else
            }//end if
            else if (xMovement == 1 && yMovement == 1) {
                //Diagonal, trying to take opponent. Check if opponent is there.
                if (piecePresent(currentEvent.getX(), currentEvent.getY()) && (xMovement == 1) && (yMovement == 1)) {
                    //If opponent is King, its over!
                    if (GameOver(currentEvent.getX(), currentEvent.getY())) {
                        String winMessage = whitePawn ? "White Win" : "Black Win";
                        JOptionPane.showMessageDialog(null, winMessage);
                        System.exit(1);
                    }//end if
                    Boolean opponentCondition = whitePawn ? checkWhiteOpponent(currentEvent.getX(), currentEvent.getY()): checkBlackOpponent(currentEvent.getX(), currentEvent.getY());
                    if (opponentCondition) {
                        validMove = true;
                    }//end if
                    else {
                        validMove = false;
                    }//end else
                }//end if
                else {
                    validMove = false;
                }//end else
            }//end else if
        }//end if
        else {
            Boolean p2StartCondition = whitePawn ? (startX - 1 >= 0) || (startX + 1 <= 7) : (startX <= 7) || (startX - 1 == 0);
            if (p2StartCondition) {
                //Enforce that  movement is diagonal, 1 square AND opponent piece is present
                if (piecePresent(currentEvent.getX(), currentEvent.getY()) && (xMovement == 1) && (yMovement == 1)) {
                    if (GameOver(currentEvent.getX(), currentEvent.getY())) {
                        String winMessage = whitePawn ? "White Win" : "Black Win";
                        JOptionPane.showMessageDialog(null, winMessage);
                        System.exit(1);
                    }//end if
                    Boolean opponentCondition = whitePawn ? checkWhiteOpponent(currentEvent.getX(), currentEvent.getY()): checkBlackOpponent(currentEvent.getX(), currentEvent.getY());
                    if (opponentCondition) {
                        validMove = true;
                    }//end if
                    else {
                        validMove = false;
                    }//end else
                }//end if
                 else {
                    //Normal move, no piece present, movement only 1 square in the Y direction.
                    if (!piecePresent(currentEvent.getX(), (currentEvent.getY()))) {
                        if (xMovement == 0 && yMovement == 1) {
                            Boolean successStartCondition = whitePawn == true ? startY == 6 : startY == 1;
                            if (successStartCondition) {
                                success = true;
                            }//end if
                            validMove = true;
                        }//end if
                        else {
                            validMove = false;
                        }//end else
                    }//end if
                    else {
                        validMove = false;
                    }//end else
                }//end else
            }//end if
            else {
                validMove = false;
            }//end else
        }//end else
    }//end move pawn

     //Knight
    private void moveKnight() {
        if ((landingX < 0 || landingX > 7) || (landingY < 0 || landingY > 7)) {
            validMove = false;
            return;
        }//end if

        //Knight moves in an L shape
        if ((xMovement == 1 && yMovement == 2) || (xMovement == 2 && yMovement == 1)) {
            EndMove();
        }//end if
        else {
            validMove = false;
        }//end else
    }//end moveKnight

    //Bishop
    private void moveBishop() {
        Boolean inTheWay = false;
        int distance = Math.abs(startX-landingX);
        if ((landingX < 0 || landingX > 7) || (landingY < 0 || landingY > 7)) {
            validMove = false;
            return;
        }//end if
        else {
            //Bishop can move diagonally, with no piece in the way
            validMove = true;
            if (xMovement == yMovement) {
                inTheWay = inTheWayDiagonal();
                if (inTheWay) {
                    validMove = false;
                }//end if
                else {
                    EndMove();
                }//end else
            }//end if
            else {
                validMove = false;
            }//end else
        }//end else
    }//end moveBishop

    //Rook
    private void moveRook() {
        if ((landingX < 0 || landingX > 7) || (landingY < 0 || landingY > 7)) {
            validMove = false;
            return;
        }//end if
        //Rook can move vertical or horizontal, with no piece in the way
        if ((xMovement !=0 && yMovement == 0) || (yMovement != 0 && xMovement == 0)) {
            Boolean inTheWay = inTheWayLateral();
            if (inTheWay) {
                validMove = false;
                return;
            }//end if
            else {
                EndMove();
            }//end else
        }//end if
        else {
            validMove = false;
        }//end else
    }//end moveRook
    //Queen
    private void moveQueen() {
        if ((landingX < 0 || landingX > 7) || (landingY < 0 || landingY > 7)) {
            validMove = false;
            return;
        }//end if
        Boolean inTheWay = false;
        //Queen can move vertical, horizontal and diagonal, with no piece in the way
        if ((xMovement != 0 && yMovement == 0) || (yMovement != 0 && xMovement == 0)) {
            //Lateral
            inTheWay = inTheWayLateral();
        }//end if
        else if (xMovement == yMovement) {
            //Diagonal
            inTheWay = inTheWayDiagonal();
        }//end else if
        else {
            validMove = false;
            return;
        }//end else
        if (inTheWay) {
            validMove = false;
            return;
        }//end if
        else {
            EndMove();
        }//end else
    }//end moveQueen

    //King
    private void moveKing() {
        validMove = true;
        //If either absX or absY movement is larger than 1, we know King has overstepped
        if (xMovement > 1 || yMovement > 1) {
            validMove = false;
            return;
        }
        //If's to make sure the king is not moving twoard the other king
        if ((KingLoc(currentEvent.getX() - 75, currentEvent.getY() + 75))
                || (KingLoc(currentEvent.getX() - 75, currentEvent.getY()))
                || (KingLoc(currentEvent.getX() - 75, currentEvent.getY() - 75))
                || (KingLoc(currentEvent.getX(), currentEvent.getY() - 75))
                || (KingLoc(currentEvent.getX() + 75, currentEvent.getY() - 75))
                || (KingLoc(currentEvent.getX() + 75, currentEvent.getY()))
                || (KingLoc(currentEvent.getX() + 75, currentEvent.getY() + 75))
                || (KingLoc(currentEvent.getX(), currentEvent.getY() + 75))) {
            validMove = false;
            return;
        }//end if
        EndMove();
    }//end moveKing

    //Determine if King lies at location
    private Boolean KingLoc(int x, int y) {
        try {
            Component c1 = chessBoard.findComponentAt(x, y);
            if (c1 instanceof JPanel) {
                return false;
            }//end if
            JLabel checkingPiece = (JLabel) c1;
            String tmp1 = checkingPiece.getIcon().toString();
            return tmp1.contains("King");
        }//end try
        catch (Exception e) {
            return false;
        }//end catch
    }//end

    //Complete a move for all pieces except a Pawn
    private void EndMove() {
        if (piecePresent(currentEvent.getX(), currentEvent.getY())) {
            if (pieceName.contains("White")) {
                if (checkWhiteOpponent(currentEvent.getX(), currentEvent.getY())) {
                    if (GameOver(currentEvent.getX(), currentEvent.getY())) {
                        JOptionPane.showMessageDialog(null, "White Win");
                        //end
                        System.exit(0);
                    }//end if
                    validMove = true;
                }//end if
                else {
                    validMove = false;
                }//end else
            }//end if
            else if (pieceName.contains("Black")) {
                if (checkBlackOpponent(currentEvent.getX(), currentEvent.getY())) {
                    if (GameOver(currentEvent.getX(), currentEvent.getY())) {
                        JOptionPane.showMessageDialog(null, "Black Win");
                        //end game
                        System.exit(0);
                    }//end if
                    validMove = true;
                }//end if
                else {
                    validMove = false;
                }//end else
            }//end else if
        }//end if
        else {
            validMove = true;
        }//end else
    }//end EndMove

    //Check if there is a piece in the way in any diagonal path
    private boolean inTheWayDiagonal() {
        Boolean inTheWay = false;
        if ((startX-landingX < 0) && (startY-landingY) < 0) {
            for (int i = 0; i < xMovement; i++) {
                if (piecePresent((initialX+(i*75)), (initialY+(i*75)))) {
                    inTheWay = true;
                }//end if
            }//end for
        }//end if
        else if ((startX-landingX) < 0 && (startY-landingY) > 0) {
            for (int i = 0; i < xMovement; i++) {
                if (piecePresent((initialX+(i*75)), (initialY-(i*75)))) {
                    inTheWay = true;
                }//end if
            }//end for
        }//end else if
        else if ((startX-landingX) > 0 && (startY - landingY) > 0) {
            for (int i = 0; i < xMovement; i++) {
                if (piecePresent((initialX-(i*75)), (initialY-(i*75)))) {
                    inTheWay = true;
                }//end if
            }//end for
        }//end else if
        else if ((startX-landingX) > 0 && (startY - landingY) < 0) {
            for (int i = 0; i < xMovement; i++) {
                if (piecePresent((initialX-(i*75)), (initialY+(i*75)))) {
                    inTheWay = true;
                }//end if
            }//end for
        }//end else if
        return inTheWay;
    }//end inTheWayDiagonal

    //Check if a piece is in the way in any lateral path
    private boolean inTheWayLateral() {
        Boolean inTheWay = false;
        if (xMovement != 0) {
            if (startX - landingX > 0) {
                //Moving left on X-Axis
                for (int i = 0 ; i < xMovement ; i++) {
                    if (piecePresent(initialX-(i*75), currentEvent.getY())) {
                        return true;
                    }//end for
                    else {
                        inTheWay = false;
                    }//end else
                }//end for
            }//end if
            else if (startX - landingX < 0) {
                //Moving right on X-Axis
                for (int i = 0 ; i < xMovement ; i++) {
                    if (piecePresent(initialX+(i*75), currentEvent.getY())) {
                        return true;
                    }
                    else {
                        inTheWay = false;
                    }//end else
                }//end for
            }//end else if
        }//end if
        else if (yMovement != 0) {
            if (startY - landingY > 0) {
                //Moving Up on Y-Axis
                for (int i = 0 ; i < yMovement ; i++) {
                    if (piecePresent(currentEvent.getX(), initialY-(i*75))) {
                        return true;
                    }//end if
                    else {
                        inTheWay = false;
                    }//end else
                }//end for
            }//end if
            else if (startY - landingY < 0) {
                //Moving down on Y-Axis
                for (int i = 0 ; i < yMovement ; i++) {
                    if (piecePresent(currentEvent.getX(), initialY+(i*75))) {
                        return true;
                    }//end if
                    else {
                        inTheWay = false;
                    }//end else
                }//end for
            }//end else if
        }//end else if
        return inTheWay;
    }//end inTheWayLateral

    //Check if the game is over
    private Boolean GameOver(int newX, int newY) {
        Boolean kingTaken = false;
        Component c1 = chessBoard.findComponentAt(newX, newY);
        JLabel takenPiece = (JLabel) c1;
        String tmp1 = takenPiece.getIcon().toString();
        if (((tmp1.contains("King")))) {
            kingTaken = true;
        }//end if
        else {
            kingTaken = false;
        }//end else
        return kingTaken;
    }//end GameOver

    //Method to put piece back if user made an invalid move
    private void PieceBack() {
        int location = 0;
        if (startY == 0) {
            location = startX;
        }//end if
        else {
            location = (startY * 8) + startX;
        }//end else
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource(pieceName + ".png")));
        panels = (JPanel) chessBoard.getComponent(location);
        panels.add(pieces);
        panels.validate();
        panels.repaint();
    }//end PieceBack

    public void mouseClicked(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new ChessProject();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }//end main
}//end class
