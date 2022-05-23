import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {

    private GameView view;
    private int turn;
    boolean isWinner;
    GameModel winner;
    private char grid[][];

    private List<GameModel> players;

    static char PLAYER_O = 'O';
    static char PLAYER_X = 'X';
    static char PLAYER_NONE = ' ';
    
    public GameController()
    {
        Random r = new Random();
        this.turn = r.nextInt(2); // randomly choose who starts

        this.isWinner = false;
        this.winner = null;
        this.grid = new char[3][3];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                this.grid[i][j] = PLAYER_NONE;

        this.players = new ArrayList<GameModel>();
    }

    public void resetGame()
    {
        Random r = new Random();
        this.turn = r.nextInt(2); // randomly choose who starts

        this.isWinner = false;
        this.winner = null;
        this.grid = new char[3][3];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                this.grid[i][j] = PLAYER_NONE;
    }

    public void setView(GameView view) {this.view = view; this.view.displayTurnMessage(this.turn);}
    public GameModel getPlayer(int id) {return this.players.get(turn);}
    public GameModel getPlayer(char token)
    {
        for(GameModel p: this.players)
        {
            if(p.getToken() == token)
                return p;
        }

        return null;
    }
    public void addPlayer(GameModel player)
    {
        this.players.add(player);
    }

    public boolean canMove(int x, int y)
    {
        return grid[x][y] == PLAYER_NONE && !isWinner;
    }

    public void executeMove(int x, int y)
    {
        GameModel player = getPlayer(this.turn);
        player.move();
        grid[x][y] = player.getToken();

        // update turn id
        switchTurns();

        // Update GUI
        this.view.setToken(x, y, player.getToken());

        // check winner
        checkWinner(player.getToken());

        if(isWinner || getFreeMovements() == 0)
        {
            this.view.disableGrid();
            if(isWinner)
            {
                this.view.displayFrontMessage(Character.toString(winner.getToken()) + " wins!");
            }
            else
                this.view.displayFrontMessage("It is a tie!");
        }
    }

    public void switchTurns() 
    {
        if(this.turn == 0)
            this.turn = 1;
        else
            this.turn = 0;
    }

    public void checkWinner(char token)
    {
        // Check for winner in horizontal lines
        // Check for 'X' winner
        int count;
        for(int x = 0; x < 3; x++)
        {
            count = 0;
            for(int y = 0; y < 3; y++)
            {
                if(grid[x][y] == token)
                {
                    count++;
                    if(count == 3) // X winner
                    {
                        winner = getPlayer(token);
                        isWinner = true;
                        return;
                    }
                }
                else
                    break;
            }
        }

        // Now check vertical lines
        for(int y = 0; y < 3; y++)
        {
            count = 0;
            for(int x = 0; x < 3; x++)
            {
                if(grid[x][y] == token)
                {
                    count++;
                    if(count == 3) // X winner
                    {
                        winner = getPlayer(token);
                        isWinner = true;
                        return;
                    }
                }
                else
                    break;
            }
        }

        // Now check diagonal
        // First check \
        count = 0;
        for(int c = 0; c < 3; c++)
        {
            if(grid[c][c] == token)
            {
                count++;
                if(count == 3) // X winner
                {
                    winner = getPlayer(token);
                    isWinner = true;
                    return;
                }
            }
            else
                break;
        }

        // Now check /
        count = 0;
        int y = 2;
        for(int x = 0; x < 3; x++)
        {
            if(grid[x][y] == token)
            {
                count++;
                if(count == 3) // X winner
                {
                    winner = getPlayer(token);
                    isWinner = true;
                    return;
                }
            }
            else
                break;

            y--;
        }
    }

    public int getTurn() {return this.turn;}

    public int getFreeMovements()
    {
        int count = 0;
        for(int x = 0; x < 3; x++)
        {
            for(int y = 0; y < 3; y++)
            {
                if(grid[x][y] == PLAYER_NONE)
                {
                    count++;
                }
            }
        }
        return count;
    }

}
