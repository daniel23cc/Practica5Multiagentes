# Análisis y diseño Ontomouse
## Juegos de Tablero
En este documento se presenta el análisis, diseño e implementación para la ontología que se utilizarán en el desarrollo de práctica relacionada con el juego [Mouse Run](https://mouse-run.appspot.com/). El diseño de la ontología estará pensado para resolver las necesidades de comunicación de los agentes implicados en las prácticas. Estos agentes estarán diseñados para responder a los eventos necesarios para:
- Localizar a los agentes especializados:
 - Agentes organizadores que serán los encargados de crear las diferentes partidas para un juego con los ratones correspondientes. Serán los encargados de crear los agentes que completan una partida para un laberinto con la lista de ratones que se le ha suministrado.
 - Agentes jugadores para uno o varios juegos. Los agentes corresponden a los ratones que se diseñan que jueguen correctamente, es decir, no tienen la posibilidad para *hacer trampa*.
  
- Realizar las tareas necesarias para que los agentes cumplan correctamente con sus objetivos:
 - Localizar a los agentes jugadores que estén dispuestos a jugar un juego.
 - Localizar un agente organizador para que pueda completar ese juego.
 - Crear los agentes de partida necesarios para cada una de las partidas que componen un juego.
 - Completar los turnos necesarios para una partida individual entre los agentes jugadores.
 - Comunicar el resultado de las diferentes partidas para así completar el resultado del juego.
Como el diseño de la ontología estará enfocado en resolver las necesidades de comunicación entre los agentes se incluirán los diagramas UML para los diferentes protocolos de comunicación entre **agentes-FIPA** que deben implementarse para que todos los agentes puedan participar independientemente del grupo que los desarrolle.

## Análisis 
En este apartado se procederá a enumerar los agentes necesarios para el juego del ratón y el laberinto. juego involucra a múltiples agentes, cada uno de los cuales tiene un rol y responsabilidades específicas. El juego es coordinado por un agente, llamado **AgenteMonitor**, que interactúa con otros agentes, como **AgenteRatón**, **AgentePartida** y **AgenteLaberinto**, para organizar y ejecutar los juegos. La ontología que utilizamos para la comunicación entre los agentes está bien definida, lo que facilita las interacciones y la cooperación entre ellos.

### Agentes necesarios
1.  **AgenteMonitor**: Este agente coordina la realización de todos los juegos posibles y la presentación de los resultados de esos juegos. Se encarga de leer la configuración del juego, crear agentes especializados (como AgenteRatón y AgenteLaberinto), organizar y coordinar los juegos y recoger y almacenar los resultados de los juegos.
    
2.  **AgenteRatón**: Este agente representa al jugador en los juegos. Sus responsabilidades incluyen aceptar la propuesta de un juego del AgenteMonitor y realizar movimientos en el laberinto durante un juego.
    
3.  **AgenteLaberinto**: Este agente organiza las partidas del juego. Sus tareas incluyen aceptar la organización de un juego propuesto por el AgenteMonitor, generar partidas para el juego  que está organizando, crear un AgentePartida para cada partida y obtener y guardar los resultados de las partidas.
    
4.  **AgentePartida**: Este agente es responsable de organizar una partida individual. Sus tareas incluyen crear el laberinto con los parámetros de configuración, organizar los turnos de juego, visualizar el resultado de la partida y comunicar el resultado de la partida al AgenteLaberinto.


#### Agente Monitor

El AgenteMonitor trabaja con otros agentes especializados, como los AgentesRatón y los AgentesLaberinto, para llevar a cabo su trabajo. Su comportamiento se guía en gran medida por una configuración del juego, que se le proporciona al inicio y que puede incluir información sobre qué AgentesRatón y AgentesLaberinto están disponibles para jugar, y qué juegos deben realizarse.

#### Tareas principales del AgenteMonitor

-  **Organizar jugadores y juegos**: El AgenteMonitor utiliza la configuración del juego para determinar cuántos y cuáles AgentesRatón y AgentesLaberinto deben participar en cada juego. Luego, envía solicitudes a estos agentes para organizar los juegos. Esta tarea puede incluir la organización de varios juegos simultáneos, así como la realización de juegos de demostración o competitivos según las necesidades.

```mermaid
sequenceDiagram
    participant AgenteMonitor
    participant AgenteRaton
    participant AgenteLaberinto

    loop Organizar Juego
        AgenteMonitor->>AgenteRaton: CFP(Participar)
        alt Acepta Participar
            AgenteRaton-->>AgenteMonitor: Agree(Participar)
            AgenteMonitor->>AgenteLaberinto: CFP(Organizar)
            alt Acepta Organizar
                AgenteLaberinto-->>AgenteMonitor: Agree(Organizar)
                AgenteMonitor->>AgenteLaberinto: Propose(Crear Juego, AgenteRaton, AgenteLaberinto)
                AgenteLaberinto-->>AgenteMonitor: Accept Proposal(Crear Juego)
                AgenteLaberinto->>AgenteRaton: CFP(Iniciar Partida)
                alt Inicia Partida
                    AgenteRaton-->>AgenteLaberinto: Agree(Iniciar Partida)
                    AgenteRaton->>AgenteLaberinto: Inform(Fin Partida)
                    AgenteLaberinto-->>AgenteRaton: Agree(Fin Partida)
                    AgenteLaberinto->>AgenteMonitor: Inform(Fin Juego)
                    AgenteMonitor-->>AgenteLaberinto: Agree(Fin Juego)
                else Rechaza Inicio Partida
                    AgenteRaton-->>AgenteLaberinto: Refuse(Iniciar Partida)
                end
            else Rechaza Organizar
                AgenteLaberinto-->>AgenteMonitor: Refuse(Organizar)
            end
        else Rechaza Participar
            AgenteRaton-->>AgenteMonitor: Refuse(Participar)
        end
    end
```

-  **Coordinar con AgentesLaberinto**: Para cada juego que necesita ser organizado, el AgenteMonitor debe localizar un AgenteLaberinto adecuado y enviarle una solicitud para que organice el juego.
```mermaid
sequenceDiagram
    participant AgenteMonitor
    participant AgenteLaberinto

    loop Coordinar con AgentesLaberinto
        AgenteMonitor->>AgenteLaberinto: Propose(Organizar)
        alt Acepta Organizar
            AgenteLaberinto-->>AgenteMonitor: Accept Proposal(Organizar)
            AgenteLaberinto->>AgenteMonitor: Inform(Fin Juego)
        else Rechaza Organizar
            AgenteLaberinto-->>AgenteMonitor: Reject Proposal(Organizar)
        end
    end
```
-  **Seguimiento de los resultados del juego**: Una vez que un juego ha terminado, el AgenteMonitor recoge los resultados del AgenteLaberinto y los almacena para su posterior consulta. Este registro de resultados podría guardarse en un archivo o en una base de datos, dependiendo de los requerimientos del sistema.
```mermaid
sequenceDiagram
    participant AgenteMonitor
    participant AgenteLaberinto

    loop Seguimiento de los resultados del juego
        AgenteLaberinto->>AgenteMonitor: Inform(Resultado Juego)
        AgenteMonitor->>AgenteMonitor: Almacena resultado
    end
```
    
- **Proporcionar una interfaz para consultar los resultados**: Además de almacenar los resultados de los juegos, el AgenteMonitor puede proporcionar una interfaz a través de la cual los usuarios pueden consultar estos resultados. Esta interfaz podría ser una interfaz de usuario gráfica, una interfaz de línea de comandos, o incluso una API web, dependiendo de los requerimientos del sistema.

```mermaid
sequenceDiagram
    participant Usuario
    participant AgenteMonitor

    Usuario->>AgenteMonitor: Request(Resultados)
    AgenteMonitor->>Usuario: Inform(Resultados)
```

#### Tareas principales del AgenteRatón
- **Suscripción a las páginas amarillas**:
```mermaid
sequenceDiagram
    AgenteRaton->>PáginasAmarillas: subscribe (AgenteMonitor)
    PáginasAmarillas-->>AgenteRaton: inform (success)
    Note over PáginasAmarillas: AgenteRaton es notificado sobre la creación del AgenteMonitor
    alt Suscripción falla
    PáginasAmarillas-->>AgenteRaton: refuse (motivo)
    end

    AgenteRaton->>PáginasAmarillas: subscribe (AgenteLaberinto)
    PáginasAmarillas-->>AgenteRaton: inform (success)
    Note over PáginasAmarillas: AgenteRaton es notificado sobre la creación del AgenteLaberinto
    alt Suscripción falla
    PáginasAmarillas-->>AgenteRaton: refuse (motivo)
    end

```
-  **Aceptar invitaciones a los juegos**: El AgenteRaton debe estar dispuesto a aceptar la invitación a un juego propuesto por el AgenteMonitor. Debe estar listo para aceptar jugar al menos en 3 juegos simultáneos.
```mermaid
sequenceDiagram
    participant AgenteMonitor
    participant AgenteRaton

    AgenteMonitor->>AgenteRaton: Propose(Invitación a Juego)
    alt Acepta Invitación
        AgenteRaton-->>AgenteMonitor: Accept Proposal(Invitación a Juego)
    else Rechaza Invitación
        AgenteRaton-->>AgenteMonitor: Reject Proposal(Invitación a Juego)
    end

```

-  **Participar en juegos**: Una vez que el AgenteRaton acepta la invitación, deberá participar en el juego. Esto significa que tiene que realizar los movimientos correspondientes dentro del laberinto durante su turno. Debe tener en cuenta los dos modos de juego disponibles: competitivo o de búsqueda.
```mermaid
sequenceDiagram
    participant AgentePartida
    participant AgenteRaton
    loop Durante la partida
        AgentePartida->>AgenteRaton: request(Solicitud de movimiento)
        Note right of AgentePartida: Solicita el próximo movimiento al AgenteRaton
        alt Movimiento válido
            AgenteRaton-->>AgentePartida: agree(Movimiento)
            Note right of AgenteRaton: Responde con el movimiento seleccionado
            AgentePartida->>AgentePartida: Actualizar estado de la partida
            Note right of AgentePartida: Actualiza la posición del ratón en la partida
        else Movimiento inválido
            AgenteRaton-->>AgentePartida: refuse(Motivo)
            Note right of AgenteRaton: Rechaza la solicitud debido a la inválida acción de movimiento
        end
    end
    AgentePartida->>AgenteRaton: inform(Resultado de la partida)
    Note right of AgentePartida: Informa el resultado de la partida al AgenteRaton
```

#### Tareas principales del AgenteLaberinto
- **Suscripción a las páginas amarillas**: Esta tarea comprende las operaciones necesarias para que el AgenteLaberinto se suscriba a las notificaciones de creación de los AgentesMonitor y AgentesRaton en la plataforma. Esto permite al AgenteLaberinto mantenerse actualizado sobre las novedades de estos agentes, como su disponibilidad para los juegos. En caso de una suscripción exitosa, el agente recibe una confirmación. Sin embargo, si la suscripción falla, recibe un mensaje de rechazo indicando el motivo.
```mermaid
sequenceDiagram
        AgenteLaberinto->>PaginasAmarillas: subscribe (AgenteMonitor)
    Note right of AgenteLaberinto: Solicita suscripción a las notificaciones del AgenteMonitor
    alt Suscripción exitosa
        PaginasAmarillas-->>AgenteLaberinto: inform (Suscripción exitosa)
        Note right of PaginasAmarillas: Informa a AgenteLaberinto sobre la suscripción exitosa
    else Suscripción fallida
        PaginasAmarillas-->>AgenteLaberinto: refuse (motivo)
        Note right of PaginasAmarillas: Informa a AgenteLaberinto sobre el fallo y su motivo
    end
    AgenteLaberinto->>PaginasAmarillas: subscribe (AgenteRaton)
    Note right of AgenteLaberinto: Solicita suscripción a las notificaciones del AgenteRaton
    alt Suscripción exitosa
        PaginasAmarillas-->>AgenteLaberinto: inform (Suscripción exitosa)
        Note right of PaginasAmarillas: Informa a AgenteLaberinto sobre la suscripción exitosa
    else Suscripción fallida
        PaginasAmarillas-->>AgenteLaberinto: refuse (motivo)
        Note right of PaginasAmarillas: Informa a AgenteLaberinto sobre el fallo y su motivo
    end
```
-  **Aceptar la organización de juegos**: Esta tarea consiste en recibir las solicitudes del AgenteMonitor para organizar un juego. Como mínimo, debe aceptar organizar 3 juegos simultáneos.
```mermaid
sequenceDiagram
Monitor->>Laberinto: Propose (SolicitudJuego)
Note right of Monitor: Propone la organización de un juego

alt Acepta la organización
Laberinto-->>Monitor: accept-proposal (JuegoAceptado)
Note right of Laberinto: Acepta la propuesta para organizar el juego
else No puede organizar más juegos
Laberinto-->>Monitor: reject-proposal (Motivo)
Note right of Laberinto: Rechaza la propuesta ya que no puede organizar más juegos en este momento
end


```
- **Crear el AgentePartida**: Para cada partida, el AgenteLaberinto debe crear un AgentePartida. Este AgentePartida se encargará de coordinar los movimientos dentro del laberinto.
```mermaid
sequenceDiagram
Laberinto->>Partida: request (SolicitudPartida)
Note right of Laberinto: Solicita la creación de una nueva partida con un laberinto específico

alt Partida creada correctamente
Partida-->>Laberinto: inform (PartidaCreada)
Note right of Partida: Informa que la partida ha sido creada correctamente
else Fallo en la creación de la partida
Partida-->>Laberinto: failure (MotivoFallo)
Note right of Partida: Informa del fallo en la creación de la partida y el motivo del fallo
end

```
    
-  **Generar las partidas**: Para cada juego que está organizando, el AgenteLaberinto debe crear las partidas, que están representadas por los laberintos. La forma en que organiza estas partidas depende del tipo de juego que está organizando.
```mermaid
sequenceDiagram
    participant AgenteLaberinto
    participant AgentePartida
    AgenteLaberinto->>AgentePartida: request(Información de la Partida)
    Note right of AgenteLaberinto: Solicita la creación de la partida
    alt Información de partida válida
        AgentePartida-->>AgenteLaberinto: agree
        Note right of AgentePartida: Acepta la solicitud y procede a crear la partida
        AgentePartida->>AgentePartida: Generar laberinto y posiciones iniciales
        Note right of AgentePartida: Utiliza la información proporcionada para crear la partida
        AgentePartida-->>AgenteLaberinto: inform(Detalles de la partida creada)
        Note right of AgentePartida: Informa que la partida ha sido creada exitosamente
    else Información de partida inválida
        AgentePartida-->>AgenteLaberinto: refuse(Motivo)
        Note right of AgentePartida: Rechaza la solicitud debido a la inválida información de partida
    end

```
   
    
-  **Obtener resultados de las partidas**: Una vez que una partida ha terminado, el AgenteLaberinto debe obtener los resultados de la partida. Estos resultados se utilizarán para determinar los resultados del juego en general.
```mermaid
sequenceDiagram
loop Recolección de resultados
Laberinto->>Partida: request (SolicitudResultados)
Note right of Laberinto: Solicita los resultados de una partida específica

alt Resultados obtenidos
Partida-->>Laberinto: inform (ResultadosPartida)
Note right of Partida: Informa de los resultados de la partida
else Fallo en la obtención de resultados
Partida-->>Laberinto: failure (MotivoFallo)
Note right of Partida: Informa del fallo en la obtención de resultados y el motivo del fallo
end
end

```
    
-  **Guardar registro de las partidas**: El AgenteLaberinto debe mantener un registro de todas las partidas que ha organizado. Este registro podría ser necesario si se solicita la reproducción de una partida una vez que ha finalizado.
```mermaid
sequenceDiagram
loop Registro de partidas
Laberinto->>BD: request (DatosPartida)
Note right of Laberinto: Solicita registrar los datos de una partida específica

alt Datos registrados correctamente
BD-->>Laberinto: inform (DatosRegistrados)
Note right of BD: Informa que los datos de la partida han sido registrados correctamente
else Fallo en el registro de datos
BD-->>Laberinto: failure (MotivoFallo)
Note right of BD: Informa del fallo en el registro de datos y el motivo del fallo
end
end

```
    
-  **Informar los resultados del juego**: Una vez que todas las partidas de un juego han terminado, el AgenteLaberinto debe informar los resultados del juego al AgenteMonitor que solicitó la organización del juego.
```mermaid
sequenceDiagram
Laberinto->>Monitor: inform (ResultadosJuego)
Note right of Laberinto: Informa los resultados del juego al AgenteMonitor
alt Recibido correctamente
Monitor-->>Laberinto: agree (ReciboResultados)
Note right of Monitor: Acusa recibo de los resultados del juego
else Fallo en la recepción de resultados
Monitor-->>Laberinto: failure (MotivoFallo)
Note right of Monitor: Informa del fallo en la recepción de resultados y el motivo del fallo
end

```

#### Tareas principales del AgentePartida
- **Crear la partida**: Esta tarea involucra la generación de la partida basándose en la información proporcionada por el AgenteLaberinto. Esta información incluiría las reglas del juego, la estructura del laberinto y la posición inicial de los agentes Ratón.
```mermaid
sequenceDiagram
    participant AgenteLaberinto
    participant AgentePartida
    AgenteLaberinto->>AgentePartida: request(Información de la Partida)
    Note right of AgenteLaberinto: Solicita la creación de la partida
    alt Información de partida válida
        AgentePartida-->>AgenteLaberinto: agree
        Note right of AgentePartida: Acepta la solicitud y procede a crear la partida
        AgentePartida->>AgentePartida: Generar laberinto y posiciones iniciales
        Note right of AgentePartida: Utiliza la información proporcionada para crear la partida
        AgentePartida-->>AgenteLaberinto: inform(Detalles de la partida creada)
        Note right of AgentePartida: Informa que la partida ha sido creada exitosamente
    else Información de partida inválida
        AgentePartida-->>AgenteLaberinto: refuse(Motivo)
        Note right of AgentePartida: Rechaza la solicitud debido a la inválida información de partida
    end

```

    
-  **Gestionar la partida**: Durante esta tarea, el AgentePartida coordina el desarrollo de la partida, gestionando las acciones de los AgentesRatón, verificando las condiciones de victoria y controlando el avance de la partida.
```mermaid
sequenceDiagram
    participant AgentePartida
    participant AgenteRaton
    loop Durante la partida
        AgentePartida->>AgenteRaton: request(Solicitud de movimiento)
        Note right of AgentePartida: Solicita el próximo movimiento al AgenteRaton
        alt Movimiento válido
            AgenteRaton-->>AgentePartida: agree(Movimiento)
            Note right of AgenteRaton: Responde con el movimiento seleccionado
            AgentePartida->>AgentePartida: Actualizar estado de la partida
            Note right of AgentePartida: Actualiza la posición del ratón en la partida
        else Movimiento inválido
            AgenteRaton-->>AgentePartida: refuse(Motivo)
            Note right of AgenteRaton: Rechaza la solicitud debido a la inválida acción de movimiento
        end
    end
    AgentePartida->>AgenteRaton: inform(Resultado de la partida)
    Note right of AgentePartida: Informa el resultado de la partida al AgenteRaton
```

    
    
-  **Informar los resultados**: Finalmente, el AgentePartida comunica los resultados del juego a los agentes participantes y al AgenteMonitor, permitiendo la visualización y análisis de estos datos.
```mermaid
sequenceDiagram
    AgentePartida->>AgenteMonitor: inform(Resultados de la partida)
    Note right of AgentePartida: Informa los resultados de la partida al AgenteMonitor

```
## Diseño