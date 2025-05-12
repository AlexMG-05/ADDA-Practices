package ejercicio3_manual;

import ejercicio3.DatosFestival;

public class TestBTManual {
	public static void main(String[] args) {
		for (Integer file = 1; file <= 3; file++) {
			System.out.println("\n------------------------- ↓↓↓ Entry "+ file +" ↓↓↓ -------------------------");
			DatosFestival.iniDatos("resources/ejercicio3/DatosEntrada"+file+".txt"); // .txt
		
			FestivalBT.search();
			System.out.println(FestivalBT.getSolucion()+ "\n");
		}
	}
}
