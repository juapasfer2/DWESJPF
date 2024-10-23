package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoveWrapper {

    @JsonProperty("move")
    Movimiento movimiento;

    @Override
    public String toString() {
        return     "{movimiento=" + movimiento +
                '}';
    }
}