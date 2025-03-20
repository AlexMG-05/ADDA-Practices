package ejercicio2;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class DatosCursos {
    
    public static record Curso(Integer id, Integer area, Integer coste, Integer relevancia, Integer duracion) {
        public static Curso create(String s) {
            String[] v = s.split(":");
            return new Curso(Integer.parseInt(v[0].trim()), Integer.parseInt(v[1].trim()),  
                             Integer.parseInt(v[2].trim()), Integer.parseInt(v[3].trim()), 
                             Integer.parseInt(v[4].trim()));
        }
        
        @Override
        public String toString() {
            return String.format("ID = %d; Área = %d; Coste = %d; Relevancia = %d; Duración = %d", 
                                  id, area, coste, relevancia, duracion);
        }   
    }
    
    private static List<Integer> areas;
    private static List<Curso> cursos;
    private static Integer presupuestoTotal;

    public static void iniDatos(String fichero) {
        cursos = List2.empty();
        Set<Integer> cat = new TreeSet<>();
        
        List<String> ls = Files2.linesFromFile(fichero);
        
        presupuestoTotal = Integer.parseInt(ls.remove(0).split("=")[1].trim());
        for(String linea: ls) {
            if(linea.startsWith("//"))
                continue;
            else {
                Curso c = Curso.create(linea);
                cursos.add(c);
                cat.add(c.area());
            }
        }
        areas = List2.ofCollection(cat);
        toConsole();
    }
    
    public static Integer getNumCursos() {
        return cursos.size();
    }
    public static Integer getNumAreas() {
        return areas.size();
    }
    public static Integer getCoste(Integer i) {
        return cursos.get(i).coste();
    }
    public static Curso getCurso(Integer index){
        return cursos.get(index);
    }
    public static Integer getArea(Integer index){
        return cursos.get(index).area();
    }
    public static Integer getRelevancia(Integer index) {
        return cursos.get(index).relevancia();
    }
    public static Integer getDuracion(Integer index) {
        return cursos.get(index).duracion();
    }
    public static Integer getPresupuestoTotal() {
        return presupuestoTotal;
    }

    public static void toConsole() {
        String2.toConsole(cursos, "Cursos");
        String2.toConsole("Presupuesto total para el programa: %d", presupuestoTotal);
        String2.toConsole(String2.linea());        
    }
    
    public static void main(String[] args) {
		iniDatos("resources/ejercicio2/DatosEntrada1.txt");
    }
}

