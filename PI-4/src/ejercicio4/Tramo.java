package ejercicio4;

public record Tramo(Integer id, double tiempo, double costeBillete) {

    private static int num = 0;

    public static Tramo ofFormat(String[] formato) {
        int tiempo = Integer.parseInt(formato[2].trim());
        double costeBillete = Double.parseDouble(formato[3].trim());
        Integer id = num;
        num++;
        return new Tramo(id, tiempo, costeBillete);
    }

    @Override
    public String toString() {
        return "(T: " + tiempo + " minutos, C: " + costeBillete + " euros)";
    }
}