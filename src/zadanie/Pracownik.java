package zadanie;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Pracownik extends Thread {

    private final int ciezarCegly;
    private final TasmaTransportowa tasmaTransportowa;

    public Pracownik(int ciezarCegly, TasmaTransportowa tasmaTransportowa) {
        this.ciezarCegly = ciezarCegly;
        this.tasmaTransportowa = tasmaTransportowa;
    }
    Random random= new Random();
    private AtomicBoolean running = new AtomicBoolean(false);
    private AtomicBoolean stopped = new AtomicBoolean(false);

    boolean isRunning() {
        return running.get();
    }
    public void makeInterrupt() {
        running.set(false);
        interrupt();
    }
    boolean isStopped() {
        return stopped.get();
    }
    @Override
    public void run() {
        running.set(true);
        stopped.set(false);
        while (running.get()) {
        	try {
				Thread.sleep(ciezarCegly*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            tasmaTransportowa.wstaw(new Cegla(ciezarCegly));
        }
        stopped.set(true);
    }

}
