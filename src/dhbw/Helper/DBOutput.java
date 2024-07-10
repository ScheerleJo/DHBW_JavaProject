package dhbw.Helper;

import dhbw.Data.Actor;
import dhbw.Data.DB;
import dhbw.Data.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Integer.parseInt;

public class DBOutput {
    DB db;
    public DBOutput(DB db) {
        this.db = db;
    }
    public DBOutput(){}

    private final DBHelper dbHelper = new DBHelper();
    /**
     * Creates the Output-String with the list of persons
     * @param list the List of Elements, to get the Name from
     * @param getName the function to get the name of the person
     * @param <T> the type of the person
     * @return the output string
     */
    public <T> String createOutputString(List<T> list, Function<T, String> getName) {

        StringBuilder sb = new StringBuilder();
        for(T item : list) {
            sb.append(getName.apply(item));
            if(list.indexOf(item) != list.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    /**
     * Creates the Output-String for the Movie with the specified title
     * @param title the title of the movie
     * @return the output string with all Movie Details
     */
    public String createMovieOutput(String title) {
        List<Movie> movies = dbHelper.getElementsByName(title, db.getMovies(), Movie::getTitle);
        StringBuilder sb = new StringBuilder();
        for(Movie item : movies) {
            sb.append(item.getId());
            sb.append(" Titel: ").append(item.getTitle());
            if(!item.getGenre().isEmpty()) sb.append(" Genre: ").append(item.getGenre());
            if(!item.getPlot().isEmpty()) sb.append(" Plot: ").append(item.getPlot());
            if(!item.getPublished().isEmpty()) sb.append(" Datum: ").append(item.getPublished());
            if(item.getImdbRating() != -1) sb.append(" Bewertung: ").append(item.getImdbRating());
            if(movies.indexOf(item) != movies.size() - 1) sb.append(",\n");
        }
        return sb.toString();
    }

    /**
     * Creates the Output-String for the Actor with the specified name
     * @param name the name of the actor
     * @return the output string with all Actor Details
     */
    public String createActorOutput(String name) {
        List<Actor> list = dbHelper.getElementsByName(name, db.getActors(), Actor::getName);
        StringBuilder sb = new StringBuilder();
        for(Actor item : list) {
            sb.append(item.getId()).append(" ").append(item.getName());
            if(list.indexOf(item) != list.size() - 1) sb.append(",\n");
        }
        return sb.toString();
    }

    /**
     * Creates the Output-String for the Movie-Network with the specified movie
     * @param id the id of the movie the network is created for
     * @return the output string with the given format
     */
    public String createMovieNetworkOutput(String id) {
        Map<Integer, List<Integer>> actorsInMovies = db.getActorsInMovies();
        Map<Integer, List<Integer>> moviesFromActors = db.getMoviesFromActors();

        List<Actor> actors = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        Movie selectedMovie = dbHelper.getElementById(parseInt(id), db.getMovies(), Movie::getId);
        if (selectedMovie == null) return null;
        movies.add(selectedMovie);

        List<Integer> actorIDs = actorsInMovies.get(selectedMovie.getId());

        for (Integer actorID : actorIDs) {
            Actor actor = dbHelper.getElementById(actorID, db.getActors(), Actor::getId);
            if(!actors.contains(actor)) actors.add(actor);
            List<Integer> movieIDs = moviesFromActors.get(actorID);
            for (Integer movieID : movieIDs){
                Movie movie = dbHelper.getElementById(movieID, db.getMovies(), Movie::getId);
                if(!movies.contains(movie)) movies.add(movie);
            }
        }
        movies.remove(selectedMovie);
        //Build OutputString
        String actorString = createOutputString(actors, Actor::getName);
        String movieString = createOutputString(movies, Movie::getTitle);
        return "Schauspieler: " + actorString + "\r\nFilme: " + movieString;
    }

    /**
     * Creates the Output-String for the Actor-Network with the specified actor
     * @param id the id of the actor the network is created for
     * @return the output string with the given format
     */
    public String createActorNetworkOutput(String id) {
        Map<Integer, List<Integer>> actorsInMovies = db.getActorsInMovies();
        Map<Integer, List<Integer>> moviesFromActors = db.getMoviesFromActors();
        List<Actor> actors = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        Actor selectedActor = dbHelper.getElementById(parseInt(id), db.getActors(), Actor::getId);
        if(selectedActor == null) return null;
        actors.add(selectedActor);

        List<Integer> movieIDs = moviesFromActors.get(selectedActor.getId());
        for (Integer movieID : movieIDs){
            Movie movie = dbHelper.getElementById(movieID, db.getMovies(), Movie::getId);
            if(!movies.contains(movie)) movies.add(movie);
            List<Integer> actorIDs = actorsInMovies.get(movieID);
            for (Integer actorID : actorIDs){
                Actor actor = dbHelper.getElementById(actorID, db.getActors(), Actor::getId);
                if(!actors.contains(actor)) actors.add(actor);
            }
        }
        actors.remove(selectedActor);
        String actorString = createOutputString(actors, Actor::getName);
        String movieString = createOutputString(movies, Movie::getTitle);
        return "Filme: " + movieString + "\r\nSchauspieler: " + actorString;
    }
}
