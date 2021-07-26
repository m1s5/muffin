import org.jfugue.Player;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.*;
/**
 * Created by Алексей on 03.06.2017.
 */


public class Note {
///////////////////////////////////////////////////////////////////////////
// Variables
///////////////////////////////////////////////////////////////////////////

    static int defaultHeight = 20;
    static int defaultWidth = 40;
    static int isResizableWidth = 10;

    static boolean inChange;
    byte value = 10;
    long dur = 10;
    boolean selected, aimed, isAimedResizable, isOn, changing, isInChange;
    int x, x1, y, height, width, index, frq;
    int x0, delta;

    protected Player mPlayer;

///////////////////////////////////////////////////////////////////////////
// Box Constructor
///////////////////////////////////////////////////////////////////////////

    Note(int x, int y, int index) {

        height = 10;
        width = defaultWidth;
        isOn = true;

// System.out.print(x/modX + " ("+ modX + ") " + " ... ");
        this.x = modX(x);
        this.x1 = modX1(x);
        this.y = modY(y);
        this.index = index;
        this.frq = 84 - (this.y - 5) / 20;

        // Create player object for this Note
        mPlayer = new Player();

// System.out.println(this.x);

        // And play the note asynchronously
        playMidiAsync("[" + this.frq + "]q");
    }

///////////////////////////////////////////////////////////////////////////
// Static Methods
///////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////
// Booleans
///////////////////////////////////////////////////////////////////////////

    /**
     * Plays the given MIDI string using jfugue asynchronously.
     * @param midiString
     */
    protected void playMidiAsync(String midiString) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                playMidi(midiString);
            }
        }).start();
    }

    /**
     * Plays the given MIDI string using jfugue synchronously.
     * @param midiString
     */
    protected void playMidi(String midiString) {
        mPlayer.play(midiString);
    }

    public boolean inBounds() {

        if ((Mouse.getX() >= (x - (width / 2))) && (Mouse.getX() <= x1) && ((Main.height - Mouse.getY()) >= (y - (height / 2))) && ((Main.height - Mouse.getY()) <= (y + (height / 2))) && isOn) {
            return true;
        }

        return false;
    }

    boolean inBoundsBord() {

        if ((modX(Mouse.getX()) >= x) && (modX1(Mouse.getX()) <= x1) && (modY((Main.height - Mouse.getY())) >= y) && (modY((Main.height - Mouse.getY())) <= y) && isOn) {
            return true;
        } else return false;

    }

    boolean inBoundsResizable() {
        if ((Mouse.getX() >= (x1 - isResizableWidth)) && (Mouse.getX() <= x1) && ((Main.height - Mouse.getY()) >= (y - (height / 2))) && ((Main.height - Mouse.getY()) <= (y + (height / 2))) && isOn) {
            return true;
        }
        return false;
    }

    boolean isSelected() {

        if (inBounds() && !inBoundsResizable() && !Mouse.isButtonDown(0) && isOn) {
            aimed = true;
        }

        if (!inBounds() && !Mouse.isButtonDown(0) && isOn) {
            aimed = false;
        }

        if (Mouse.isButtonDown(0) && aimed && isOn && ((inBounds() && !inBoundsResizable() && !selected) || selected)) {
            selected = true;
            return true;
        }

        if (!Mouse.isButtonDown(0) && selected) {
            aimed = false;
            selected = false;
        }

        return false;
    }

    boolean isSelectedResizable() {
        if (inBoundsResizable() && !Mouse.isButtonDown(0) && isOn) {
            isAimedResizable = true;
        }

        if (!inBoundsResizable() && !Mouse.isButtonDown(0) && isOn) {
            isAimedResizable = false;
        }

        if (Mouse.isButtonDown(0) && isAimedResizable && isOn && ((inBoundsResizable() && !changing) || changing)) {
            changing = true;
            return true;
        }

        if (!Mouse.isButtonDown(0) && changing) {
            isAimedResizable = false;
            changing = false;
        }

        return false;
    }

///////////////////////////////////////////////////////////////////////////
// Int
///////////////////////////////////////////////////////////////////////////

    int getIndex() {

        return index;

    }

    int modX(int x) {
        int result = (((x / width) * width) + (width / 2)) - (width / 2);
        return result;
    }

    int modX1(int x) {
        int result = (((x / width) * width) + (width / 2)) - (width / 2) + width;
        return result;
    }

    int modY(int y) {
        int result = ((y / height / 2) * 2 * height) + (height / 2);
        return result;
    }

///////////////////////////////////////////////////////////////////////////
// Methods
///////////////////////////////////////////////////////////////////////////

    void draw() {

        glBegin(GL_QUADS);
        glColor3f(0f, 0.8f, 1f);
        glVertex2i(x, y + height / 2);
        glVertex2i(x1 +
                delta, y + height / 2);
        glVertex2i(x1 + delta, y - height / 2);
        glVertex2i(x, y - height / 2);
        glEnd();

        glBegin(GL_QUADS);
        glColor3f(0f, 0.2f, 0.3f);
        glVertex2i(x1 - isResizableWidth, y + height / 2);
        glVertex2i(x1 + delta, y + height / 2);
        glVertex2i(x1 + delta, y - height / 2);
        glVertex2i(x1 - isResizableWidth, y - height / 2);
        glEnd();

    }

    void chgCord() {
/**
 System.out.print(x1 + " ");
 System.out.print(x0 + " ");
 System.out.print(delta + " :");

 if(isSelected()){
 System.out.print(1 + " ");
 }

 if(isSelectedResizable()){
 System.out.print(2 + " ");
 }

 if(inBoundsResizable()){
 System.out.print(3 + " ");
 }

 if(inBounds()){
 System.out.print(4 + " ");
 }
 System.out.println();
 */


        if (isSelectedResizable() && !isInChange) {
            x0 = Mouse.getX();
            isInChange = true;
        }

        if (isSelectedResizable() && isInChange && Window.inArea()) {
            delta = (Mouse.getX() - x0) / width * width;
            if (x1 + delta - x < width) {
                delta = 0;
            }
        }

        if (!isSelectedResizable()) {
            x1 += delta;
            delta = 0;
            isInChange = false;
        }

    }

    /** void deleteBox() {
        if (Mouse.isButtonDown(1) && inBounds()) {
            isOn = false;

        }
    }
    */ //not an annot

    void defaultParams() {
        selected = false;
        aimed = false;
        isAimedResizable = false;
        height = 10;
        width = defaultWidth;
    }

    void bools() {
        isSelected();
        inBounds();
        inBoundsResizable();
        isSelectedResizable();
        inBoundsBord();
    }


    /**
     * height = 10;
     * width = defaultWidth;
     * isOn = true;
     *
     * modX = width*width+width/2- width/2;
     * modX1 = width*width+ width;
     * modY = height/2*2*height+height/2;
     *
     * this.x = x / modX;
     * this.x1 = x / modX1;
     * this.y = y / modY;
     * this.index = index;
     */ //not an annot


    void boxMethods() {

//        if (isOn) {

        draw();
        chgCord();
        bools();

//        }

//        if (!isOn) {
//            defaultParams();
//        }
    }




}


