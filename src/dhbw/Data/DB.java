package dhbw.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB {
    private List<Actor> actors = new ArrayList<>();
    private List<Director> directors =new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();

    //Movie-ID as Key for Map and List of Actor-IDs/Director-IDs as Value
    private Map<Integer,List<Integer>> actorsInMovies = new HashMap<>();
    private Map<Integer,List<Integer>> directorsInMovies = new HashMap<>();

    //Actor ID as Key and Movies with that Actor in it as Value in List
    private Map<Integer,List<Integer>> moviesFromActors = new HashMap<>();


    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
    public void setActorsInMovies(Map<Integer, List<Integer>> ActorsInMovies) {
        this.actorsInMovies = ActorsInMovies;
    }
    public void setDirectorsInMovies(Map<Integer, List<Integer>> DirectorsInMovies) {
        this.directorsInMovies = DirectorsInMovies;
    }
    public void setMoviesFromActors(Map<Integer, List<Integer>> MoviesFromActors) {
        this.moviesFromActors = MoviesFromActors;
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }
    public void addDirector(Director director) {
        directors.add(director);
    }
    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public List<Actor> getActors() {
        return actors;
    }
    public List<Director> getDirectors() {
        return directors;
    }
    public List<Movie> getMovies() {
        return movies;
    }
    public Map<Integer,List<Integer>> getActorsInMovies() {
        return actorsInMovies;
    }
    public Map<Integer,List<Integer>> getDirectorsInMovies() {
        return directorsInMovies;
    }
    public Map<Integer,List<Integer>> getMoviesFromActors() {
        return moviesFromActors;
    }
}
