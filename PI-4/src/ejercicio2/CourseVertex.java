package ejercicio2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CourseVertex(Integer index, List<Integer> chosen, Integer remBudget, Set<Integer> covAreas) implements VirtualVertex<CourseVertex, CourseEdge, Integer>{
	/*
	 Properties:
	 -Index
	 -List of chosen courses
	 -Remaining budget
	 -Set of the areas already covered
	 */
	public static CourseVertex of(Integer index, List<Integer> chosen, Integer remBudget, Set<Integer> covAreas) {
		return new CourseVertex(index, chosen, remBudget, covAreas);
	}
	
	public static CourseVertex initialVertex() {
		return of(0, List2.empty(), DatosCursos.getPresupuestoTotal(), Set2.empty());
	}

	@Override
	public List<Integer> actions() {
		if(this.index >= DatosCursos.getNumCursos()) return List2.of();
		
		List<Integer> res = new ArrayList<>();
		res.add(0);
		
		if(DatosCursos.getCoste(this.index) <= this.remBudget) res.add(1);
		
		return res;
	}

	@Override
	public CourseVertex neighbor(Integer action) {
		List<Integer> newChosen = new ArrayList<>(this.chosen);
		Integer newRemBudget = this.remBudget;
		Set<Integer> newCovAreas = new HashSet<>(this.covAreas);
		
		if(action==1) {
			newChosen.add(this.index);
			newRemBudget = this.remBudget - DatosCursos.getCoste(this.index);
			newCovAreas.add(DatosCursos.getArea(this.index));
		}
		
		return of(index+1, newChosen, newRemBudget, newCovAreas);
	}

	@Override
	public CourseEdge edge(Integer action) {
		return CourseEdge.of(this, this.neighbor(action), action);
	}
	
	public Boolean goal() {
		return this.index == DatosCursos.getNumCursos();
	}
	
	public Boolean goalHasSolution() {
		Boolean allAreasCovered = true;
		Boolean techCourses = true;
		Boolean avgDuration = true;
		
		Integer numTechCourses = (int) this.chosen.stream().filter(c->c==0).count();
		Integer numOtherCourse;
		Double averageDur = this.chosen.stream().mapToDouble(x->DatosCursos.getDuracion(x)).sum() / this.chosen.size();
		
		if(this.covAreas.size() < DatosCursos.getNumAreas()) allAreasCovered = false;
		if(averageDur<20) avgDuration = false;
		
		for(int course:this.chosen) {
			if(course == 0) continue;
			
			numOtherCourse = (int) this.chosen.stream().filter(c->c==course).count();
			if(numTechCourses < numOtherCourse) {
				techCourses = false;
				break;
			}
		}
		
		return allAreasCovered && techCourses && avgDuration;
	}
	
	public String toGraph() {
		return String.format("%d", index);
	}
}

