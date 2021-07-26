import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import org.jfugue.Player;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Alex on 02.06.2017.
 */
public class Window {

///////////////////////////////////////////////////////////////////////////
// Variables
///////////////////////////////////////////////////////////////////////////

    public static int mousex, mousey;
    public static int index = 0;
    public static int indexDel = 0;

    protected Player mPlayer = new Player();


    static public boolean oneTime, oneTimePlay, oneTimeDelete, smthSelected, smthinBounds, smthinBoundsBord, aimedClearArea, smthAimed;

    static public List<Note> notes = new ArrayList<>(index);

///////////////////////////////////////////////////////////////////////////
// Constructor
///////////////////////////////////////////////////////////////////////////

    Window(int width, int height) {


        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle("MuffinTest");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }


//Initialization code OpenGL

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        Bar bar = new Bar(120);

        Grid grid = new Grid();

        //Seq.setString();

//Cycle

        while (!Display.isCloseRequested()) {

            glClear(GL_COLOR_BUFFER_BIT);

            // System.out.println(indexDel);

            mouseInput();
            boolsSetters();
            grid.draw();
            bar.draw();

            playString();




            /**if(smthinBounds&&Mouse.isButtonDown(1)&&!oneTime) {

             if(notes.get(indexDel).inBounds()) {

             System.out.println(1);
             oneTime = true;
             Seq.delNoteFromSeq(OnNote());
             notes.remove(indexDel);
             }
             }**/

            /**try {
             indexDel = OnNote().index;
             //System.out.println(indexDel + "\t" + index + "\t" + OnNote().index + "\t" + OnNote());
             } catch (NullPointerException e){}*/




            DelBox();

            for (Note note : notes) {
                note.boxMethods();
            }


//Exit & creation

            Seq.defaultDeletedNote();
            createBox();

            exit();

//Refresh

            Display.update();
            Display.sync(60);

        }

        Display.destroy();
    }

///////////////////////////////////////////////////////////////////////////
// Methods
///////////////////////////////////////////////////////////////////////////


    void playString(){
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !oneTimePlay){

            parseRoll();
            System.out.println("\n \n \t \t \t \t Str:" + Seq.str);
            oneTimePlay = true;
            playMidiAsync(Seq.str);

        }
        if (oneTimePlay && !Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            oneTimePlay = false;
        }
    }

    /**
     * Plays the given MIDI string using jfugue asynchronously.
     * @param midiString
     */
    void playMidiAsync(String midiString) {
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
    void playMidi(String midiString) {
        mPlayer.play(midiString);
    }


    static boolean nearBar() {
        return Mouse.getX() < 10;

    }

    static boolean inArea() {
        return (Mouse.getX() > 0) && (Mouse.getX() < (Main.width - Bar.barWidth)) && ((Main.height - Mouse.getY()) > 0) && ((Main.height - Mouse.getY()) < Main.height);
    }

    void exit() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Display.destroy();
            System.exit(0);
        }
    }

    void createBox() {
        if (Mouse.isButtonDown(0) && inArea() && !oneTime && !smthSelected && !smthinBoundsBord && !Note.inChange) {
            Window.notes.add(new Note(Window.mousex, Window.mousey, Window.index));
            Window.oneTime = true;
            Seq.addNote2Seq(notes.get(Window.index));
            index++;
        }

        if (!Mouse.isButtonDown(0) && Window.oneTime) {
            Window.oneTime = false;
        }
    }


    public void parseRoll(){

        int i = Main.width/ Note.defaultWidth - 3;

        Seq.str = "";

        for (int j = 0; j < i; j++) {

         boolean fl = false;
         boolean cord = false;

            for (Note note : notes) {
                if (note.x == j*Note.defaultWidth ) {
                    if(!cord){Seq.str += " [" + note.frq + "]q";}
                    else {Seq.str += "+[" + note.frq + "]q";}
                    System.out.println(fl);
                    cord = true;
                    fl = true;
                }
            }

          if(!fl){
              System.out.println(fl);
                Seq.str +=" Rq";
          }

        }

    }

    void DelBox(){
        if(Mouse.isButtonDown(1)&&smthinBounds&&!oneTimeDelete&&smthAimed){
            Seq.deletedNote = OnNote().index;
            Seq.delNoteFromSeq(OnNote());
            index--;
            oneTimeDelete = true;
            for (Note note : notes) {
                if (note.index > OnNote().index) {
                    note.index--;
                }
            }
            notes.remove(OnNote());
        }
        if(!Mouse.isButtonDown(1)&&oneTimeDelete){
            oneTimeDelete = false;
        }
    }


///////////////////////////////////////////////////////////////////////////
// Booleans
///////////////////////////////////////////////////////////////////////////


    void mouseInput() {
        mousey = Main.height - Mouse.getY();
        mousex = Mouse.getX();
    }

    void setSmthAimed() {
        smthAimed = false;
        for (Note note : notes) {
            if (note.aimed) {
                smthAimed = true;
            }
        }
    }

    void setSmthinBounds() {
        smthinBounds = false;
        for (Note note : notes) {
            if (note.inBounds()) {
                smthinBounds = true;
            }
        }
    }

    void setSmthinBoundsBord() {
        smthinBoundsBord = false;
        for (Note note : notes) {
            if (note.inBoundsBord()) {
                smthinBoundsBord = true;
            }
        }
    }

    void setSmthSelected() {
        smthSelected = false;
        for (Note note : notes) {
            if (note.isSelected()) {
                smthSelected = true;
            }
        }
    }

    void setInChange() {
        Note.inChange = false;
        for (Note note : notes) {
            if (note.isInChange) {
                Note.inChange = true;
            }
        }
    }

    void setAimedClearArea() {
        aimedClearArea = false;
        for (Note note : notes) {
            if (!Mouse.isButtonDown(0) && !smthinBoundsBord) {
                aimedClearArea = true;
            }
        }
    }

    void boolsSetters() {
        setSmthAimed();
        setSmthinBounds();
        setSmthinBoundsBord();
        setAimedClearArea();
        setSmthSelected();
        setInChange();
        nearBar();
        inArea();
    }

    Note OnNote(){
        if(smthinBounds){
            for (Note note : notes) {

                if(note.inBounds()){
                    return note;
                }

            }
        } return null;
    }

    void boolTest(){
        for(Note note : notes){
            if (note.inBoundsBord()){
                System.out.print(1 + "\t");
            } else System.out.print(0 + "\t");
        }
        System.out.println();
    }

}

