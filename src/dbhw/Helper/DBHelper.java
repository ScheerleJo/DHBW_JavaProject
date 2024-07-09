package dbhw.Helper;

import dbhw.Data.Actor;
import dbhw.Data.DB;
import dbhw.Data.Director;
import dbhw.Data.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Integer.parseInt;

public class DBHelper {
    private static final String dbPath = System.getProperty("user.dir") + "\\movieproject2024.db";
    private int entityCount = 0;

    /**
     * Reads the local file and imports it into the DB
     * @return the database with all imported data
     * @throws IOException if file is not found or could not be opened
     */
    public DB importDB() throws IOException {
        DB db = new DB();
        //Scheme for Importing from file [dbhw.Data.Actor,dbhw.Data.Movie,dbhw.Data.Director,ActorInMovie,DirectorInMovie];

        File file = new File(dbPath);
        if(!(file.exists() && file.canRead())) {
            throw new FileNotFoundException("File at location " + dbPath + " is unreadable or doesn't exist");
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dbPath));
            String line;
            while ((line = br.readLine()) != null) {
                handleLine(line, db);
            }
        } catch(Exception e) {e.printStackTrace();} finally {
            try {
                if(br != null)br.close();
            }catch(Exception e) {e.printStackTrace();}
        }
        return db;
    }

    /**
     * Handles the line and adds the data to the database
     * @param line the line to be handled
     * @param db the database to add the data to
     */
    private void handleLine(String line, DB db) {
        if(line.startsWith("New_Entity:")){
            entityCount++;
            return;
        }
        String[] data = trimLine(line);
        Map<Integer,List<Integer>> map;
        switch (entityCount) {
            case 1:
                Actor actor = enterActorInput(data);
                if(actor == null ) break;
                if(getElementById(actor.getId(), db.getActors(), Actor::getId) == null) db.addActor(actor);
                break;
            case 2:
                Movie movie = enterMovieInput(data);
                if(movie == null ) break;
                if(getElementById(movie.getId(), db.getMovies(), Movie::getId) == null) db.addMovie(movie);
                break;
            case 3:
                Director director = enterDirectorInput(data);
                if(director == null) break;
                if(getElementById(director.getId(), db.getDirectors(), Director::getId) == null) db.addDirector(director);
                break;
            case 4:
                map = addItemToIdMap(data, db.getActorsInMovies(), db.getMovies(), Movie::getId);
                if(map != null) db.setActorsInMovies(map);
                data = new String[]{data[1], data[0]};
                map = addItemToIdMap(data, db.getMoviesFromActors(), db.getActors(), Actor::getId);
                if(map != null) db.setMoviesFromActors(map);
                break;
            case 5:
                map = addItemToIdMap(data, db.getDirectorsInMovies(), db.getMovies(), Movie::getId);
                if(map != null) db.setDirectorsInMovies(map);
                break;
            default: break;
        }
    }

    /**
     * Splits the String with "," delimiter into an array and Trims excess Spaces and Quotes
     * @param line the line to be split and trimmed
     * @return the trimmed line as an array
     */
    public String[] trimLine(String line) {
        String[] data = line.split("\",\"");
        data[0] = data[0].replaceAll("\"", "");
        data[data.length - 1] = data[data.length - 1].replaceAll("\"", "");
        for(int i = 0; i < data.length; i++){
            data[i] = data[i].trim();
        }
        return data;
   }

   /**
    * Creates a new actor object from the data provided in the array
    * @param data data of the current line in the db-file
    * @return the actor object created from the data
    */
   public Actor enterActorInput(String[] data) {
        try{
            int id = parseInt(data[0]);
            return new Actor(id, data[1]);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
   }

    /**
     * Creates a new director object from the data provided in the array
     * @param data data of the current line in the db-file
     * @return the director object created from the data
     */
   public Director enterDirectorInput(String[] data) {
       try {
           int id = parseInt(data[0]);
           return new Director(id, data[1]);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
   }

    /**
     * Creates a new movie object from the data provided in the array
     * @param data data of the current line in the db-file
     * @return the movie object created from the data
     */
   public Movie enterMovieInput(String[] data) {
        try {
            if(data[1].isEmpty() || data[2].isEmpty() || data[4].isEmpty()) return null;

            int id = !data[0].isEmpty() ? parseInt(data[0]) :-1;
            int votes = !data[5].isEmpty() ? parseInt(data[5]) : -1;
            double rating = !data[6].isEmpty() ? Double.parseDouble(data[6]) : -1.0;
            return new Movie(id, data[1],data[2], data[3], data[4],votes, rating);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
   }

    /**
     * Adds the person to the map of persons in movies
     * @param data the data to be added
     * @param map the map to add the data to
     * @param list the list to search for the movie
     * @param getId the function to get the id of the element
     * @param <T> the type of the element
     * @return the updated map
     */
    public <T> Map<Integer,List<Integer>>addItemToIdMap(String[] data, Map<Integer,List<Integer>> map, List<T> list, Function<T,Integer> getId) {
        if (data[0].isEmpty()|| data[1].isEmpty()) return null;
        int valueID = parseInt(data[0]);
        int keyID = parseInt(data[1]);
        if(getElementById(keyID, list, getId) != null){
            List<Integer> valueIDs = map.get(keyID);
            if(valueIDs == null) valueIDs = new ArrayList<>();
            if(!valueIDs.contains(valueID)) valueIDs.add(valueID);
            map.put(keyID, valueIDs);
        }
        return map;
   }

    /**
     * Returns the element with the specified id from the list
     * @param id the id of the element to be returned
     * @param list the list to search in
     * @param getId the function to get the id of the element
     * @return the element with the specified id or null if not found
     * @param <T> the type of the element
     */
   public <T> T getElementById(int id, List<T> list, Function<T, Integer> getId) {
       T result = null;
       for (T item : list) {
           if(getId.apply(item).equals(id)) result = item;
       }
       return result;
   }

    /**
     * Returns a list of elements with the specified name
     * @param name the name to search for
     * @param list the list to search in
     * @param getName the function to get the name of the element
     * @return the list of elements with the specified name
     * @param <T> the type of the element
     */
   public <T> List<T> getElementsByName(String name, List<T> list, Function<T, String> getName){
       List<T> result = new ArrayList<>();
       for(T item : list) {
           if(getName.apply(item).contains(name)) {
               result.add(item);
           }
       }
       if(result.isEmpty()) return null;
       return result;
   }
}