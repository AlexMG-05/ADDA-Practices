package ejercicio1;

public record Spm(Integer action, Integer weight) implements Comparable<Spm> {
	public static Spm of(Integer action, Integer weight) {
		return new Spm(action, weight);
	}
	
	public int compareTo(Spm sp) {
		return this.weight.compareTo(sp.weight);
	}
}
