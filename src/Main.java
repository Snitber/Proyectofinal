// Archivo: Main.java
// Desarrollado para: Jhonnier Ortega
// Paso 8: Interfaz de Consola con Inyección Directa de Datos

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ControladorAcademico sistema = new ControladorAcademico();
        GobernadorCampus campus = new GobernadorCampus();
        // Inyectamos automáticamente los 20 estudiantes al arrancar el sistema
        try (Scanner teclado = new Scanner(System.in)) {
            // Inyectamos automáticamente los 20 estudiantes al arrancar el sistema
            inicializarDatosPorDefecto(sistema);
            
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

    // --- INYECCIÓN DIRECTA DE LOS 20 ESTUDIANTES REQUERIDOS MÁS DATOS BASE ---
    private static void inicializarDatosPorDefecto(ControladorAcademico sistema) {
        // Estudiantes agregados directamente usando el orden de parámetros de la clase Estudiante de Jhonnier:
        // Estudiante(String codigoIdentificacion, String nombreCompleto, String correoElectronico, int cicloBase)
        sistema.registrarEstudianteDirecto(new Estudiante("2124242", "Juan Jacob Lopez Fernández", "juan@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1005123", "Carlos Andrés Pérez Ospina", "carlos@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1005456", "María Camila Torres Buendía", "maria@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1005789", "Andrés Felipe Beltrán Ruiz", "andres@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1006111", "Diana Marcela Gómez Castro", "diana@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1006222", "Santiago Alejandro Muñoz Marín", "santiago@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1006333", "Valentina Sofía Erazo Córdoba", "valentina@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1006444", "Mateo Nicolás Delgado Ortiz", "mateo@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1006555", "Isabella Rose Gómez Hurtado", "isabella@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1006666", "Juan Diego Palacios Vivas", "juand@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1006777", "Mariana Lucía Rojas Benavides", "mariana@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1006888", "Kevin Alberto Restrepo Solano", "kevin@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1006999", "Laura Daniela Caicedo Peña", "laura@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1007112", "Sebastián Camilo Murillo Paz", "sebastian@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1007223", "Valeria Alexandra Mina Castillo", "valeria@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1007334", "Daniel Fernando Angulo Tenorio", "daniel@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1007445", "Natalia María Suárez Vargas", "natalia@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1007556", "Alejandro José Borrero Navia", "alejandro@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1007667", "Gabriela Estefanía Cifuentes Saa", "gabriela@u.edu.co", 1));
        sistema.registrarEstudianteDirecto(new Estudiante("1007778", "Jhan Carlos Hinestroza Mosquera", "jhan@u.edu.co", 1));
    }
}