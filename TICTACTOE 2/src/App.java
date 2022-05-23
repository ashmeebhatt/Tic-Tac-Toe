import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        GameView view = new GameView();
        GameController controller = new GameController();
        view.setController(controller);
        controller.setView(view);

        // Create Models (players)
        GameModel playerO = new GameModel(0, 'O');
        GameModel playerX = new GameModel(1, 'X');
        controller.addPlayer(playerO);
        controller.addPlayer(playerX);
    }
}
