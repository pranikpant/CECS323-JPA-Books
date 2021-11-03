package csulb.cecs323.model;

import java.util.Set;
import javax.persistence.*;

@Entity
@DiscriminatorValue("2")
public class IndividualAuthor extends AuthoringEntity
{
    @Id
    Long id;

    @ManyToMany
    Set<AdHocTeam> partcipants;

    public IndividualAuthor () {}

    public IndividualAuthor (String authorEmail, String name, String writerName, int year)
    {
        super(authorEmail, name);
    }
}
