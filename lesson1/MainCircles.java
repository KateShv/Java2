package lesson1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainCircles extends JFrame {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private Sprite[] sprites = new Sprite[1];
    private Sprite background;
    private int counter;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainCircles();
            }
        });
    }

    private MainCircles() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        GameCanvas canvas = new GameCanvas(this);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = e.getX();
                    int y = e.getY();
                    addSprite(new Ball(x, y));
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    deleteSprite();
                }
            }
        });
        add(canvas, BorderLayout.CENTER);
        setTitle("Circles");
        initApplication();
        setVisible(true);
    }

    private void initApplication() {
        background = new Background();
    }

    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime); // obnovlenie // S = v * t
        render(canvas, g); // otrisovka
    }

    private void update(GameCanvas canvas, float deltaTime) {
        background.update(canvas, deltaTime);
        for (int i = 0; i < counter; i++) {
            sprites[i].update(canvas, deltaTime);
        }
    }

    private void render(GameCanvas canvas, Graphics g) {
        background.render(canvas, g);
        for (int i = 0; i < counter; i++) {
            sprites[i].render(canvas, g);
        }
    }

    private void addSprite(Sprite sprite) {
        if (counter == sprites.length) {
            Sprite[] temp = new Sprite[sprites.length * 2];
            System.arraycopy(sprites, 0, temp, 0, sprites.length);
            sprites = temp;
        }
        sprites[counter] = sprite;
        counter++;
    }

    private void deleteSprite() {
        if (counter > 0) {
            counter--;
        }
    }

}
