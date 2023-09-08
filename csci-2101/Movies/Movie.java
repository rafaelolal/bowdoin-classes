package Movies;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Movie {
    private String name;
    private String director;
    private int releaseYear;
    private int runTime;

    public Movie(String n, String d, int y, int t) {
        name = n;
        director = d;
        releaseYear = y;
        runTime = t;
    }

    public String toString() {
        return String.format("%s by %s %d Length: %d",
            name, director, releaseYear, runTime);
    }

    public static Movie[] readMovies(String fileName) throws IOException {
        // we'll learn about how to resize arrays in the future
		Movie [] movies = new Movie[5];
		
		int idx = 0;
		
		for (String line : Files.readAllLines(Path.of(fileName))) {
			try {
				
				// your code goes here
				// pass the line to the readMovieFromLineInFile method
				// then store the movie object into the array
				// make sure to increment the idx variable so that we don't overwrite the same slot every single time
				
                movies[idx] = readMovie(line);
                idx ++;
				
			}
			catch (IllegalArgumentException iae) {
				// invalid movie, skip it & go on to the next line
                continue;
			}
		}
		
		// your code goes here -- make sure to return our results
        return movies;
    }

    private static Movie readMovie(String line) {
		try (Scanner sc = new Scanner(line)){
			sc.useDelimiter(",");
			
			// normally you might want to do some checking to make sure "Yeah, this information is available" 
			// before you try to pull it from the scanner
			String name = sc.next();
			String director = sc.next();
			int releaseYear = sc.nextInt();
			int runTime = sc.nextInt();
			
			// your code goes here -- create a new Movie object from the information we just grabbed & return it
            return new Movie(name, director, releaseYear, runTime);
		}
    }
}