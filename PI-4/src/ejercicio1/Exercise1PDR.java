package ejercicio1;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import us.lsi.common.List2;
import us.lsi.common.Map2;

public class Exercise1PDR {
	public static Map<WarehouseVertex, Spm> memory;
	public static Integer best;
	
	public static SolucionAlmacen search() {
		memory = Map2.empty();
		best = Integer.MIN_VALUE;
		pdrSearch(WarehouseVertex.initialVertex(), 0);
		return getSolucion();
	}
	
	public static Spm pdrSearch(WarehouseVertex vertex, Integer acc) {
		Spm res = null;
		Boolean isFinal = vertex.index().equals(DatosAlmacenes.getNumProductos());
		
		if(memory.keySet().contains(vertex)) {
			res = memory.get(vertex);
		} else if(isFinal) {
			res = Spm.of(null, 0);
			memory.put(vertex, res);
			
			if(acc > best) {
				best = acc;
			}
		} else {
			List<Spm> solutions = List2.empty();
			
			for(Integer action : vertex.actions()) {
				Double trim = trimmer(acc, vertex, action); //In PDR we have to trim (podar)
				
				if(trim < best) { //If even the best trim possible isn't better than the best value overall, then discard the whole trim
					continue;
				}
				WarehouseVertex neighbour = vertex.neighbor(action);
				
				int sum;
				if (action >= 0) sum = 1; //If we chose a warehouse, we sum 1 product to the total
				else sum = 0;	   
				
				Spm subSol = pdrSearch(neighbour, acc+sum);
				
				if(subSol!=null	) {
					Spm extended = Spm.of(action, subSol.weight()+sum);
					solutions.add(extended);
				}
			}
			res = solutions.stream().max(Comparator.naturalOrder()).orElse(null);
			
			if(res!=null) memory.put(vertex, res);
		}
		return res;
	}
	
	public static Double trimmer(Integer acc, WarehouseVertex v, Integer action) {
		int sum;
		if (action >= 0) sum = 1;
		else sum = 0;
		
		WarehouseVertex neighbour = v.neighbor(action);
		return acc+sum+neighbour.heuristic();
	}
	
	public static SolucionAlmacen getSolucion() {
		List<Integer> actions = List2.empty();
		WarehouseVertex vertx = WarehouseVertex.initialVertex();
		Spm spm = memory.get(vertx);
		
		while(spm!=null && spm.action()!=null) {
			actions.add(spm.action());
			vertx = vertx.neighbor(spm.action());
			spm = memory.get(vertx);
		}
		return SolucionAlmacen.create(actions);
	}
}
