import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
/**
 * Created by Алексей on 13.06.2017.
 */
public class Bar {

    static int barWidth;

    public Bar(int barWidth) {
        Bar.barWidth = barWidth;
        draw();
    }

    void draw() {
        glBegin(GL_QUADS);
        glColor3f(0.1f, 0.6f, 0.2f);
        glVertex2i(Main.width - barWidth, 0);
        glVertex2i(Main.width, 0);
        glVertex2i(Main.width, Main.height);
        glVertex2i(Main.width - barWidth, Main.height);
        glEnd();
    }

    class Button{
        boolean isAimed, firstTime;
        int height, width, x, y;

        Button(int height, int width, int x, int y){



            height = this.height;
            height = this.width;
            x = this.x;
            y = this.y;
        }


        void setIsAimed(){

                if(!firstTime && !Mouse.isButtonDown(0) && inBounds()){
                firstTime = true;
            }

            if(!inBounds()){
                firstTime = false;
            }

        }

        public boolean inBounds() {

            boolean xl, xr, yb, yt;

            xl = (Mouse.getX() >= (x - (width / 2)));
            xr = (Mouse.getX() <= (x + (width / 2)));
            yb = ((Main.height - Mouse.getY()) >= (y - (height / 2)));
            yt = ((Main.height - Mouse.getY()) <= (y + (height / 2)));

            if (xl && xr && yb && yt) {
                System.out.println("Bar_inBounds");
                return true;
            }

            return false;
        }
    }
}