package lesson1;

import java.awt.*;

public class Background extends Sprite {
    private float time;
    private Color color;

    @Override
    public void update(GameCanvas gameCanvas, float deltaTime) {
        time += deltaTime;
        int red = (int)(100 + Math.cos(time) * 100);
        int green = (int)(100 + Math.sin(time) * 100);
        int blue = 255;
        color = new Color(red, green, blue);
    }

    @Override
    public void render(GameCanvas gameCanvas, Graphics g) {
        gameCanvas.setBackground(color);
    }
}
