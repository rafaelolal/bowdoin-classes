package Movies;
import java.io.IOException;
import java.util.Arrays;

public class MovieMain {
    public static void main(String[] args) {
        try {
            Movie[] movies = Movie.readMovies("/Users/ralmeida/scripts/bowdoin-classes/csci-2101/Movies/movies.txt");
            System.out.println(Arrays.toString(movies));
        } catch(IOException e) {
            System.out.println("IOException");
        }
        
    }
}