package ejercicio3;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;


//Voy a representar el cromosoma como un array bidimensional [tipo, area] aplanado

public class Ejercicio3AG implements ValuesInRangeData<Integer, SolucionFestival>{
	public Ejercicio3AG(String file) {
		DatosFestival.iniDatos(file);

	}

	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return DatosFestival.getNumAreas() * DatosFestival.getNumTiposEntrada();
	}

	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Range;
	}

	@Override
	public Double fitnessFunction(List<Integer> ls) {
		// TODO Auto-generated method stub
		Double goal = 0.0;
		Double penalties = 0.0;
		Map<Integer, Integer> aforoPorArea = new HashMap<Integer, Integer>();
		Map<Integer, Integer> aforoPorTipo = new HashMap<Integer, Integer>();

		
		for(int i=0; i<ls.size();i++) {
			Integer aforoAreaTipo = ls.get(i);
			Integer currentType = i / DatosFestival.getNumAreas();
			Integer currentArea = i % DatosFestival.getNumAreas();
			
			if(aforoAreaTipo>0) goal+= aforoAreaTipo * DatosFestival.getCosteAsignacion(currentType, currentArea);
			
			if(aforoPorArea.containsKey(currentArea)) {
				aforoPorArea.put(currentArea, aforoPorArea.get(currentArea) + aforoAreaTipo);
			} else {
				aforoPorArea.put(currentArea, aforoAreaTipo);
			}
			if(aforoPorTipo.containsKey(currentType)) {
				aforoPorTipo.put(currentType, aforoPorTipo.get(currentType) + aforoAreaTipo);
			} else {
				aforoPorTipo.put(currentType, aforoAreaTipo);
			}
			
			
		}
		
		for(Integer key: aforoPorArea.keySet()) {
			if(DatosFestival.getAforoMaximoArea(key) < aforoPorArea.get(key)) {
				penalties += (aforoPorArea.get(key) - DatosFestival.getAforoMaximoArea(key));
			}
		}
		
		for(Integer key: aforoPorTipo.keySet()) {
			if(DatosFestival.getCuotaMinima(key) > aforoPorTipo.get(key)) {
				penalties += ((DatosFestival.getCuotaMinima(key) - aforoPorTipo.get(key))*200)	; //Cambiad el valor de la constante
			}
		}
		
		
		return -goal- 2000*penalties;  //Cambiad el valor de la constante
	}

	@Override
	public SolucionFestival solucion(List<Integer> value) {
		// TODO Auto-generated method stub
		return SolucionFestival.create(value);
	}

	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		return DatosFestival.getAforoMaximoArea(i % DatosFestival.getNumAreas());
	}

	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}
}
