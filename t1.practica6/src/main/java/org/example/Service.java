
package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private String urlConexion; //= "jdbc:postgresql://database-daw.c7yg0m8y0202.us-east-1.rds.amazonaws.com:5432/hogwarts";
    private String usuario; //= "postgres";
    private String password;//= "f12006";

    private Connection conexion;
    Statement declaracion;



    public Service(String urlConexion, String usuario, String password) {
        this.urlConexion = urlConexion;
        this.usuario = usuario;
        this.password = password;
    }
    public void abrirConexion() throws SQLException {
        this.conexion = DriverManager.getConnection(urlConexion, usuario, password);
        this.declaracion = this.conexion.createStatement();

    }

    public void cerrarRecursos(Connection conexion, Statement declaracion, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) rs.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar ResultSet: " + e.getMessage());
        }
        try {
            if (declaracion != null && !declaracion.isClosed()) declaracion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar Statement: " + e.getMessage());
        }
        try {
            if (conexion != null && !conexion.isClosed()) conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }


    public List<Estudiante> estudiantesSinMascota() {
        List<Estudiante> listaEstudiantes = new ArrayList<>();
        List<Mascota> mascotas = new ArrayList<>();
        try {
            this.abrirConexion();

            String consulta = "SELECT id_estudiante FROM Mascota";
            System.out.println(consulta);
            ResultSet rs = declaracion.executeQuery(consulta);
            while (rs.next()) {
                Mascota mascota = new Mascota();
                mascota.setIdEstudiante(rs.getInt("id_estudiante"));
                if (mascota.getIdEstudiante() != 0) {
                    mascotas.add(mascota);
                }
            }

            consulta = "SELECT * FROM Estudiante";
            System.out.println(consulta);
            rs = declaracion.executeQuery(consulta);

            while (rs.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setId_estudiante(rs.getInt("id_estudiante"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setId_casa(rs.getInt("id_casa"));
                estudiante.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
                estudiante.setAno_curso(rs.getInt("año_curso"));

                boolean tieneMascota = false;
                for (Mascota mascota : mascotas) {
                    if (estudiante.getId_estudiante() == mascota.getIdEstudiante()) {
                        tieneMascota = true;
                        break;
                    }
                }
                if (!tieneMascota) {
                    listaEstudiantes.add(estudiante);
                }
            }
            this.cerrarRecursos(conexion, declaracion, rs);
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }

        return listaEstudiantes;
    }

    public List<String> obtenerEstudiantesYCasas() {
        List<String> listaEstudiantesCasas = new ArrayList<>();

        try {
            this.abrirConexion();
            String consulta = "SELECT e.nombre, e.apellido, c.nombre_casa " +
                    "FROM Estudiante e " +
                    "JOIN Casa c ON e.id_casa = c.id_casa";
            System.out.println(consulta);
            ResultSet rs = declaracion.executeQuery(consulta);


            while (rs.next()) {
                String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                String nombreCasa = rs.getString("nombre_casa");

                listaEstudiantesCasas.add("Estudiante: " + nombreCompleto + ", Casa: " + nombreCasa);
            }

            this.cerrarRecursos(conexion, declaracion, rs);
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }

        return listaEstudiantesCasas;
    }

    public List<String> obtenerEstudiantesYMascotas() {
        List<String> listaEstudiantesMascotas = new ArrayList<>();

        try {
            this.abrirConexion();

            String consulta = "SELECT e.nombre, e.apellido, m.nombre_mascota " +
                    "FROM Estudiante e " +
                    "LEFT JOIN Mascota m ON e.id_estudiante = m.id_estudiante";
            System.out.println(consulta);
            ResultSet rs = declaracion.executeQuery(consulta);

            while (rs.next()) {
                String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                String nombreMascota = rs.getString("nombre_mascota");

                if (nombreMascota != null) {
                    listaEstudiantesMascotas.add("Estudiante: " + nombreCompleto + ", Mascota: " + nombreMascota);
                } else {
                    listaEstudiantesMascotas.add("Estudiante: " + nombreCompleto + ", Mascota: No tiene");
                }
            }

            this.cerrarRecursos(conexion, declaracion, rs);
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return listaEstudiantesMascotas;
    }

    public List<String> obtenerEstudiantesConMascotas() {
        List<String> listaEstudiantesConMascotas = new ArrayList<>();
        try {
            this.abrirConexion();

            String consulta = "SELECT e.nombre, e.apellido, m.nombre_mascota " +
                    "FROM Estudiante e " +
                    "INNER JOIN Mascota m ON e.id_estudiante = m.id_estudiante";
            System.out.println(consulta);
            ResultSet rs = declaracion.executeQuery(consulta);

            while (rs.next()) {
                String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                String nombreMascota = rs.getString("nombre_mascota");

                listaEstudiantesConMascotas.add("Estudiante: " + nombreCompleto + ", Mascota: " + nombreMascota);
            }
            this.cerrarRecursos(conexion, declaracion, rs);
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return listaEstudiantesConMascotas;
    }

    public List<String> obtenerPromedioCalificacionesPorEstudiante() {
        List<String> listaPromedios = new ArrayList<>();
        try {
            this.abrirConexion();
            String consulta = "SELECT e.nombre, e.apellido, AVG(ea.calificacion) AS promedio " +
                    "FROM Estudiante e " +
                    "INNER JOIN Estudiante_Asignatura ea ON e.id_estudiante = ea.id_estudiante " +
                    "GROUP BY e.id_estudiante, e.nombre, e.apellido";
            System.out.println(consulta);
            ResultSet rs = declaracion.executeQuery(consulta);

            while (rs.next()) {
                String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                double promedio = rs.getDouble("promedio");
                listaPromedios.add("Estudiante: " + nombreCompleto + ", Promedio: " + promedio);
            }
            this.cerrarRecursos(conexion, declaracion, rs);
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return listaPromedios;
    }

    public  List<String> obtenerNumeroEstudiantesQuintoAno() {
        List<String> listaEstudiantesEnQuinto = new ArrayList<>();
        try {
            this.abrirConexion();
            String consulta = "SELECT c.nombre_casa, COUNT(e.id_estudiante) AS num_estudiantes " +
                    "FROM Casa c " +
                    "LEFT JOIN Estudiante e ON c.id_casa = e.id_casa " +
                    "WHERE e.año_curso = 5 " +
                    "GROUP BY c.nombre_casa";
            System.out.println(consulta);
            ResultSet rs = declaracion.executeQuery(consulta);

            while (rs.next()) {
                String nombreCasa = rs.getString("nombre_casa");
                int numEstudiantes = rs.getInt("num_estudiantes");

                listaEstudiantesEnQuinto.add("Nombre CAsa: " + nombreCasa + ", Numero Estudiantes 5º: " + numEstudiantes);
            }
            this.cerrarRecursos(conexion, declaracion, rs);
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }

        return listaEstudiantesEnQuinto;
    }

    public List<String> obtenerMejorCalificacionEnAsignatura(String nombreAsignatura) {
        List<String> mejorEstudiante = new ArrayList<>();
        try {
            this.abrirConexion();
            String consulta = "SELECT e.nombre, e.apellido, ea.calificacion " +
                    "FROM Estudiante e " +
                    "JOIN Estudiante_Asignatura ea ON e.id_estudiante = ea.id_estudiante " +
                    "JOIN Asignatura a ON ea.id_asignatura = a.id_asignatura " +
                    "WHERE a.nombre_asignatura = '" + nombreAsignatura + "' " +"ORDER BY ea.calificacion DESC " + "LIMIT 1";
            System.out.println(consulta);
            ResultSet rs = declaracion.executeQuery(consulta);

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                double calificacion = rs.getDouble("calificacion");
                mejorEstudiante.add(nombre + ", " + apellido + ", " + calificacion);
            } else {
                System.out.println("No se encontraron estudiantes para la asignatura: " + nombreAsignatura);
            }

            this.cerrarRecursos(conexion, declaracion, rs);
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return mejorEstudiante;
    }

    public List<String> promedioCalificacionesPorCasaEnAsignatura(String nombreAsignatura) {
        List<String> promedios = new ArrayList<>();
        try {
            this.abrirConexion();
            String consulta = "SELECT c.nombre_casa, AVG(ea.calificacion) AS promedio_calificacion " +
                    "FROM Casa c " +
                    "JOIN Estudiante e ON c.id_casa = e.id_casa " +
                    "JOIN Estudiante_Asignatura ea ON e.id_estudiante = ea.id_estudiante " +
                    "JOIN Asignatura a ON ea.id_asignatura = a.id_asignatura " +
                    "WHERE a.nombre_asignatura = '" + nombreAsignatura + "' " + "GROUP BY c.nombre_casa";

            System.out.println(consulta);
            ResultSet rs = declaracion.executeQuery(consulta);

            while (rs.next()) {
                String nombreCasa = rs.getString("nombre_casa");
                double promedioCalificacion = rs.getDouble("promedio_calificacion");
                promedios.add(nombreCasa + ", " + promedioCalificacion);
            }

            this.cerrarRecursos(conexion, declaracion, rs);
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return promedios;
    }

    public void actualizarCalificacionesUltimoAno() {
        try {
            this.abrirConexion();
            String consulta = "UPDATE Estudiante_Asignatura ea " +
                    "SET calificacion = calificacion * 1.10 " + "WHERE ea.id_estudiante IN (" +
                    "  SELECT e.id_estudiante " + "  FROM Estudiante e " + "  WHERE e.año_curso = 5" +")";
            System.out.println(consulta);
            int filasActualizadas = declaracion.executeUpdate(consulta);
            System.out.println("Calificaciones actualizadas para " + filasActualizadas + " estudiantes.");
            this.cerrarRecursos(conexion, declaracion, null);
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public void desmatricularEstudiantesOptativasBajas() {
        try {
            this.abrirConexion();

            String consulta = "DELETE FROM Estudiante_Asignatura " +
                    "WHERE id_asignatura IN (" +
                    "  SELECT a.id_asignatura " +
                    "  FROM Asignatura a " +
                    "  WHERE a.obligatoria = false" + ") " + "AND calificacion < 5;";
            System.out.println(consulta);
            int filasEliminadas = declaracion.executeUpdate(consulta);
            System.out.println("Asignaturas optativas eliminadas para " + filasEliminadas + " calificaciones inferiores a 5.");

            this.cerrarRecursos(conexion, declaracion, null);
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }
}