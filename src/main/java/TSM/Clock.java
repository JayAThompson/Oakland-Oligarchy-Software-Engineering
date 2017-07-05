/**
 * Updates the specified JLabel with the current elapsed time
 */

import javax.swing.JLabel;

public class Clock {

    private final int MAX_VAL = 60;
    private final int MIN_VAL = 0;

    private JLabel label;
    private int hours;
    private int minutes;
    private int seconds;

    public Clock(JLabel l) {
        this.label = l;
        this.hours = MIN_VAL;
        this.minutes = MIN_VAL;
        this.seconds = MIN_VAL;
    }

    public Clock(JLabel l, int h, int m, int s) {
        this.label = l;
        this.hours = h;
        this.minutes = m;
        this.seconds = s;
    }

    public void start() {
        Thread t = new Thread(() ->
        {
            
        });
    }

    public String getElapsedTime() {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
