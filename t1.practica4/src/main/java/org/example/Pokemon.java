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
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    @JsonProperty("name")
    String nombre;
    @JsonProperty("url")
    String url;
    @JsonProperty("height")
    int altura;
    @JsonProperty("moves")
    List<MoveWrapper> movimientosList = new ArrayList<>();



    public void setDatosPokemon(){
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            Pokemon pokemon = objectMapper.readValue(new URL(this.url), Pokemon.class);
            this.altura = pokemon.altura;
           this.movimientosList=pokemon.movimientosList;
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "nombre='" + nombre + '\'' +
                ", altura=" + altura + ", Movimientos: " + movimientosList +
                '}';
    }



}
