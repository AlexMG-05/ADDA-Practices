package ejercicio3_manual;

import ejercicio3.SolucionFestival;

public class FestivalBT {
	private static Double bestValue;
	private static FestivalState state;
	private static SolucionFestival solution;
	
	public static void search() {
		solution = null;
		bestValue = Double.MAX_VALUE;
		state = FestivalState.initial();
		bt_search();
	}
	
	private static void bt_search() {
		if(state.esSolucion()) {
			Double valueObtained = state.acc;
			if(valueObtained < bestValue) {
				bestValue = valueObtained;
				solution = state.getSolucion();
			}
			
		} else if(!state.esTerminal()) {
			for(Integer a: state.alternatives()) {
				if(state.cota(a) < bestValue) {
					state.forward(a);
					bt_search();
					state.back();
				}
			}
		}
	}
	
	public static SolucionFestival getSolucion() {
		return solution;
	}
}
