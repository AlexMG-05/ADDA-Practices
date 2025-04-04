package ejercicio1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ejercicio1.DatosAlmacenes.Producto;

public class SolucionAlmacen {
	private Integer numproductos;
	private Map<Producto, Integer> solucion;
	
	public static SolucionAlmacen create(List<Integer> ls) {
		return new SolucionAlmacen(ls);
	}
	public static SolucionAlmacen empty() {
		return new SolucionAlmacen();
	}

	private SolucionAlmacen() { //Empty creation
		this.numproductos=0;
		this.solucion = new HashMap<>();
	}
	private SolucionAlmacen(List<Integer> ls) {
		//TODO
		this.solucion = new HashMap<>();
		
		for(int i = 0; i < ls.size(); i++) {
			Integer warehouse = ls.get(i);
			if(warehouse != null && warehouse >= 0) {
				Producto product = DatosAlmacenes.getProducto(i);
				this.solucion.put(product, warehouse); //Dictionary for products and warehouses
			}
		}
		this.numproductos = this.solucion.size();
		
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
