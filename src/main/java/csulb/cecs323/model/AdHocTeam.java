package csulb.cecs323.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("3")
public class AdHocTeam extends AuthoringEntity {
    @Id
    Long id;

    @ManyToMany
    Set<IndividualAuthor> members;

    public AdHocTeam () {}

    public AdHocTeam (String authorEmail, String name) {
        super(authorEmail, name);
    }
}
