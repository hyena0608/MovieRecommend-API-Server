package wizard.movierecom.domain;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "director")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Director {

    @Id @GeneratedValue
    @Column(name = "director_id")
    private Long id;

    @Column(name = "director_name")
    private String name;

    @OneToMany(mappedBy = "director", cascade = CascadeType.ALL)
    private List<MovieDirector> movieDirectors;


}
