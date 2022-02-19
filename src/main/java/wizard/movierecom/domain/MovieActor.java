package wizard.movierecom.domain;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movie_actor")
public class MovieActor {

    @Id @GeneratedValue
    @Column(name = "movie_actor_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private Actor actor;

}
