package ejercicio1;

import java.util.List;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class Ejercicio1AG implements BinaryData<SolucionAlmacen>{
	public Ejercicio1AG(String file) {
		DatosAlmacenes.iniDatos(file);
	}
	
	public SolucionAlmacen solucion(List<Integer> ls) {
		return SolucionAlmacen.create(ls);
	}
	
	public Integer size() {
		return DatosAlmacenes.getNumAlmacenes() * DatosAlmacenes.getNumProductos();
	}
	
	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}
	
	public Double fitnessFunction(List<Integer> ls) {
		Double goal = 0.0;
		Double error = 0.0;
		
		Double singleRestriction = 0.;
		Double spaceRestriction = 0.;
		
		Integer nProductos = DatosAlmacenes.getNumProductos();
		Integer nAlmacenes = DatosAlmacenes.getNumAlmacenes();
		
		//Goal calculation
		for (int i = 0; i < size(); i++) {
			if(ls.get(i) > 0) {
				goal += ls.get(i);
			}
		}
		//Single restriction
		for (int i=0; i<nProductos; i++) {
			singleRestriction = 0.;
		
			for(int j = 0; j < nAlmacenes; j++) {
				Integer index = j*nProductos+i;
				
				if(ls.get(index) == 1) {
					singleRestriction++;
				}
			}
			if(singleRestriction >1) {
				error += singleRestriction;
			}
		}
		
		//Space restriction
		for(int j = 0; j<nAlmacenes; j++) {
			spaceRestriction = 0.;
			
			for (int i=0; i<nProductos; i++) {
				Integer index = j*nProductos+i;
				
				if(ls.get(index) ==1) {
					spaceRestriction += DatosAlmacenes.getMetrosCubicosProducto(i);
				}
			}
			if(spaceRestriction > DatosAlmacenes.getMetrosCubicosAlmacen(j)) {
				error+=spaceRestriction;
			}
		}
		
		//Incompability restriction
		for (int j = 0; j < nAlmacenes; j++) {
			for(int i=0; i<nProductos;i++) {
				Integer firstIndex = j*nProductos+i;
				
				if(ls.get(firstIndex) == 1) {
					for(int z = i + 1; z < nProductos; z++) {
						Integer secondIndex = j * nProductos + z;
						
						if(ls.get(secondIndex) == 1 && DatosAlmacenes.esIncompatible(i, z) == 1) {
							error++;
						}
					}
				}
			}
		}
		return goal - 1000*Math.pow(error, 2);
	}
}
