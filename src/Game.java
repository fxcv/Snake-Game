import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Game{
    private JFrame frame;
    private JPanel panel;
    private boolean isAlive;
    private int[] bodyPartsX;
    private int[] bodyPartsY;
    private final int UNIT_SIZE = 25;
    private boolean left = false;
    private boolean right = false;
    private boolean up = true;
    private boolean down = false;
    private int appleX;
    private int appleY;
    private int bodyParts;
    private int[] appleSpawningsX;
    private int[] appleSpawningsY;
    private final Random random = new Random();

    public Game(){
        setUpGui();
        while(true){
            startGame();
            endGame();
        }
    }

    private void setUpGui(){
        frame = new JFrame("Snake Game");
        panel = new MyPanel();


        frame.addKeyListener(new GameKeyListener());
        frame.setResizable(false);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void startGame(){
        setBodyParts();
        setAppleSpawnings();
        randomizeApple();
        isAlive = true;
        try{
            while(isAlive){
                panel.repaint();
                isGameOver();
                if(checkApple(bodyPartsX[0], bodyPartsY[0], appleX, appleY)){
                    randomizeApple();
                    addBodyParts();
                }
                Thread.sleep(200);
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void endGame(){

    }

    private void isGameOver(){
        if(bodyPartsX[0] >= panel.getWidth() || bodyPartsY[0] >= panel.getHeight() || bodyPartsX[0] < 0 || bodyPartsY[0] < 0){
            isAlive = false;
        }
        for(int i = 1; i<bodyParts; i++){
            if(bodyPartsX[0] == bodyPartsX[i] && bodyPartsY[0] == bodyPartsY[i]){
                isAlive = false;
                break;
            }
        }
    }

    private void setBodyParts(){
        bodyParts = 1;
        bodyPartsX = new int[600];
        bodyPartsY = new int[600];
        bodyPartsX[0] = 300;
        bodyPartsY[0] = 300;
    }

    private void setAppleSpawnings(){
        appleSpawningsX = new int[600];
        appleSpawningsY = new int[600];
        for(int i = 0, j = 0; j<panel.getWidth() / 25; i+=25, j++){
            appleSpawningsX[j] = i;
        }
        for(int i = 0, j = 0; j<panel.getHeight() / 25; i+=25, j++){
            appleSpawningsY[j] = i;
        }
    }

    private void randomizeApple(){
        appleX = appleSpawningsX[random.nextInt(panel.getWidth() / 25)];
        appleY = appleSpawningsY[random.nextInt(panel.getHeight() / 25)];
    }

    private void refreshMap(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
        g.setColor(Color.GREEN);
    }

    private void addBodyParts(){
        ++bodyParts;
        bodyPartsX[bodyParts-1] = bodyPartsX[bodyParts-2];
        bodyPartsY[bodyParts-1] = bodyPartsY[bodyParts-2] + UNIT_SIZE;
    }

    private boolean checkApple(int snakeX, int snakeY, int appleX, int appleY){
        if(snakeX==appleX && snakeY == appleY) return true;
        return false;
    }

    private class MyPanel extends JPanel{
        private int[] oldCordinatesX = new int[600];
        private int[] oldCordinatesY = new int[600];
        @Override
        public void paintComponent(Graphics g){
            for(int i = 0; i<bodyParts; i++){
                oldCordinatesX[i] = bodyPartsX[i];
                oldCordinatesY[i] = bodyPartsY[i];
            }
            refreshMap(g);
            if(left){
                bodyPartsX[0]-=25;
            }
            else if(right){
                bodyPartsX[0]+=25;
            }
            else if(down){
                bodyPartsY[0]+=25;
            }
            else if(up){
                bodyPartsY[0]-=25;
            }
            g.fillOval(bodyPartsX[0], bodyPartsY[0], UNIT_SIZE, UNIT_SIZE);

            for(int i = 1;i<bodyParts; i++){
                bodyPartsX[i] = oldCordinatesX[i-1];
                bodyPartsY[i] = oldCordinatesY[i-1];
                g.fillOval(bodyPartsX[i], bodyPartsY[i], UNIT_SIZE, UNIT_SIZE);
            }
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
        }
    }

    private class GameKeyListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
            switch(e.getKeyChar()){
                case 'w':
                    if(down==true && bodyParts>1){
                        break;
                    } else{
                        up = true;
                        down = false;
                        right = false;
                        left = false;
                        break;
                    }
                case 'a':
                    if(right==true && bodyParts>1){
                        break;
                    } else{
                        up = false;
                        down = false;
                        right = false;
                        left = true;
                        break;
                    }
                case 's':
                    if(up==true && bodyParts>1){
                        break;
                    } else{
                        up = false;
                        down = true;
                        right = false;
                        left = false;
                        break;
                    }
                case 'd':
                    if(left==true && bodyParts>1){
                        break;
                    } else{
                        up = false;
                        down = false;
                        right = true;
                        left = false;
                    }
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
