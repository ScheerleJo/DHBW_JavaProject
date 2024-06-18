public class LoadDB {
    private static final String dbPath = System.getProperty("user.dir") + "\\movieproject2024.db";

    public String getDbPath() {
        return dbPath;
    }


    public void importDB(){

    }

    public void handleAction(String[] args) {
        switch (args[0]){
            case "--filmsuche":
                System.out.println("Film Suche");
                break;
            case "--schauspielersuche":
                System.out.println("Schauspieler Suche");
                break;
            case "--filmnetzwerk":
                System.out.println("Filmnetzwerk");
                break;
            case "--schauspielernetzwerk":
                System.out.println("Schauspielernetzwerk");
                break;
        }
    }







}
