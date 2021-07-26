package Music;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Alex on 02.06.2017.
 */
public class NoteTest {
    int dur, vel, start, frq;
    NoteTest(int dur, int vel, int start, int frq){
        this.dur = dur;
        this.vel = vel;
        this.start = start;
        this.frq = frq;
    }

    void draw(){

//как то связать высоту тона и игрик. пока что все будет находиться в середине экрана
        glBegin(GL_QUADS);

        glVertex2i(start, frq);
        glVertex2i(start + dur, frq);
        glVertex2i(start + dur, frq - 10);//высоту надо высчитать(эту 10-ку)
        glVertex2i(start, frq - 10);

        glEnd();

    }
}