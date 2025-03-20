package ejercicio4;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;

public class SolucionEstaciones {

    public static SolucionEstaciones create(List<Integer> ls) {
        return new SolucionEstaciones(ls);
    }
    
    private Integer numEstaciones;
    private List<Estacion> camino;
    private Double tiempoTotal;
    private Double tiempoMedio;

    private SolucionEstaciones(List<Integer> ls) {

    	numEstaciones = DatosTren.getNumStations();
    	camino = new LinkedList<Estacion>();
    	tiempoTotal = 0.0;


    	for(int i=0;i<ls.size();i++) {
    		
    		camino.add(DatosTren.getStation(ls.get(i)));
    		try {
    		tiempoTotal+= DatosTren.getAverageTimeSection( i-1, i);
    		} catch(Exception e) {
    			tiempoTotal+=0;
    		}
    		
    	}
    	camino.add(DatosTren.getStation(ls.get(0)));
    	tiempoMedio = tiempoTotal / numEstaciones;
    	
    	
    	
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Resumen del recorrido:\n");

        result.append("Camino seguido: ").append(camino.stream()
                .map(Estacion::nombre)
                .collect(Collectors.joining(" -> "))).append("\n");

        result.append(String.format("Tiempo total: %.2f min\n", tiempoTotal));
        result.append(String.format("Tiempo medio por estación: %.2f min\n", tiempoMedio));

        return result.toString();
    }

    public Integer getNumEstaciones() {
        return numEstaciones;
    }

    public List<Estacion> getCamino() {
        return camino;
    }

    public Double getTiempoTotal() {
        return tiempoTotal;
    }

    public Double getTiempoMedio() {
        return tiempoMedio;
    }
    
    
    public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		AlgoritmoAG.ELITISM_RATE = 0.10;
		AlgoritmoAG.CROSSOVER_RATE = 0.95;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 1000;

		StoppingConditionFactory.NUM_GENERATIONS = 10000;
		StoppingConditionFactory.MAX_ELAPSEDTIME = 50;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;

		Ejercicio4AG p = new Ejercicio4AG("resources/ejercicio4/DatosEntrada1.txt");

		AlgoritmoAG<List<Integer>, SolucionEstaciones> ap = AlgoritmoAG.of(p);
		ap.ejecuta();

		System.out.println("================================");
		System.out.println(ap.bestSolution());
		System.out.println("================================");
	}
    
}
