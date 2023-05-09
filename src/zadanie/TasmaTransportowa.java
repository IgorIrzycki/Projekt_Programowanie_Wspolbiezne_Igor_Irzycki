package zadanie;

import zadanie.gui.MainWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TasmaTransportowa {

    private final Cegla[] cegly;
    private final int maksymalnaMasa;
    private int masa;

    private final Lock dostep;
    private final Condition pusty;
    private final Condition pelny;

    {
        dostep = new ReentrantLock();
        pusty = dostep.newCondition();
        pelny = dostep.newCondition();
    }

    private final MainWindow gui;

    public TasmaTransportowa(int pojemnosc, int maksymalnaMasa, MainWindow gui) {
        this.maksymalnaMasa = maksymalnaMasa;
        cegly = new Cegla[pojemnosc];
        this.gui = gui;
    }

    public void wstaw(Cegla cegla) {
        dostep.lock();
        try {
            boolean masaPrzekroczona;
            while ((masaPrzekroczona = masa + cegla.getMasa() > maksymalnaMasa) || cegly[0] != null) {
                if (masaPrzekroczona) {
                    pusty.signalAll();
                }
                pelny.await();
            }
            cegly[0] = cegla;
            masa += cegla.getMasa();
            gui.ustawWageCalejTasmy(masa);
            System.out.print("[P-" + cegla.getMasa() + "]>>  ");
            gui.pokazCegleUPracownika(cegla.getMasa());
            pokazCegly();
            pusty.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dostep.unlock();
        }
    }

    public Cegla pobierz(int numerCiezarowki, int iloscCegiel, int masaCiezarowki) {
        dostep.lock();
        Cegla ostatniaCegla = null;
        try {
            do {
                ostatniaCegla = przesun();
                pokazCegly();

                pelny.signal();
                if (ostatniaCegla == null) {
                    pusty.await();
                }
            } while (ostatniaCegla == null);
            masa -= ostatniaCegla.getMasa();
            gui.ustawWageCalejTasmy(masa);
            System.out.print("[C-" + numerCiezarowki + "]>> ");
            pokazCegly();

            System.out.println(" iloscC: " + iloscCegiel + " masaC: " + masaCiezarowki);
            gui.przeniescCeglyDoCiezarowki(ostatniaCegla.getMasa(),masaCiezarowki+ostatniaCegla.getMasa(),numerCiezarowki, iloscCegiel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dostep.unlock();
        }
        return ostatniaCegla;
    }

    private Cegla przesun() {
        Cegla ostatniaCegla = cegly[cegly.length - 1];

        for (int i = cegly.length - 1; i > 0; i--) {
            cegly[i] = cegly[i - 1];
        }

        cegly[0] = null;

        return ostatniaCegla;
    }

    private void pokazCegly() {
        List<Integer> ceglyDoGui = new ArrayList<>();
        for (Cegla cegla : cegly) {
            int masaCegly = cegla == null ? 0 : cegla.getMasa();
            ceglyDoGui.add(masaCegly);
            System.out.print(masaCegly + " ");
        }
        System.out.println("masa: " + masa);
        gui.przesunElementyNaTasmie(ceglyDoGui);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}