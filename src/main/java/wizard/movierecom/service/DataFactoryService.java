package wizard.movierecom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import wizard.movierecom.domain.Genre;
import wizard.movierecom.domain.Movie;
import wizard.movierecom.repository.DataFactoryRepository;
import wizard.movierecom.repository.GenreRepository;
import wizard.movierecom.repository.MovieRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("ALL")
@Service
@RequiredArgsConstructor
@Slf4j
public class DataFactoryService {

    private final DataFactoryRepository dataFactoryRepository;
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    public JSONObject getMovieJsonObject(int cnt) throws IOException, ParseException {
        JSONObject movieJsonObject = new JSONObject();
        try {
            String api_url = "https://api.themoviedb.org/3/movie/" + Integer.toString(cnt) + "?api_key=c491655bb791f450cb71d101f64552cc&language=ko";
            movieJsonObject = getMovieJsonObject(api_url);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("번호에 따른 영화 정보가 없어 넘깁니다. : ", e.getMessage());
        } finally {
            return movieJsonObject;
        }
    }

    private static JSONObject getMovieJsonObject(String api_url) throws ParseException, IOException {
        return (JSONObject) new JSONParser().parse(
                new BufferedReader(new InputStreamReader(new URL(api_url).openStream(), "UTF-8")).readLine());
    }
}
