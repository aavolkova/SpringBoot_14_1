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
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    DirectorRepo directorRepo;

    @Autowired
    MovieRepo movieRepo;

//    @RequestMapping("/")
    @GetMapping("/secondindex")
    public @ResponseBody String showDir2()
    {
        Director test = directorRepo.findOne(new Long(2));
        for(Movie newM: test.getMovies())
        {
            System.out.println(newM.getTitle());
        }
        return "Your results are ready";
    }
    @GetMapping("/")
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

        // Add the movie to an empty list
        Set<Movie> movies = new HashSet<Movie>();
        movies.add(movie);

        movie = new Movie();
        movie.setTitle("DeathStar Ewoks");
        movie.setYear(2011);
        movie.setDescription("About Ewoks on the DeathStar...");
        movies.add(movie);


        // Add the list of movies to the director's movie list
        director.setMovies(movies);

        // Save the director to the database
        directorRepo.save(director);
        Set <Movie> directorMovies = director.getMovies();
        for(Movie aMovie :directorMovies)
        {
            System.out.println(aMovie.getTitle());
        }

        // Grab all the directors from the database and send them to the template

        Director nd = new Director();
        nd.setName("Person Director");
        nd.setGenre("A genre");

        Movie m = new Movie();
        m.setDescription("A movie about stuff");
        m.setTitle("The ultimate movie");

        Set <Movie> mList = new HashSet<Movie>();
        mList.add(m);

        nd.setMovies(mList);
        directorRepo.save(nd);

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
    public String resultDirectors(@Valid @ModelAttribute("newDirector") Director director, BindingResult bindingResult) {


        if(bindingResult.hasErrors()){
            return "directorForm";
        }


        directorRepo.save(director);
        return "resultDirector";

    }


    // Add new Movie
    @GetMapping("/movieForm")
    public String addMovies(Model model) {

        model.addAttribute("newMovie", new Movie());
        return "movieForm";
    }


    @PostMapping("/movieForm")
    public String resultMovies(@Valid @ModelAttribute("newMovie") Movie movie, BindingResult bindingResult) {


        if(bindingResult.hasErrors()){
            return "movieForm";
        }


        movieRepo.save(movie);
        return "resultMovie";

    }

}
