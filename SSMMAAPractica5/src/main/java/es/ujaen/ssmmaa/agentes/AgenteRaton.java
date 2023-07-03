/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.ssmmaa.agentes;


import es.ujaen.ssmmaa.ontomouserun.OntoMouseRun;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.NombreServicio.JUGADOR;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.DFSubscriber;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.leap.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConstantesInterface;

/**
 *
 * @author danie
 */
public class AgenteRaton extends Agent implements ConstantesInterface {
    //Variables del agente

    //AgenteMonitorJFrame myGui;
    // private String nombreFichero;
    // Para trabajar con la ontología
    private final ContentManager manager = getContentManager();

    // El lenguaje utilizado por el agente para la comunicación es SL 
    private final Codec codec = new SLCodec();

    // La ontología que utilizará el agente
    private Ontology ontology;

    @Override
    protected void setup() {
        //Inicialización de las variables del agente

        try {
            //Inicialización de las variables del agente

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

            //No necesito buscar ningun agente, raton siempre tiene rol de participante
            //añado tareas principales
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

}
