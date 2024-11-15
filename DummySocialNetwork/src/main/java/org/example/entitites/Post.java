package org.example.entitites;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@BsonDiscriminator(value = "post", key = "_cls")
public class Post {
    @BsonProperty("title")
    String title; // título del post
    @BsonProperty("content")
    String content; // contenido del post
    @BsonProperty("publishedDate")
    LocalDate publishedDate; // fecha de creación del post
    @BsonProperty("likes")
    int likes; // cantidad de likes del post
    @BsonProperty("comments")
    List<String> comments; // lista de comentarios del post

    @BsonCreator()
    public Post(@BsonProperty("title") String title, @BsonProperty("content") String content) {
        this.title = title;
        this.content=content;
        this.comments = new ArrayList<>();
        this.publishedDate = LocalDate.now();
        this.likes=0;
    }

    @Override
    public String toString() {
        String string = title + "\n" + publishedDate + "\n" + likes + " likes\n" + content + "\n";
        if (this.comments != null) {
            for (String comment : comments) {
                string += " - " + comment + "\n";
            }
        }
        return string;
    }
    public void setLikes(int likes){
        this.likes=likes;

    }
    public int getLikes(){
        return likes;
    }

    public String getTitle() {
        return title;
    }
    public List<String> getComments(){
        return comments;
    }
}
