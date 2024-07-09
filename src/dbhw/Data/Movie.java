package dbhw.Data;

public class Movie {
    private int id;
    private String title;
    private String plot;
    private String genre;
    private String published;
    private int votes;
    private double imdbRating;
    /*private List<Integer> actors = new ArrayList<>();
    private List<Integer> directors = new ArrayList<>();*/

    public Movie(int id, String title, String plot, String genre, String published, int votes, double imdbRating) {
        this.id = id;
        this.title = title;
        this.plot = plot;
        this.genre = genre;
        this.published = published;
        this.votes = votes;
        this.imdbRating = imdbRating;
    }

    /*public void setActors(List<Integer> actors) {
        this.actors = actors;
    }
    public void setDirectors(List<Integer> directors) {
        this.directors = directors;
    }
*/
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
    /*public List<Integer> getActors() {
        return actors;
    }
    public List<Integer> getDirectors() {
        return directors;
    }
*/

}
