/**
 * Updates the specified JLabel with the current elapsed time
 */

import javax.swing.JLabel;

public class Clock {

    private final int MAX_VAL = 60;
    private final int MIN_VAL = 0;

    private boolean gameOver = false;

    private JLabel label;
    private int hours;
    private int minutes;
    private int seconds;

    /**
     * Class constructor
     * Initialize hours, minutes, seconds with minimum value of 0
     */
    public Clock(JLabel l) {
        this.label = l;
        this.hours = MIN_VAL;
        this.minutes = MIN_VAL;
        this.seconds = MIN_VAL;
    }

    /**
     * Class constructor
     * Initialize hours, minutes, seconds with existing values
     */
    public Clock(JLabel l, int h, int m, int s) {
        this.label = l;
        this.hours = h;
        this.minutes = m;
        this.seconds = s;
    }

    /**
     * Start the clock
     * Create a separate thread to increment the time values every one second
     * Update JLabel text with formatted string
     */
    public void startClock() {
        Thread t = new Thread(() ->
        {
            while (!gameOver) {
                if (++seconds == MAX_VAL) {
                    seconds = MIN_VAL;
                    minutes++;
                }
                if (minutes == MAX_VAL) {
                    minutes = MIN_VAL;
                    hours++;
                }
                label.setText(getElapsedTime());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException iex) {
                    // Do nothing
                }
            }
        });

        t.start();
    }

    /**
     * Format string to contain the elapsed time data
     * @return String formatted string for JLabel text
     */
    public String getElapsedTime() {
        return String.format("<html><b>Elapsed Time: </b>%02d:%02d:%02d</html>", hours, minutes, seconds);
    }

    /**
     * Get the value of boolean variable gameOver
     * @return boolean Value of gameOver
     */
     public boolean isGameOver() {
         return this.gameOver;
     }

    /**
     * Set the value of boolean variable gameOver
     * -> If gameOver == true, the clock thread will stop running
     * @param value Boolean value to set as new gameOver value
     */
    public void setGameOver(boolean value) {
        final Object ref = new Object();
        synchronized(ref) {
            this.gameOver = value;
        }
    }
}