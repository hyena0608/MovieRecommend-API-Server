package wizard.movierecom.domain;

import javax.persistence.*;

@Entity
@Table(name = "tag")
public class Tag {

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String name;
}
