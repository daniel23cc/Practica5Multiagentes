package es.ujaen.ssmmaa.ontomouserun.elementos;


import es.ujaen.ssmmaa.ontomouserun.Vocabulario.DificultadJuego;
import jade.content.AgentAction;
import jade.content.onto.annotations.Slot;


public class ProponerJuego implements AgentAction {
    private Juego juego;
    private DificultadJuego dificultadJuego;

    public ProponerJuego() {
        this.juego = null;
        this.dificultadJuego = null;
    }
    public ProponerJuego(Juego juego, DificultadJuego dificultadJuego) {
        this.juego = juego;
        this.dificultadJuego = dificultadJuego;
    }

    @Slot(mandatory=true)
    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    @Slot(mandatory=true)
    public DificultadJuego getDificultadJuego() {
        return dificultadJuego;
    }

    public void setDificultadJuego(DificultadJuego dificultadJuego) {
        this.dificultadJuego = dificultadJuego;
    }

    @Override
    public String toString() {
        return "ProponerJuego{" +
                "juego=" + juego +
                ", dificultadJuego=" + dificultadJuego +
                '}';
    }
}
