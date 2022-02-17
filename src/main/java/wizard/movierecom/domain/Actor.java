package wizard.movierecom.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "actors")
public class Actor {

    @Id @GeneratedValue
    @Column(name = "actor_id")
    private Long id;

    @Column(name = "actor_name")
    private String name;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    private List<MovieActor> movieActors;
}