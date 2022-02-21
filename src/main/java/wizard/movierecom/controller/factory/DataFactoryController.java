package wizard.movierecom.controller.factory;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import wizard.movierecom.domain.Movie;
import wizard.movierecom.repository.MovieRepository;

import java.io.*;
import java.net.URL;

@Controller
@RequiredArgsConstructor
public class DataFactoryController {

    private final MovieRepository movieRepository;

    @PostMapping("/api/buildData")
    public String startDB() {
        int cnt = 1;
        while (true) {
            try {
                String api_url = "https://api.themoviedb.org/3/movie/" + Integer.toString(++cnt) + "?api_key=c491655bb791f450cb71d101f64552cc&language=ko";
                JSONObject jsonObject = getJsonObject(api_url);
                // 영화 추출
                Movie findMovie = getMovieList(jsonObject);
                movieRepository.insertMovie(findMovie);
                // 배우 추출

                // 감독 추출

                System.out.println(jsonObject);
                System.out.println("cnt = " + cnt);
            } catch (Exception ignored) {

            } finally {
                if (cnt > 30) break;
            }
        }
        return "ok";
    }

    private static Movie getMovieList(JSONObject jsonObject) {
        String title = jsonObject.get("title").toString();
        String overview = jsonObject.get("overview").toString();
        String release_date = jsonObject.get("release_date").toString();
        int runtime = Integer.parseInt(jsonObject.get("runtime").toString());
        float vote_average = Float.parseFloat(jsonObject.get("vote_average").toString());
        int vote_count = Integer.parseInt(jsonObject.get("vote_count").toString());
        return new Movie(title, overview, release_date, runtime, vote_average, vote_count);
    }

    private static JSONObject getJsonObject(String api_url) throws ParseException, IOException {
        return (JSONObject) new JSONParser().parse(new BufferedReader(new InputStreamReader(new URL(api_url).openStream(), "UTF-8")).readLine());
    }


}
