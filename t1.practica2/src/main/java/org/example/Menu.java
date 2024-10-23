package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Menu {
    public static void mostrarMenu(){
        System.out.println("1-Cargar Información");
        System.out.println("2-Guardar Información");
        System.out.println("3-Añadir Animal");
        System.out.println("4-Borrar Animal");
        System.out.println("5-Consultar Animal");
        System.out.println("6-Mostrar Animales");
        System.out.println("7-Salir");
    }
    public static Animal añadirAnimal(){
        Scanner sc = new Scanner(System.in);
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Especie: ");
        String especie = sc.nextLine();
        System.out.print("Edad: ");
        int edad = sc.nextInt();
        sc.nextLine();
        System.out.print("Sexo (Macho/Hembra): ");
        String sexo = sc.nextLine();
        System.out.print("Fecha Ingreso (yyyy-MM-dd): ");
        LocalDate fechaIngreso;
        try {
            fechaIngreso = LocalDate.parse(sc.nextLine());
        } catch (DateTimeParseException e) {
            fechaIngreso = LocalDate.now();
        }
        System.out.print("¿Adoptado? (true/false): ");
        boolean adoptado = sc.nextBoolean();

        return new Animal(id, nombre, especie, edad, sexo, fechaIngreso, adoptado);
    }
}
