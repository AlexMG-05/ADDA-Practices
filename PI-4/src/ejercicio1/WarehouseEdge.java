package ejercicio1;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record WarehouseEdge(WarehouseVertex source, WarehouseVertex target, Integer action, Double weight) implements SimpleEdgeAction<WarehouseVertex, Integer>{
	
	public static WarehouseEdge of(WarehouseVertex source, WarehouseVertex target, Integer action) {
		Double weight = (action > -1) ? 1.0 : 0.0;
		return new WarehouseEdge(source, target, action, weight);
	}
	
	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
}
