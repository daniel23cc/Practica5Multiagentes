/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareas;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

/**
 *
 * @author danie
 */
public class TareaOrganizarJugadoresJuegosIniciador extends ContractNetInitiator {
    
    public TareaOrganizarJugadoresJuegosIniciador(Agent a, ACLMessage cfp) {
        super(a, cfp);
    }
    
}
