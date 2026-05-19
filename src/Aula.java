// Archivo: Aula.java
// Paso 3: Molde básico para almacenar en el TreeMap de infraestructura

public class Aula {
    private String bloqueSalon;
    private String estadoDisponibilidad;

    // Constructor directo
    public Aula(String bloqueSalon, String estadoDisponibilidad) {
        this.bloqueSalon = bloqueSalon;
        this.estadoDisponibilidad = estadoDisponibilidad;
    }

    // Getters y Setters sencillos
    public String getBloqueSalon() {
        return bloqueSalon;
    }

    public void setBloqueSalon(String bloqueSalon) {
        this.bloqueSalon = bloqueSalon;
    }

    public String getEstadoDisponibilidad() {
        return estadoDisponibilidad;
    }

    public void setEstadoDisponibilidad(String estadoDisponibilidad) {
        this.estadoDisponibilidad = estadoDisponibilidad;
    }

    // Método simple para mostrar en pantalla sin rodeos
    public void mostrarInfo() {
        System.out.println("-> Aula/Laboratorio: " + bloqueSalon + " | Estado actual: " + estadoDisponibilidad);
    }
}