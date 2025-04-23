package ejercicio3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class FestivalHeuristic {
	public static Double heuristic(FestivalVertex source, Predicate<FestivalVertex> goal, FestivalVertex target) {
		return (double) source.costs();
	}
}
