

public class Estudiante extends Persona {
    
    // Matriz bidimensional estática exigida por la rúbrica [10 semestres][20 materias]
    private final Double[][] mallaCalificaciones;
    private int asignaturasRegistradas;

    // Constructor que invoca a la clase padre (Persona)
    public Estudiante(String codigoIdentificacion, String nombreCompleto, String correoElectronico, int cicloBase) {
        super(codigoIdentificacion, nombreCompleto, correoElectronico, cicloBase);
        // Inicialización explícita de la matriz estática fijada en la guía
        this.mallaCalificaciones = new Double[10][20];
        this.asignaturasRegistradas = 0;
    }

    // Método alternativo para insertar notas en la matriz
    public boolean insertarCalificacion(int numeroSemestre, double valorNota) {
        if (numeroSemestre < 1 || numeroSemestre > 10) {
            System.out.println("Semestre fuera del rango permitido (1-10).");
            return false;
        }
        
        int filaMesa = numeroSemestre - 1; // Ajuste de índice base 0
        
        // Recorremos las columnas (materias) buscando el primer espacio vacío (null)
        for (int col = 0; col < 20; col++) {
            if (this.mallaCalificaciones[filaMesa][col] == null) {
                this.mallaCalificaciones[filaMesa][col] = valorNota;
                this.asignaturasRegistradas++;
                return true;
            }
        }
        
        System.out.println("Cupo de 20 materias agotado para el semestre " + numeroSemestre);
        return false;
    }

    // Obtener promedio de un semestre específico con lógica estructurada diferente
    public double obtenerPromedioPeriodo(int semestreConsultado) {
        if (semestreConsultado < 1 || semestreConsultado > 10) return 0.0;
        
        double acumuladoNotas = 0.0;
        int cantidadMaterias = 0;
        int fila = semestreConsultado - 1;

        for (int j = 0; j < 20; j++) {
            Double notaActual = this.mallaCalificaciones[fila][j];
            if (notaActual != null) {
                acumuladoNotas += notaActual;
                cantidadMaterias++;
            }
        }
        
        return (cantidadMaterias > 0) ? (acumuladoNotas / cantidadMaterias) : 0.0;
    }

    // Calcular el promedio acumulado global de toda la carrera
    public double calcularPromedioGlobalAcumulado() {
        double sumatoriaTotal = 0.0;
        int totalNotasRegistradas = 0;

        // Recorrido por bloques bidimensionales
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (this.mallaCalificaciones[i][j] != null) {
                    sumatoriaTotal += this.mallaCalificaciones[i][j];
                    totalNotasRegistradas++;
                }
            }
        }
        
        return (totalNotasRegistradas > 0) ? (sumatoriaTotal / totalNotasRegistradas) : 0.0;
    }

    // Implementación obligatoria del método abstracto modificado
    @Override
    public void desplegarFichaTecnica() {
        System.out.println("=========================================");
        System.out.println("REGISTRO DE ESTUDIANTE - CÓDIGO: " + this.codigoIdentificacion);
        System.out.println("Estudiante: " + this.nombreCompleto);
        System.out.println("Contacto institucional: " + this.correoElectronico);
        System.out.println("Ciclo de ingreso: " + this.cicloBase);
        System.out.println("Total materias cargadas: " + this.asignaturasRegistradas);
        System.out.println("Promedio General: " + String.format("%.2f", calcularPromedioGlobalAcumulado()));
        System.out.println("=========================================");
    }

    // Getters y Setters necesarios para la lógica del sistema
    public Double[][] getMallaCalificaciones() {
        return mallaCalificaciones;
    }

    public int getAsignaturasRegistradas() {
        return asignaturasRegistradas;
    }
}