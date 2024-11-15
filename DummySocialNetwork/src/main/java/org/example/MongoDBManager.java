package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.example.entitites.Post;
import org.example.entitites.Profile;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBManager {
    MongoCollection<Profile> profiles; // Colección de perfiles
    Profile myProfile; // Mi perfil

    public MongoDBManager(String uri, String databaseName, String collectionName) {
        MongoClient mongoClient;
        try {
            mongoClient = MongoClients.create(uri);
            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            MongoDatabase database = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);

            profiles = database.getCollection(collectionName, Profile.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void createProfile(String name, String status, int age) {
        Profile existingProfile = profiles.find(eq("name", name)).first();
        myProfile = new Profile(name, status, age);
        if(existingProfile==null){
            profiles.insertOne(myProfile);
            System.out.println("Libro insertado: " + myProfile.getName() + "\n");
        }else
            System.out.println("Has entrado en tu perfil ya que ya existe");
    }

    public void publishPost(String title, String content) {
        myProfile.anadirPost(new Post(title, content));
        profiles.updateOne(
                eq("name", myProfile.getName()),
                Updates.set("posts", myProfile.getPosts())
        );//tenemos que hacer update ya q si hacemos replace id es inmutable
        /*
        profiles.replaceOne(
                eq("name",myProfile.getName()),
                myProfile
        );
        */
    }

    public void updateStatus(String status) {
        myProfile.setStatus(status);
        profiles.updateOne(
                eq("name", myProfile.getName()),
                Updates.set("status", myProfile.getStatus())
        );
    }

    public void deleteProfile() {
        profiles.deleteOne(
                eq("name", myProfile.getName())
        );
    }

    public void showProfiles() {
        for (Profile profile : profiles.find()) {
            System.out.println(profile.toString());
        }
    }

    public void showPosts(String profileName) {
        try {
            for( Profile profile : profiles.find(eq("name",profileName))){
                profile.getPosts().forEach(p -> System.out.println(p.toString()));
            }
        }catch(NullPointerException e){
            System.out.println(profileName + " no tiene comentarios\n");
        }
    }

    public void likePost(String profileName, String title) {
        try {
            for (Profile profile : profiles.find(eq("name", profileName))) {
                boolean postFound = false;
                for (Post post : profile.getPosts()) {
                    if (post.getTitle().equals(title)) {
                        post.setLikes(post.getLikes() + 1);
                        profiles.updateOne(
                                eq("name", profileName),
                                Updates.set("posts", profile.getPosts())
                        );
                        postFound = true;
                        System.out.println("El post ha recibido un like. Likes actuales: " + post.getLikes());
                        break;
                    }
                }
                if (!postFound) {
                    System.out.println("No se ha encontrado el post con el título especificado.\n");
                }
                return;
            }
            System.out.println("No se ha encontrado el perfil con el nombre especificado.\n");
        }
        catch (NullPointerException e){
            System.out.println("No se ha encontrado el perfil o la publicacion\n");
        }
    }

    public void commentPost(String profileName, String title, String comment) {
        try{
            for (Profile profile : profiles.find(eq("name", profileName))) {
                boolean postFound = false;
                for (Post post : profile.getPosts()) {
                    if (post.getTitle().equals(title)) {
                        post.getComments().add(comment);
                        profiles.updateOne(
                                eq("name", profileName),
                                Updates.set("posts", profile.getPosts())
                        );
                        postFound = true;
                        System.out.println("El post ha recibido comentario. Comentarios actuales: " + post.getComments());
                        break;
                    }
                }
                if (!postFound) {
                    System.out.println("No se ha encontrado el post con el título especificado.\n");
                }
                return;
            }
            System.out.println("No se ha encontrado el perfil con el nombre especificado.\n");
        }catch (NullPointerException e){
            System.out.println("Ha ocurrido un errror al comentar\n");
        }
    }

    public void showProfileStats() {
        try {
            int numPublicaciones=0;
            int totalLikes = 0;
            int totalComentarios = 0;
            Profile profile = profiles.find(eq("name", myProfile.getName())).first();

            if(profile.getPosts()!=null) {
                numPublicaciones = profile.getPosts().size();
                for (Post post : profile.getPosts()) {
                    totalLikes += post.getLikes();
                    totalComentarios += post.getComments().size();
                }
            }
            System.out.println("Numero de publicaciones: " + numPublicaciones + "\nTotal de likes: " + totalLikes + "\nTotal de comentarios: " + totalComentarios);
        }catch (NullPointerException e){
            System.out.println("No has entrado en un perfil");
        }
    }

    public void showAllStats() {
        try {
            int totalProfiles = 0;
            int totalPosts = 0;
            int totalLikes = 0;
            for (Profile profile : profiles.find()) {
                totalProfiles++;
                for (Post post : profile.getPosts()) {
                    totalPosts++;
                    totalLikes += post.getLikes();
                }
            }
            System.out.println("Número total de perfiles: " + totalProfiles);
            System.out.println("Número total de publicaciones: " + totalPosts);
            System.out.println("Número total de likes: " + totalLikes);
        } catch (Exception e) {
            System.out.println("Error al obtener las estadísticas de la red social: " + e.getMessage());
        }
    }
}
