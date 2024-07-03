import Data.Actor;
import Data.DB;
import Data.Director;
import Data.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DBHelper {
    private static final String dbPath = System.getProperty("user.dir") + "\\movieproject2024.db";
    private int entityCount = 0;


    public void importDB(DB db) throws IOException {
        //Scheme for Importing from file [Data.Actor,Data.Movie,Data.Director,ActorInMovie,DirectorInMovie];

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
        System.out.println("ActorCount: " + actorCount);
        System.out.println("MovieCount: " + movieCount);
        System.out.println("DirectorCount: " + directorCount);
        System.out.println("ActorMovieRelation: " + actorMovieRelation);
        System.out.println("DirectorMovieRelation: " + directorMovieRelation);
    }
    int actorCount, directorCount, movieCount, actorMovieRelation, directorMovieRelation;
    private void handleLine(String line, DB db) {
        if(line.startsWith("New_Entity:")){
            entityCount++;
            return;
        }
        String[] data = trimLine(line);
        Map<Integer,List<Integer>> map;
        switch (entityCount) {
            case 1:
                actorCount++;
                List<Actor> actorList = db.getActors();
                Actor actor = enterActorInput(data);
                if(actor == null ) break;
                if(getElementById(actor.getId(), actorList, Actor::getId) == null) db.addActor(actor);
                break;
            case 2:
                movieCount++;
                Movie movie = enterMovieInput(data);
                if(movie == null ) break;
                if(getElementById(movie.getId(), db.getMovies(), Movie::getId) == null) db.addMovie(movie);
                break;
            case 3:
                directorCount++;
                Director director = enterDirectorInput(data);
                if(director == null) break;
                if(getElementById(director.getId(), db.getDirectors(), Director::getId) == null) db.addDirector(director);
                break;
            case 4:
                actorMovieRelation++;
                map = addItemToIdMap(data, db.getActorsInMovies(), db.getMovies(), Movie::getId);
                if(map != null) db.setActorsInMovies(map);
                break;
            case 5:
                directorMovieRelation++;
                map = addItemToIdMap(data, db.getDirectorsInMovies(), db.getMovies(), Movie::getId);
                if(map != null) db.setDirectorsInMovies(map);
                break;
            default: break;
        }
    }

    private String[] trimLine(String line) {
        String[] data = line.split("\",\"");
        data[0] = data[0].replaceAll("\"", "");
        data[data.length - 1] = data[data.length - 1].replaceAll("\"", "");
        for(int i = 0; i < data.length; i++){
            data[i] = data[i].trim();
        }
        return data;
   }

   private Actor enterActorInput(String[] data) {
        try{
            int id = Integer.parseInt(data[0]);
            return new Actor(id, data[1]);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
   }
   private Director enterDirectorInput(String[] data) {
       try {
           int id = Integer.parseInt(data[0]);
           return new Director(id, data[1]);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
   }
   private Movie enterMovieInput(String[] data) {
        try {
            if(data[1].isEmpty() || data[2].isEmpty() || data[4].isEmpty()) return null;

            int id = !data[0].isEmpty() ? Integer.parseInt(data[0]) :-1;
            int votes = !data[5].isEmpty() ? Integer.parseInt(data[5]) : -1;
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
    private <T> Map<Integer,List<Integer>>addItemToIdMap(String[] data, Map<Integer,List<Integer>> map, List<T> list, Function<T,Integer> getId) {
        if (data[0].isEmpty()|| data[1].isEmpty()) return null;
        int personID = Integer.parseInt(data[0]);
        int movieID = Integer.parseInt(data[1]);
        if(getElementById(movieID, list, getId) != null){
            List<Integer> personIds = map.get(movieID);
            if(personIds == null) personIds = new ArrayList<>();
            if(!personIds.contains(personID)) personIds.add(personID);
            map.put(movieID, personIds);
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
       for (T object : list) {
           if(getId.apply(object).equals(id)) result = object;
       }
       return result;
   }
}