//package mouserun.mouse;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.Queue;
//import java.util.Random;
//import java.util.Stack;
//import javafx.util.Pair;
//import mouserun.game.Mouse;
//import mouserun.game.Grid;
//import mouserun.game.Cheese;
//
///**
// * Clase que contiene el esqueleto del raton base para las prácticas de
// * Inteligencia Artificial del curso 2020-21.
// *
// * @author Cristóbal José Carmona (ccarmona@ujaen.es)
// */
//public class M21A06a extends Mouse {
//
//    private final HashMap<Pair<Integer, Integer>, Grid> celdasVisitadas;
//    private final Stack<Grid> pilaMovimientos;
//
//    /**
//     * Constructor (Puedes modificar el nombre a tu gusto).
//     */
//    public M21A06a() {
//        super("M21A06a");
//        celdasVisitadas = new HashMap<>();
//        pilaMovimientos = new Stack<>();
//    }
//
//    private int MarchaAtras(Grid casActual) {
//        Grid casAnt = pilaMovimientos.pop();
//        int movimiento = -1;
//        if (casActual.getX() == casAnt.getX() && casActual.getY() - 1 == casAnt.getY()) {
//            movimiento = Mouse.DOWN;
//        }
//        if (casActual.getX() - 1 == casAnt.getX() && casActual.getY() == casAnt.getY()) {
//            movimiento = Mouse.LEFT;
//        }
//        if (casActual.getX() == casAnt.getX() && casActual.getY() + 1 == casAnt.getY()) {
//            movimiento = Mouse.UP;
//        }
//        if (casActual.getX() + 1 == casAnt.getX() && casActual.getY() == casAnt.getY()) {
//            movimiento = Mouse.RIGHT;
//        }
//        return movimiento;
//    }
//
//    private int aniadeMovimientos(Grid currentGrid, Cheese cheese) {
//        HashMap<Integer, Integer> opciones = new HashMap<>();
//        int mov = -1;
//        Pair<Integer, Integer> CasillaArriba = new Pair<>(currentGrid.getX(), currentGrid.getY() + 1);
//        Pair<Integer, Integer> CasillaDerecha = new Pair<>(currentGrid.getX() + 1, currentGrid.getY());
//        Pair<Integer, Integer> CasillaAbajo = new Pair<>(currentGrid.getX(), currentGrid.getY() - 1);
//        Pair<Integer, Integer> CasillaIzquierda = new Pair<>(currentGrid.getX() - 1, currentGrid.getY());
//
//        if (currentGrid.canGoUp() && !celdasVisitadas.containsKey(CasillaArriba)) {
//            opciones.put(1, Mouse.UP);
//        }
//        if (currentGrid.canGoRight() && !celdasVisitadas.containsKey(CasillaDerecha)) {
//            opciones.put(2, Mouse.RIGHT);
//        }
//        if (currentGrid.canGoDown() && !celdasVisitadas.containsKey(CasillaAbajo)) {
//            opciones.put(3, Mouse.DOWN);
//        }
//        if (currentGrid.canGoLeft() && !celdasVisitadas.containsKey(CasillaIzquierda)) {
//            opciones.put(4, Mouse.LEFT);
//        }
//
//
//        if (cheese.getY() > currentGrid.getY() && opciones.containsKey(1)) {
//            return Mouse.UP;
//        } else if (cheese.getY() < currentGrid.getY() && opciones.containsKey(3)) {
//            return Mouse.DOWN;
//        } else if (cheese.getX() > currentGrid.getX() && opciones.containsKey(2)) {
//            return Mouse.RIGHT;
//        } else if (cheese.getX() < currentGrid.getX() && opciones.containsKey(4)) {
//            return Mouse.LEFT;
//        }
//
//        if (!opciones.isEmpty()) {
//            if (opciones.containsKey(1)) {
//                return Mouse.UP;
//            } else if (opciones.containsKey(2)) {
//                return Mouse.RIGHT;
//            } else if (opciones.containsKey(3)) {
//                return Mouse.DOWN;
//            } else if (opciones.containsKey(4)) {
//                return Mouse.LEFT;
//            }
//        }
//
//        return mov;
//    }
//
//    private int ExploracionQueso(Grid currentGrid, Cheese cheese) {
//        Pair<Integer, Integer> CasillaActual = new Pair<>(currentGrid.getX(), currentGrid.getY());
//        if (!celdasVisitadas.containsKey(CasillaActual)) {
//            celdasVisitadas.put(CasillaActual, currentGrid);
//            incExploredGrids();
//        }
//        int queMuevo = aniadeMovimientos(currentGrid, cheese);
//        if (queMuevo != -1) {
//            pilaMovimientos.add(currentGrid);
//            return queMuevo;
//        } else {
//            return MarchaAtras(currentGrid);
//        }
//    }
//
//    private int aniadeMovimientos2(Grid currentGrid) {
//        int mov = -1;
//        Pair<Integer, Integer> CasillaArriba = new Pair<>(currentGrid.getX(), currentGrid.getY() + 1);
//        Pair<Integer, Integer> CasillaDerecha = new Pair<>(currentGrid.getX() + 1, currentGrid.getY());
//        Pair<Integer, Integer> CasillaAbajo = new Pair<>(currentGrid.getX(), currentGrid.getY() - 1);
//        Pair<Integer, Integer> CasillaIzquierda = new Pair<>(currentGrid.getX() - 1, currentGrid.getY());
//
//        if (currentGrid.canGoUp() && !celdasVisitadas.containsKey(CasillaArriba)) {
//            mov = Mouse.UP;
//        } else if (currentGrid.canGoRight() && !celdasVisitadas.containsKey(CasillaDerecha)) {
//            mov = Mouse.RIGHT;
//        } else if (currentGrid.canGoDown() && !celdasVisitadas.containsKey(CasillaAbajo)) {
//            mov = Mouse.DOWN;
//        } else if (currentGrid.canGoLeft() && !celdasVisitadas.containsKey(CasillaIzquierda)) {
//            mov = Mouse.LEFT;
//        }
//        return mov;
//    }
//
//    private int ExploracionReloj(Grid currentGrid) {
//        Pair<Integer, Integer> CasillaActual = new Pair<>(currentGrid.getX(), currentGrid.getY());
//        if (!celdasVisitadas.containsKey(CasillaActual)) {
//            celdasVisitadas.put(CasillaActual, currentGrid);
//            incExploredGrids();
//        }
//        int queMuevo = aniadeMovimientos2(currentGrid);
//        if (queMuevo != -1) {
//            pilaMovimientos.add(currentGrid);
//            return queMuevo;
//        } else {
//            return MarchaAtras(currentGrid);
//        }
//    }
//    
//    public int ExploracionAleatoria(Grid currentGrid) {
//        Pair<Integer, Integer> CasillaActual = new Pair<>(currentGrid.getX(), currentGrid.getY());
//        Random r=new Random();
//        if (!celdasVisitadas.containsKey(CasillaActual)) {
//            celdasVisitadas.put(CasillaActual, currentGrid);
//            incExploredGrids();
//        }
//        ArrayList<Integer> queMuevo=aniadeMovimientos3(currentGrid);
//          if(!queMuevo.isEmpty()){
//              pilaMovimientos.add(currentGrid);
//              return queMuevo.get(r.nextInt(queMuevo.size()));
//          }else{
//              return MarchaAtras(currentGrid);
//          }
//
//    }
//    
//    private ArrayList<Integer> aniadeMovimientos3(Grid currentGrid) {
//        ArrayList<Integer> opciones = new ArrayList<>();
//        int mov=-1;
//        Pair<Integer, Integer> CasillaArriba = new Pair<>(currentGrid.getX(), currentGrid.getY() + 1);
//        Pair<Integer, Integer> CasillaDerecha = new Pair<>(currentGrid.getX() + 1, currentGrid.getY());
//        Pair<Integer, Integer> CasillaAbajo = new Pair<>(currentGrid.getX(), currentGrid.getY() - 1);
//        Pair<Integer, Integer> CasillaIzquierda = new Pair<>(currentGrid.getX() - 1, currentGrid.getY());
//
//        if (currentGrid.canGoUp() && !celdasVisitadas.containsKey(CasillaArriba)) {
//            opciones.add(Mouse.UP);
//        }
//        if (currentGrid.canGoRight() && !celdasVisitadas.containsKey(CasillaDerecha)) {
//            opciones.add(Mouse.RIGHT);
//        }
//        if (currentGrid.canGoDown()&& !celdasVisitadas.containsKey(CasillaAbajo)) {
//            opciones.add(Mouse.DOWN);
//        }
//        if (currentGrid.canGoLeft() && !celdasVisitadas.containsKey(CasillaIzquierda)) {
//            opciones.add(Mouse.LEFT);
//        }
//        return opciones;
//    }
//
//    /**
//     */
//    @Override
//    public int move(Grid currentGrid, Cheese cheese) {
//        return ExploracionQueso(currentGrid,cheese);
//        //return ExploracionReloj(currentGrid);
//        //return ExploracionAleatoria(currentGrid);
//    }
//
//    /**
//     * @brief Método que se llama cuando aparece un nuevo queso
//     */
//    @Override
//    public void newCheese() {
//
//    }
//
//    /**
//     * @brief Método que se llama cuando el raton pisa una bomba
//     */
//    @Override
//    public void respawned() {
//
//    }
//
//}
