package org.example;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Service service = new Service("jdbc:postgresql://database-daw.c7yg0m8y0202.us-east-1.rds.amazonaws.com:5432/f12006","postgres", "12345678");

        System.out.println(service.obtenerEstudiantesYCasas());
        System.out.println();
        System.out.println(service.obtenerEstudiantesYMascotas());
        System.out.println();
        System.out.println(service.obtenerEstudiantesConMascotas());
        System.out.println();
        System.out.println(service.estudiantesSinMascota());
        System.out.println();
        System.out.println(service.obtenerPromedioCalificacionesPorEstudiante());
        System.out.println();
        System.out.println(service.obtenerNumeroEstudiantesQuintoAno());
        System.out.println();
        System.out.println(service.obtenerMejorCalificacionEnAsignatura("Pociones"));
        System.out.println();
        System.out.println(service.promedioCalificacionesPorCasaEnAsignatura("Pociones"));
        System.out.println();
        //service.actualizarCalificacionesUltimoAno();
        //service.desmatricularEstudiantesOptativasBajas();



    }
}