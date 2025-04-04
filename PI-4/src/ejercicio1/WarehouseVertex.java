package ejercicio1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record WarehouseVertex(Integer index, Integer QStored, List<Set<Integer>> storedProducts, List<Integer> remSpace) implements WarehouseVertexInterface {
	/*
	-Individual properties of each vertex:
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
		return of(0, 0, initialProducts, spaceWarehouses);
	}

	@Override
	public List<Integer> actions() {
		//Final case
		if(this.index >= DatosAlmacenes.getNumProductos()) {
			return List.of();
		}
		List<Integer> actions = new ArrayList<>();
		int curProduct = this.index;
		int volProduct = DatosAlmacenes.getMetrosCubicosProducto(curProduct);
		
		for (int i=0; i<DatosAlmacenes.getNumAlmacenes(); i++) { //In which warehouse to put the current product
			if(remSpace.get(i) >= volProduct) {
				boolean isIncompatible = storedProducts.get(i).stream()
						.anyMatch(p -> DatosAlmacenes.sonIncompatibles(p, curProduct) || DatosAlmacenes.sonIncompatibles(curProduct, p));
				if (!isIncompatible) {
					actions.add(i);
				}
			}
		}
		actions.add(-1); //Possibilty of adding no object
		return actions;
	}

	@Override
	public WarehouseVertex neighbor(Integer action) { //Action that led to this neighbour, that is, the warehouse we chose
		// This contains the values of the neighbouring vertices
		Integer newIndex = index+1;
		Integer newQStored = this.QStored;
		List<Set<Integer>> newStoredProducts = new ArrayList<>(this.storedProducts);
		List<Integer> newRemSpace = new ArrayList<>(this.remSpace);
		
		if(action >= 0) {
			newQStored++; //Updating accumulator
			Set<Integer> prodsPerWarehouse = new HashSet<>(newStoredProducts.get(action));
			prodsPerWarehouse.add(index);
			newStoredProducts.set(action, prodsPerWarehouse); //Updating the set of products of the warehouse that we chose
			int remainingSpace = newRemSpace.get(action)-DatosAlmacenes.getMetrosCubicosProducto(index);
			newRemSpace.set(action, remainingSpace);
		}
		return WarehouseVertex.of(newIndex, newQStored, newStoredProducts, newRemSpace);
	}

	@Override
	public WarehouseEdge edge(Integer a) {
		// TODO Auto-generated method stub
		return WarehouseEdge.of(this, this.neighbor(a), a);
	}
	
	public Boolean isGoal() {
		return index == DatosAlmacenes.getNumProductos();
	}

	@Override
	public Double heuristic() {
		// TODO Auto-generated method stub
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
}
