import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int id;
    private String title;
    private String plot;
    private int year;
    private int votes;
    private double imdbRating;
    private List<Integer> actors = new ArrayList<>();
    private List<Integer> directors = new ArrayList<>();

    public Movie(int id, String title, String plot, int year, int votes, double imdbRating) {
        this.id = id;
        this.title = title;
        this.plot = plot;
        this.year = year;
        this.votes = votes;
        this.imdbRating = imdbRating;
    }

    public void setActors(List<Integer> actors) {
        this.actors = actors;
    }
    public void setDirectors(List<Integer> directors) {
        this.directors = directors;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getPlot() {
        return plot;
    }
    public int getYear() {
        return year;
    }
    public double getImdbRating() {
        return imdbRating;
    }
    public int getVotes() {
        return votes;
    }
    public List<Integer> getActors() {
        return actors;
    }
    public List<Integer> getDirectors() {
        return directors;
    }


}
