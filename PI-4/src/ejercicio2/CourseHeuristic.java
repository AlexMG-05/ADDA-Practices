package ejercicio2;

import java.util.function.Predicate;
import java.util.stream.IntStream;

public class CourseHeuristic {
	public static Double heuristic(CourseVertex src, Predicate<CourseVertex> goal, CourseVertex tgt) {
		return IntStream.range(src.index(), DatosCursos.getNumCursos())
				.mapToDouble(DatosCursos::getRelevancia) 
		        .sum();
	}
}
