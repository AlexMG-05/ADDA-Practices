package ejercicio2_manual;

import ejercicio2.DatosCursos;

public class TestPDRManual {
	public static void main(String[] args) {
		for (Integer file = 1; file <= 3; file++) {
			System.out.println("\n------------------------- ↓↓↓ Entry "+ file +" ↓↓↓ -------------------------");
			DatosCursos.iniDatos("resources/ejercicio2/DatosEntrada"+file+".txt");
			System.out.println("Solucion obtenida: " + CoursePDR.search());
		}
	}
}
