package csulb.cecs323.model;

import java.util.ArrayList;
import java.util.Set;
import javax.persistence.*;

@Entity
@DiscriminatorValue("IndividualAuthor")
public class IndividualAuthor extends AuthoringEntity
{
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="AdHocTeamMember",
               joinColumns=@JoinColumn(name="authorEmail"),
               inverseJoinColumns=@JoinColumn(name="authorEmail"))
    private ArrayList<AdHocTeam> adHocTeams = new ArrayList<>();

    public IndividualAuthor () {}
    public IndividualAuthor (String authorEmail, String name, String writerName, int year)
    {
        super(authorEmail, name);
    }

    public ArrayList<AdHocTeam> getAdHocTeams() {
        return adHocTeams;
    }

    public void setAdHocTeams(ArrayList<AdHocTeam> adHocTeams) {
        this.adHocTeams = adHocTeams;
    }

}
