package org.example;
import java.util.Scanner;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        boolean salir = false;
        ProtectoraAnimales protectora = new ProtectoraAnimales();
        while(!salir){
            Menu.mostrarMenu();
            int p=sc.nextInt();
            if(p == 1){
                protectora = ProtectoraAnimales.cargarJSON(Path.of(".","t1.practica3", "src", "main", "resources", "protectoraAnimales.json"));
            }
            if(p == 2){
                ProtectoraAnimales.guardarInformacion(protectora,Path.of(".","t1.practica3", "src", "main", "resources", "protectoraAnimales2.json"));
            }
            if(p == 3){
                System.out.println("-Añadiendo Animal-");
                protectora.anadirAnimal(Menu.añadirAnimal());
            }
            if(p == 4){
                System.out.println("-Borrando Animal-");
                System.out.print("Introduce el id del animal:");
                int id= sc.nextInt();
                protectora.borrarAnimal(id);
            }
            if(p == 5){
                System.out.println("-Consultando Animal-");
                System.out.print("Introduce el id del animal:");
                int id= sc.nextInt();
                System.out.println(protectora.consultarAnimal(id));
            }
            if(p == 6){
                System.out.println("-Mostrando Animales-");
                System.out.println(protectora.mostarAnimales());
            }
            if(p==7){
                salir=true;
            }
        }
    }
}