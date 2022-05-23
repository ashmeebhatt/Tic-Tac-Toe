public class GameModel {
    private int playerId;
    private int moves;
    private char token;

    public GameModel(int id, char symbol)
    {
        this.playerId = id;
        this.moves = 0;
        this.token = symbol;
    }

    public int getId() {return this.playerId;}

    public char getToken()
    {
        if(this.playerId == 0)
            return 'O';
        else if(this.playerId == 1)
            return 'X';
        else
            return ' ';
    }

    public void setMoves(int newmoves) {this.moves = newmoves;}
    public void move() {this.moves++;}
}
