/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.ujaen.ssmmaa.agentes;

import es.ujaen.ssmmaa.gui.AgenteLaberintoJFrame;
import es.ujaen.ssmmaa.ontomouserun.OntoMouseRun;
import es.ujaen.ssmmaa.ontomouserun.Vocabulario;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.NOMBRE_SERVICIOS;
import es.ujaen.ssmmaa.ontomouserun.Vocabulario.NombreServicio;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.NombreServicio.ORGANIZADOR;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.TIPO_SERVICIO;
import es.ujaen.ssmmaa.ontomouserun.elementos.Juego;
import es.ujaen.ssmmaa.ontomouserun.elementos.JuegoAceptado;
import es.ujaen.ssmmaa.ontomouserun.elementos.Justificacion;
import es.ujaen.ssmmaa.ontomouserun.elementos.Organizador;
import es.ujaen.ssmmaa.ontomouserun.elementos.OrganizarJuego;
import es.ujaen.ssmmaa.ontomouserun.elementos.Partida;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.MicroRuntime;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConstantesInterface;
import static utils.ConstantesInterface.NUM_MAX_JUEGOS_LABERINTO;

/**
 *
 * @author javierarandaizquierdo
 */
public class AgenteLaberinto extends Agent implements ConstantesInterface {

    private AgenteLaberintoJFrame myGui;

    private TareaOrganizarJuegoLaberinto tareaOrganizarJuego;
    private Ontology ontology;

    private final ContentManager manager = (ContentManager) getContentManager();
    // El lenguaje utilizado por el agente para la comunicación es SL 
    private final Codec codec = new SLCodec();

    private ArrayList<AID>[] listaAgentes;

    List<Partida> lista_partidas;

    private int numero_juegos_organizados;

    private Organizador organizador;

    private Agent myAgent;

    @Override
    protected void setup() {

        try {

            myGui = new AgenteLaberintoJFrame(this);
            myGui.setVisible(true);

            lista_partidas = new ArrayList<>();

            // Regisro de la Ontología
            ontology = OntoMouseRun.getInstance();

            manager.registerLanguage(codec);
            manager.registerOntology(ontology);

            numero_juegos_organizados = 0;

            myAgent = this;

            listaAgentes = new ArrayList[NOMBRE_SERVICIOS.length];
            for (NombreServicio categoria : NOMBRE_SERVICIOS) {
                listaAgentes[categoria.ordinal()] = new ArrayList<>();
            }

            organizador = new Organizador(this.getLocalName(), this.getAID());

            //Registro ESTE agente en las Páginas Amarrillas
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setType(TIPO_SERVICIO);
            sd.setName(ORGANIZADOR.name());
            dfd.addServices(sd);

            try {
                DFService.register(this, dfd);
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }

            myGui.presentarSalida("\tSe inicia la ejecución del agente: " + this.getName());

            //Llamaos a la tarea para  localizar a los demas agente en las paginas amarillas
            //Para localiar a los agente cocina y cliente
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription templateSd = new ServiceDescription();
            templateSd.setType(TIPO_SERVICIO);
            template.addServices(templateSd);
            addBehaviour(new TareaSuscripcionDF(this, template));

            //Llamamos a la clase que recibe el Propose del agente monitor
            addBehaviour(new llamada_Propose(myAgent, 2000));

        } catch (Exception ex) {
            Logger.getLogger(AgenteLaberinto.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        myGui.dispose();
        //Despedida
        System.out.println("Finaliza la ejecución del agente: " + this.getName());
    }

    /**
     * Tarea de suscripcion
     */
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

    public class llamada_Propose extends WakerBehaviour {

        public llamada_Propose(Agent a, long timeout) {
            super(a, timeout);
        }

        @Override
        protected void onWake() {

            MessageTemplate mt = MessageTemplate.and(
                    MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE),
                    MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
            //Añado al tarea para recibir los juegos desde el Monitor
            addBehaviour(new TareaOrganizarJuegoLaberinto(myAgent, mt));

        }

    }

    /**
     *
     *
     * Esta tarea responde al PROPOSE por parte del monitor para organizar un
     * juego
     */
    public class TareaOrganizarJuegoLaberinto extends ProposeResponder {

        private Juego juego;
        private OrganizarJuego organizarJuego;

        public TareaOrganizarJuegoLaberinto(Agent a, MessageTemplate mt) {
            super(a, mt);
            myGui.presentarSalida("Se responde al propose");
        }

        @Override
        protected ACLMessage prepareResponse(ACLMessage propose) throws NotUnderstoodException, RefuseException {

            Action ac = null;
            ACLMessage respuesta = propose.createReply();
            JuegoAceptado juegoAceptado;
            Justificacion justificacion;

            myGui.presentarSalida("<---" + this.myAgent.getLocalName() + " ha recibido solicitud de Monitor");

            try {
                ac = (Action) manager.extractContent(propose);
            } catch (Codec.CodecException ex) {
                Logger.getLogger(AgenteRaton.class.getName()).log(Level.SEVERE, null, ex);
            } catch (OntologyException ex) {
                Logger.getLogger(AgenteRaton.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Obtener el objeto ProponerJuego del mensaje
            OrganizarJuego organizarJuego = (OrganizarJuego) ac.getAction();

            if (numero_juegos_organizados < NUM_MAX_JUEGOS_LABERINTO) {
                try {
                    myGui.presentarSalida(myAgent.getLocalName() + " acepta la propuesta de organizar el juego: " + organizarJuego.getJuego().getIdJuego());
                    juegoAceptado = new JuegoAceptado();
                    juegoAceptado.setAgenteJuego(organizador);
                    juegoAceptado.setJuego(organizarJuego.getJuego());

                    respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    manager.fillContent(respuesta, juegoAceptado);

                    addBehaviour(new TareaGenerarPartidas(organizarJuego));
                    numero_juegos_organizados++;
                } catch (Codec.CodecException ex) {
                    Logger.getLogger(AgenteLaberinto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(AgenteLaberinto.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //No se ha podido organizar el juego
                justificacion = new Justificacion(organizarJuego.getJuego(), Vocabulario.Motivo.PARTICIPACION_EN_JUEGOS_SUPERADA);

                respuesta.setPerformative(ACLMessage.REJECT_PROPOSAL);
                try {
                    manager.fillContent(respuesta, justificacion);
                } catch (Codec.CodecException ex) {
                    Logger.getLogger(AgenteLaberinto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(AgenteLaberinto.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            return respuesta;
        }
    }

    /**
     * Tarea que crea las partidas
     */
    class TareaGenerarPartidas extends OneShotBehaviour {

        OrganizarJuego organizarJuego;

        public TareaGenerarPartidas(OrganizarJuego organizarJuego) {

            this.organizarJuego = organizarJuego;

        }

        @Override
        public void action() {

//            Object[] arrAux = new Object[1];
//            arrAux[0] = organizarJuego;
            try {
                //CREAMOS EL AGENTE PARTIDA Y LE PASAMOS LOS PRARMETROS
                MicroRuntime.startAgent("Partida1", "es.ujaen.ssmmaa.agentes.AgentePartida", null);
            } catch (Exception ex) {
                Logger.getLogger(AgenteLaberinto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
