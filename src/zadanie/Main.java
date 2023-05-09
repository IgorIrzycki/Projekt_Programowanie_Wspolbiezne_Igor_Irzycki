package zadanie;

import zadanie.gui.MainWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static TasmaTransportowa tasmaTransportowa;
    public static Ciezarowka ciezarowka;

    public static void main(String[] args) throws Exception {
        MainWindow gui = createFrame();
    }

    private static MainWindow createFrame() {
        MainWindow mainWindow = new MainWindow("Fabryka cegieł");
        mainWindow.run();
        return mainWindow;
    }
    public static List<Thread> workingThreads;

    public static void stworzFabryke(MainWindow gui, Map<String, Integer> wartosciParametrow) {
        tasmaTransportowa = new TasmaTransportowa(wartosciParametrow.get("DLUGOSC_TASMY"), wartosciParametrow.get("MAKS_UDZWIG_TASMY"), gui);
        ciezarowka = new Ciezarowka(wartosciParametrow.get("POJEMNOSC_CIEZAROWKI"), tasmaTransportowa);
        Pracownik pracownik1 = new Pracownik(wartosciParametrow.get("MASA_CEGLY_PR_1"), tasmaTransportowa);
        pracownik1.start();
        Pracownik pracownik2 = new Pracownik(wartosciParametrow.get("MASA_CEGLY_PR_2"), tasmaTransportowa);
        pracownik2.start();
        Pracownik pracownik3 = new Pracownik(wartosciParametrow.get("MASA_CEGLY_PR_3"), tasmaTransportowa);
        pracownik3.start();

        ciezarowka.start();

        workingThreads = Arrays.asList(pracownik1,pracownik2,pracownik3,ciezarowka);
        try {
            pracownik1.join();
            pracownik2.join();
            pracownik3.join();
            ciezarowka.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
