package ejercicio4;


import java.util.LinkedList;
import java.util.List;


import us.lsi.ag.SeqNormalData;

import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;


public class Ejercicio4AG implements SeqNormalData<SolucionEstaciones>{
	public Ejercicio4AG(String file){
		DatosTren.iniData(file);
	}

	@Override
	public ChromosomeType type() {

		return ChromosomeType.Permutation; //Andrés nos recomendó hacerlo así en lugar de con PermutationSublist, que es como venía en la plantilla
	}


	@Override
	public Integer itemsNumber() {

		return DatosTren.getNumStations(); //Porque tiene que pasar por cada estacion una vez, salvo la primera que se repite al final
	}

	@Override
	public Double fitnessFunction(List<Integer> ls) {
	    Double goal = 0.0;
	    Double penalties = 0.0;
	    boolean consecutiveStationsWithGreatSatisfaction = false;

	    List<Integer> visitedStationsIndexes = new LinkedList<>();
	    double totalCostJourney = 0.0;

	    for (int i = 1; i < ls.size()-1; i++) { 
	        int currentStation = ls.get(i);
	        int previousStation = ls.get(i - 1);


	        if (visitedStationsIndexes.contains(currentStation)) {
	            penalties += 50;
	        }

	 
	        if (DatosTren.areConnected(previousStation, currentStation)==false) {
	            penalties += 200;
	            //return -2000000.0;
	            //System.out.println("No están conectadas " + DatosTren.getStation(currentStation).nombre() + " y " + DatosTren.getStation(previousStation).nombre());
	        }

	        visitedStationsIndexes.add(currentStation);

	     
	        goal += DatosTren.getAverageTimeSection(previousStation, currentStation);

	     
	        totalCostJourney += DatosTren.sectionCost(previousStation, currentStation);

	        
	        if (DatosTren.getClientsSatisfaction(previousStation) >= 7 &&
	            DatosTren.getClientsSatisfaction(currentStation) >= 7) {
	            consecutiveStationsWithGreatSatisfaction = true;
	        }
	    }
	    
	    if (DatosTren.areConnected(ls.get(0), ls.get(ls.size()-1))==false) {
            penalties += 200;
        }

	   
	    if (totalCostJourney > DatosTren.costAllSections() * 3 / 4) {
	        penalties += 50;
	    }

	   
	    if (!consecutiveStationsWithGreatSatisfaction) {
	        penalties += 100;
	    }

	    return goal/DatosTren.getNumStations() - 1000 * penalties;
	}

	@Override
	public SolucionEstaciones solucion(List<Integer> ls) {
		// TODO Auto-generated method stub
		return SolucionEstaciones.create(ls);
	}

}
