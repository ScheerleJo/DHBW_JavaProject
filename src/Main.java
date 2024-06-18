public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("No Parameter Specified");
        } else {
            LoadDB loadDB = new LoadDB();

            loadDB.importDB();
            loadDB.handleAction(args);
        }
    }
}