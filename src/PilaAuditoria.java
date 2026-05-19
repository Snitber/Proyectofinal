// Archivo: PilaAuditoria.java
// Desarrollado para: Jhonnier Ortega
// Paso 4: Estructura lineal manual LIFO para la bitácora de navegación

public class PilaAuditoria {
    
    // Clase Nodo interna y simplificada
    private class Nodo {
        String mensajeAccion;
        Nodo enlaceSiguiente;

        public Nodo(String mensajeAccion) {
            this.mensajeAccion = mensajeAccion;
            this.enlaceSiguiente = null;
        }
    }

    private Nodo nodoCima;

    public PilaAuditoria() {
        this.nodoCima = null;
    }

    // Operación Push: Apila una nueva acción en la cima
    public void registrarAccion(String descripcion) {
        Nodo nuevoNodo = new Nodo(descripcion);
        nuevoNodo.enlaceSiguiente = nodoCima;
        nodoCima = nuevoNodo;
    }

    // Operación Pop: Desapila y retorna la última acción realizada
    public String deshacerAccion() {
        if (nodoCima == null) {
            return null;
        }
        String texto = nodoCima.mensajeAccion;
        nodoCima = nodoCima.enlaceSiguiente;
        return texto;
    }

    // Operación Peek: Muestra qué hay en la cima sin borrarlo (Exigido en la guía)
    public String verUltimaAccion() {
        if (nodoCima == null) {
            return "No se registran operaciones en la bitácora actual.";
        }
        return nodoCima.mensajeAccion;
    }

    // Valida si la pila está vacía
    public boolean estaVacia() {
        return nodoCima == null;
    }
}