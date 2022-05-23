
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameView {

    private JFrame gui;
    private JTextArea log;
    private JButton reset;
    private JButton[][] cells;
    private JTextArea winlose_message;

    private GameController controller;

    public GameView()
    {
        this.gui = new JFrame("Tic Tac Toe");
        this.log = new JTextArea();
        this.reset = new JButton("Reset");
        this.cells = new JButton[3][3];
        initializeWindow();
    }

    public void setController(GameController controller) {this.controller = controller;}
    public void initializeWindow()
    {
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    gui.setSize(new Dimension(500, 350));
	    gui.setResizable(true);
        
        this.log.setBounds(0, 0, 50, 50);

        this.reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                clearGrid();
                enableGrid();
                controller.resetGame();
             }
        });


	    JPanel gamePanel = new JPanel(new FlowLayout());
	    JPanel game = new JPanel(new GridLayout(3,3));
	    gamePanel.add(game, BorderLayout.CENTER);
	    JPanel options = new JPanel(new FlowLayout());
	    options.add(reset);
	    gui.add(gamePanel, BorderLayout.NORTH);
	    gui.add(options, BorderLayout.CENTER);
        gui.add(this.log, BorderLayout.SOUTH);
        
        for(int x = 0; x<3 ;x++) {
	        for(int y = 0; y<3 ;y++) {
	            cells[x][y] = new JButton();
	            cells[x][y].setPreferredSize(new Dimension(75,75));
                cells[x][y].setText("");
                

                cells[x][y].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e)
                    {
                        int[] pos = getButtonPosition(e);
                        if(pos != null)
                        {
                            if(controller.canMove(pos[0], pos[1]))
                                controller.executeMove(pos[0], pos[1]);
                        }
                        else
                            log.setText("Button not found!");
                     }
                });
	            game.add(cells[x][y]);
	            
		    }
        }
        
        gui.setVisible(true);
    }

    public void disableGrid()
    {
        for(int x = 0; x<3 ;x++) {
	        for(int y = 0; y<3 ;y++) {
                cells[x][y].setEnabled(false);
            }
        }
    }

    public void enableGrid()
    {
        for(int x = 0; x<3 ;x++) {
	        for(int y = 0; y<3 ;y++) {
                cells[x][y].setEnabled(true);
            }
        }
    }

    public void displayFrontMessage(String message)
    {
        this.log.setText(message);
    }

    public int[] getButtonPosition(ActionEvent e)
    {
        for(int x = 0; x<3 ;x++)
        {
            for(int y = 0; y<3 ;y++)
            {
                if(this.cells[x][y] == e.getSource())
                    return new int[] {x,y};
            }
        }

        return null;
    }

    public void setToken(int x, int y, char token)
    {
        this.cells[x][y].setText(Character.toString(token));
        this.cells[x][y].setEnabled(false);
        displayTurnMessage(this.controller.getTurn());
    }

    public void displayTurnMessage(int turn)
    {
        if(turn == 0) // 'o'
        {
            
            this.log.setText("It's O turn");
        }
        else
        {
            this.log.setText("It's X turn");
        }
    }

    public void clearGrid()
    {
        for(int x = 0; x<3 ;x++) {
	        for(int y = 0; y<3 ;y++) {
	            cells[x][y].setText("");
		    }
        }
    }
}
