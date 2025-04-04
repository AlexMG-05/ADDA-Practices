package ejercicio1;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record WarehouseEdge(WarehouseVertexInterface source, WarehouseVertexInterface target, Integer action, Double weight) implements SimpleEdgeAction<WarehouseVertexInterface, Integer>{
	public static WarehouseEdge of(WarehouseVertexInterface src, WarehouseVertexInterface tgt, Integer action) {
		return new WarehouseEdge(src, tgt, action, action > -1 ? 1.0 : 0.0);
	}
}
