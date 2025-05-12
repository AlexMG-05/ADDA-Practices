package ejercicio3_manual;

import java.util.ArrayList;
import java.util.List;

import ejercicio3.DatosFestival;
import us.lsi.common.List2;

public record FestivalProblem(Integer index, List<Integer> ticketsRemPerType, List<Integer> remSpaceAreas, List<Integer> ticketsAssigned, Double cost) {
	
	public static FestivalProblem initial() {
		List<Integer> tpt = new ArrayList<>();
		List<Integer> rsa = new ArrayList<>();
		List<Integer> t = new ArrayList<>();
		
		for(int type = 0; type < DatosFestival.getNumTiposEntrada(); type++) {
			tpt.add(DatosFestival.getCuotaMinima(type));
			for(int area = 0; area < DatosFestival.getNumAreas(); area++) {
				t.add(0);
			}
		}
		for(int area = 0; area < DatosFestival.getNumAreas(); area++) {
			rsa.add(DatosFestival.getAforoMaximoArea(area));
		}
		
		return of(0, tpt, rsa, t, 0.);
	}
	
	public static FestivalProblem of(Integer i, List<Integer> tpt, List<Integer> rsa, List<Integer> t, Double c) {
		return new FestivalProblem(i, tpt, rsa, t, c);
	}
	
	public List<Integer> actions(){
		Integer curTicket = index / DatosFestival.getNumAreas();
		if(index >= DatosFestival.getNumTiposEntrada()*DatosFestival.getNumAreas()) {
			return List2.empty();
		} else if (index==DatosFestival.getNumAreas() * DatosFestival.getNumTiposEntrada()-1) {
			return List.of(ticketsRemPerType.get(curTicket));
		}
		List<Integer> res = new ArrayList<>();
		Integer curArea = index % DatosFestival.getNumAreas();
		
		for(int i = 0; i <= ticketsRemPerType.get(curTicket); i++) {
			if(i<=remSpaceAreas.get(curArea)) res.add(i);
		}
		return res;
	}
	
	public FestivalProblem neighbor(Integer action) {
		List<Integer> tpt = new ArrayList<>(ticketsRemPerType);
		List<Integer> rsa = new ArrayList<>(remSpaceAreas);
		List<Integer> t = new ArrayList<>(ticketsAssigned);
		
		Integer curArea = index % DatosFestival.getNumAreas();
		Integer curTicket = index / DatosFestival.getNumAreas();
		Double newCost = (double) (this.cost + action*DatosFestival.getCosteAsignacion(curTicket, curArea));
		
		Integer tptValue = tpt.get(curTicket)-action;
		tpt.set(curTicket, tptValue);
		
		Integer rsaValue = rsa.get(curArea)-action;
		rsa.set(curArea, rsaValue);
		
		t.set(index, action);
		
		return of(index+1, tpt, rsa, t, newCost);
	}
	
	public Double heuristic() {
		double h = 0.;
		int aux = 0;
		for (int i = index; i < DatosFestival.getNumAreas() * DatosFestival.getNumTiposEntrada(); i++) {
			int area = i % DatosFestival.getNumAreas();
	        int ticket = i / DatosFestival.getNumAreas();
	        int remaining = ticketsRemPerType.get(ticket)-aux;
	        if(remaining > 0) {
	        	aux += remaining;
	        	h += remaining * DatosFestival.getCosteAsignacion(ticket, area);
	        }
		}
		return h;
	}
	
}
