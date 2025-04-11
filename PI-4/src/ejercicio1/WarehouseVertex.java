package ejercicio1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record WarehouseVertex(Integer index, Integer QStored, List<Set<Integer>> storedProducts, List<Integer> remSpace) implements VirtualVertex<WarehouseVertex, WarehouseEdge, Integer>{
	/*
	Individual properties of the vertex:
	-Current index
	-Quantity already stored (accumulator)
	-Products stored in each warehouse
	-Remaining space in each Warehouse
	*/
	public static WarehouseVertex of(Integer index, Integer QStored, List<Set<Integer>> storedProducts, List<Integer> remSpace) {
		return new WarehouseVertex(index, QStored, storedProducts, remSpace);
	}
	
	public static WarehouseVertex initialVertex() {
		List<Set<Integer>> initialProducts = new ArrayList<>();
		List<Integer> spaceWarehouses = new ArrayList<>();
		
		for(int i=0; i < DatosAlmacenes.getNumAlmacenes(); i++) {
			initialProducts.add(new HashSet<>());
			spaceWarehouses.add(DatosAlmacenes.getMetrosCubicosAlmacen(i));
		}
		return WarehouseVertex.of(0, 0, initialProducts, spaceWarehouses);
	}
	
	public List<Integer> actions() {
		if(this.index >= DatosAlmacenes.getNumProductos()) { //Final case
			return List2.empty();
		}
		
		int volProduct = DatosAlmacenes.getMetrosCubicosProducto(index);
		
		List<Integer> actions = IntStream.range(0, DatosAlmacenes.getNumAlmacenes())
				.filter(j->hasNoIncompatibilities(index, j))
				.filter(j-> remSpace.get(j) >= volProduct)
				.boxed().toList();
		
		List<Integer> res = new ArrayList<>(actions);
		res.add(-1); //Possibilty of not adding the current object
		
		return res;
	}
	
	public WarehouseVertex neighbor(Integer action) { //Action that led to this neighbour, that is, the warehouse we chose
		Integer newQStored = this.QStored;
		List<Set<Integer>> newStoredProducts = new ArrayList<>(this.storedProducts);
		List<Integer> newRemSpace = new ArrayList<>(this.remSpace);
		
		if(action >= 0) {
			newQStored++; 
			Set<Integer> prodsInWarehouse = new HashSet<>(newStoredProducts.get(action));
			prodsInWarehouse.add(index);
			newStoredProducts.set(action, prodsInWarehouse); //Updating the set of products of the warehouse that we chose
			int remainingSpace = newRemSpace.get(action)-DatosAlmacenes.getMetrosCubicosProducto(index);
			newRemSpace.set(action, remainingSpace);
		}
		return WarehouseVertex.of(index+1, newQStored, newStoredProducts, newRemSpace);
	}

	public WarehouseEdge edge(Integer a) {
		return WarehouseEdge.of(this, this.neighbor(a), a);
	}
	
	public Boolean goal() {
		return this.index == DatosAlmacenes.getNumProductos();
	}
	
	public Boolean goalHasSolution() {
		return true;
	}
	
	public Boolean hasNoIncompatibilities(Integer product, Integer warehouse) {
		Set<Integer> productsInWarehouse = storedProducts.get(warehouse);
		
		return productsInWarehouse.stream().noneMatch(v -> DatosAlmacenes.sonIncompatibles(product, v) || DatosAlmacenes.sonIncompatibles(v, product));
	}
	
	public Integer greedyAction() {
	    // Comparator to prioritize warehouses with the most remaining space
	    Comparator<Integer> cmp = Comparator.comparing(j -> remSpace.get(j), Comparator.reverseOrder());

	    // Find the best warehouse for the current product
	    Integer action = IntStream.range(0, DatosAlmacenes.getNumAlmacenes())
	            .filter(j -> hasNoIncompatibilities(index, j))
	            .filter(j -> remSpace.get(j) >= DatosAlmacenes.getMetrosCubicosProducto(index))
	            .boxed()
	            .max(cmp)
	            .orElse(-1);

	    return action;
	}

	public Double heur() {
		int possible = 0;
		
		for (int i = index; i < DatosAlmacenes.getNumProductos(); i++) {
			int volumeProd = DatosAlmacenes.getMetrosCubicosProducto(i);
			final int product = i;
			
			for (int j = 0; j < remSpace.size(); j++) {
				if(remSpace.get(j) >= volumeProd) {
					Boolean compatible = storedProducts.get(j).stream().noneMatch(k->DatosAlmacenes.sonIncompatibles(k, product)
							|| DatosAlmacenes.sonIncompatibles(product, k));
					
					if(compatible) {
						possible++;
					}
				}
			}
			
		}
		return (double) possible;
	}
	
	public String toGraph() {
		return String.format("%d", index);
	}
}
