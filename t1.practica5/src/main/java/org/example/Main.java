package org.example;
public class Main {
    public static void main(String[] args) {
        Service service = new Service("jdbc:postgresql://database-daw.c7yg0m8y0202.us-east-1.rds.amazonaws.com:5432/f12006","postgres", "12345678");
        service.cargarBaseDeDatos();
        System.out.println();
        service.estudiantesPorCasa("Gryffindor");
        System.out.println();
        service.asignaturasObligatorias();
        System.out.println();
        service.obtenerMascotaDe("Cho");
        System.out.println();
        service.estudiantesSinMascota();
        System.out.println();
        service.promedioEstudiante("Harry Potter");
        System.out.println();
        service.numEstudiantesPorCasa();
        System.out.println();
        service.estudiantesDeAsignatura("Defensa Contra las Artes Oscuras");
        System.out.println();
        service.insertarNuevoEstudiante("paco", "garcia",1, 5, "1980-07-31");
    }
}