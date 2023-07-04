package es.ujaen.ssmmaa.ontomouserun.elementos;


import es.ujaen.ssmmaa.ontomouserun.Vocabulario.ModoJuego;
import jade.content.Concept;
import jade.content.onto.annotations.Slot;

/**
 *
 * @author pedroj
 */
public class Juego implements Concept {
    private String idJuego;
    private ModoJuego modoJuego;

    public Juego() {
        this.idJuego = null;
        this.modoJuego = null;
    }

    public Juego(String idJuego, ModoJuego modoJuego) {
        this.idJuego = idJuego;
        this.modoJuego = modoJuego;
    }

    @Slot(mandatory=true)
    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
    }

    @Slot(mandatory=true)
    public ModoJuego getModoJuego() {
        return modoJuego;
    }

    public void setModoJuego(ModoJuego modoJuego) {
        this.modoJuego = modoJuego;
    }

    @Override
    public String toString() {
        return "Juego{" +
                "idJuego='" + idJuego + '\'' +
                ", modoJuego=" + modoJuego +
                '}';
    }
}
