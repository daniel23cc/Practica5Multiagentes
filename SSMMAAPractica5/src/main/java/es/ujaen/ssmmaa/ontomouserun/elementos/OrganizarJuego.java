package es.ujaen.ssmmaa.ontomouserun.elementos;



import es.ujaen.ssmmaa.ontomouserun.Vocabulario.DificultadJuego;
import jade.content.AgentAction;
import jade.content.onto.annotations.AggregateSlot;
import jade.content.onto.annotations.Slot;
import jade.util.leap.List;

public class OrganizarJuego implements AgentAction {
    private Juego juego;
    private DificultadJuego dificultadJuego;
    private List listaJugadores;

    public OrganizarJuego() {
        this.juego = null;
        this.dificultadJuego = null;
        this.listaJugadores = null;
    }

    public OrganizarJuego(Juego juego, DificultadJuego dificultadJuego, List listaJugadores) {
        this.juego = juego;
        this.dificultadJuego = dificultadJuego;
        this.listaJugadores = listaJugadores;
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

    @AggregateSlot(cardMin=1, type=Jugador.class)
    public List getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(List listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    @Override
    public String toString() {
        return "OrganizarJuego{" +
                "juego=" + juego +
                ", dificultadJuego=" + dificultadJuego +
                ", listaJugadores=" + listaJugadores +
                '}';
    }
}
