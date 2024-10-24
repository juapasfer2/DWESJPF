package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Pokedex pokedex = new Pokedex();
        pokedex.setListaPokemons();
        Scanner sc= new Scanner(System.in);
        int option = 100;
            while( option !=0){
                System.out.println("1-Mostar Pokemons , 2-Consultar Pokemons , 3-Ordenar Por Altura");
                option = sc.nextInt();
                if(option==1){
                    pokedex.mostrarPokemons();
                } else if(option==2){
                    System.out.println("Introduce un pokemon a consultar: ");
                    pokedex.buscarPokemon(sc.nextLine());
                } else if(option==3){
                    pokedex.ordenarPorAltura();
                }
            }




    }
}