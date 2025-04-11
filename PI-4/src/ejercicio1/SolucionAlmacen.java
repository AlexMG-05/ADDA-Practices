package ejercicio1;

import java.util.*;
import java.util.stream.Collectors;
import org.jgrapht.GraphPath;
import ejercicio1.DatosAlmacenes.Producto;

public class SolucionAlmacen {
	private Integer numproductos;
	private Map<Producto, Integer> solucion;
	private List<Integer> path;
	
	public static SolucionAlmacen of(List<Integer> ls) {
		return new SolucionAlmacen(ls);
	}
	
	private SolucionAlmacen(List<Integer> ls) {
		this.numproductos = 0;
		this.solucion = new HashMap<>();
		
		for(int i=0; i<ls.size();i++) {
			if(ls.get(i)>-1) {
				solucion.put(DatosAlmacenes.getProducto(i), ls.get(i));
			}
		}
		numproductos = solucion.size();
	}
	
	public static SolucionAlmacen of(GraphPath<WarehouseVertex, WarehouseEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionAlmacen res = of(ls);
		res.path = ls;
		return res;
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
}

