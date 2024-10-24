package org.example;
import java.util.Date;
import java.util.List;

public class Estudiante {
    private int id_estudiante;
    private String nombre;
    private String apellido;
    private int id_casa;
    private int ano_curso;
    private Date fecha_nacimiento;
    private Mascota mascota;
    private List<Asignatura> asignaturas;

    public int getId_estudiante() {
        return id_estudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getId_casa() {
        return id_casa;
    }

    public int getAno_curso() {
        return ano_curso;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setId_estudiante(int id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setId_casa(int id_casa) {
        this.id_casa = id_casa;
    }

    public void setAno_curso(int ano_curso) {
        this.ano_curso = ano_curso;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "id_estudiante=" + id_estudiante +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", id_casa=" + id_casa +
                ", ano_curso=" + ano_curso +
                ", fecha_nacimiento=" + fecha_nacimiento +
                '}';
    }
}
