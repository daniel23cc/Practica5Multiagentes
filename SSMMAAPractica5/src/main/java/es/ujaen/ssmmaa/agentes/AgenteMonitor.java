/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.ssmmaa.agentes;

import es.ujaen.ssmmaa.gui.AgenteMonitorJFrame;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.NombreServicio.ORGANIZADOR;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.TIPO_SERVICIO;
import jade.core.Agent;
import jade.core.MicroRuntime;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tareas.TareaCrearAgentesMonitor;
import static utils.Constantes.*;

/**
 *
 * @author danie
 */
public class AgenteMonitor extends Agent {

    //Variables del agente
    AgenteMonitorJFrame myGui;

    private ArrayList<String> arrayNombreAgentes;
    private ArrayList<String> arrayNombreClientes;
    private ArrayList<String> arrayClaseAgentes;
    private ArrayList<ArrayList<String>> arrayArgumentos;

    private String nombreAgente;
    private String claseAgente;

    @Override
    protected void setup() {
        //Inicialización de las variables del agente

        //Configuración del GUI
        //Registro del agente en las Páginas Amarrillas
        //Registro de la Ontología
        try {
            //Inicialización de las variables del agente
            //archivo="configuracion.txt";

            arrayNombreAgentes = new ArrayList<>();
            arrayClaseAgentes = new ArrayList<>();
            arrayArgumentos = new ArrayList<ArrayList<String>>();

            //Configuración del GUI
            myGui = new AgenteMonitorJFrame(this);
            myGui.setVisible(true);
            myGui.presentarSalida("Se inicializa la ejecución de " + this.getName() + "\n");
            //Registro del agente en las Páginas Amarrillas

            DFAgentDescription template = new DFAgentDescription();
            template.setName(getAID());
            ServiceDescription templateSd = new ServiceDescription();
            templateSd.setType(TIPO_SERVICIO);
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
            reader.readLine();
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

            for (int i = 0; i < 2; i++) {
                arrayArgumentos.add(new ArrayList());
            }

            while ((linea = reader.readLine()) != null) {
                String[] nombYclas = linea.split(":");
                nombreAgente = nombYclas[0];
                claseAgente = nombYclas[1];

                if (claseAgente.contains("Raton")) {
                    for (int i = 0; i < argumentos.length; i++) {
                        arrayArgumentos.get(0).add(argumentos[i]);
                    }
                } else {
                    arrayArgumentos.get(1).add("NOSE");
                }

                arrayNombreAgentes.add(nombreAgente);
                //System.out.println("Nombre: " + nombreAgente);
                if (!arrayClaseAgentes.contains(claseAgente)) {
                    arrayClaseAgentes.add(claseAgente);
                }
            }

            System.out.println(arrayArgumentos.get(0));

            myGui.presentarSalida("Agentes que se van a crear: \n");
            for (int i = 0; i < arrayNombreAgentes.size(); i++) {
                myGui.presentarSalida(arrayNombreAgentes.get(i) + "\n ");
            }

            myGui.presentarSalida("Clases Agentes que se van a crear: \n");
            for (int i = 0; i < arrayClaseAgentes.size(); i++) {
                myGui.presentarSalida(arrayClaseAgentes.get(i) + "\n ");
            }

            myGui.presentarSalida("\nArgumentos: \n");
            for (int i = 0; i < arrayArgumentos.size(); i++) {
                for (int j = 0; j < arrayArgumentos.get(i).size(); j++) {
                    myGui.presentarSalida(arrayArgumentos.get(i).get(j) + "  ");
                }
                myGui.presentarSalida("\n");
            }

            arrayNombreClientes = new ArrayList<>();
            for (int i = 0; i < arrayNombreAgentes.size(); i++) {
                if (arrayNombreAgentes.get(i).contains("Cliente")) {
                    arrayNombreClientes.add(arrayNombreAgentes.get(i));
                }
            }
            addBehaviour(new TareaCrearAgentesMonitor(this));
            //System.out.println("TAM: "+arrayNombreClientes.size());

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
    public ArrayList<String> getArrayNombreAgentes() {
        return arrayNombreAgentes;
    }

    public void setArrayNombreAgentes(ArrayList<String> arrayNombreAgentes) {
        this.arrayNombreAgentes = arrayNombreAgentes;
    }

    public ArrayList<String> getArrayNombreClientes() {
        return arrayNombreClientes;
    }

    public void setArrayNombreClientes(ArrayList<String> arrayNombreClientes) {
        this.arrayNombreClientes = arrayNombreClientes;
    }

    public ArrayList<String> getArrayClaseAgentes() {
        return arrayClaseAgentes;
    }

    public void setArrayClaseAgentes(ArrayList<String> arrayClaseAgentes) {
        this.arrayClaseAgentes = arrayClaseAgentes;
    }

    public ArrayList<ArrayList<String>> getArrayArgumentos() {
        return arrayArgumentos;
    }

    public void setArrayArgumentos(ArrayList<ArrayList<String>> arrayArgumentos) {
        this.arrayArgumentos = arrayArgumentos;
    }

    public AgenteMonitorJFrame getMyGui() {
        return myGui;
    }

    public void setMyGui(AgenteMonitorJFrame myGui) {
        this.myGui = myGui;
    }
}
