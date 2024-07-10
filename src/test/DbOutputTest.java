package test;

import dhbw.Data.Actor;
import dhbw.Data.DB;
import dhbw.Data.Movie;
import dhbw.Helper.DBOutput;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DbOutputTest {

    /**
     * GIVEN a dbOutput-object and a list of actors
     * WHEN createOutputString is called with the list and a function to get the name
     * THEN the output string is created with the names of the actors
     */
    @Test
    public void testCreateOutputString() {
        DBOutput dbOutput = new DBOutput();
        List<Actor> actors = List.of(new Actor(1, "Actor1"), new Actor(2, "Actor2"), new Actor(3, "Actor3"));

        String output = dbOutput.createOutputString(actors, Actor::getName);

        assertEquals("Actor1, Actor2, Actor3", output);
        assertNotEquals("Actor1, Actor2, Actor3, ", output);
    }

    /**
     * GIVEN a dbOutput-object and a movie in the DB
     * WHEN createMovieOutput is called with the title of the movie and the DB
     * THEN the output string is created with the information existent for the movie
     */
    @Test
    public void testCreateMovieOutput() {
        DB db = new DB();
        db.addMovie(new Movie(1, "Movie1", "","Genre1", "2021-01-01", 1, 1.0));
        DBOutput dbOutput = new DBOutput(db);

        String output = dbOutput.createMovieOutput("Movie1");

        assertEquals("1 Titel: Movie1 Genre: Genre1 Datum: 2021-01-01 Bewertung: 1.0", output);
    }

    /**
     * GIVEN a dbOutput-object and an actor in the DB
     * WHEN createActorOutput is called with the name of the actor and the DB
     * THEN the output string is created with the information existent for the actor
     */
    @Test
    public void testCreateActorOutput() {
        DB db = new DB();
        db.addActor(new Actor(1, "Actor1"));
        DBOutput dbOutput = new DBOutput(db);

        String output = dbOutput.createActorOutput("Actor1");

        assertEquals("1 Actor1", output);
    }

    /**
     * GIVEN a dbOutput-object and a DB filled with actors and movies
     * WHEN createMovieNetworkOutput is called with the id of the movie and the DB
     * THEN the output string is created with the Name of the actors in the movie and the movies they are in
     */
    @Test
    public void testCreateMovieNetworkOutput() {
        DB db = new DB();
        db.addMovie(new Movie(100, "Movie1", "", "Genre1", "2021-01-01", 1, 1.0));
        db.addMovie(new Movie(101, "Movie2", "", "Genre2", "2021-01-02", 2, 2.0));
        db.addActor(new Actor(1, "Actor1"));
        db.addActor(new Actor(2, "Actor2"));
        db.addActor(new Actor(3, "Actor3"));
        db.addActor(new Actor(4, "Actor4"));
        db.addActor(new Actor(5, "Actor5"));
        db.addActor(new Actor(6, "Actor6"));

        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(100, List.of(1, 2, 3));
        map.put(101, List.of(2, 5, 6));
        db.setActorsInMovies(map);

        map = new HashMap<>();
        map.put(1, List.of(100));
        map.put(2, List.of(100, 101));
        map.put(3, List.of(100));
        map.put(5, List.of(101));
        map.put(6, List.of(101));
        db.setMoviesFromActors(map);
        DBOutput dbOutput = new DBOutput(db);

        String output = dbOutput.createMovieNetworkOutput("100");
        assertEquals("Schauspieler: Actor1, Actor2, Actor3\r\nFilme: Movie2", output);

        output = dbOutput.createMovieNetworkOutput("101");
        assertEquals("Schauspieler: Actor2, Actor5, Actor6\r\nFilme: Movie1", output);
    }

    /**
     * GIVEN a dbOutput-object and a DB filled with actors and movies
     * WHEN createActorNetworkOutput is called with the id of the actor and the DB
     * THEN the output string is created with the Name of the actor and the movies he is in as well as the other actors in the movies and the movies they are in
     */
    @Test
    public void testcreateActorNetworkOutput(){
        DB db = new DB();
        db.addMovie(new Movie(100, "Movie1", "", "Genre1", "2021-01-01", 1, 1.0));
        db.addMovie(new Movie(101, "Movie2", "", "Genre2", "2021-01-02", 2, 2.0));
        db.addActor(new Actor(1, "Actor1"));
        db.addActor(new Actor(2, "Actor2"));
        db.addActor(new Actor(3, "Actor3"));
        db.addActor(new Actor(4, "Actor4"));
        db.addActor(new Actor(5, "Actor5"));
        db.addActor(new Actor(6, "Actor6"));

        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(100, List.of(1, 2, 3));
        map.put(101, List.of(2, 5, 6));
        db.setActorsInMovies(map);

        map = new HashMap<>();
        map.put(1, List.of(100));
        map.put(2, List.of(100, 101));
        map.put(3, List.of(100));
        map.put(5, List.of(101));
        map.put(6, List.of(101));
        db.setMoviesFromActors(map);
        DBOutput dbOutput = new DBOutput(db);


        String output = dbOutput.createActorNetworkOutput("1");
        assertEquals("Filme: Movie1\r\nSchauspieler: Actor2, Actor3", output);

    }
}
