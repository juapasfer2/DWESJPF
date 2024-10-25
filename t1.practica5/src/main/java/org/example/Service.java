
package org.example;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
public class Service {
    private String urlConexion; //= "jdbc:postgresql://database-daw.c7yg0m8y0202.us-east-1.rds.amazonaws.com:5432/hogwarts";
    private String usuario;
    private String password;
    private Connection conexion;
    Statement declaracion;

    private List<Casa> listaCasas = new ArrayList<>();
    private List<Estudiante> listaEstudiantes = new ArrayList<>();
    private List<Mascota> listaMascotas  = new ArrayList<>();
    private List<Asignatura> listaAsiganituras  = new ArrayList<>();
    private List<Profesor> listaProfesores = new ArrayList<>();
    private List<Estudiante_Asignatura> listaEstudianteAsignatura = new ArrayList<>();

    public Service(String urlConexion, String usuario, String password) {
        this.urlConexion = urlConexion;
        this.usuario = usuario;
        this.password = password;
    }
    private void abrirConexion() throws SQLException {
        this.conexion = DriverManager.getConnection(urlConexion, usuario, password);
        this.declaracion = this.conexion.createStatement();

    }
    private void cerrarRecursos(Connection conexion, Statement declaracion, ResultSet rs) {
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

    public void cargarBaseDeDatos(){
        try{
            this.abrirConexion();
            String consulta = "SELECT * FROM Casa";
            ResultSet rs = declaracion.executeQuery(consulta);
            while(rs.next()){
                Casa casa = new Casa();
                casa.setId_casa(rs.getInt("id_casa"));
                casa.setNombre_casa(rs.getString("nombre_casa"));
                casa.setFundador(rs.getString("fundador"));
                casa.setJefe_casa(rs.getString("jefe_casa"));
                casa.setFantasma(rs.getString("fantasma"));
                listaCasas.add(casa);
            }
            consulta = "SELECT * FROM Estudiante";
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
            consulta = "SELECT * FROM Mascota";
            rs = declaracion.executeQuery(consulta);
            while(rs.next()){
                Mascota mascota = new Mascota();
                mascota.setId(rs.getInt("id_mascota"));
                mascota.setNombre(rs.getString("nombre_mascota"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setIdEstudiante(rs.getInt("id_estudiante"));
                listaMascotas.add(mascota);
            }
            consulta = "SELECT * FROM Asignatura";
            rs = declaracion.executeQuery(consulta);
            while(rs.next()){
                Asignatura asignatura = new Asignatura();
                asignatura.setIdAsignatura(rs.getInt("id_asignatura"));
                asignatura.setNombreAsignatura(rs.getString("nombre_asignatura"));
                asignatura.setAula(rs.getString("aula"));
                asignatura.setObligatoria(rs.getBoolean("obligatoria"));
                listaAsiganituras.add(asignatura);
            }
            consulta = "SELECT * FROM Profesor";
            rs = declaracion.executeQuery(consulta);
            while(rs.next()){
                Profesor profesor = new Profesor();
                profesor.setIdProfesor(rs.getInt("id_profesor"));
                profesor.setNombre(rs.getString("nombre"));
                profesor.setApellido(rs.getString("apellido"));
                profesor.setIdAsignatura(rs.getInt("id_asignatura"));
                profesor.setFechaInicio(rs.getDate("fecha_inicio"));
                listaProfesores.add(profesor);
            }
            consulta = "SELECT * FROM Estudiante_Asignatura";
            rs = declaracion.executeQuery(consulta);
            while(rs.next()){
                Estudiante_Asignatura estudianteAsignatura = new Estudiante_Asignatura();
                estudianteAsignatura.setIdEstudiante(rs.getInt("id_estudiante"));
                estudianteAsignatura.setIdAsignatura(rs.getInt("id_asignatura"));
                estudianteAsignatura.setCalificacion(rs.getDouble("calificacion"));
                listaEstudianteAsignatura.add(estudianteAsignatura);
            }
            this.cerrarRecursos(conexion,declaracion,rs);
        }catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }


    private List<Estudiante> listaEstudiantesPorCasa(String casa){
        Casa casaBusqueda = listaCasas.stream().filter(c -> c.getNombre_casa().equals(casa)).findFirst().orElse(null);
        if(casaBusqueda == null){
            return null;
        }
        return listaEstudiantes.stream().filter(e -> e.getId_casa()==casaBusqueda.getId_casa()).toList();
    }

    public void estudiantesPorCasa(String casa){
        List<Estudiante> listaEstudiantesPorCasa = this.listaEstudiantesPorCasa(casa);
        System.out.println("Estudiantes de " + casa + ": ");
        if(listaEstudiantesPorCasa == null){
            System.out.println("Casa no encontrada");
            return;
        }
        for(Estudiante estudiante : listaEstudiantesPorCasa){
            System.out.println(estudiante.getNombre() + " " + estudiante.getApellido());
        }
    }

    public void asignaturasObligatorias(){
        System.out.println("AsignaturasObligatorias");
        for(Asignatura asignatura : listaAsiganituras.stream().filter(a -> a.isObligatoria()).toList()){
            System.out.println(asignatura.getNombreAsignatura());
        }
    }

    public void obtenerMascotaDe(String estudianteNombre){
       Estudiante estudiante = listaEstudiantes.stream().filter(e -> e.getNombre().equals(estudianteNombre)).findFirst().orElse(null);
       if(estudiante == null){
           System.out.println("estudiante no encontrado");
           return;
       }
       Mascota mascota = listaMascotas.stream().filter(e -> e.getIdEstudiante()==estudiante.getId_estudiante()).findFirst().orElse(null);
       if(mascota == null){
           System.out.println("El estudiante "+ estudianteNombre +" no tiene mascota");
       }else
           System.out.println("La mascota de "+ estudianteNombre + " es " +mascota.getNombre());
    }

    public void estudiantesSinMascota(){
        System.out.println("EstudiantesSinMascota");
        List<Integer> listaIds = listaMascotas.stream().map(m -> m.getIdEstudiante()).toList();
        for (Estudiante estudiante : listaEstudiantes.stream().filter(e -> !listaIds.contains(e.getId_estudiante())).toList()) {
            System.out.println(estudiante.getNombre());
        }

        //listaEstudiantes.stream().filter(e -> !listaIds.contains(e.getId_estudiante())).forEach(p -> System.out.println(p.getNombre()));

    }

    public void promedioEstudiante(String nombreApellidoEstudiante){
        String[] nombreApellido= nombreApellidoEstudiante.split(" ");
        String nombre = nombreApellido[0];
        String apellido = nombreApellido[1];
        double promedio = 0.0;
        int contador = 0;
        System.out.println("Promedio Estudiante " + nombre + apellido + ": ");
        Estudiante estudiante = listaEstudiantes.stream().filter(e -> e.getNombre().equals(nombre) && e.getApellido().equals(apellido)).findFirst().orElse(null);
        if(estudiante == null){
            System.out.println("estudiante no encontrado");
            return;
        }
        for(Estudiante_Asignatura estudianteAsignatura: listaEstudianteAsignatura.stream().filter(e -> e.getIdEstudiante()==estudiante.getId_estudiante()).toList()){
            promedio += estudianteAsignatura.getCalificacion();
            contador++;
        }
        System.out.println("El promedio es: "+ promedio/contador);
    }

    public void numEstudiantesPorCasa(){
        System.out.println("EstudiantesPorCasa");
        for (Casa casa : listaCasas) {
            List<Estudiante> listaEstudiantesPorCasa = this.listaEstudiantesPorCasa(casa.getNombre_casa());
            System.out.println("Casa '" + casa.getNombre_casa() + "' tiene: " + listaEstudiantesPorCasa.size() + " estudiantes.");

        }
    }

    public void estudiantesDeAsignatura(String nombreAsignatura){
        System.out.println("Estudiantes matriculados en " + nombreAsignatura +":");
        List<Integer> idsAsignaturas = new ArrayList<>();
        List<Integer> idsEstudiantes = new ArrayList<>();
        for(Asignatura asignatura : listaAsiganituras.stream().filter(e -> e.getNombreAsignatura().equals(nombreAsignatura)).toList()){
            idsAsignaturas.add(asignatura.getIdAsignatura());
        }
        for(Estudiante_Asignatura estudianteAsignatura : listaEstudianteAsignatura.stream().filter(e -> idsAsignaturas.contains(e.getIdAsignatura())).toList()){
            idsEstudiantes.add(estudianteAsignatura.getIdEstudiante());
        }
        for(Estudiante estudiante : listaEstudiantes.stream().filter(e -> idsEstudiantes.contains(e.getId_estudiante())).toList()){
            System.out.println(estudiante.getNombre());
        }
    }
    public void insertarNuevoEstudiante(String nombre, String apellido, int idCasa, int anoCurso, String fechaNacimiento){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fecha = formatoFecha.parse(fechaNacimiento);
            listaEstudiantes.add(new Estudiante(listaEstudiantes.size() + 1, nombre, apellido, idCasa, anoCurso, fecha));
            System.out.println("Se ha añadido con exito");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void modificarAula(int idAsignatura, String nuevaAula){
        listaAsiganituras.stream().filter(e -> e.getIdAsignatura() == idAsignatura).findFirst().ifPresent(e -> e.setAula(nuevaAula));
    }
    public void desmatricularEstudiante(int idEstudiante , int idAsignatura){
        listaEstudianteAsignatura.removeIf(e -> e.getIdEstudiante() == idEstudiante && e.getIdAsignatura() == idAsignatura);
    }

}