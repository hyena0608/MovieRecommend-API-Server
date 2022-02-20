package wizard.movierecom.factory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DataFactory {
    public static void main(String[] args) throws IOException, ParseException {


        int cnt = 1;
        while (true) {
            try {
                String api_url = "https://api.themoviedb.org/3/movie/" + Integer.toString(++cnt) + "?api_key=c491655bb791f450cb71d101f64552cc&language=ko";
                JSONObject jsonObject = getJsonObject(api_url);
                // 영화 추출

                // 배우 추출

                // 감독 추출


                System.out.println(jsonObject);
                System.out.println("cnt = " + cnt);
            } catch (Exception e) {

                if (cnt > 30) break;
            }
        }

    }

    private static JSONObject getJsonObject(String api_url) throws ParseException, IOException {
        return (JSONObject) new JSONParser().parse(new BufferedReader(new InputStreamReader(new URL(api_url).openStream(), "UTF-8")).readLine());
    }

    static class MovieDto {
        private String title;
        private String overview;
        private String release_date;
        private int runtime;
        private float vote_average;
        private int vote_count;
        private String country;

        public MovieDto(String title, String overview, String release_date, int runtime, float vote_average, int vote_count, String country) {
            this.title = title;
            this.overview = overview;
            this.release_date = release_date;
            this.runtime = runtime;
            this.vote_average = vote_average;
            this.vote_count = vote_count;
            this.country = country;
        }
    }

}
