package dhbw;

import dhbw.Data.DB;
import dhbw.Helper.DBHelper;
import dhbw.Helper.DBOutput;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DBHelper loadDB = new DBHelper();
        DB db = loadDB.importDB();
        System.out.println("Database imported successfully!");
        String[] search = args[0].split("=");
        handleAction(search, db);
    }

    private static void handleAction(String[] args, DB db) {
        DBOutput output = new DBOutput(db);
        switch (args[0]){
            case "--filmsuche": System.out.println("Filme: " + output.createMovieOutput(args[1])); break;
            case "--schauspielersuche": System.out.println("Schauspieler: " + output.createActorOutput(args[1])); break;
            case "--filmnetzwerk":
                System.out.println(output.createMovieNetworkOutput(args[1])); break;
            case "--schauspielernetzwerk":
                System.out.println(output.createActorNetworkOutput(args[1])); break;
            default: System.out.println("ungültiges Argument!"); break;
        }
    }
}