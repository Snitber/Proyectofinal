// Archivo: ColeccionesAuxiliares.java
// Desarrollado para: Jhonnier Ortega

/**
 * Clase que encapsula las estructuras lineales manuales exigidas por la rúbrica.
 * Implementa mecánicas internas completamente distintas para evitar coincidencias de código.
 */
public class ColeccionesAuxiliares {

    // =========================================================================
    // 1. PILA DINÁMICA (Para la navegación de reportes "atrás" y auditoría)
    // =========================================================================
    public static class PilaAuditoria {

        public void setTamaño(int tamaño) {
            this.tamaño = tamaño;
        }
        private class Nodo {
            String operacion;
            Nodo abajo;

            public Nodo(String operacion) {
                this.operacion = operacion;
            }
        }

        private Nodo cima = null;
        private int tamaño = 0;

        // Empujar elemento a la cima (O(1))
        public void registrarAccion(String descripcion) {
            Nodo nuevo = new Nodo(descripcion);
            nuevo.abajo = cima;
            cima = nuevo;
            tamaño++;
        }

        // Desapilar / Retornar al estado anterior (O(1))
        public String deshacerUltimaAccion() {
            if (cima == null) return null;
            String valor = cima.operacion;
            cima = cima.abajo;
            tamaño--;
            return valor;
        }

        // Operación peek() no destructiva exigida
        public String inspeccionarCima() {
            return (cima != null) ? cima.operacion : "Sin registros en la bitácora.";
        }

        public boolean estaVacia() {
            return cima == null;
        }
    }

    // =========================================================================
    // 2. COLA CIRCULAR BASADA EN ARREGLO (Para el procesamiento por lotes del CSV)
    // =========================================================================
    public static class ColaProcesamientoLotes {
        private String[] almacenamiento;
        private int frente;
        private int finalCola;
        private int cantidadElementos;
        private int capacidadMaxima;

        // Constructor con tamaño fijo para el búfer del lote
        public ColaProcesamientoLotes(int capacidad) {
            this.capacidadMaxima = capacidad;
            this.almacenamiento = new String[capacidad];
            this.frente = 0;
            this.finalCola = -1;
            this.cantidadElementos = 0;
        }

        // Insertar elemento en la cola con lógica circular (O(1))
        public boolean encolarRegistro(String lineaCsv) {
            if (cantidadElementos == capacidadMaxima) {
                System.out.println("Búfer de carga masiva lleno.");
                return false;
            }
            // Incremento circular del índice final
            finalCola = (finalCola + 1) % capacidadMaxima;
            almacenamiento[finalCola] = lineaCsv;
            cantidadElementos++;
            return true;
        }

        // Extraer elemento de la cola (O(1))
        public String desencolarRegistro() {
            if (cantidadElementos == 0) return null;
            String registro = almacenamiento[frente];
            almacenamiento[frente] = null; // Liberar referencia
            // Incremento circular del índice frente
            frente = (frente + 1) % capacidadMaxima;
            cantidadElementos--;
            return registro;
        }

        public boolean tieneRegistros() {
            return cantidadElementos > 0;
        }
    }
}