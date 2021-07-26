/*import org.jtransforms.*;
import javax.sound.sampled.*;


public class HabraCopy {





    final AudioFormat format = getFormat(); //Заполнить объект класса AudioFormat параметрами
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine line;

    {
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
        line.open(format);
        line.start();

    public AudioFormat getFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 8;
        int channels = 1; //mono
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    public static Complex[] fft(Complex[] x) {
        int N = x.length;

        // fft чётных элементов
        Complex[] even = new Complex[N / 2];
        for (int k = 0; k < N / 2; k++) {
            even[k] = x[2 * k];
        }
        Complex[] q = fft(even);

        // fft нечетных элементов
        Complex[] odd = even; // повторное использование массива
        for (int k = 0; k < N / 2; k++) {
            odd[k] = x[2 * k + 1];
        }
        Complex[] r = fft(odd);

        // комбинируем
        Complex[] y = new Complex[N];
        for (int k = 0; k < N / 2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k] = q[k].plus(wk.times(r[k]));
            y[k + N / 2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }
}
*/