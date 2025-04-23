package ejercicio2;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CourseEdge(CourseVertex source, CourseVertex target, Integer action, Double weight) implements SimpleEdgeAction<CourseVertex, Integer>{
	
	public static CourseEdge of(CourseVertex source, CourseVertex target, Integer action) {
		Double weight = action == 1? weight = (double) DatosCursos.getRelevancia(source.index()) : 0.;
		
		return new CourseEdge(source, target, action, weight);
	}
}
