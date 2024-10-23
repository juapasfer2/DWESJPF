package org.example;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokedex {
    @JsonProperty("results")
    List<Pokemon> listaPokemons =new ArrayList<>();

    public void setListaPokemons(){
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            JsonNode rootNode = objectMapper.readTree(new URL("https://pokeapi.co/api/v2/pokemon"));
            listaPokemons = objectMapper.readValue(rootNode.get("results").traverse(), new TypeReference<>(){});

            //Aqui llamamos al metodo setDatos para cada pokemon de la lista
            for (Pokemon pokemon : listaPokemons) {
                pokemon.setDatosPokemon();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pokemon buscarPokemon (String nombre){
        return listaPokemons.stream().filter(a -> a.getNombre().equals(nombre)).findFirst().orElse(null);
    }
    public void mostrarPokemons(){
        for(int i=0 ; i<listaPokemons.size();i++){
            System.out.println(listaPokemons.get(i));
        }
    }
    public void ordenarPorAltura() {
        listaPokemons.sort(Comparator.comparingInt(Pokemon::getAltura));  // Ordena por altura usando Comparator
    }
    @Override
    public String toString() {
        return "Pokedex{" +
                "listaPokemons=" + listaPokemons +
                '}';
    }
}
