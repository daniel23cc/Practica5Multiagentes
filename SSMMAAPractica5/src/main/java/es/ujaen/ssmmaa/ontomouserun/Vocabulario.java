package es.ujaen.ssmmaa.ontomouserun;

import java.util.Random;

public interface Vocabulario {
    // Generador Aleatorio
    Random aleatorio = new Random();
    // Registro en las páginas Amarillas para los agentes que representan a los ratones y laberintos
    public static final String TIPO_SERVICIO = "Mouse Run";
    enum NombreServicio {
        JUGADOR, ORGANIZADOR
    }
    NombreServicio[] NOMBRE_SERVICIOS = NombreServicio.values();

    enum ModoJuego {
        BUSQUEDA, TORNEO, ELIMINATORIA
    }
    ModoJuego[] MODOS_JUEGOS = ModoJuego.values();

    enum DificultadLaberinto {
        FACIL(1,3,5,10),
        NORMAL(2,4,10,20),
        DIFICIL(4,8,30,40);

        private int minQuesos;
        private int maxQuesos;
        private int minDimLaberinto;
        private int maxDimLaberinto;

        DificultadLaberinto(int minQuesos, int maxQuesos, int minDimLaberinto, int maxDimLaberinto) {
            this.minQuesos = minQuesos;
            this.maxQuesos = maxQuesos;
            this.minDimLaberinto = minDimLaberinto;
            this.maxDimLaberinto = maxDimLaberinto;
        }

        /**
         * Obtenemos un valor aleatorio de quesos para una dificuldad del laberinto
         * @return número de quesos
         */
        public int getQuesos() {
            return aleatorio.nextInt(minQuesos,maxQuesos+1);
        }

        /**
         * Obtenemos un valor aleatorio para las filas del laberinto atendiendo a su dificultad
         * @return número de filas del laberinto
         */
        public int getFilas() {
            return aleatorio.nextInt(minDimLaberinto,maxDimLaberinto+1);
        }

        /**
         * Obtenemos un valor aleatorio para las columnas del laberinto atendiendo a su dificultad
         * @return número de columnas del laberinto
         */
        public int getColumnas() {
            return aleatorio.nextInt(minDimLaberinto,maxDimLaberinto+1);
        }
    }
    DificultadLaberinto[] DIFICULTAD_LABERINTOS = DificultadLaberinto.values();

    enum DificultadJuego {
        BUSQUEDA(1,1), FACIL(2,4), NORMAL(4,8), DIFICIL(8,12);

        private int minRatones;
        private int maxRatones;

        DificultadJuego(int minRatones, int maxRatones) {
            this.minRatones = minRatones;
            this.maxRatones = maxRatones;
        }

        /**
         * Un número de ratones para un juego atendiendo a su dificultad
         * @return número de ratones
         */
        public int getRatones() {
            return aleatorio.nextInt(minRatones,maxRatones+1);
        }
    }

    enum Incidencia {
        CANCELADO, JUGADORES_INSUFICIENTES,
        ERROR_AGENTE, ERROR_MENSAJE_INCORRECTO, ERROR_CONTENIDO_MENSAJE,
    }

    enum Motivo {
        JUEGOS_ACTIVOS_SUPERADOS, PARTICIPACION_EN_JUEGOS_SUPERADA,
        TIPO_JUEGO_NO_IMPLEMENTADO, DEMASIADOS_JUEGOS_SIN_COMPLETAR, SUPERADO_LIMITE_PARTIDAS,
        SUBSCRIPCION_ACEPTADA, ERROR_SUBSCRIPCION, ERROR_CANCELACION, ONTOLOGIA_DESCONOCIDA,
        FINALIZACION_AGENTE
    }

    enum Estado {
        GANADOR, ABANDONO, SEGUIR_JUGANDO, FIN_PARTIDA, JUGADOR_NO_ACTIVO
    }

    enum Orientacion {
        NORTE, SUR, ESTE, OESTE
    }

    enum Pared {
        ABIERTO, MURO
    }

    enum Accion {
        MOVIMIENTO, BOMBA
    }
}
