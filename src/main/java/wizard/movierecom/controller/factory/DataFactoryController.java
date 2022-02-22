package wizard.movierecom.controller.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import wizard.movierecom.domain.Genre;
import wizard.movierecom.domain.Movie;
import wizard.movierecom.domain.MovieGenre;
import wizard.movierecom.repository.DataFactoryRepository;
import wizard.movierecom.repository.GenreRepository;
import wizard.movierecom.repository.MovieRepository;
import wizard.movierecom.service.DataFactoryService;
import wizard.movierecom.service.GenreService;
import wizard.movierecom.service.MovieService;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static wizard.movierecom.domain.MovieGenre.createMovieGenre;


@RestController
@RequiredArgsConstructor
@Slf4j
public class DataFactoryController {

    private final MovieService movieService;
    private final GenreService genreService;

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @PostMapping("/api/buildData")
    public String startDB() {
        int cnt = 1;
        while (true) {
            try {
                String api_url = "https://api.themoviedb.org/3/movie/" + Integer.toString(++cnt) + "?api_key=c491655bb791f450cb71d101f64552cc&language=ko";
                JSONObject movieJsonObject = getMovieJsonObject(api_url);

                Movie movie = getMovie(movieJsonObject);

                List<MovieGenre> movieGenreList = new ArrayList<>();
                ArrayList genreArray = (ArrayList) movieJsonObject.get("genres");
                for (Object o : genreArray) {
                    JSONObject genreJsonObject = new JSONObject((Map) o);
                    Genre genre = new Genre(genreJsonObject.get("name").toString());
                    genreService.save(genre);
                    movieGenreList.add(createMovieGenre(movie, genre));
                }
                Movie movie1 = Movie.createMoive(movie, movieGenreList);
                movieService.save(movie1);


            } catch (Exception e) {

            } finally {
                if (cnt > 5) break;
            }
        }

        return "ok";
    }

    private static Movie getMovie(JSONObject jsonObject) {
        String title = jsonObject.get("title").toString();
        String overview = jsonObject.get("overview").toString();
        String release_date = jsonObject.get("release_date").toString();
        int runtime = Integer.parseInt(jsonObject.get("runtime").toString());
        float vote_average = Float.parseFloat(jsonObject.get("vote_average").toString());
        int vote_count = Integer.parseInt(jsonObject.get("vote_count").toString());

        return new Movie(title, overview, release_date, runtime, vote_average, vote_count);
    }

    private static JSONObject getMovieJsonObject(String api_url) throws ParseException, IOException {
        return (JSONObject) new JSONParser().parse(new BufferedReader(new InputStreamReader(new URL(api_url).openStream(), "UTF-8")).readLine());
    }

}