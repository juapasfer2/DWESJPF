package org.example.entitites;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@BsonDiscriminator(value = "profile", key = "_cls") // Atributo _cls en MongoDB, define el nombre de la clase del objeto / subdocumento
public class  Profile {

    @BsonId
    ObjectId miId;
    @BsonProperty("name")
    String name; // nombre del perfil
    @BsonProperty("status")
    String status; // estado del perfil
    @BsonProperty("age")
    int age; // edad del perfil
    @BsonProperty("since")
    LocalDate since; // fecha de creación del perfil
    @BsonProperty("posts")
    List<Post> posts; // lista de amigos del perfil

    @BsonCreator
    public Profile(@BsonProperty("name") String name,
                   @BsonProperty("status") String status,
                   @BsonProperty("age") int age) {
        this.name = name;
        this.status = status;
        this.age = age;
        posts = new ArrayList<>();
    }

    @Override
    public String toString() {
        String string = "-".repeat(20) + "\n" + name + "\nUsuario desde: " + since + "\nEstado:" + status + "\nEdad: " + age + " años\n";
        if (posts != null) {
            string += "Publicaciones:\n";
            for (Post post : posts) {
                string += post + "\n";
            }
        } else {
            string += "No ha publicado nada todavía.\n";
        }
        string += "-".repeat(20);
        return string;
    }
    public void anadirPost(Post post){
        posts.add(post);

    }

    public String getName() {
        return name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status){
        this.status=status;

    }
}

