import static org.lwjgl.opengl.GL11.*;

public class Grid {

    int cellHeight, cellWidth, verAmount, horAmount;

    Grid() {

        verAmount = Main.width / Note.defaultWidth;
        horAmount = Main.height / Note.defaultHeight;

    }

    void draw() {

        for (int i = 0; i < horAmount; i++){
            glBegin(GL_LINES);
            glColor3f(0.85f, 0.5f, 0.1f);

            glVertex2i(0, Note.defaultHeight * i - 1);
            glVertex2i(Main.width, Note.defaultHeight * i - 1);

            glEnd();
        }

        for (int i = 0; i < verAmount; i++){
            glBegin(GL_LINES);

            glVertex2i(Note.defaultWidth * i, 0);
            glVertex2i(Note.defaultWidth * i, Main.height);

            glEnd();
        }

    }
}