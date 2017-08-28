package me.anna.demo.models;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

//    @NotEmpty
//    @Size(min=1, max=50, message = "Must enter name.")
    private String name;

//    @NotEmpty
//    @Size(min=1, max=50, message = "Must enter genre.")
    private String genre;

    @OneToMany(mappedBy = "director", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Movie> movies;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }


}
