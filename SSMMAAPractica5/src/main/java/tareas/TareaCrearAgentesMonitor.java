/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareas;

import es.ujaen.ssmmaa.agentes.AgenteMonitor;
import es.ujaen.ssmmaa.gui.AgenteMonitorJFrame;
import jade.core.Agent;
import jade.core.MicroRuntime;
import jade.core.behaviours.OneShotBehaviour;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danie
 */
public class TareaCrearAgentesMonitor extends OneShotBehaviour {

    private ArrayList<String> arrayNombreAgentes;
    private ArrayList<String> arrayClaseAgentes;
    private ArrayList<ArrayList<String>> arrayArgumentos;
    private AgenteMonitorJFrame myGui;

    public TareaCrearAgentesMonitor(AgenteMonitor a) {
        super(a);
        this.arrayNombreAgentes = a.getArrayNombreAgentes();
        this.arrayClaseAgentes = a.getArrayClaseAgentes();
        this.arrayArgumentos = a.getArrayArgumentos();
        this.myGui = a.getMyGui();
    }

    @Override
    public void action() {

        Object[] arrAux;

        try {
//            arrAux = new Object[arrayArgumentos.get(1).size()];
//            for (int i = 0; i < arrayArgumentos.get(1).size(); i++) {
//                arrAux[i] = arrayArgumentos.get(1).get(i);
//            }
//            myGui.presentarSalida("\nCreando agente Laberinto...");
//            System.out.println("ARGS: " + arrayNombreAgentes.get(1) + "; " + arrayClaseAgentes.get(1) + " " + arrAux);
//            MicroRuntime.startAgent(arrayNombreAgentes.get(1), arrayClaseAgentes.get(1), arrAux);

            arrAux = new Object[arrayArgumentos.get(0).size()];
            for (int i = 0; i < arrayArgumentos.get(0).size(); i++) {
                arrAux[i] = arrayArgumentos.get(0).get(i);
            }

            myGui.presentarSalida("\nCreando agente Raton...");
            System.out.println("ARGS: " + arrayNombreAgentes.get(0) + "; " + arrayClaseAgentes.get(0) + " " + arrAux);
            MicroRuntime.startAgent(arrayNombreAgentes.get(0), arrayClaseAgentes.get(0), arrAux);

        } catch (Exception ex) {
            Logger.getLogger(AgenteMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
