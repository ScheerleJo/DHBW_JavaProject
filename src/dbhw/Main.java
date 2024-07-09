package dbhw;

import dbhw.Data.DB;
import dbhw.Helper.DBHelper;
import dbhw.Helper.DBOutput;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DBHelper loadDB = new DBHelper();
        DB db = loadDB.importDB();
        System.out.println("Database imported successfully!");
        args = new String[]{"--filmnetzwerk=2047"};
        String[] search = args[0].split("=");
        handleAction(search, db);
    }

    private static void handleAction(String[] args, DB db) {
        DBOutput output = new DBOutput();
        switch (args[0]){
            case "--filmsuche": System.out.println("Filme: " + output.createMovieOutput(args[1], db)); break;
            case "--schauspielersuche": System.out.println("Schauspieler: " + output.createActorOutput(args[1], db)); break;
            case "--filmnetzwerk": System.out.println(output.createMovieNetworkOutput(args[1], db)); break;
            case "--schauspielernetzwerk": System.out.println(output.createActorNetworkOutput(args[1], db)); break;
            default: System.out.println("ungültiges Argument!"); break;
        }
    }
}