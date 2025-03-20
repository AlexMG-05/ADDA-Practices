package ejercicio3;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import ejercicio1.Ejercicio1AG;
import ejercicio1.SolucionAlmacen;
import ejercicio2.Ejercicio2AG;
import ejercicio2.SolucionCursos;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

import java.util.HashMap;

public class SolucionFestival {

    public static SolucionFestival create(List<Integer> ls) {
        return new SolucionFestival(ls);
    }

    private int numAsignaciones;
    private Map<Integer, Integer> solucion;
    private Double costeTotal;
    private int unidadesTotales;

    private SolucionFestival(List<Integer> ls) {
    	numAsignaciones = ls.size();
    	solucion = new HashMap<>();
    	costeTotal = 0.0;
    	unidadesTotales = 0;
    	
    	for(int i =0;i<ls.size();i++) {
			Integer aforoAreaTipo = ls.get(i);
			Integer currentType = i / DatosFestival.getNumAreas();
			Integer currentArea = i % DatosFestival.getNumAreas();
    		costeTotal += DatosFestival.getCosteAsignacion(currentType, currentArea);
    		unidadesTotales += aforoAreaTipo;
    		solucion.put(i, aforoAreaTipo);
    	}
    	
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Resumen de asignaciones:\n");

        Map<Integer, Integer> aforoOcupadoPorArea = new HashMap<>();
        Map<Integer, Map<Integer, Integer>> entradasPorArea = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : solucion.entrySet()) {
            Integer tipoEntrada = entry.getKey() / DatosFestival.getNumAreas();
            Integer area = entry.getKey() % DatosFestival.getNumAreas();
            Integer unidades = entry.getValue();

            if (unidades > 0) {
                aforoOcupadoPorArea.put(area, aforoOcupadoPorArea.getOrDefault(area, 0) + unidades);
                entradasPorArea.computeIfAbsent(area, k -> new HashMap<>())
                        .put(tipoEntrada, entradasPorArea.get(area).getOrDefault(tipoEntrada, 0) + unidades);
            }
        }

        for (int i = 0; i < DatosFestival.getNumAreas(); i++) {
            Integer aforoOcupado = aforoOcupadoPorArea.getOrDefault(i, 0);
            if (aforoOcupado > 0) {
                result.append(String.format("Aforo área %s: %d/%d\n",
                        DatosFestival.getArea(i).nombre(),
                        aforoOcupado,
                        DatosFestival.getAforoMaximoArea(i)));

                entradasPorArea.getOrDefault(i, new HashMap<>()).forEach((tipoEntrada, unidades) ->
                        result.append(String.format("Tipo de entrada %s asignadas: %d unidades\n",
                                DatosFestival.getTipoEntrada(tipoEntrada).tipo(), unidades)
                ));
            }
        }

        result.append(String.format("\nCoste total: %.2f\nUnidades totales: %d\n", costeTotal, unidadesTotales));

        return result.toString();
    }

    public Integer getNumAsignaciones() {
        return numAsignaciones;
    }

    public Map<Integer, Integer> getSolucion() {
        return solucion;
    }

    public Double getCosteTotal() {
        return costeTotal;
    }

    public Integer getUnidadesTotales() {
        return unidadesTotales;
    }
    
    public static void main(String[] args) {
    	Locale.setDefault(Locale.of("en", "US"));

		AlgoritmoAG.ELITISM_RATE = 0.10;
		AlgoritmoAG.CROSSOVER_RATE = 0.95;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 1000;
		
		StoppingConditionFactory.MAX_ELAPSEDTIME = 30;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.ElapsedTime;
		
		List<String> files = List.of(
				"DatosEntrada1.txt",
				"DatosEntrada2.txt",
				"DatosEntrada3.txt");
		
		for (String file : files) {
			Ejercicio3AG alg = new Ejercicio3AG("resources/ejercicio3/" + file);
			
			AlgoritmoAG<List<Integer>, SolucionFestival> ap = AlgoritmoAG.of(alg);
			
			ap.ejecuta();
			
			System.out.println("\n" + file + " ================================ " + file);
			System.out.println(ap.bestSolution());
			System.out.println(file + " ================================ " + file + "\n");
		}
	}
}
