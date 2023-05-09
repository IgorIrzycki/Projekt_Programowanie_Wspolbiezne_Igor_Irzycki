package zadanie;

import java.util.concurrent.atomic.AtomicBoolean;

public class Ciezarowka extends Thread {

    private int iloscCegiel;
    private int maksymalnaIloscCegiel;
    private int numerCiezarowki;
    private int masaCiezarowki;
    private TasmaTransportowa tasmaTransportowa;
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

    public Ciezarowka(int maksymalnaIloscCegiel, TasmaTransportowa tasmaTransportowa) {
        this.maksymalnaIloscCegiel = maksymalnaIloscCegiel;
        this.tasmaTransportowa = tasmaTransportowa;
    }

    @Override
    public void run() {
        running.set(true);
        stopped.set(false);
        while (running.get()) {
        	
            Cegla cegla = tasmaTransportowa.pobierz(numerCiezarowki, iloscCegiel, masaCiezarowki);
            masaCiezarowki += cegla.getMasa();
            iloscCegiel++;
            
            if (iloscCegiel == maksymalnaIloscCegiel) {
                iloscCegiel = 0;
                masaCiezarowki = 0;
                numerCiezarowki++;
            }
            try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
        }
        stopped.set(true);
    }

}