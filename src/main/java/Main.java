import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

public class Main {

    public static void main(String[] args) {
        staticFileLocation("/public");

        get("/", (req, res) -> null);
    }
}
