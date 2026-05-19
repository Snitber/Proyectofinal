// Archivo: GobernadorCampus.java
// Paso 6: Grafo por Matriz de Adyacencia y Algoritmo de Dijkstra

public class GobernadorCampus {

    // Nombres de los 5 edificios del campus modificados para Jhonnier
    public static final String[] EDIFICIOS = {
        "Bloque Principal", "Laboratorios Ingenieria", "Biblioteca Norte", "Auditorio Mayor", "Bienestar Universitario"
    };
    
    private final int totalNodos = 5;
    private final double[][] redCaminerias;

    public GobernadorCampus() {
        this.redCaminerias = new double[totalNodos][totalNodos];
        inicializarRed();
    }

    private void inicializarRed() {
        // Llenamos la matriz con un valor "infinito" simulado para representar que no hay paso directo
        for (int i = 0; i < totalNodos; i++) {
            for (int j = 0; j < totalNodos; j++) {
                if (i == j) this.redCaminerias[i][j] = 0;
                else this.redCaminerias[i][j] = Double.MAX_VALUE;
            }
        }

        // Configuración de aristas con metros totalmente diferentes a tu proyecto
        configurarPaso(0, 1, 145.0); // Bloque Principal <-> Laboratorios (145m)
        configurarPaso(0, 2, 280.0); // Bloque Principal <-> Biblioteca (280m)
        configurarPaso(1, 2, 95.0);  // Laboratorios <-> Biblioteca (95m)
        configurarPaso(1, 3, 310.0); // Laboratorios <-> Auditorio (310m)
        configurarPaso(2, 4, 185.0); // Biblioteca <-> Bienestar (185m)
        configurarPaso(3, 4, 120.0); // Auditorio <-> Bienestar (120m)
    }

    private void configurarPaso(int origen, int destino, double metros) {
        this.redCaminerias[origen][destino] = metros;
        this.redCaminerias[destino][origen] = metros; // Grafo no dirigido (doble sentido)
    }

    // Algoritmo de Dijkstra refactorizado estructuralmente
    public void calcularRutaOptima(int puntoInicio, int puntoFin) {
        double[] distanciasCalculadas = new double[totalNodos];
        boolean[] marcasVisitado = new boolean[totalNodos];
        int[] mapaRastreo = new int[totalNodos];

        for (int i = 0; i < totalNodos; i++) {
            distanciasCalculadas[i] = Double.MAX_VALUE;
            marcasVisitado[i] = false;
            mapaRastreo[i] = -1;
        }

        distanciasCalculadas[puntoInicio] = 0;

        for (int cuenta = 0; cuenta < totalNodos - 1; cuenta++) {
            int u = buscarNodoMasCercano(distanciasCalculadas, marcasVisitado);
            if (u == -1) break;
            
            marcasVisitado[u] = true;

            for (int v = 0; v < totalNodos; v++) {
                if (!marcasVisitado[v] && redCaminerias[u][v] != Double.MAX_VALUE && 
                    distanciasCalculadas[u] != Double.MAX_VALUE && 
                    distanciasCalculadas[u] + redCaminerias[u][v] < distanciasCalculadas[v]) {
                    
                    distanciasCalculadas[v] = distanciasCalculadas[u] + redCaminerias[u][v];
                    mapaRastreo[v] = u;
                }
            }
        }

        imprimirResultadoCamino(puntoInicio, puntoFin, distanciasCalculadas[puntoFin], mapaRastreo);
    }

    private int buscarNodoMasCercano(double[] distancias, boolean[] visitados) {
        double minimoValor = Double.MAX_VALUE;
        int indiceMinimo = -1;

        for (int i = 0; i < totalNodos; i++) {
            if (!visitados[i] && distancias[i] <= minimoValor) {
                minimoValor = distancias[i];
                indiceMinimo = i;
            }
        }
        return indiceMinimo;
    }

    private void imprimirResultadoCamino(int inicio, int fin, double metrosTotales, int[] rastreo) {
        if (metrosTotales == Double.MAX_VALUE) {
            System.out.println("No existe una ruta disponible entre esos puntos.");
            return;
        }

        System.out.println("\n=========================================");
        System.out.println("RESULTADO DE RUTA ÓPTIMA - CAMPUS");
        System.out.println("Origen: " + EDIFICIOS[inicio]);
        System.out.println("Destino: " + EDIFICIOS[fin]);
        System.out.println("Distancia calculada: " + metrosTotales + " metros.");
        System.out.print("Trayectoria sugerida: ");
        
        // Reconstrucción iterativa inversa del camino
        String caminoTexto = EDIFICIOS[fin];
        int nodoActual = fin;
        while (rastreo[nodoActual] != -1) {
            nodoActual = rastreo[nodoActual];
            caminoTexto = EDIFICIOS[nodoActual] + " -> " + caminoTexto;
        }
        System.out.println(caminoTexto);
        System.out.println("=========================================");
    }

    // Método auxiliar de búsqueda de texto para el menú
    public int buscarIndicePorNombre(String nombreBloque) {
        for (int i = 0; i < totalNodos; i++) {
            if (EDIFICIOS[i].equalsIgnoreCase(nombreBloque.trim())) {
                return i;
            }
        }
        return -1;
    }
}