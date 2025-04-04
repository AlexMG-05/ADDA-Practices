package ejercicio1;

public class TestPDR {
	public static void main(String[] args) {
		Integer files= 3;
		for (int file = 1; file <=files; file++) {
			System.out.println("\n------------------------- ↓↓↓ Entry "+ file +" ↓↓↓ -------------------------");
			DatosAlmacenes.iniDatos("resources/ejercicio1/DatosEntrada"+file+".txt");
			System.out.print(Exercise1PDR.search());
		}
	}
}
