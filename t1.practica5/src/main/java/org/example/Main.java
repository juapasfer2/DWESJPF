package org.example;
import java.util.List;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Service service = new Service("jdbc:postgresql://database-daw.c7yg0m8y0202.us-east-1.rds.amazonaws.com:5432/f12006","postgres", "12345678");

        List<Estudiante> estudiantes = service.estudiantesPorCasa("Gryffindor");
        for(Estudiante estudiante : estudiantes){
            System.out.print(estudiante.getNombre() + ", ");
        }
        System.out.println();
        System.out.println();

        List<Asignatura> asignaturas = service.asignaturasObligatorias();
        for(Asignatura asignatura : asignaturas){
            System.out.print(asignatura.getNombreAsignatura() + ", ");
        }
        System.out.println();
        System.out.println();

        Mascota mascota = service.obtenerMascotaDe("Harry");
        if(mascota.getNombre()!=null){
            System.out.println(mascota);

        }



    }
}