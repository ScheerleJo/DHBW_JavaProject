package dhbw.Data;

public class Movie {
    private int id;
    private String title;
    private String plot;
    private String genre;
    private String published;
    private int votes;
    private double imdbRating;

    public Movie(int id, String title, String plot, String genre, String published, int votes, double imdbRating) {
        this.id = id;
        this.title = title;
        this.plot = plot;
        this.genre = genre;
        this.published = published;
        this.votes = votes;
        this.imdbRating = imdbRating;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setPublished(String published) {
        this.published = published;
    }
    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }
    public void setVotes(int votes) {
        this.votes = votes;
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
    public String getGenre() {
        return genre;
    }
    public String getPublished() {
        return published;
    }
    public double getImdbRating() {
        return imdbRating;
    }
    public int getVotes() {
        return votes;
    }
}
