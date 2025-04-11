package ejercicio1;

import java.util.function.Predicate;
import java.util.stream.IntStream;

public class WarehouseHeuristic {
	public static Double heuristic(WarehouseVertex v1, Predicate<WarehouseVertex> goal, WarehouseVertex v2) {
        return IntStream.range(v1.index(), DatosAlmacenes.getNumProductos())
                .mapToDouble(i -> 1.0) 
                .sum();
    }
}
