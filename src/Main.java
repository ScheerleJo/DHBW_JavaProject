import Data.Actor;
import Data.DB;
import Data.Director;
import Data.Movie;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        DBHelper loadDB = new DBHelper();
        DB db = loadDB.importDB();

        System.out.println("Imported");
        List<Actor> actors = db.getActors();
        List<Movie> movies = db.getMovies();
        List<Director> directors = db.getDirectors();
        Map<Integer, List<Integer>> actorsInMovies = db.getActorsInMovies();
        Map<Integer, List<Integer>> directorsInMovies = db.getDirectorsInMovies();
        System.out.println("output now");

        if(args.length == 0) {
            System.out.println("No Parameter Specified");
        } else {
            String[] search = args[0].split("=");
            handleAction(search, loadDB, db);
        }
    }

    private static void handleAction(String[] args, DBHelper dbHelper, DB db) {
        switch (args[0]){
            case "--filmsuche":
                List<Movie> movies = dbHelper.getElementsByName(args[1], db.getMovies(), Movie::getTitle);
                System.out.println("Filme: " + dbHelper.createOutputString(movies, Movie::getTitle));
                break;
            case "--schauspielersuche":
                List<Actor> actors = dbHelper.getElementsByName(args[1], db.getActors(), Actor::getName);
                System.out.println("Schauspieler: " + dbHelper.createOutputString(actors, Actor::getName));
                break;
            case "--filmnetzwerk":
                System.out.println("Filmnetzwerk"); //Placeholder
                break;
            case "--schauspielernetzwerk":
                System.out.println("Schauspielernetzwerk"); //Placeholder
                break;
        }
    }
}