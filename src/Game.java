import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Game{
    private JFrame frame;
    private JPanel panel;
    private boolean isAlive = false;
    private int x = 300;
    private int y = 300;
    private final int UNIT_SIZE = 25;
    private boolean left = false;
    private boolean right = false;
    private boolean up = true;
    private boolean down = false;

    public Game(){
        setUpGui();
        startGame();
    }

    private void setUpGui(){
        frame = new JFrame("Snake Game");
        panel = new MyPanel();


        frame.addKeyListener(new GameKeyListener());
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void startGame(){
        isAlive = true;
        try{
            while(isAlive){
                panel.repaint();
                Thread.sleep(6);
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void refreshMap(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
        g.setColor(Color.GREEN);
    }

    private boolean checkApple(int snakeX, int snakeY, int appleX, int appleY){
        for(int i = 0; i<=25; i++){
            if(snakeX == (appleX+UNIT_SIZE) && (snakeY == (appleY + i) || snakeY == (appleY - i))) return true;
            if((snakeX + UNIT_SIZE) == appleX && (snakeY == (appleY + i) || snakeY == (appleY - i))) return true;
            if(snakeY == (appleY+UNIT_SIZE) && (snakeX == (appleX + i) || snakeX == (appleX - i))) return true;
            if((snakeY + UNIT_SIZE) == appleY && (snakeX == (appleX + i) || snakeX == (appleX - i))) return true;
        }
        return false;
    }

    private class MyPanel extends JPanel{
        private int appleX = 50;
        private int appleY = 50;
        private Random random = new Random();

        @Override
        public void paintComponent(Graphics g){
            if(left){
                refreshMap(g);
                g.fillOval(--x, y, UNIT_SIZE, UNIT_SIZE);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            }
            else if(right){
                refreshMap(g);
                g.fillOval(++x, y, UNIT_SIZE, UNIT_SIZE);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            }
            else if(down){
                refreshMap(g);
                g.fillOval(x, ++y, UNIT_SIZE, UNIT_SIZE);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            }
            else if(up){
                refreshMap(g);
                g.fillOval(x, --y, UNIT_SIZE, UNIT_SIZE);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            }

            if(checkApple(x, y, appleX, appleY)){
                appleX = random.nextInt(this.getWidth());
                appleY = random.nextInt(this.getHeight());
            }
        }
    }

    private class GameKeyListener implements KeyListener{
        private boolean notStarted = true;
        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println("dziala");
            switch(e.getKeyChar()){
                case 'w': up = true; down = false; right = false; left = false; break;
                case 'a': up = false; down = false; right = false; left = true; break;
                case 's': up = false; down = true; right = false; left = false; break;
                case 'd': up = false; down = false; right = true; left = false;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
