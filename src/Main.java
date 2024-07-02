import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        DBhelper loadDB = new DBhelper();
        DB db = new DB();

        loadDB.importDB(db);
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

            //handleAction(args);
        }
    }

    private static void handleAction(String[] args) {
        switch (args[0]){
            case "--filmsuche":
                System.out.println("Film Suche"); //Placeholder
                break;
            case "--schauspielersuche":
                System.out.println("Schauspieler Suche");//Placeholder
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