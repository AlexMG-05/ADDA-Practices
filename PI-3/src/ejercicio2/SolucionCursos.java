package ejercicio2;

import java.util.*;
import java.util.stream.Collectors;

import ejercicio1.Ejercicio1AG;
import ejercicio1.SolucionAlmacen;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class SolucionCursos {

    public static SolucionCursos create(List<Integer> ls) {
        return new SolucionCursos(ls);
    }

    private Integer numCursos;
    private Map<Integer, Integer> solucion;
    private Double puntuacionTotal;
    private Integer costeTotal;

    private SolucionCursos(List<Integer> ls) {
       numCursos = 0;
       solucion = new HashMap<Integer, Integer>(); //Voy a poner 1 si el curso está elegido y 0 si no. No sé por qué viene como <Integer, Integer>
       puntuacionTotal = 0.0; //Es un entero entre 0 y 5, por algún motivo lo han hecho decimal
       costeTotal = 0;
       
       
       
       for(int i=0; i<ls.size();i++) {
    	   
    	   if(ls.get(i)==1) {
    		   numCursos+=1;
    		   solucion.put(i, 1);
    		   costeTotal+= DatosCursos.getCoste(i);
    		   puntuacionTotal+=DatosCursos.getRelevancia(i);
    		   
    	   } 
    	   
       }
       
    }

    @Override
    public String toString() {
        return solucion.entrySet().stream()
                .map(p -> "Curso " + p.getKey() + ": Seleccionado")
                .collect(Collectors.joining("\n", "Cursos seleccionados:\n", String.format("\nTotal de cursos seleccionados: %d\nPuntuación total: %.2f\nCoste total: %d", numCursos, puntuacionTotal, costeTotal)));
    }

    public Integer getNumCursos() {
        return numCursos;
    }

    public Map<Integer, Integer> getSolucion() {
        return solucion;
    }

    public Double getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public Integer getCosteTotal() {
        return costeTotal;
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

		List<String> files = List.of(
				"DatosEntrada1.txt",
				"DatosEntrada2.txt",
				"DatosEntrada3.txt");
		
		for (String file : files) {
			Ejercicio2AG alg = new Ejercicio2AG("resources/ejercicio2/" + file);
			
			AlgoritmoAG<List<Integer>, SolucionCursos> ap = AlgoritmoAG.of(alg);
			
			ap.ejecuta();
			
			System.out.println("\n" + file + " ================================ " + file);
			System.out.println(ap.bestSolution());
			System.out.println(file + " ================================ " + file + "\n");
		}
	}
    
    
}