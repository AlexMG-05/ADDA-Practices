package ejercicio1;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import ejercicio1.DatosAlmacenes.Producto;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class SolucionAlmacen {
	
	public static SolucionAlmacen create(List<Integer> ls) {
		return new SolucionAlmacen(ls);
	}

	private Integer numproductos;
	private Map<Producto, Integer> solucion;

	private SolucionAlmacen(List<Integer> ls) {
		//TODO
		this.numproductos = 0;
		this.solucion = new HashMap<>();
		
		System.out.println(ls);
		
		for(int i = 0; i < ls.size(); i++) {
			if(ls.get(i) > 0) {
				Integer curProduct = i%DatosAlmacenes.getNumProductos();
				Integer curWarehouse = i/DatosAlmacenes.getNumProductos();
				Producto product = DatosAlmacenes.getProducto(curProduct);
				this.numproductos += ls.get(i);
				this.solucion.put(product, curWarehouse);
			}
		}
		
	}
	
	@Override
	public String toString() {
		return solucion.entrySet().stream()
		.map(p -> p.getKey().producto()+": Almacen "+p.getValue())
		.collect(Collectors.joining("\n", "Reparto de productos y almacen en el que se coloca cada uno de ellos:\n", String.format("\nProductos colocados: %d", numproductos)));
	}
	
	public Integer getNumProductos() {
    	return solucion.size();
    }
	
	public static void main (String [] args) {
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
			Ejercicio1AG alg = new Ejercicio1AG("resources/ejercicio1/" + file);
			
			AlgoritmoAG<List<Integer>, SolucionAlmacen> ap = AlgoritmoAG.of(alg);
			
			ap.ejecuta();
			
			System.out.println("\n" + file + " ================================ " + file);
			System.out.println(ap.bestSolution());
			System.out.println(file + " ================================ " + file + "\n");
		}
	}

}
