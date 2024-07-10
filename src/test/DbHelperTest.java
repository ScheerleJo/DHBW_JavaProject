package test;

import dhbw.Data.Actor;
import dhbw.Data.Director;
import dhbw.Data.Movie;
import dhbw.Helper.DBHelper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class DbHelperTest {

    /**
     * GIVEN a dbHelper-Object and a line of data
     * WHEN trimLine is called with the String
     * THEN the line is split into an array of Strings
     */
    @Test public void testTrimLine(){
        DBHelper dbHelper = new DBHelper();
        String input = "\"New_Entity: \",\"1  \"";
        String[] data = dbHelper.testtrimLine(input);
        assertEquals(2, data.length);
        assertEquals("New_Entity:", data[0]);
        assertEquals("1", data[1]);
    }

    /**
     * GIVEN a dbHelper-Object and an input String array
     * WHEN enterActorInput is called with the data of an actor
     * THEN a new actor object is created with the data
     */
    @Test public void testEnterActorInput() {
        DBHelper dbHelper = new DBHelper();
        String[] input = {"256", "New Actor"};

        Actor actor = dbHelper.testenterActorInput(input);

        assertNotNull(actor);
        assertEquals("New Actor", actor.getName());
        assertEquals(256, actor.getId());
    }

    /**
     * GIVEN a dbHelper-Object and an input String array
     * WHEN enterMovieInput is called with the data of a movie
     * THEN a new movie object is created with the data
     */
    @Test public void testEnterMovieInput() {
        DBHelper dbHelper = new DBHelper();
        String[] input = {"256", "New Movie", "This is the plot", "Genre", "2024-07-03", "24", "5.7"};

        Movie movie = dbHelper.testenterMovieInput(input);

        assertNotNull(movie);
        assertEquals("New Movie", movie.getTitle());
        assertEquals("This is the plot", movie.getPlot());
        assertEquals("Genre", movie.getGenre());
        assertEquals("2024-07-03", movie.getPublished());
        assertEquals(24, movie.getVotes());
        assertEquals(5.7, movie.getImdbRating(), 0.01);
    }

    /**
     * GIVEN a dbHelper-Object and an input String array
     * WHEN enterDirectorInput is called with the data of a director
     * THEN a new director object is created with the data
     */
    @Test public void testEnterDirectorInput() {
        DBHelper dbHelper = new DBHelper();
        String[] input = {"256", "New Director"};

        Director director = dbHelper.testenterDirectorInput(input);

        assertNotNull(director);
        assertEquals("New Director", director.getName());
        assertEquals(256, director.getId());
    }

    /**
     * GIVEN a dbHelper-Object, a List of movies, a map and an input String array
     * WHEN addItemToIdMap is called with a list of movies and a map containing the ids of the movies and a list of actors
     * THEN the map is updated with the new ids if they do not exist in the map
     */
    @Test public void testAddItemToIdMap() {
        DBHelper dbHelper = new DBHelper();
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "Movie1", "Plot1", "Genre1", "2024-07-03", 24, 5.7));
        movies.add(new Movie(2, "Movie2", "Plot2", "Genre2", "2024-07-03", 24, 5.7));
        movies.add(new Movie(3, "Movie3", "Plot3", "Genre3", "2024-07-03", 24, 5.7));
        String[] input = {"6", "2"};
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(5);
        map.put(1, list);

        dbHelper.testaddItemToIdMap(input, map, movies, Movie::getId);

        assertEquals(2, map.size());
        assertEquals(6, map.get(2).getFirst().intValue());
        assertEquals(5, map.get(1).getFirst().intValue());
    }

    /**
     * GIVEN a dbHelper-Object and a List of actors
     * WHEN getElementById is called with the id of an actor
     * THEN the actor is returned if it exists in the list otherwise null is returned
     */
    @Test public void testGetElementById() {
        DBHelper dbHelper = new DBHelper();
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor(1, "Actor1"));
        actors.add(new Actor(2, "Actor2"));
        actors.add(new Actor(3, "Actor3"));
        assertNotNull(dbHelper.getElementById(1, actors, Actor::getId));
        assertEquals(1, dbHelper.getElementById(1, actors, Actor::getId).getId());
        assertEquals("Actor2", dbHelper.getElementById(2, actors, Actor::getId).getName());
        assertNull(dbHelper.getElementById(4, actors, Actor::getId));
    }

    /**
     * GIVEN a dbHelper-Object and a List of actors
     * WHEN getElementsByName is called with the name of an actor
     * THEN the actor is returned if it exists in the list otherwise null is returned
     */
    @Test public void testGetElementsByName() {
        DBHelper dbHelper = new DBHelper();
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor(1, "Actor1"));
        actors.add(new Actor(2, "Actor2"));
        actors.add(new Actor(3, "Actor3"));
        assertEquals(1, dbHelper.getElementsByName("Actor1", actors, Actor::getName).size());
        assertEquals(2, dbHelper.getElementsByName("Actor2", actors, Actor::getName).getFirst().getId());
        assertNull(dbHelper.getElementsByName("Actor4", actors, Actor::getName));
    }
}
