/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.ssmmaa.agentes;

import es.ujaen.ssmmaa.gui.AgenteMonitorJFrame;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.NombreServicio.ORGANIZADOR;
import jade.core.Agent;
import jade.core.MicroRuntime;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static utils.Constantes.*;

/**
 *
 * @author danie
 */
public class AgenteMonitor extends Agent {
    //Variables del agente

    AgenteMonitorJFrame myGui;
    private String nombreFichero;

    @Override
    protected void setup() {
        //Inicialización de las variables del agente

        //Configuración del GUI
        //Registro del agente en las Páginas Amarrillas
        //Registro de la Ontología
        try {
            //Inicialización de las variables del agente
            //archivo="configuracion.txt";

            //Configuración del GUI
            myGui = new AgenteMonitorJFrame(this);
            myGui.setVisible(true);
            myGui.presentarSalida("Se inicializa la ejecución de " + this.getName() + "\n");
            //Registro del agente en las Páginas Amarrillas

            DFAgentDescription template = new DFAgentDescription();
            template.setName(getAID());
            ServiceDescription templateSd = new ServiceDescription();
            templateSd.setType("SERVICIO");
            templateSd.setName(ORGANIZADOR.name());
            template.addServices(templateSd);
            try {
                DFService.register(this, template);
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }
            leerArchivo();
            // addBehaviour(new TareaCrearAgentes());
        } catch (Exception ex) {
            Logger.getLogger(AgenteMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Se inicia la ejecución del agente: " + this.getName());
        //Añadir las tareas principales
    }

    private void leerArchivo() throws Exception {
        //Object[] args = getArguments();
        //if (args != null && args.length > 0) {
        // Lee el fichero de configuración
        //nombreFichero = (String) args[0];
        
        myGui.presentarSalida("****LEYENDO ARCHIVO: " + NOMBRE_FICHERO + " **** \n");
        try (BufferedReader reader = new BufferedReader(new FileReader(NOMBRE_FICHERO))) {
            String linea = reader.readLine();
            String[] argumentos = linea.split(" ");

            WIDTH_MIN = Integer.parseInt(argumentos[0]);
            WIDTH_MAX = Integer.parseInt(argumentos[1]);
            HEIGHT_MIN = Integer.parseInt(argumentos[2]);
            HEIGHT_MAX = Integer.parseInt(argumentos[3]);
            MIN_QUESOS = Integer.parseInt(argumentos[4]);
            MAX_QUESOS = Integer.parseInt(argumentos[5]);
            DURACION = Integer.parseInt(argumentos[6]);

            myGui.presentarSalida("WIDTH_MIN: " + argumentos[0]);
            myGui.presentarSalida("WIDTH_MAX: " + argumentos[1]);
            myGui.presentarSalida("HEIGHT_MIN: " + argumentos[2]);
            myGui.presentarSalida("HEIGHT_MAX: " + argumentos[3]);
            myGui.presentarSalida("MIN_QUESOS: " + argumentos[4]);
            myGui.presentarSalida("MAX_QUESOS: " + argumentos[5]);
            myGui.presentarSalida("DURACION: " + argumentos[6]);

            // Utiliza las variables ANCHO, ALTO, NUM_QUESOS y TIEMPO_PARTIDA como sea necesario
        } catch (IOException ex) {
            System.err.println("Error al leer el fichero de configuración: " + ex.getMessage());
            throw new Exception();
        }
        //}
    }

    @Override
    protected void takeDown() {
        //Desregistro de las Páginas Amarillas
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        //Se liberan los recuros y se despide
        myGui.dispose();
        System.out.println("Finaliza la ejecución de " + this.getName());
        MicroRuntime.stopJADE();
    }

    //Métodos de trabajo del agente
    //Clases internas que representan las tareas del agente
}
