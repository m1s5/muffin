import org.jfugue.Player;

public class Seq {

    public static String sequence = "";
    public static String str = "";


    static int length;
    static int deletedNote = 100000;
    static int oneNoteLength = 6;




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





    public static void setString() {

        int i = Main.width/ Note.defaultWidth;

        for (int j = 0; j < i; j++) {
            sequence += "Rq ";
        }
        System.out.println(i);

        System.out.println(sequence);

    }

    public static void addNote2Seq(Note note) {

        /*if(Window.index == 0){

            sequence = "[" + note.frq + "]q";

            length++;

        } else */{

            sequence = sequence + " [" + note.frq + "]q";
            length++;

        }

       // System.out.println("sequence :" + sequence);

    }

    public static void delNoteFromSeq(Note note) {

        deletedNote = note.index;
        String seq1, seq2;

        if (note.index == 0 && Window.index == 1) {
            seq2 = "";
            //System.out.println("seq2 (1):" + seq2);

            sequence = seq2;

        }       else if (note.index == 0) {
            seq2 = sequence.substring((note.index + 1) * oneNoteLength, sequence.length() );
            //System.out.println("seq2 (1):" + seq2);

            sequence = seq2;

        }           else{
            seq1 = sequence.substring(0, note.index * oneNoteLength - 1);
            seq2 = sequence.substring((note.index + 1) * oneNoteLength - 1, sequence.length() );
            //System.out.println("seq1 (n):" + seq1);
            //System.out.println("seq2 (n):" + seq2);
            sequence = seq1 + seq2;
        }

        //System.out.println("new sequence:" + sequence);

        length--;

    }

    public static void defaultDeletedNote() {

        deletedNote = 100000;

    }
}


//backup
/**public class Seq {
 public static String sequence;

 static int length;
 static int deletedNote = 100000;

 static boolean firstTime;

 public static void addNote2Seq(Note note) {

 if(!firstTime){

 sequence = "[" + note.frq + "]";

 length++;

 }

 if(firstTime){

 sequence = sequence + " [" + note.frq + "]";
 length++;

 }
 firstTime = true;
 System.out.println(sequence);

 }

 public static void delNoteFromSeq(Note note) {

 deletedNote = note.index;
 String seq1 = sequence.substring(1, note.index * 4 + note.index-1);
 System.out.println(seq1);
 String seq2 = sequence.substring(note.index * 5, sequence.length()+1);
 System.out.println(seq2);
 sequence = seq1 + seq2;
 System.out.println(sequence);
 length--;

 }

 public static void defaultDeletedNote() {

 deletedNote = 100000;

 }
 }
 **/