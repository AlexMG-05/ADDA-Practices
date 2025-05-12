package ejercicio3_manual;

import java.util.List;

import ejercicio3.DatosFestival;
import ejercicio3.SolucionFestival;
import us.lsi.common.List2;

public class FestivalState {
	FestivalProblem actual;
	Double acc;
	List<Integer> actions;
	List<FestivalProblem> anteriores;
	
	private FestivalState(FestivalProblem p, Double a, List<Integer> ls1, List<FestivalProblem> ls2) {
		actual = p; acc = a; actions = ls1; anteriores = ls2;
	}
	
	public static FestivalState initial() {
		FestivalProblem pi = FestivalProblem.initial();
		return of(pi, 0., List2.empty(), List2.empty());
	}
	
	public static FestivalState of(FestivalProblem p, Double a, List<Integer> ls1, List<FestivalProblem> ls2) {
		return new FestivalState(p, a, ls1, ls2);
	}
	
	public void forward(Integer action) {
		Integer curArea = actual.index() % DatosFestival.getNumAreas();
		Integer curTicket = actual.index() / DatosFestival.getNumAreas();
		acc += action*DatosFestival.getCosteAsignacion(curTicket, curArea);
		actions.add(action);
		anteriores.add(actual);
		actual = actual.neighbor(action);
	}
	
	public void back() {
		int last = actions.size()-1;
		FestivalProblem prev_prob = anteriores.get(last);
		
		Integer lastArea = last % DatosFestival.getNumAreas();
		Integer lastTicket = last / DatosFestival.getNumAreas();
		acc -= actions.get(last) * DatosFestival.getCosteAsignacion(lastTicket, lastArea);
		
		actions.remove(last);
		anteriores.remove(last);
		actual = prev_prob;
	}
	
	public List<Integer> alternatives(){
		return actual.actions();
	}
	
	public Double cota(Integer action) {
		return Double.MIN_VALUE;
	}
	
	public Boolean esSolucion() { //If all ticket type quotas have been achieved
		for (int remQuota:actual.ticketsRemPerType()) {
			if(remQuota!=0) {
				return false;
			}
		}
		return true;
	}
	
	public boolean esTerminal() {
		return actual.index() == DatosFestival.getNumTiposEntrada() * DatosFestival.getNumAreas();
	}
	
	public SolucionFestival getSolucion() {
		return SolucionFestival.of(actions);
	}
}
