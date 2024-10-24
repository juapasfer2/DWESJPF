
package org.example;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
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


    public List<Estudiante> estudiantesPorCasa(String casa){
        List<Estudiante> listaEstudiantes = new ArrayList<>();
        try{
            this.abrirConexion();
            String consulta = "SELECT * FROM Casa WHERE nombre_casa = '" + casa + "';";
            System.out.println(consulta);
            ResultSet rs = declaracion.executeQuery(consulta);
            rs.next();
            int id_casa = rs.getInt("id_casa");
            consulta = "SELECT * FROM Estudiante WHERE id_casa = " + id_casa;
            System.out.println(consulta);
            rs = declaracion.executeQuery(consulta);
            while(rs.next()){
                Estudiante estudiante = new Estudiante();
                estudiante.setId_estudiante(rs.getInt("id_estudiante"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setId_casa(rs.getInt("id_casa"));
                estudiante.setAno_curso(rs.getInt("año_curso"));
                estudiante.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
                listaEstudiantes.add(estudiante);
            }
            this.cerrarRecursos(conexion,declaracion,rs);
        }catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return listaEstudiantes;
    }

    public List<Asignatura> asignaturasObligatorias(){
        List<Asignatura> listaObligatorias = new ArrayList<>();
        try{
            this.abrirConexion();

            String consulta = "SELECT * FROM Asignatura WHERE obligatoria = " + true ;
            System.out.println(consulta);
            ResultSet rs = declaracion.executeQuery(consulta);
            while(rs.next()){
                Asignatura asignatura = new Asignatura();
                asignatura.setIdAsignatura(rs.getInt("id_asignatura"));
                asignatura.setNombreAsignatura(rs.getString("nombre_asignatura"));
                asignatura.setObligatoria(rs.getBoolean("obligatoria"));
                asignatura.setAula(rs.getString("aula"));
                listaObligatorias.add(asignatura);
            }

            this.cerrarRecursos(conexion,declaracion,rs);
        }catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return listaObligatorias;
    }

    public Mascota obtenerMascotaDe(String estudianteNombre){
        Mascota mascota = new Mascota();
        try{
            this.abrirConexion();

            String consulta = "SELECT * FROM Estudiante WHERE nombre = '" + estudianteNombre + "';";
            System.out.println(consulta);
            ResultSet rs = declaracion.executeQuery(consulta);
            if (rs.next()) {
                int id_estudiante = rs.getInt("id_estudiante");

                consulta = "SELECT * FROM Mascota WHERE id_estudiante = " + id_estudiante + ";";
                System.out.println(consulta);
                rs = declaracion.executeQuery(consulta);

                if (rs.next()) {
                    mascota.setId(rs.getInt("id_mascota"));
                    mascota.setNombre(rs.getString("nombre_mascota"));
                    mascota.setEspecie(rs.getString("especie"));
                    mascota.setIdEstudiante(rs.getInt("id_estudiante"));
                } else {
                    System.out.println("El estudiante no tiene mascota.");
                }
            } else {
                System.out.println("Estudiante no encontrado.");
            }
            this.cerrarRecursos(conexion,declaracion,rs);
        }catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return mascota;
    }


    public List<Estudiante> estudiantesSinMascota (){
        List<Estudiante> listaEstudiantes = new ArrayList<>();
        List<Estudiante> listaEstudiantesMascota = new ArrayList<>();
        List<Mascota> listaMascotas = new ArrayList<>();
        try{
            this.abrirConexion();
            String consulta = "SELECT * FROM Mascota id_estudiante";
            System.out.println(consulta);
            ResultSet rs = declaracion.executeQuery(consulta);
            while(rs.next()){
                Mascota mascota = new Mascota();
                mascota.setIdEstudiante(rs.getInt("id_estudiante"));
                listaMascotas.add(mascota);
            }
            for(int i=0; i < listaMascotas.size() ; i++){
                consulta ="SELECT * FROM Estudiante Where id_estudiante = '" + listaMascotas.get(i).getIdEstudiante() + "'";

            }

            this.cerrarRecursos(conexion,declaracion,null);
        }catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }


        return listaEstudiantes;

    }
}