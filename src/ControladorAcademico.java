// Archivo: ControladorAcademico.java
// Desarrollado para: Jhonnier Ortega
// Paso 7: Gestor Central Corregido

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

public class ControladorAcademico {

    // Colecciones nativas requeridas
    private final HashMap<String, Estudiante> mapaEstudiantes;
    private final TreeMap<String, Aula> mapaAulas;

    // Estructuras lineales manuales de los pasos anteriores
    private final PilaAuditoria bitacoraNavegacion;
    private final ColaProcesamiento colaLotes;

    public ControladorAcademico() {
        this.mapaEstudiantes = new HashMap<>();
        this.mapaAulas = new TreeMap<>();
        this.bitacoraNavegacion = new PilaAuditoria();
        this.colaLotes = new ColaProcesamiento();
        
        // Poblamos el TreeMap con datos iniciales para que la Opción 4 funcione directo
        inicializarAulasPredeterminadas();
    }

    private void inicializarAulasPredeterminadas() {
        mapaAulas.put("Aula 101", new Aula("Aula 101", "Disponible"));
        mapaAulas.put("Lab 202", new Aula("Lab 202", "Ocupado"));
        mapaAulas.put("Lab 305", new Aula("Lab 305", "Disponible"));
        mapaAulas.put("Aula 401", new Aula("Aula 401", "Ocupado"));
    }

    // GESTIÓN DE ESTUDIANTES (HashMap)
    public boolean registrarEstudianteDirecto(Estudiante est) {
        if (mapaEstudiantes.containsKey(est.getCodigoIdentificacion())) {
            return false;
        }
        mapaEstudiantes.put(est.getCodigoIdentificacion(), est);
        bitacoraNavegacion.registrarAccion("Registrado estudiante: " + est.getNombreCompleto());
        return true;
    }

    public Estudiante buscarEstudiantePorCodigo(String codigo) {
        Estudiante est = mapaEstudiantes.get(codigo);
        if (est != null) {
            bitacoraNavegacion.registrarAccion("Consulta de datos de: " + est.getNombreCompleto());
        }
        return est;
    }

    // GESTIÓN DE INFRAESTRUCTURA (TreeMap)
    public Aula consultarEstadoAula(String nombreAula) {
        Aula aula = mapaAulas.get(nombreAula);
        if (aula != null) {
            bitacoraNavegacion.registrarAccion("Consulta disponibilidad de: " + nombreAula);
        }
        return aula;
    }

    // VER HISTORIAL DE AUDITORÍA (Pila - LIFO)
    public void desplegarBitacora() {
        System.out.println("\n--- BITÁCORA DE NAVEGACIÓN HISTÓRICA ---");
        if (bitacoraNavegacion.estaVacia()) {
            System.out.println("No se registran movimientos en esta sesión.");
            return;
        }
        
        System.out.println("Última acción en memoria (Cima): " + bitacoraNavegacion.verUltimaAccion());
        System.out.println("----------------------------------------");
        
        // Desapilamos temporalmente usando la lógica limpia
        PilaAuditoria pilaEspejo = new PilaAuditoria();
        while (!bitacoraNavegacion.estaVacia()) {
            String accion = bitacoraNavegacion.deshacerAccion();
            System.out.println("- " + accion);
            pilaEspejo.registrarAccion(accion);
        }
        
        // Restauramos
        while (!pilaEspejo.estaVacia()) {
            bitacoraNavegacion.registrarAccion(pilaEspejo.deshacerAccion());
        }
    }

    // CARGA MASIVA (Cola - FIFO)
    public void importarDesdeArchivoCsv(String rutaArchivo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String lineaLeida;
            while ((lineaLeida = lector.readLine()) != null) {
                if (!lineaLeida.trim().isEmpty()) {
                    colaLotes.encolarRegistro(lineaLeida);
                }
            }
            
            int cargadosConExito = 0; // CORREGIDO: Variable unificada sin espacios
            
            while (colaLotes.tieneRegistros()) {
                String registroActual = colaLotes.desencolarRegistro();
                String[] campos = registroActual.split(",");
                
                if (campos.length >= 4) {
                    String id = campos[0].trim();
                    String nombre = campos[1].trim();
                    String correo = campos[2].trim();
                    int ciclo = Integer.parseInt(campos[3].trim());
                    
                    Estudiante nuevoEst = new Estudiante(id, nombre, correo, ciclo);
                    
                    if (campos.length > 4) {
                        for (int k = 4; k < campos.length; k++) {
                            nuevoEst.insertarCalificacion(1, Double.parseDouble(campos[k].trim()));
                        }
                    }
                    
                    if (mapaEstudiantes.put(id, nuevoEst) == null) {
                        cargadosConExito++;
                    }
                }
            }
            System.out.println("Carga masiva finalizada. Estudiantes procesados en cola: " + cargadosConExito);
            bitacoraNavegacion.registrarAccion("Importación masiva CSV ejecutada con éxito.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Aviso: No se pudo procesar el archivo origen (" + e.getMessage() + ").");
        }
    }

    // RESPALDO DE SEGURIDAD
    public void exportarAArchivoCsv(String rutaArchivo) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Estudiante est : mapaEstudiantes.values()) {
                escritor.write(est.getCodigoIdentificacion() + "," +
                               est.getNombreCompleto() + "," +
                               est.getCorreoElectronico() + "," +
                               est.getCicloBase());
                escritor.newLine();
            }
            System.out.println("Copia de seguridad guardada con éxito en: " + rutaArchivo);
            bitacoraNavegacion.registrarAccion("Respaldo general del sistema en disco.");
        } catch (IOException e) {
            System.out.println("Error crítico al generar el respaldo: " + e.getMessage());
        }
    }
}