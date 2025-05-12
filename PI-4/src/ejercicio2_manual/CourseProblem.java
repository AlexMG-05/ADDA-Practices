package ejercicio2_manual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ejercicio2.DatosCursos;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CourseProblem(Integer index, List<Integer> chosen, Integer remBudget, Set<Integer> covAreas) {
	/*
	 Properties:
	 -Index
	 -List of chosen courses
	 -Remaining budget
	 -Set of the areas already covered
	 */
	public static CourseProblem of(Integer index, List<Integer> chosen, Integer remBudget, Set<Integer> covAreas) {
		return new CourseProblem(index, chosen, remBudget, covAreas);
	}
	
	public static CourseProblem initial() {
		return of(0, List2.empty(), DatosCursos.getPresupuestoTotal(), Set2.empty());
	}

	public List<Integer> actions() {
		if(this.index >= DatosCursos.getNumCursos()) return List2.of();
		
		List<Integer> res = new ArrayList<>();
		res.add(0);
		
		if(DatosCursos.getCoste(this.index) <= this.remBudget) res.add(1);
		
		return res;
	}

	public CourseProblem neighbor(Integer action) {
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

	public Double heuristic() {
		Double h = 0.;
		
		for(int i = index; i < DatosCursos.getNumCursos(); i++) {
			h += DatosCursos.getRelevancia(i);
		}
		return h;
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
}
