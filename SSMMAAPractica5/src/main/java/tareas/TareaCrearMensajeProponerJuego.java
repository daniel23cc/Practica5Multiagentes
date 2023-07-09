/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareas;

import jade.core.behaviours.OneShotBehaviour;

/**
 *
 * @author danie
 */
//public class TareaCrearMensajeProponerJuego extends OneShotBehaviour{
//
//    @Override
//    public void action() {
//        ACLMessage reply;
//    }
//    
//    
//    
//    
//}

//
//reply = accept.createReply();
//            for (int i = 0; i < numPlatosServicio; i++) {
//                //inicio protocolo propose con la cocina
//                if (!platosPedidos.isEmpty()) {
//                    ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
//                    msg.setProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE);
//                    msg.setSender(getAID());
//                    msg.setContent(platosPedidos.remove(PRIMERO).toString());
//                    if (listaAgentes[COCINA.ordinal()].size() > 0) {
//                        java.util.Iterator<AID> it = listaAgentes[COCINA.ordinal()].iterator();
//                        int cocinaAenviar;
//                        int numAgentes = listaAgentes[COCINA.ordinal()].size();
//                        myGui.presentarSalida("Agentes cocina encontrados:" + numAgentes);
//                        if (numAgentes <= contCocinas) {
//                            contCocinas = 0;
//                        }
//
//                        //aqui aplico un algoritmo donde reparto la presion selectiva, priorizaré el 90% de las veces repartir aleatoriamente los pedidos entre las cocinas
//                        //sin embargo, dare oportunidad a cambiar la politica de reparto en caso de que las cocinas esten sobresaturadas
//                        if (aleatorio.nextInt(D100) > 80) {
//                            cocinaAenviar = contCocinas;
//                        } else {
//                            cocinaAenviar = aleatorio.nextInt(numAgentes);
//                        }
//
//                        msg.addReceiver(listaAgentes[COCINA.ordinal()].get(cocinaAenviar));
//                        //solicito a la cocina cocinar el plato
//                        addBehaviour(new TareaSolicitarCocinado(myAgent, msg));
//                    }
//                }
//            }
//
//            return null;
//        }
//
//    }
//
//    public class TareaSolicitarCocinado extends ProposeInitiator {
//
//        public TareaSolicitarCocinado(Agent a, ACLMessage initiation) {
//            super(a, initiation);
//            myGui.presentarSalida("Se va enviar a la cocina: " + initiation.getContent());
//        }
//
//        @Override
//        protected void handleAcceptProposal(ACLMessage accept_proposal) {
//            myGui.presentarSalida("<--- Restaurante ha recibido cocinado el plato: " + accept_proposal.getContent());
//            platosCocinados.add(Plato.valueOf(accept_proposal.getContent()));
//
//            // Se han recibido platos cocinados, realizar la lógica correspondiente
//            platosAentregar.add(platosCocinados.remove(PRIMERO));
//            myGui.presentarSalida("--->  Preparando en la bandeja de: " + platosAentregar.get(platosAentregar.size() - 1).getAIDcliente().getName() + " plato: " + platosAentregar.get(platosAentregar.size() - 1).toString());
//            //myGui.presentarSalida("pla Entr: "+platosAentregar.size()+", numPlServ: "+numPlatosServicio);
//            if (platosAentregar.size() == numPlatosServicio) {
//
//                // Crear estructura del mensaje
//                inform = reply;
//                inform.setPerformative(ACLMessage.INFORM);
//                inform.setContent(platosAentregar.toString());
//                // Enviar el plato al cliente
//                inform.addReceiver(platosAentregar.get(PRIMERO).getAIDcliente());
//                // Incrementar contador de servicios
//                numServicios++;
//                // Finalizar el comportamiento
//                numPlatosServicio = 0;
//                platosAentregar.clear();
//                numComensales--;
//                send(inform);
//            }
//
//        }