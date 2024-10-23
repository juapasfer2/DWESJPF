import org.example.Animal;
import org.example.ProtectoraAnimales;
import org.junit.Test;
import java.nio.file.Path;
import static org.junit.Assert.assertTrue;


public class ProtectoraTests {

    @Test
    public void probarCargaAnimales(){
        ProtectoraAnimales protectora = ProtectoraAnimales.cargarXML(Path.of("..","t1.practica2", "src", "main", "resources", "protectoraAnimales.xml"));
        int tamanoLista=0;
        try{
            tamanoLista=protectora.mostarAnimales().size();
            assertTrue(tamanoLista==2);
        }
        catch (AssertionError error){
            throw error;
        }
    }

    @Test
    public void probarAñadirAnimal(){
        ProtectoraAnimales protectora = ProtectoraAnimales.cargarXML(Path.of("..","t1.practica2", "src", "main", "resources", "protectoraAnimales.xml"));
        int tamanoLista=0;
        protectora.anadirAnimal(new Animal(3,"pepe"));
        try{
            tamanoLista=protectora.mostarAnimales().size();
            assertTrue(tamanoLista==3);
        }
        catch (AssertionError error){
            throw error;
        }


    }


}
