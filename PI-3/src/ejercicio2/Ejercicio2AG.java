package ejercicio2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class Ejercicio2AG implements ValuesInRangeData<Integer, SolucionCursos> {
	public Ejercicio2AG(String file){
		DatosCursos.iniDatos(file);
	}

	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return DatosCursos.getNumCursos();
	}

	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Binary;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
	    Double goal = 0.0;
	    Double error = 0.0;
	    int totalSelected = 0;
	    int totalDuration = 0;
	    int totalCost = 0;
	    Map<Integer, Integer> coursesPerArea = new HashMap<>();

	    for (int i = 0; i < size(); i++) {
	        if (value.get(i) == 1) {
	            goal += DatosCursos.getRelevancia(i);
	            totalSelected++;
	            totalDuration += DatosCursos.getDuracion(i);
	            totalCost += DatosCursos.getCoste(i);

	            int area = DatosCursos.getArea(i);
	            coursesPerArea.put(area, coursesPerArea.getOrDefault(area, 0) + 1);
	        }
	    }

	    // Constraint 1: At least one course per area
	    error += (DatosCursos.getNumAreas() - coursesPerArea.size()) * 20;

	    // Constraint 2: Technology courses >= courses from any other area
	    int techCourses = coursesPerArea.getOrDefault(0, 0);
	    int maxNonTechCourses = coursesPerArea.entrySet().stream()
	        .filter(entry -> entry.getKey() != 0)
	        .mapToInt(Map.Entry::getValue)
	        .max()
	        .orElse(0);
	    if (techCourses < maxNonTechCourses) {
	        error += (maxNonTechCourses - techCourses) * 10;
	    }

	    // Constraint 3: Average duration >= 20 hours
	    if (totalSelected > 0 && (totalDuration / totalSelected) < 20) {
	        error += (20 - (totalDuration / totalSelected)) * 10;
	    }

	    // Constraint 4: Total cost <= budget
	    if (totalCost > DatosCursos.getPresupuestoTotal()) {
	        error += (totalCost - DatosCursos.getPresupuestoTotal()) * 10;
	    }

	    // Fitness value
	    return goal - 1000 *error;
	}


	public SolucionCursos solucion(List<Integer> value) {
		// TODO Auto-generated method stub
		return SolucionCursos.create(value);
	}

	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}