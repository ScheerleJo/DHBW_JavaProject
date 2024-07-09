package dbhw.Helper;

import dbhw.Data.Actor;
import dbhw.Data.DB;
import dbhw.Data.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Integer.parseInt;

public class DBOutput {
    DBHelper dbHelper = new DBHelper();
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

    public String createMovieOutput(String title, DB db) {
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

    public String createActorOutput(String arg, DB db) {
        List<Actor> list = dbHelper.getElementsByName(arg, db.getActors(), Actor::getName);
        StringBuilder sb = new StringBuilder();
        for(Actor item : list) {
            sb.append(item.getId()).append(" ").append(item.getName());
            if(list.indexOf(item) != list.size() - 1) sb.append(",\n");
        }

        return sb.toString();
    }

    public String createMovieNetworkOutput(String arg, DB db) {
        Map<Integer, List<Integer>> actorsInMovies = db.getActorsInMovies();
        Map<Integer, List<Integer>> moviesFromActors = db.getMoviesFromActors();

        List<Actor> actors = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        Movie selectedMovie = dbHelper.getElementById(parseInt(arg), db.getMovies(), Movie::getId);
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
                for(Integer actorID2 : actorsInMovies.get(movieID)){
                    Actor actor2 = dbHelper.getElementById(actorID2, db.getActors(), Actor::getId);
                    if(!actors.contains(actor2)) actors.add(actor2);
                }
            }
        }
        //Build OutputString
        return "Schauspieler: " + createOutputString(actors, Actor::getName) + "\nFilme: " + createOutputString(movies, Movie::getTitle);
    }

    public String createActorNetworkOutput(String arg, DB db) {
        Map<Integer, List<Integer>> actorsInMovies = db.getActorsInMovies();
        Map<Integer, List<Integer>> moviesFromActors = db.getMoviesFromActors();

        List<Actor> actors = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        Actor selectedActor = dbHelper.getElementById(parseInt(arg), db.getActors(), Actor::getId);
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
                for(Integer movieID2 : moviesFromActors.get(actorID)){
                    Movie movie2 = dbHelper.getElementById(movieID2, db.getMovies(), Movie::getId);
                    if(!movies.contains(movie2)) movies.add(movie2);
                }
            }
        }
        return "Filme: " + createOutputString(movies, Movie::getTitle) + "\nSchauspieler: " + createOutputString(actors, Actor::getName);
    }
}
