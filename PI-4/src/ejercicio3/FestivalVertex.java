package ejercicio3;

import java.util.ArrayList;
import java.util.List;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record FestivalVertex(Integer index, List<Integer> tickets, List<Integer> remSpaceAreas, Integer costs) implements VirtualVertex<FestivalVertex, FestivalEdge, Integer>{
	/*
	 Individual properties:
	 -Current index
	 -Tickets assigned
	 -Remaining space in each area
	 -Accumulated costs
	 
	 Modelled as a matrix, where rows are ticket types and columns are areas
	 */
	public static FestivalVertex of(Integer index, List<Integer> tickets, List<Integer> remSpaceAreas, Integer costs) {
		return new FestivalVertex(index, tickets, remSpaceAreas, costs);
	}
	
	public static FestivalVertex initialVertex() {
		List<Integer> iniTickets = new ArrayList<>();
		List<Integer> remSpaceAreas = new ArrayList<>();
		
		for(int i = 0; i < DatosFestival.getNumTiposEntrada(); i++) {
			iniTickets.add(0);
		}
		for(int i = 0; i < DatosFestival.getNumAreas(); i++) {
			remSpaceAreas.add(DatosFestival.getAforoMaximoArea(i));
		}
		
		return of(0, iniTickets, remSpaceAreas, 0);
	}
	@Override
	public List<Integer> actions() {
		if(this.index >= DatosFestival.getNumTiposEntrada()*DatosFestival.getNumAreas()) {
			return List2.empty();
		}
		
		List<Integer> res = new ArrayList<>();
		Integer curTicket = this.index/DatosFestival.getNumAreas();
		Integer curArea = this.index%DatosFestival.getNumAreas();
		
		for(int i=0; i <= DatosFestival.getCuotaMinima(curTicket)-this.tickets.get(curTicket); i++) {
			if(i<=this.remSpaceAreas.get(curArea)) res.add(i);
		}
		
		return res;
	}

	@Override
	public FestivalVertex neighbor(Integer action) {
		List<Integer> newTickets = new ArrayList<>(this.tickets);
		List<Integer> newRemSpaceAreas = new ArrayList<>(this.remSpaceAreas);
		Integer newCost = this.costs;
		
		Integer curTicket = this.index/DatosFestival.getNumAreas();
		Integer curArea = this.index%DatosFestival.getNumAreas();
		
		if(action>0) {
			newTickets.set(curTicket, this.tickets.get(curTicket)+action);
			newRemSpaceAreas.set(curArea, this.remSpaceAreas.get(curArea)-action);
			newCost = this.costs+DatosFestival.getCosteAsignacion(curTicket, curArea);
		}
		
		return of(index+1, newTickets, newRemSpaceAreas,newCost);
	}

	@Override
	public FestivalEdge edge(Integer action) {
		return FestivalEdge.of(this, this.neighbor(action), action);
	}
	
	public Boolean goal() {
		return this.index == DatosFestival.getNumTiposEntrada()*DatosFestival.getNumAreas();
	}
	
	public Boolean goalHasSolution() {
		Boolean minQuota = true;
		Boolean capacity = true;
		
		for(int i=0; i<DatosFestival.getNumTiposEntrada(); i++) {
			if(this.tickets.get(i) < DatosFestival.getCuotaMinima(i)) minQuota = false;
		}
		for(int i = 0; i<DatosFestival.getNumAreas(); i++) {
			if(this.remSpaceAreas.get(i)<0) capacity = false;
		}
		
		return minQuota && capacity;
	}
	
	public Integer greedyAction() {
		List<Integer> actions = new ArrayList<>();
		
		Integer curTicket = this.index/DatosFestival.getNumAreas();
		Integer curArea = this.index%DatosFestival.getNumAreas();
		
		for(int i=0; i <= DatosFestival.getCuotaMinima(curTicket)-this.tickets.get(curTicket); i++) {
			if(i<=this.remSpaceAreas.get(curArea)) actions.add(i);
		}

		if(!actions.isEmpty()) {
			return actions.getLast();
		}
		return null;
	}
	
	public String toGraph() {
		return String.format("%d", index);
	}

}
