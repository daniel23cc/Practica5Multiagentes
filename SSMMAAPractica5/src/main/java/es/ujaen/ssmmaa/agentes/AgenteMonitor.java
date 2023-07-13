/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.ssmmaa.agentes;

import es.ujaen.ssmmaa.gui.AgenteMonitorJFrame;
import es.ujaen.ssmmaa.ontomouserun.OntoMouseRun;

import es.ujaen.ssmmaa.ontomouserun.Vocabulario.DificultadJuego;
import es.ujaen.ssmmaa.ontomouserun.Vocabulario.ModoJuego;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.NOMBRE_SERVICIOS;
import es.ujaen.ssmmaa.ontomouserun.Vocabulario.NombreServicio;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.NombreServicio.JUGADOR;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.NombreServicio.ORGANIZADOR;
import es.ujaen.ssmmaa.ontomouserun.elementos.Juego;
import es.ujaen.ssmmaa.ontomouserun.elementos.JuegoAceptado;
import es.ujaen.ssmmaa.ontomouserun.elementos.Jugador;
import es.ujaen.ssmmaa.ontomouserun.elementos.Justificacion;
import es.ujaen.ssmmaa.ontomouserun.elementos.OrganizarJuego;
import es.ujaen.ssmmaa.ontomouserun.elementos.ProponerJuego;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;

import jade.core.Agent;
import jade.core.MicroRuntime;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.DFSubscriber;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ProposeInitiator;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import tareas.TareaCrearAgentesMonitor;
import tareas.TareaProponerJuegoInitiator;
import static utils.Constantes.*;
import static utils.ConstantesInterface.TIPO_SERVICIO;

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

    private final ContentManager manager = (ContentManager) getContentManager();

    // El lenguaje utilizado por el agente para la comunicación es SL 
    private final Codec codec = new SLCodec();

    // La ontología que utilizará el agente
    private Ontology ontology;

    private ArrayList<AID>[] listaAgentes;

    private Map<Juego, List> ratonesEnJuego;

    private int idJuego;

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
            ratonesEnJuego = new HashMap<>();
            idJuego = 0;
            //Configuración del GUI
            myGui = new AgenteMonitorJFrame(this);
            myGui.setVisible(true);
            myGui.presentarSalida("Se inicializa la ejecución de " + this.getName() + "\n");

            //Registro de la Ontología
          
                ontology = OntoMouseRun.getInstance();
        
            manager.registerLanguage(codec);
            manager.registerOntology(ontology);

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

//            leerArchivo();
//            //Busco agentes jugadores
//            // Se añaden las tareas principales
//            DFAgentDescription template2 = new DFAgentDescription();
//            ServiceDescription templateSd2 = new ServiceDescription();
//            templateSd2.setType(TIPO_SERVICIO);
//            templateSd2.setName(JUGADOR.name());
//            template2.addServices(templateSd2);
//
//            addBehaviour(new TareaSuscripcionDF(this, template2));
//            addBehaviour(new TareaCrearMensajeProponerJuego());
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

            NUM_JUEGOS = Integer.parseInt(argumentos[0]);
            NUM_PARTIDAS_JUEGO = Integer.parseInt(argumentos[1]);
            WIDTH_MIN = Integer.parseInt(argumentos[2]);
            WIDTH_MAX = Integer.parseInt(argumentos[3]);
            HEIGHT_MIN = Integer.parseInt(argumentos[4]);
            HEIGHT_MAX = Integer.parseInt(argumentos[5]);
            MIN_QUESOS = Integer.parseInt(argumentos[6]);
            MAX_QUESOS = Integer.parseInt(argumentos[7]);
            DURACION = Integer.parseInt(argumentos[8]);

            myGui.presentarSalida("NUM_JUEGOS: " + argumentos[0]);
            myGui.presentarSalida("NUM_PARTIDAS_JUEGO: " + argumentos[1]);
            myGui.presentarSalida("WIDTH_MIN: " + argumentos[2]);
            myGui.presentarSalida("WIDTH_MAX: " + argumentos[3]);
            myGui.presentarSalida("HEIGHT_MIN: " + argumentos[4]);
            myGui.presentarSalida("HEIGHT_MAX: " + argumentos[5]);
            myGui.presentarSalida("MIN_QUESOS: " + argumentos[6]);
            myGui.presentarSalida("MAX_QUESOS: " + argumentos[7]);
            myGui.presentarSalida("DURACION: " + argumentos[8]);

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
                    NUM_RATONES++;
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

    public class TareaCrearMensajeProponerJuego extends OneShotBehaviour {

        @Override
        public void action() {
            //To change body of generated methods, choose Tools | Templates.
            ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
            msg.setProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE);
            msg.setSender(getAID());
            msg.setLanguage(codec.getName());
            msg.setOntology(ontology.getName());
            Juego nJuego = new Juego(String.valueOf(idJuego++), ModoJuego.BUSQUEDA);
            ProponerJuego nuevoJuego = new ProponerJuego(nJuego, DificultadJuego.BUSQUEDA);
            // Añadimos el contenido del mensaje
            Action action = new Action(myAgent.getAID(), nuevoJuego);
            try {
                manager.fillContent(msg, action);
            } catch (Codec.CodecException | OntologyException ex) {
                Logger.getLogger(AgenteMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }

            //indico los que van a recibir
            if (listaAgentes[JUGADOR.ordinal()].size() > 0) {
                for (AID jugador : listaAgentes[JUGADOR.ordinal()]) {
                    msg.addReceiver(jugador);
                }
            }

            addBehaviour(new TareaProponerJuegoInitiator(myAgent, msg));

        }

    }

    public class TareaProponerJuegoInitiator extends ProposeInitiator {

        private JuegoAceptado juegoAceptado;
        private Justificacion justificcion;

        public TareaProponerJuegoInitiator(Agent a, ACLMessage msg) {
            super(a, msg);
            myGui.presentarSalida("Se va a proponer un juego: " + msg.getContent());
        }

        @Override
        protected void handleAllResponses(Vector responses) {
            super.handleAllResponses(responses); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        protected void handleAcceptProposal(ACLMessage accept_proposal) {
            try {
                this.juegoAceptado = (JuegoAceptado) manager.extractContent(accept_proposal);
                ratonesEnJuego.get(juegoAceptado.getJuego()).add(new Jugador(accept_proposal.getSender().getName(), accept_proposal.getSender()));
                //addBehaviour(new TareaEnviarInforme(myAgent));

                if (NUM_RATONES == ratonesEnJuego.get(juegoAceptado.getJuego()).size()) {
                    ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
                    msg.setProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE);
                    msg.setSender(getAID());
                    msg.setLanguage(codec.getName());
                    msg.setOntology(ontology.getName());
                    OrganizarJuego nuevoOrganizarJuego = new OrganizarJuego(juegoAceptado.getJuego(), DificultadJuego.BUSQUEDA, ratonesEnJuego.get(juegoAceptado.getJuego()));

                    // Añadimos el contenido del mensaje
                    try {
                        Action action = new Action(myAgent.getAID(), nuevoOrganizarJuego);
                        manager.fillContent(msg, action);
                    } catch (Codec.CodecException | OntologyException ex) {
                        Logger.getLogger(AgenteMonitor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    addBehaviour(new TareaOrganizarJuegoInitiator(myAgent, msg));
                }
            } catch (CodecException ex) {
                try {
                    throw new NotUnderstoodException("No se puede decodificar el mensaje");
                } catch (NotUnderstoodException ex1) {
                    Logger.getLogger(AgenteMonitor.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (OntologyException ex) {
                Logger.getLogger(AgenteMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        protected void handleRejectProposal(ACLMessage reject_proposal) {
            try {
                this.justificcion = (Justificacion) manager.extractContent(reject_proposal);
                //addBehaviour(new TareaEnviarInforme(myAgent));
            } catch (CodecException ex) {
                try {
                    throw new NotUnderstoodException("No se puede decodificar el mensaje");
                } catch (NotUnderstoodException ex1) {
                    Logger.getLogger(AgenteMonitor.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (OntologyException ex) {
                Logger.getLogger(AgenteMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public class TareaOrganizarJuegoInitiator extends ProposeInitiator {

        public TareaOrganizarJuegoInitiator(Agent a, ACLMessage msg) {
            super(a, msg);
            myGui.presentarSalida("Se va a organizar un juego: " + msg.getContent());
        }

    }

//    public class TareaEnviarInforme extends OneShotBehaviour {
//
//        private Juegi
//        public TareaEnviarInforme(Agent a) {
//            super(a);
//        }
//
//        @Override
//        public void action() {
//            //To change body of generated methods, choose Tools | Templates.
//            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
//            msg.setSender(getAID());
//            msg.setLanguage(codec.getName());
//            msg.setOntology(ontology.getName());
//            Juego nJuego = new Juego(String.valueOf(idJuego++), ModoJuego.BUSQUEDA);
//            ProponerJuego nuevoJuego = new ProponerJuego(nJuego, DificultadJuego.BUSQUEDA);
//            // Añadimos el contenido del mensaje
//            try {
//                Action action = new Action(myAgent.getAID(), nuevoJuego);
//                manager.fillContent(msg, action);
//            } catch (Codec.CodecException | OntologyException ex) {
//                Logger.getLogger(AgenteMonitor.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            //indico los que van a recibir
//            if (listaAgentes[JUGADOR.ordinal()].size() > 0) {
//                for (AID jugador : listaAgentes[JUGADOR.ordinal()]) {
//                    msg.addReceiver(jugador);
//                }
//            }
//
//            addBehaviour(new TareaProponerJuegoInitiator(myAgent, msg));
//        }
//
//    }
    public class TareaSuscripcionDF extends DFSubscriber {

        public TareaSuscripcionDF(Agent a, DFAgentDescription template) {
            super(a, template);
        }

        @Override
        public void onRegister(DFAgentDescription dfad) {
            Iterator it = dfad.getAllServices();
            while (it.hasNext()) {
                ServiceDescription sd = (ServiceDescription) it.next();

                for (NombreServicio nombreServicio : NOMBRE_SERVICIOS) {
                    if (sd.getName().equals(nombreServicio.name())) {
                        listaAgentes[nombreServicio.ordinal()].add(dfad.getName());
                    }
                }
            }

            myGui.presentarSalida("El agente: " + myAgent.getName()
                    + "ha encontrado a:\n\t" + dfad.getName());
        }

        @Override
        public void onDeregister(DFAgentDescription dfad) {
            AID agente = dfad.getName();

            for (NombreServicio servicio : NOMBRE_SERVICIOS) {
                if (listaAgentes[servicio.ordinal()].remove(agente)) {
                    myGui.presentarSalida("El agente: " + agente.getName()
                            + " ha sido eliminado de la lista de "
                            + myAgent.getName());
                }
            }
        }
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
