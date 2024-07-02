import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB {
    private List<Actor> actors = new ArrayList<>();
    private List<Director> directors =new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();


    // Movie-ID as Key for Map and List of Actor-IDs/Director-IDs as Value
    private Map<Integer,List<Integer>> ActorsInMovies = new HashMap<>();
    private Map<Integer,List<Integer>> DirectorsInMovies = new HashMap<>();

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
        this.ActorsInMovies = ActorsInMovies;
    }
    public void setDirectorsInMovies(Map<Integer, List<Integer>> DirectorsInMovies) {
        this.DirectorsInMovies = DirectorsInMovies;
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
        return ActorsInMovies;
    }
    public Map<Integer,List<Integer>> getDirectorsInMovies() {
        return DirectorsInMovies;
    }
}
