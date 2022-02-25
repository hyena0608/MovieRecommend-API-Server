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
import wizard.movierecom.repository.GenreRepository;
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
    private final GenreRepository genreRepository;
    private final GenreService genreService;

    @PostMapping("/api/buildData")
    public String startDB() {
        int cnt = 1;
        while (true) {
            try {
                String api_url = "https://api.themoviedb.org/3/movie/" + Integer.toString(++cnt) + "?api_key=c491655bb791f450cb71d101f64552cc&language=ko";
                JSONObject movieJsonObject = getMovieJsonObject(api_url);

                List<MovieGenre> movieGenreList = new ArrayList<>();
                ArrayList genreArray = (ArrayList) movieJsonObject.get("genres");
                for (Object o : genreArray) {
                    Genre genre = makeGenre((Map) o);
                    genreService.save(genre);
                    List<Genre> findGenres = genreRepository.findGenreByName(genre.getName());
                    collectMovieGenre(movieGenreList, findGenres.get(0));
                }

                Movie movie = getMovie(movieJsonObject);
                Movie movie1 = Movie.createMovie(movie, movieGenreList);
                movieService.save(movie1);

            } catch (Exception e) {
                log.warn("No Movie In this Situation");
            } finally {
                if (cnt > 30) break;
            }
        }
        return "ok";
    }




    private void collectMovieGenre(List<MovieGenre> movieGenreList, Genre findGenre) {
        MovieGenre movieGenre = createMovieGenre(findGenre);
        movieGenreList.add(movieGenre);
    }

    private Genre makeGenre(Map o) {
        JSONObject genreJsonObject = new JSONObject(o);
        Genre genre = new Genre(genreJsonObject.get("name").toString());
        return genre;
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