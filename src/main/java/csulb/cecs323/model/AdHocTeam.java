package csulb.cecs323.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@DiscriminatorValue("AdHocTeam")
public class AdHocTeam extends AuthoringEntity {

    @ManyToMany(mappedBy = "adHocTeams", cascade = CascadeType.ALL)
    private ArrayList<IndividualAuthor> individualAuthors = new ArrayList<>();

    public AdHocTeam () {}

    public AdHocTeam (String authorEmail, String name) {
        super(authorEmail, name);
    }

    public ArrayList<IndividualAuthor> getIndividualAuthors() {
        return individualAuthors;
    }

    public void setIndividualAuthors(ArrayList<IndividualAuthor> individualAuthors) {
        this.individualAuthors = individualAuthors;
    }
}
