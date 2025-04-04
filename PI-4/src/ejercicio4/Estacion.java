package ejercicio4;

public record Estacion(String nombre, int pasajerosDiarios, int numeroEmpleados, double satisfaccionClientes) {

    public static Estacion ofFormat(String[] formato) {
        String nombre = formato[0].trim();
        int pasajerosDiarios = Integer.parseInt(formato[1].trim());
        double satisfaccionClientes = Double.parseDouble(formato[2].trim());
        int numeroEmpleados = Integer.parseInt(formato[3].trim());

        return new Estacion(nombre, pasajerosDiarios, numeroEmpleados, satisfaccionClientes);
    }

    @Override
    public String toString() {
        return this.nombre + " (Pasajeros diarios: " + pasajerosDiarios 
               + ", Empleados: " + numeroEmpleados 
               + ", Satisfacción: " + satisfaccionClientes + ")";
    }
}
