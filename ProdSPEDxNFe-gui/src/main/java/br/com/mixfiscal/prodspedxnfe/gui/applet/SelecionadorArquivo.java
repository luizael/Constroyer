package br.com.mixfiscal.prodspedxnfe.gui.applet;

import java.awt.FlowLayout;
import javax.swing.JApplet;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

public class SelecionadorArquivo extends JApplet {
    public void init() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    JFileChooser fc = new JFileChooser();
                    FlowLayout flow = new FlowLayout();
                    flow.addLayoutComponent("fc", fc);
                    SelecionadorArquivo.this.setLayout(flow);
                }
            });            
        } catch(Exception ex) {
            System.err.println("Erro ao carregar o applet: " + ex.getMessage());
        }
    }
}
