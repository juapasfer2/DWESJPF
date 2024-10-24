package org.example;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "protectoraDeAnimales")
public class ProtectoraAnimales {
    @JacksonXmlElementWrapper(localName = "animales")
    @JacksonXmlProperty(localName = "animal")
    private List<Animal> animales;

/*    public List<Animal> getAnimales() {
        return animales;
    }*/

    public void setAnimales(List<Animal> animales) {
        this.animales = animales;
    }

    public static ProtectoraAnimales cargarXML(Path ruta){
        try {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(ruta.toFile(), ProtectoraAnimales.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void guardarInformacion(ProtectoraAnimales protectora, Path ruta) {
        try {
            Files.deleteIfExists(ruta);
            XmlMapper xmlMapper = new XmlMapper();
            // La siguiente línea es opcional, pero hace que el JSON se muestre con formato
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlMapper.writeValue(ruta.toFile(), protectora);
        } catch (IOException e) {
            System.out.println("El fichero no existe");
        }
    }

    public void anadirAnimal(Animal a) {
        if (animales == null) {
            animales = new ArrayList<>();
        }else {
            animales.add(a);
        }
    }

    public void borrarAnimal(int id){
        if (animales != null) {
            animales.removeIf(animal -> animal.getId() == id);
        }
    }
    public Animal consultarAnimal(int id){
        return animales.stream()
                .filter(a -> a.getId()==id)
                .toList().get(0);
    }

    public List<Animal> mostarAnimales(){
        return animales;
    }

    @Override
    public String toString() {
        return "ProtectoraAnimales{" +
                "animales=" + animales +
                '}';
    }
}
