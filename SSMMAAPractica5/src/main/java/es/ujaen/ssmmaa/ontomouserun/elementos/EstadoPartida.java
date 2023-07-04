package es.ujaen.ssmmaa.ontomouserun.elementos;




import es.ujaen.ssmmaa.ontomouserun.Vocabulario.Estado;
import jade.content.Predicate;
import jade.content.onto.annotations.Slot;

public class EstadoPartida implements Predicate {
    private Partida partida;
    private Estado estado;

    public EstadoPartida() {
        this.partida = null;
        this.estado = null;
    }

    public EstadoPartida(Partida partida, Estado estado) {
        this.partida = partida;
        this.estado = estado;
    }

    @Slot(mandatory=true)
    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    @Slot(mandatory=true)
    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "EstadoPartida{" +
                "partida=" + partida +
                ", estado=" + estado +
                '}';
    }
}
