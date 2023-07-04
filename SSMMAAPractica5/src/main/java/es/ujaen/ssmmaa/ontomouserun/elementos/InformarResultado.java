package es.ujaen.ssmmaa.ontomouserun.elementos;


import jade.content.AgentAction;
import jade.content.onto.annotations.Slot;

public class InformarResultado implements AgentAction {
    private AgenteJuego agenteJuego;

    public InformarResultado() {
        this.agenteJuego = null;
    }

    public InformarResultado(AgenteJuego agenteJuego) {
        this.agenteJuego = agenteJuego;
    }

    @Slot(mandatory=true)
    public AgenteJuego getAgente() {
        return agenteJuego;
    }

    public void setAgente(AgenteJuego agenteJuego) {
        this.agenteJuego = agenteJuego;
    }

    @Override
    public String toString() {
        return "InformarResultado{" +
                "agenteJuego=" + agenteJuego +
                '}';
    }
}
