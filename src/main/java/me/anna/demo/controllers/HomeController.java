package me.anna.demo.controllers;

import me.anna.demo.repositories.DirectorRepo;
import me.anna.demo.repositories.MovieRepo;
import me.anna.demo.models.Director;
import me.anna.demo.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    DirectorRepo directorRepo;

    @Autowired
    MovieRepo movieRepo;

//    @RequestMapping("/")
    // For testing:
//    @GetMapping("/secondindex")
//    public @ResponseBody String showDir2()
//    {
//        Director test = directorRepo.findOne(new Long(2));
//        for(Movie newM: test.getMovies())
//        {
//            System.out.println(newM.getTitle());
//        }
//        return "Your results are ready";
//    }


//    @GetMapping("/")
    @RequestMapping("/")
    public String index(Model model){

        // First let's create a derector
        Director director = new Director();
        director.setName("Stephen Bullock");
        director.setGenre("Sci Fi");

        // Now let's create a movie
        Movie movie = new Movie();
        movie.setTitle("Star Movie");
        movie.setYear(2050);
        movie.setDescription("About Stars...");


        // Add this movie to the director:
        director.addMovie(movie);

        // Add the movie to an empty list
//        Set<Movie> movies = new HashSet<Movie>();
//        movies.add(movie);

        // Create a new movie:
        movie = new Movie();
        movie.setTitle("DeathStar Ewoks");
        movie.setYear(2011);
        movie.setDescription("About Ewoks on the DeathStar...");
//        movies.add(movie);


        // Add this movie to the director:
        director.addMovie(movie);




        // Add the list of movies to the director's movie list
//        director.setMovies(movies);

        // Save the director to the database
        directorRepo.save(director);
//        Set <Movie> directorMovies = director.getMovies();
//        for(Movie aMovie :directorMovies)
//        {
//            System.out.println(aMovie.getTitle());
//        }

        // Grab all the directors from the database and send them to the template



        //Create a new director
//        Director nd = new Director();
//        nd.setName("Person Director");
//        nd.setGenre("A genre");
//
//        Movie m = new Movie();
//        m.setDescription("A movie about stuff");
//        m.setTitle("The ultimate movie");

//        Set <Movie> mList = new HashSet<Movie>();
//        mList.add(m);
//
//        nd.setMovies(mList);
//        directorRepo.save(nd);

        model.addAttribute("directors", directorRepo.findAll());

        return "index";
    }

    // Add new Director
    @GetMapping("/directorForm")
    public String addDirectors(Model model) {

        model.addAttribute("newDirector", new Director());
        return "directorForm";
    }


    @PostMapping("/directorForm")
    public String resultDirectors(@ModelAttribute("newDirector") Director director){//, BindingResult bindingResult) {


//        if(bindingResult.hasErrors()){
//            return "directorForm";
//        }


        directorRepo.save(director);
        return "resultDirector";

    }


    //See all directors
    @GetMapping("/listAllDirectors")
    public String showAllDirectors(Model model) {

//        Director director = directorRepo.findOne(new Long(1));
        Iterable <Director> directorlist = directorRepo.findAll();

        model.addAttribute("alldirectors", directorlist);
        return "listAllDirectors";
    }



    //============ Update Director Info ===============

    @GetMapping("/updateDirector/{id}")
    public String updateDirector(@PathVariable("id") long id, Model model)
    {
        Director d = directorRepo.findOne(id);
        model.addAttribute("newDirector", d);
        return "directorForm";
    }


    // Add new Movie
    @GetMapping("/movieForm/{id}")
    public String addMovies(@PathVariable("id") long id, Model model) {

        Director d = directorRepo.findOne(id);
        model.addAttribute("newDirector", d);
        Movie m = new Movie();
        m.setDirector(d);
        model.addAttribute("newMovie", m);
//        movieRepo.save(m);  // save only director_id here, all other movie fields: nulls
        return "movieForm";
    }

    @PostMapping("/movieForm")
    public String resultMovies(@ModelAttribute("newMovie") Movie movie, Model model){

        movieRepo.save(movie);
        System.out.println(movie.getDirector().getId());
        System.out.println(movie.getDirector().getName());

        model.addAttribute("newMovie", movie);

        String directorName = movie.getDirector().getName();
        model.addAttribute("directorname", directorName);
        return "resultMovie";

    }


/////////////    See all Movies
    @GetMapping("/listAllMovies")
    public String showAllMovies(Model model) {

        Iterable<Movie> movielist = movieRepo.findAll();

        model.addAttribute("allmovies", movielist);
        return "listAllMovies";
    }



    //============ Update Movie Info ===============

    @GetMapping("/updateMovie/{id}")
    public String updateMovie(@PathVariable("id") long id, Model model)
    {
        Movie m = movieRepo.findOne(id);
        model.addAttribute("newMovie", m);
        return "movieForm";
    }


    @GetMapping("/directorDetails/{id}")
    public String showDirectorDetails(@PathVariable("id") long id, Model model) {

        model.addAttribute("directors", directorRepo.findOne(id));
        return "directorDetails";
    }





    @GetMapping("/movieDetails/{id}")
    public String showMovieDetails(@PathVariable("id") long id, Model model) {

        Movie m = movieRepo.findOne(id);


        model.addAttribute("movie", m);
       // model.addAttribute("directorname", directorName);

        return "movieDetails";
    }

}
