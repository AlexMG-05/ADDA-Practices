package ejercicio3;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record FestivalEdge(FestivalVertex source, FestivalVertex target, Integer action, Double weight) implements SimpleEdgeAction<FestivalVertex, Integer>{
	
	public static FestivalEdge of(FestivalVertex source, FestivalVertex target, Integer action) {
		Integer curTicket = source.index()/DatosFestival.getNumAreas();
		Integer curArea = source.index()%DatosFestival.getNumAreas();
		Double weight = (double) (action*DatosFestival.getCosteAsignacion(curTicket, curArea));
		
		return new FestivalEdge(source, target, action, weight);
	}
	
	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
}
