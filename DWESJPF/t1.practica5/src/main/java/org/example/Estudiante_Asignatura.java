package org.example;

public class Estudiante_Asignatura {
    private int idEstudiante;
    private int idAsignatura;
    private double calificacion;

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    @Override
    public String toString() {
        return "Estudiante_Asignatura{" +
                "idEstudiante=" + idEstudiante +
                ", idAsignatura=" + idAsignatura +
                ", calificacion=" + calificacion +
                '}';
    }


}
