/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.ssmmaa.agentes;

import es.ujaen.ssmmaa.gui.AgenteMonitorJFrame;
import es.ujaen.ssmmaa.gui.AgenteRatonJFrame;
import es.ujaen.ssmmaa.ontomouserun.OntoMouseRun;
import es.ujaen.ssmmaa.ontomouserun.Vocabulario;
import es.ujaen.ssmmaa.ontomouserun.Vocabulario.DificultadJuego;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.Motivo.JUEGOS_ACTIVOS_SUPERADOS;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.Motivo.PARTICIPACION_EN_JUEGOS_SUPERADA;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.NOMBRE_SERVICIOS;
import es.ujaen.ssmmaa.ontomouserun.Vocabulario.NombreServicio;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.NombreServicio.JUGADOR;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.TIPO_SERVICIO;
import es.ujaen.ssmmaa.ontomouserun.elementos.AgenteJuego;
import es.ujaen.ssmmaa.ontomouserun.elementos.Juego;
import es.ujaen.ssmmaa.ontomouserun.elementos.JuegoAceptado;
import es.ujaen.ssmmaa.ontomouserun.elementos.Jugador;
import es.ujaen.ssmmaa.ontomouserun.elementos.Justificacion;
import es.ujaen.ssmmaa.ontomouserun.elementos.ProponerJuego;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.DFSubscriber;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ProposeResponder;
import jade.util.leap.Iterator;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConstantesInterface;
import static utils.ConstantesInterface.JUEGOS_ACTIVOS_SUPERADOSS;
import static utils.ConstantesInterface.PARTICIPACION_EN_JUEGOS_SUPERADAA;
/**
 *
 * @author danie
 */
public class AgenteRaton extends Agent {

    //Variables del agente
    private ArrayList<AID>[] listaAgentes;
    // Para trabajar con la ontología
    private final ContentManager manager = getContentManager();

    // El lenguaje utilizado por el agente para la comunicación es SL 
    private final Codec codec = new SLCodec();
    private AgenteRatonJFrame myGui;
    // La ontología que utilizará el agente
    private Ontology ontology;

    private int juegosActivos;
    private int participacionJuegosSuperada;
    private int partidasJugando;
    private int juegosSinCompletar;
    private Jugador jugador;

    @Override
    protected void setup() {
        //Inicialización de las variables del agente

        try {
            //Inicialización de las variables del agente
            myGui = new AgenteRatonJFrame(this);
            myGui.setVisible(true);
            juegosActivos = 0;
            participacionJuegosSuperada = 0;
            partidasJugando = 0;
            juegosSinCompletar = 0;
            jugador=new Jugador(this.getLocalName(), this.getAID());
            listaAgentes = new ArrayList[NOMBRE_SERVICIOS.length];
            for (NombreServicio categoria : NOMBRE_SERVICIOS) {
                listaAgentes[categoria.ordinal()] = new ArrayList<>();
            }
            //Registro del agente en las Páginas Amarrillas
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setType(TIPO_SERVICIO);
            sd.setName(JUGADOR.name());
            dfd.addServices(sd);
            try {
                DFService.register(this, dfd);
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }

            //Registro de la Ontología
            try {
                ontology = OntoMouseRun.getInstance();
            } catch (BeanOntologyException ex) {
                Logger.getLogger(AgenteRaton.class.getName()).log(Level.SEVERE, null, ex);
            }
            manager.registerLanguage(codec);
            manager.registerOntology(ontology);

            DFAgentDescription template2 = new DFAgentDescription();
            ServiceDescription templateSd2 = new ServiceDescription();
            templateSd2.setType(TIPO_SERVICIO);
            template2.addServices(templateSd2);

            addBehaviour(new TareaSuscripcionDF(this, template2));
            //No necesito buscar ningun agente, raton siempre tiene rol de participante
            //añado tareas principales
            MessageTemplate mt = MessageTemplate.and(
                    MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE),
                    MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));

            addBehaviour(new TareaResponderProponerJuego(this, mt));
        } catch (Exception ex) {
            Logger.getLogger(AgenteMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Se inicia la ejecución del agente: " + this.getName());
        //Añadir las tareas principales
    }

    @Override
    protected void takeDown() {
        //Eliminar registro del agente en las Páginas Amarillas
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        //Liberación de recursos, incluido el GUI
        //Despedida
        //myGui.dispose();
    }

    public class TareaResponderProponerJuego extends ProposeResponder {

        public TareaResponderProponerJuego(Agent a, MessageTemplate mt) {
            super(a, mt);
        }

        @Override
        protected ACLMessage prepareResponse(ACLMessage propose) throws NotUnderstoodException, RefuseException {
            myGui.presentarSalida("<---"+ this.myAgent.getLocalName()+" ha recibido solicitud de Monitor");

            Action ac = null;
            ACLMessage respuesta = propose.createReply();
            try {
                ac = (Action) manager.extractContent(propose);
            } catch (Codec.CodecException ex) {
                Logger.getLogger(AgenteRaton.class.getName()).log(Level.SEVERE, null, ex);
            } catch (OntologyException ex) {
                Logger.getLogger(AgenteRaton.class.getName()).log(Level.SEVERE, null, ex);
            }

//            ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
//            msg.setProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE);
//            msg.setSender(getAID());
//            msg.setLanguage(codec.getName());
//            msg.setOntology(ontology.getName());
            // Obtener el objeto ProponerJuego del mensaje
            ProponerJuego proponerJuego = (ProponerJuego) ac.getAction();

            // Acceder a los atributos de ProponerJuego
            Juego juego = proponerJuego.getJuego();
            DificultadJuego dificultad = proponerJuego.getDificultadJuego();

            // Implementa la lógica para evaluar la propuesta y decidir si se acepta o se rechaza
            boolean accept = evaluarPropuesta();

            // ...
            if (accept) {
                myGui.presentarSalida(myAgent.getLocalName() + " acepta la propuesta de jugar en el juego: " + proponerJuego.getJuego().getIdJuego());
                try {
                    // Rellenar el contenido del mensaje con JuegoAceptado
                    JuegoAceptado juegoAceptado = new JuegoAceptado();
                    juegoAceptado.setJuego(proponerJuego.getJuego());
                    
                    juegoAceptado.setAgenteJuego(jugador);
                    manager.fillContent(respuesta, juegoAceptado);
                    
                } catch (Codec.CodecException ex) {
                    Logger.getLogger(AgenteRaton.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(AgenteRaton.class.getName()).log(Level.SEVERE, null, ex);
                }

                respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
            } else {
                respuesta.setPerformative(ACLMessage.REJECT_PROPOSAL);
                Justificacion justificacion;
                if (juegosActivos > JUEGOS_ACTIVOS_SUPERADOSS) {
                    justificacion = new Justificacion(juego, JUEGOS_ACTIVOS_SUPERADOS);
                } else {
                    justificacion = new Justificacion(juego, PARTICIPACION_EN_JUEGOS_SUPERADA);
                }

                try {
                    // Rellenar el contenido del mensaje con Justificacion
                    manager.fillContent(respuesta, justificacion);
                } catch (Codec.CodecException ex) {
                    Logger.getLogger(AgenteRaton.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(AgenteRaton.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            myGui.presentarSalida("Enviando: " + respuesta.getContent());

            return respuesta;

        }

    }

    private boolean evaluarPropuesta() {
        return (juegosActivos < JUEGOS_ACTIVOS_SUPERADOSS && participacionJuegosSuperada < PARTICIPACION_EN_JUEGOS_SUPERADAA);
    }

    public class TareaSuscripcionDF extends DFSubscriber {

        public TareaSuscripcionDF(Agent a, DFAgentDescription template) {
            super(a, template);
        }

        @Override
        public void onRegister(DFAgentDescription dfad) {
            Iterator it = dfad.getAllServices();
            while (it.hasNext()) {
                ServiceDescription sd = (ServiceDescription) it.next();

                for (Vocabulario.NombreServicio nombreServicio : NOMBRE_SERVICIOS) {
                    if (sd.getName().equals(nombreServicio.name())) {
                        listaAgentes[nombreServicio.ordinal()].add(dfad.getName());
                    }
                }
            }

            myGui.presentarSalida("El agente: " + myAgent.getLocalName()
                    + " ha encontrado a:\n\t" + dfad.getName());
        }

        @Override
        public void onDeregister(DFAgentDescription dfad) {
            AID agente = dfad.getName();

            for (Vocabulario.NombreServicio servicio : NOMBRE_SERVICIOS) {
                if (listaAgentes[servicio.ordinal()].remove(agente)) {
                    myGui.presentarSalida("El agente: " + agente.getLocalName()
                            + " ha sido eliminado de la lista de "
                            + myAgent.getLocalName());
                }
            }
        }

    }
}
