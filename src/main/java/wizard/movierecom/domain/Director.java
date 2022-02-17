package wizard.movierecom.domain;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "director")
public class Director {

    @Id @GeneratedValue
    @Column(name = "director_id")
    private Long id;

    @Column(name = "director_name")
    private String name;

    @OneToMany(mappedBy = "director", cascade = CascadeType.ALL)
    private List<MovieDirector> movieDirectors;
}
