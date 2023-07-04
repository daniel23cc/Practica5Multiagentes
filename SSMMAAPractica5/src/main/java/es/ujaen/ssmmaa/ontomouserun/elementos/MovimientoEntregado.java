package es.ujaen.ssmmaa.ontomouserun.elementos;

import jade.content.Predicate;
import jade.content.onto.annotations.Slot;

public class MovimientoEntregado implements Predicate {
    private Partida partida;
    private Movimiento movimiento;

    public MovimientoEntregado() {
        this.partida = null;
        this.movimiento = null;
    }

    public MovimientoEntregado(Partida partida, Movimiento movimiento) {
        this.partida = partida;
        this.movimiento = movimiento;
    }

    @Slot(mandatory=true)
    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    @Slot(mandatory=true)
    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    @Override
    public String toString() {
        return "MovimientoEntregado{" +
                "partida=" + partida +
                ", movimiento=" + movimiento +
                '}';
    }
}
