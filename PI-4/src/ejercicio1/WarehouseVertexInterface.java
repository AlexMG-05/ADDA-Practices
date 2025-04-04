package ejercicio1;

import java.util.List;
import java.util.Set;

import us.lsi.graphs.virtual.VirtualVertex;

public interface WarehouseVertexInterface extends VirtualVertex<WarehouseVertexInterface, WarehouseEdge, Integer> {
	Integer index();
	Double heuristic();
	public static WarehouseVertex initial() {
		return WarehouseVertex.of(0, 0, List.of(Set.of()), List.of());
	}
}
