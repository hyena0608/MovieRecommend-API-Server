package wizard.movierecom.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DataFactoryRepository {

    private final EntityManager em;

    public JSONObject getMovieJsonObject(int cnt) throws IOException, ParseException {
        JSONObject jsonObject = new JSONObject();
        try {
            String api_url = "https://api.themoviedb.org/3/movie/" + Integer.toString(cnt) + "?api_key=c491655bb791f450cb71d101f64552cc&language=ko";
            jsonObject = (JSONObject) new JSONParser().parse(new BufferedReader(new InputStreamReader(new URL(api_url).openStream(), "UTF-8")).readLine());
        } catch (Exception e) {
            log.warn("번호에 따른 영화 정보가 없어 넘깁니다. : ", e.getMessage());
        }
        return jsonObject;
    }
}
