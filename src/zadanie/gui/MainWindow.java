package zadanie.gui;

import zadanie.Ciezarowka;
import zadanie.ExceptionHandler;
import zadanie.Main;
import zadanie.Pracownik;
import zadanie.gui.paintableComponents.LorryComponent;
import zadanie.gui.paintableComponents.TasmaTransportowaGuiComponent;
import zadanie.gui.paintableComponents.WorkerComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainWindow extends JFrame {
    JSpinner dlugoscTasmySpinner;
    JSpinner maksUdzwigTasmySpinner;
    JSpinner pojemnoscCiezarowkiSpinner;

    JPanel panelPracownika1;
    JPanel panelPracownika2;
    JPanel panelPracownika3;


    public MainWindow(String title) throws HeadlessException {
        super(title);
        this.setSize(300, 500);
    }

    public void run() {
        createMainContainer();
        JPanel panelParametrow = stworzPanelParametrow();
        getContentPane().add(panelParametrow);

        konfigurujGlowneOkno();
    }

    private JPanel stworzPanelPrzyciskow() {
        JPanel panelPrzyciskow = new JPanel();
        panelPrzyciskow.setLayout(new BoxLayout(panelPrzyciskow, BoxLayout.X_AXIS));
        Map<String, Integer> wartosciParametrow = new HashMap<>();
        JButton startBtn = new JButton("Start");
        startBtn.addActionListener(evt -> {
            new Thread(() -> {
                Main.stworzFabryke(this, wartosciParametrow);
            }).start();
            startBtn.setEnabled(false);
        });
        startBtn.setEnabled(false);
        JButton saveBtn = new JButton("Zapisz");
        saveBtn.addActionListener(evt -> {
            startBtn.setEnabled(true);
            wartosciParametrow.put("DLUGOSC_TASMY", (Integer) dlugoscTasmySpinner.getModel().getValue());
            wartosciParametrow.put("MAKS_UDZWIG_TASMY", (Integer) maksUdzwigTasmySpinner.getModel().getValue());
            wartosciParametrow.put("POJEMNOSC_CIEZAROWKI", (Integer) pojemnoscCiezarowkiSpinner.getModel().getValue());
            wartosciParametrow.put("MASA_CEGLY_PR_1", (Integer) ((JSpinner) panelPracownika1.getComponent(1)).getModel().getValue());
            wartosciParametrow.put("MASA_CEGLY_PR_2", (Integer) ((JSpinner) panelPracownika2.getComponent(1)).getModel().getValue());
            wartosciParametrow.put("MASA_CEGLY_PR_3", (Integer) ((JSpinner) panelPracownika3.getComponent(1)).getModel().getValue());
            JPanel panelAnimacji = stworzPanelAnimacji((Integer) dlugoscTasmySpinner.getModel().getValue());
            getContentPane().add(panelAnimacji);
            this.setSize(1000, 500);
            this.setLocationRelativeTo(null);
        });
        JButton stopBtn = new JButton("Stop");
        stopBtn.addActionListener(evt -> {
            startBtn.setEnabled(false);
            getContentPane().remove(1);
            this.setSize(300, 500);
            ustawWageCalejTasmy(0);
            Main.workingThreads.forEach(th ->{
                if (th instanceof Pracownik){
                    ((Pracownik)th).makeInterrupt();
                } if (th instanceof Ciezarowka){
                    ((Ciezarowka)th).makeInterrupt();
                }
              th.stop();
            } );
            repaint();
        });
        panelPrzyciskow.add(saveBtn);
        panelPrzyciskow.add(startBtn);
        panelPrzyciskow.add(stopBtn);
        return panelPrzyciskow;
    }

    private JPanel stworzPanelAnimacji(int dlugoscTasmy) {
        JPanel panelAnimacji = new JPanel();
        panelAnimacji.setLayout(new BoxLayout(panelAnimacji, BoxLayout.X_AXIS));
        JPanel panelPracownikow = stworzPanelPracownikow();
        JPanel panelTasmy = stworzPanelTasmy(dlugoscTasmy);
        JPanel panelCiezarowki = stworzPanelCiezarowki();


        panelAnimacji.add(panelPracownikow);
        panelAnimacji.add(panelTasmy);
        panelAnimacji.add(panelCiezarowki);
        return panelAnimacji;
    }

    JPanel panelCiezarowki;

    private JPanel stworzPanelCiezarowki() {
        panelCiezarowki = new LorryComponent(0, 0, 0);
        return panelCiezarowki;
    }

    JPanel panelTasmy;
    JLabel wagaCalejTasmyLbl = new JLabel("Waga taśmy : 0");

    public void ustawWageCalejTasmy(int waga) {
        wagaCalejTasmyLbl.setText("Waga taśmy : " + waga);
    }

    private JPanel stworzPanelTasmy(int dlugosc) {
        panelTasmy = new JPanel();
        JPanel aggregator = new JPanel();
        aggregator.setLayout(new BoxLayout(aggregator, BoxLayout.Y_AXIS));
        wagaCalejTasmyLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        aggregator.add(wagaCalejTasmyLbl);
        aggregator.add(panelTasmy);
        panelTasmy.setPreferredSize(new Dimension(300, 300));
        panelTasmy.setLayout(new BoxLayout(panelTasmy, BoxLayout.X_AXIS));

        for (int j = 0; j < dlugosc; j++) {
            int y = (int) (panelTasmy.getPreferredSize().getHeight() / 2) + 50;
            TasmaTransportowaGuiComponent segmentTasmy = new TasmaTransportowaGuiComponent(0, y, 0);
            panelTasmy.add(segmentTasmy);
            panelTasmy.add(Box.createRigidArea(new Dimension(1, 0)));
        }
        return aggregator;
    }

    WorkerComponent pracownik1;

    WorkerComponent pracownik2;
    WorkerComponent pracownik3;

    private JPanel stworzPanelPracownikow() {
        JPanel panelPracownikow = new JPanel();
        panelPracownikow.setLayout(new BoxLayout(panelPracownikow, BoxLayout.Y_AXIS));
        panelPracownikow.add(Box.createRigidArea(new Dimension(0, 50)));
        pracownik1 = new WorkerComponent(0, 0, 0);
        panelPracownikow.add(pracownik1);
        panelPracownikow.add(Box.createRigidArea(new Dimension(0, 3)));
        pracownik2 = new WorkerComponent(0, 0, 0);
        panelPracownikow.add(pracownik2);
        panelPracownikow.add(Box.createRigidArea(new Dimension(0, 3)));
        pracownik3 = new WorkerComponent(0, 0, 0);
        panelPracownikow.add(pracownik3);

        return panelPracownikow;
    }

    private void konfigurujGlowneOkno() {
        setResizable(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel stworzPanelParametrow() {
        JPanel paramsPanel = new JPanel();
        paramsPanel.setPreferredSize(new Dimension(50, 400));
        paramsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        paramsPanel.setLayout(new BoxLayout(paramsPanel, BoxLayout.Y_AXIS));
        // dlugos tasmy
        JLabel dlugoscTasmyLbl = new JLabel("Długość tasmy");
        dlugoscTasmySpinner = stworzSpinner(5, 4, 20);
        JLabel maksUdzwigTasmyLbl = new JLabel("Maksymalny udźwig taśmy");
        maksUdzwigTasmySpinner = stworzSpinner(10, 10, 30);
        JLabel pojemnoscCiezarowkiLbl = new JLabel("Pojemność ciężarówki");
        pojemnoscCiezarowkiSpinner = stworzSpinner(5, 5, 15);

        paramsPanel.add(dlugoscTasmyLbl);
        paramsPanel.add(dlugoscTasmySpinner);
        paramsPanel.add(maksUdzwigTasmyLbl);
        paramsPanel.add(maksUdzwigTasmySpinner);
        paramsPanel.add(pojemnoscCiezarowkiLbl);
        paramsPanel.add(pojemnoscCiezarowkiSpinner);
        paramsPanel.add(stworzPanelZarzadzaniaMasaCegiel());
        paramsPanel.add(stworzPanelPrzyciskow());

        return paramsPanel;
    }

    private JSpinner stworzSpinner(int wartoscPocz, int min, int max) {
        SpinnerNumberModel dlugoscTasmySpinnerModel = new SpinnerNumberModel(wartoscPocz, min, max, 1);
        return new JSpinner(dlugoscTasmySpinnerModel);
    }

    private JPanel stworzPanelZarzadzaniaMasaCegiel() {
        JPanel panelZarzadzaniaMasaCegiel = new JPanel();
        panelZarzadzaniaMasaCegiel.setLayout(new BoxLayout(panelZarzadzaniaMasaCegiel, BoxLayout.Y_AXIS));
        JLabel masaCegielLbl = new JLabel("Masa cegieł");
        panelZarzadzaniaMasaCegiel.add(masaCegielLbl);
        JPanel panelMasyCegiel = stworzPanelMasyCegiel();
        panelZarzadzaniaMasaCegiel.add(panelMasyCegiel);
        return panelZarzadzaniaMasaCegiel;
    }

    private JPanel stworzPanelMasyCegiel() {
        JPanel panelMasyCegiel = new JPanel();
        panelMasyCegiel.setBorder(BorderFactory.createLineBorder(Color.black));
        panelMasyCegiel.setLayout(new BoxLayout(panelMasyCegiel, BoxLayout.X_AXIS));
        panelPracownika1 = stworzInputDlaPracownika("P1", 1);
        panelPracownika2 = stworzInputDlaPracownika("P2", 2);
        panelPracownika3 = stworzInputDlaPracownika("P3", 3);
        panelMasyCegiel.add(panelPracownika1);
        panelMasyCegiel.add(panelPracownika2);
        panelMasyCegiel.add(panelPracownika3);
        return panelMasyCegiel;
    }

    private JPanel stworzInputDlaPracownika(String pracownik, int poczatkowaMasaCegly) {
        JPanel panelPracownikow = new JPanel();
        panelPracownikow.setPreferredSize(new Dimension(25, 25));
        panelPracownikow.setBorder(BorderFactory.createLineBorder(Color.black));
        panelPracownikow.setLayout(new BoxLayout(panelPracownikow, BoxLayout.Y_AXIS));
        JLabel nazwaPracownikaLbl = new JLabel(pracownik);
        JSpinner spinnerMasyCegly = stworzSpinner(poczatkowaMasaCegly, 1, 10);
        spinnerMasyCegly.setPreferredSize(new Dimension(10, 10));
        panelPracownikow.add(nazwaPracownikaLbl);
        panelPracownikow.add(spinnerMasyCegly);
        return panelPracownikow;
    }

    private void createMainContainer() {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
    }

    public void pokazCegleUPracownika(int numerPracownika) {
        if (numerPracownika == 1) {
            pracownik1.setBrickIdx(numerPracownika);
            pracownik2.setBrickIdx(0);
            pracownik3.setBrickIdx(0);
        }
        if (numerPracownika == 2) {
            pracownik1.setBrickIdx(0);
            pracownik2.setBrickIdx(numerPracownika);
            pracownik3.setBrickIdx(0);
        }
        if (numerPracownika == 3) {
            pracownik1.setBrickIdx(0);
            pracownik2.setBrickIdx(0);
            pracownik3.setBrickIdx(numerPracownika);
        }

    }

    public void przesunElementyNaTasmie(List<Integer> cegly) {

        int numerSegmentu = 0;
        for (int i = 0; i < panelTasmy.getComponents().length; i++) {
            Component component = panelTasmy.getComponent(i);
            if (component instanceof TasmaTransportowaGuiComponent) {
                ((TasmaTransportowaGuiComponent) component).setBrickIdx(cegly.get(numerSegmentu));
                numerSegmentu++;
            }
        }
    }

    public void przeniescCeglyDoCiezarowki(int masaOstatniejCegly, int masaCiezarowki, int numerCiezarowki, int iloscCegiel) {
        ((LorryComponent) panelCiezarowki).setBrickIdx(masaOstatniejCegly);
        ((LorryComponent) panelCiezarowki).setLorryWeight(masaCiezarowki);
        ((LorryComponent) panelCiezarowki).setLorryNo(numerCiezarowki);
        ((LorryComponent) panelCiezarowki).setBrickQty(iloscCegiel + 1);
    }
}
