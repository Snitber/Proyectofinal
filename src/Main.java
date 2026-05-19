// Archivo: Main.java
// Desarrollado para: Jhonnier Ortega
// Paso 8: Interfaz de Consola y Ejecución del Sistema

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ControladorAcademico sistema = new ControladorAcademico();
        GobernadorCampus campus = new GobernadorCampus();
        // Intentamos cargar datos desde el CSV automáticamente al iniciar
        try (Scanner teclado = new Scanner(System.in)) {
            // Intentamos cargar datos desde el CSV automáticamente al iniciar
            sistema.importarDesdeArchivoCsv("datos_academicos.csv");
            
            int opcionSeleccionada = 0;
            
            do {
                System.out.println("\n=========================================");
                System.out.println("     SISTEMA ACADÉMICO - JHONNIER ORTEGA ");
                System.out.println("=========================================");
                System.out.println("1. Registrar nuevo estudiante");
                System.out.println("2. Insertar calificación a estudiante");
                System.out.println("3. Buscar estudiante por código (O(1))");
                System.out.println("4. Consultar disponibilidad de aula (TreeMap)");
                System.out.println("5. Trazar ruta óptima en campus (Dijkstra)");
                System.out.println("6. Ver bitácora histórica (Pila Auditoría)");
                System.out.println("7. Guardar copia de seguridad (CSV)");
                System.out.println("8. Salir del sistema");
                System.out.print("Seleccione una opción: ");
                
                try {
                    opcionSeleccionada = Integer.parseInt(teclado.nextLine());
                    
                    switch (opcionSeleccionada) {
                        case 1 -> {
                            System.out.println("\n--- REGISTRO DE ESTUDIANTE ---");
                            System.out.print("Código de identificación: ");
                            String id = teclado.nextLine();
                            System.out.print("Nombre completo: ");
                            String nombre = teclado.nextLine();
                            System.out.print("Correo institucional: ");
                            String correo = teclado.nextLine();
                            System.out.print("Ciclo base de ingreso: ");
                            int ciclo = Integer.parseInt(teclado.nextLine());
                            
                            Estudiante nuevo = new Estudiante(id, nombre, correo, ciclo);
                            if (sistema.registrarEstudianteDirecto(nuevo)) {
                                System.out.println("¡Estudiante registrado exitosamente!");
                            } else {
                                System.out.println("Error: El código ya se encuentra registrado.");
                            }
                        }
                        
                        case 2 -> {
                            System.out.println("\n--- COLOQUIO DE CALIFICACIONES ---");
                            System.out.print("Ingrese el código del estudiante: ");
                            String codEst = teclado.nextLine();
                            Estudiante buscado = sistema.buscarEstudiantePorCodigo(codEst);
                            
                            if (buscado != null) {
                                System.out.print("Número del semestre (1-10): ");
                                int sem = Integer.parseInt(teclado.nextLine());
                                System.out.print("Valor de la calificación (0.0 - 5.0): ");
                                double nota = Double.parseDouble(teclado.nextLine());
                                
                                if (buscado.insertarCalificacion(sem, nota)) {
                                    System.out.println("Calificación añadida correctamente.");
                                }
                            } else {
                                System.out.println("Estudiante no localizado.");
                            }
                        }
                        
                        case 3 -> {
                            System.out.println("\n--- CONSULTA DE EXPEDIENTE ---");
                            System.out.print("Ingrese código a buscar: ");
                            String c = teclado.nextLine();
                            Estudiante e = sistema.buscarEstudiantePorCodigo(c);
                            if (e != null) {
                                e.desplegarFichaTecnica();
                            } else {
                                System.out.println("Estudiante no registrado en el sistema.");
                            }
                        }
                        
                        case 4 -> {
                            System.out.println("\n--- INFRAESTRUCTURA DE AULAS ---");
                            System.out.print("Ingrese nombre del aula (e.g., Aula 101, Lab 202): ");
                            String nombreAula = teclado.nextLine();
                            Aula aula = sistema.consultarEstadoAula(nombreAula);
                            if (aula != null) {
                                aula.mostrarInfo();
                            } else {
                                System.out.println("El aula especificada no está registrada en el TreeMap.");
                            }
                        }
                        
                        case 5 -> {
                            System.out.println("\n--- ENRUTAMIENTO DE SEDE ---");
                            System.out.println("Edificios disponibles:");
                            for (String EDIFICIOS : GobernadorCampus.EDIFICIOS) {
                                System.out.println("- " + EDIFICIOS);
                            }
                            System.out.print("\nIngrese edificio de origen: ");
                            String oriTxt = teclado.nextLine();
                            System.out.print("Ingrese edificio de destino: ");
                            String destTxt = teclado.nextLine();
                            
                            int idxOri = campus.buscarIndicePorNombre(oriTxt);
                            int idxDest = campus.buscarIndicePorNombre(destTxt);
                            
                            if (idxOri != -1 && idxDest != -1) {
                                campus.calcularRutaOptima(idxOri, idxDest);
                            } else {
                                System.out.println("Error: Uno o ambos nombres de edificios son inválidos.");
                            }
                        }
                        
                        
                        case 6 -> sistema.desplegarBitacora();
                        
                        case 7 -> sistema.exportarAArchivoCsv("datos_academicos.csv");
                        
                        case 8 -> System.out.println("Finalizando la ejecución del sistema corporativo. ¡Hasta pronto!");
                        
                        default -> System.out.println("Opción inválida. Intente de nuevo.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Error en la entrada de datos: " + ex.getMessage() + ". Regresando al menú.");
                }
                
            } while (opcionSeleccionada != 8);
        }
    }
}