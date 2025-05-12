package ejercicio2_manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ejercicio2.DatosCursos;
import ejercicio2.SolucionCursos;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class CoursePDR {
	
	public static record Spm(Integer a, Integer weight) implements Comparable<Spm> {
		public static Spm of(Integer a, Integer weight) {
			return new Spm(a, weight);
		}
		
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static Map<CourseProblem, Spm> memory;
	public static Integer bestValue = Integer.MIN_VALUE;
	
	public static SolucionCursos search() {
		memory = Map2.empty();
		bestValue = Integer.MIN_VALUE;
		
		pdr_search(CourseProblem.initial(), 0, memory);
		return getSolucion();
	}
	
	private static Spm pdr_search(CourseProblem prob, Integer acc, Map<CourseProblem, Spm> mem) {
		
		Spm res = null;
		Boolean esTerminal = prob.index().equals(DatosCursos.getNumCursos());
		Boolean esSolucion = prob.goalHasSolution();
		
		if(memory.containsKey(prob)) {
			res = memory.get(prob);
			
		} else if(esTerminal && esSolucion) {
			res = Spm.of(null, 0);
			memory.put(prob, res);
			
			if(acc > bestValue) {
				bestValue = acc;
			}
		} else {
			List<Spm> solutions = List2.empty();
			
			for(Integer action : prob.actions()) {
				Double cota = prune(acc, prob, action);
				
				if(cota < bestValue) {
					continue;
				}
				CourseProblem neighbor = prob.neighbor(action);
				Integer relevance = DatosCursos.getRelevancia(prob.index());
				Spm s = pdr_search(neighbor, acc+relevance*action, memory);
				
				if(s != null) {
					Spm amp = Spm.of(action, s.weight() + relevance * action);
					solutions.add(amp);
				} 
			}
			res = solutions.stream().max(Comparator.naturalOrder()).orElse(null);
			if(res != null) 
				memory.put(prob, res);
		}
		return res;
	}
	
	private static Double prune(Integer accum, CourseProblem p, Integer action) {
		return accum + action + p.neighbor(action).heuristic();
	}
	
	public static SolucionCursos getSolucion() {
		List<Integer> actions = List2.empty();
		CourseProblem prob = CourseProblem.initial();
		Spm spm = memory.get(prob);
		
		while (spm != null && spm.a != null) {
			CourseProblem old = prob;
			actions.add(spm.a);
			prob = old.neighbor(spm.a);
			spm = memory.get(prob);
		}
		return SolucionCursos.of(actions);
	}
}
