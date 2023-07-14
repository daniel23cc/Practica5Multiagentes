/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.ujaen.ssmmaa.agentes;

import es.ujaen.ssmmaa.gui.AgentePartidaJFrame;
import es.ujaen.ssmmaa.ontomouserun.OntoMouseRun;
import static es.ujaen.ssmmaa.ontomouserun.Vocabulario.TIPO_SERVICIO;
import es.ujaen.ssmmaa.ontomouserun.elementos.InciarPartida;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.leap.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mouserun.GameStarter;
import static utils.Constantes.WIDTH_MAX;
import static utils.Constantes.WIDTH_MIN;
import utils.ConstantesInterface;

/**
 *
 * @author javierarandaizquierdo
 */
public class AgentePartida extends Agent implements ConstantesInterface {

    private AgenteLaberinto.TareaOrganizarJuegoLaberinto tareaOrganizarJuego;
    private Ontology ontology;

    private final ContentManager manager = (ContentManager) getContentManager();
    // El lenguaje utilizado por el agente para la comunicación es SL 
    private final Codec codec = new SLCodec();

    InciarPartida iniciarPartida;

   // private AgentePartidaJFrame myGui;

    @Override
    protected void setup() {
        //Asociamos un JFrame al agente y lo hacemos visible
//        myGui = new AgentePartidaJFrame(this);
//        myGui.setVisible(true);

        try {
            //Registro de la Ontología
            ontology = OntoMouseRun.getInstance();
        } catch (BeanOntologyException ex) {
            Logger.getLogger(AgentePartida.class.getName()).log(Level.SEVERE, null, ex);
        }

        manager.registerLanguage(codec);
        manager.registerOntology(ontology);

        //Registro del agente en las Páginas Amarrillas
        DFAgentDescription template = new DFAgentDescription();
        template.setName(getAID());
        ServiceDescription templateSd = new ServiceDescription();
        templateSd.setType(TIPO_SERVICIO);
        templateSd.setName("Partida");
        template.addServices(templateSd);
        try {
            DFService.register(this, template);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }


        //----------- FIN REGISTRO --------------------------------------------

        GameStarter.main();

        System.out.println("\tSe inicia la ejecución del agente: " + this.getName());

    }

    @Override
    protected void takeDown() {
        //Eliminar registro del agente en las Páginas Amarillas
        //Desregistro de las Páginas Amarillas
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        //Liberación de recursos, incluido el GUI
        //myGui.dispose();

        //Despedida
        System.out.println("Finaliza la ejecución del agente: " + this.getName());
    }

}
