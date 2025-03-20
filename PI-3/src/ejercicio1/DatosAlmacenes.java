package ejercicio1;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class DatosAlmacenes {
	public static record Producto(String producto, Integer metroscubicosrequeridos, Set<String> incompatibilidades) {
		public static Producto create(String s) {
			String[] v0 = s.split("->");
			String[] v1 = v0[1].trim().split(";");
			Integer a = Integer.parseInt(v1[0].split("=")[1].trim());
			
			Set<String> b = new HashSet<>();
			String[] v2 = v1[1].split("=")[1].trim().split(",");
			for(String e: v2) {
				String v3 = e.trim();
				b.add(v3);
			}
			
			return new Producto(v0[0].trim(),a, b);
		}	
		@Override
		public String toString() {		
			return producto+": "+metroscubicosrequeridos+"; "+incompatibilidades+"; ";
		}
	}

	public static record Almacen(String nombre, Integer metroscubicosdisponibles) {
		public static Almacen create(String s) {
			String[] v0 = s.split(":");
			String[] v1 = v0[1].split(";");
			String a = v1[0].split("=")[1].trim();
			
			return new Almacen(v0[0].trim(), Integer.parseInt(a));
		}	
		@Override
		public String toString() {		
			return nombre+": "+metroscubicosdisponibles+"; ";
		}
	}
	
	private static List<Almacen> almacenes;
	private static List<Producto> productos;
	
	public static void iniDatos(String fichero) {		
		almacenes = List2.empty();
		productos = List2.empty();
		
		List<String> ls = Files2.linesFromFile(fichero);

		for(String s: ls) {
			
			if(s.startsWith("//")) {
				continue;
				}
			else if(s.startsWith("A")) {
				almacenes.add(Almacen.create(s));
				}
			else if(s.startsWith("P")) {
				productos.add(Producto.create(s));
			}
		}
		toConsole();
	}
	
	public static Integer getNumProductos() {
		return productos.size();
	}
	public static Integer getNumAlmacenes() {
		return almacenes.size();
	}
	public static Integer getMetrosCubicosAlmacen(Integer j) {
		return almacenes.get(j).metroscubicosdisponibles();
	}
	public static Integer getMetrosCubicosProducto(Integer i) {
		return productos.get(i).metroscubicosrequeridos();
	}
	public static Producto getProducto(Integer i) {
		return productos.get(i);
	}
	
	public static Boolean sonIncompatibles(Integer i, Integer j) {
		String s2 = productos.get(j).producto();
		return productos.get(i).incompatibilidades.contains(s2);
	}
	public static Integer esIncompatible(Integer i, Integer j) {
		String s2 = productos.get(j).producto();
		Integer res = 0;
		if(productos.get(i).incompatibilidades.contains(s2)) {
			res = 1;
		}
		return res;
	}

	public static void toConsole() {
		String2.toConsole(productos,"productos");
		String2.toConsole(almacenes,"almacenes");
	}
	
	public static void main(String[] args) throws IOException {
		iniDatos("resources/ejercicio1/DatosEntrada1.txt");
	}
}
