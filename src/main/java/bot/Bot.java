package bot;

/**
 * Created by David on 12/23/2016.
 */
public abstract class Bot {

    public abstract void run_bot();

    public void sleep(long ms) {

        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }

}
