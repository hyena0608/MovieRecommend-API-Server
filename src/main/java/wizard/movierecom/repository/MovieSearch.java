package wizard.movierecom.repository;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovieSearch {

    private String title;
    private String content;

    public MovieSearch(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
