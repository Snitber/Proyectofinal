// Archivo: ColaProcesamiento.java
// Desarrollado para: Jhonnier Ortega
// Paso 5: Estructura lineal manual FIFO para la carga por lotes del CSV

public class ColaProcesamiento {

    // Clase Nodo interna y directa para el flujo de texto
    private class Nodo {
        String lineaTexto;
        Nodo siguienteNodo;

        public Nodo(String lineaTexto) {
            this.lineaTexto = lineaTexto;
            this.siguienteNodo = null;
        }
    }

    private Nodo nodoFrente;
    private Nodo nodoFinal;
    private int contadorElementos;

    public ColaProcesamiento() {
        this.nodoFrente = null;
        this.nodoFinal = null;
        this.contadorElementos = 0;
    }

    // Operación Enqueue: Inserta una línea del CSV al final de la cola (O(1))
    public void encolarRegistro(String linea) {
        Nodo nuevoNodo = new Nodo(linea);
        if (nodoFinal == null) {
            nodoFrente = nuevoNodo;
            nodoFinal = nuevoNodo;
        } else {
            nodoFinal.siguienteNodo = nuevoNodo;
            nodoFinal = nuevoNodo;
        }
        contadorElementos++;
    }

    // Operación Dequeue: Extrae y retorna la primera línea en espera (O(1))
    public String desencolarRegistro() {
        if (nodoFrente == null) {
            return null;
        }
        String datos = nodoFrente.lineaTexto;
        nodoFrente = nodoFrente.siguienteNodo;
        
        if (nodoFrente == null) {
            nodoFinal = null;
        }
        contadorElementos--;
        return datos;
    }

    // Verifica si aún quedan registros por procesar en el lote
    public boolean tieneRegistros() {
        return nodoFrente != null;
    }

    public int getContadorElementos() {
        return contadorElementos;
    }
}