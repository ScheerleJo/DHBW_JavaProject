package test;
import dhbw.Data.Actor;
import dhbw.Data.DB;

import dhbw.Data.Director;
import dhbw.Data.Movie;
import org.junit.Test;
import static org.junit.Assert.*;

public class DbTest {


    /**
     * GIVEN a database and an actor
     * WHEN the actor is added to the database
     * THEN the database contains the actor.
     */
    @Test
    public void addActorTest() {
        DB db = new DB();
        Actor actor = new Actor(256, "New Actor");

        db.addActor(actor);

        assertEquals(1, db.getActors().size());
        assertEquals(actor, db.getActors().getFirst());
        assertNotNull(db.getActors().getLast());
    }

    /**
     * GIVEN a database and a director
     * WHEN the director is added to the database
     * THEN the database contains the director.
     */
    @Test
    public void addDirectorTest() {
        DB db = new DB();
        Director director = new Director(256, "New Director");

        db.addDirector(director);

        assertEquals(1, db.getDirectors().size());
        assertEquals(director, db.getDirectors().getFirst());
        assertNotNull(db.getDirectors().getLast());
    }


    /**
     * GIVEN a database and a movie
     * WHEN the movie is added to the database
     * THEN the database contains the movie.
     */
    @Test
    public void addMovieTest(){
        DB db = new DB();
        Movie movie = new Movie(256, "New Movie","This is the plot","Genre","2024-07-03",24,5.7);

        db.addMovie(movie);

        assertEquals(1, db.getMovies().size());
        assertEquals(movie, db.getMovies().getFirst());
        assertNotNull(db.getMovies().getLast());
    }
}
