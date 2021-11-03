package csulb.cecs323.model;

import java.util.ArrayList;
import java.util.Set;
import javax.persistence.*;

@Entity
@DiscriminatorValue("IndividualAuthor")
@NamedNativeQueries({
        @NamedNativeQuery(name = "IndividualAuthorCount",
                query = "Select count(*) " +
                        "FROM IndividualAuthor " +
                        "WHERE authorEmail = ?"),
})
public class IndividualAuthor extends AuthoringEntity
{
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="AdHocTeamMember",
               joinColumns=@JoinColumn(name="authorEmail"),
               inverseJoinColumns=@JoinColumn(name="authorEmail"))
    private ArrayList<AdHocTeam> adHocTeams = new ArrayList<>();

    public IndividualAuthor () {}
    public IndividualAuthor (String authorEmail, String name)
    {
        super(authorEmail, name);
    }

    public ArrayList<AdHocTeam> getAdHocTeams() {
        return adHocTeams;
    }

    public void setAdHocTeams(ArrayList<AdHocTeam> adHocTeams) {
        this.adHocTeams = adHocTeams;
    }

    public static int countAuthorEmail(EntityManager em, String email) {
        Query query = em.createNamedQuery("IndividualAuthorCount").
                setParameter(1, email);
        Integer count = ((Number) query.getSingleResult()).intValue();
        return count;
    }

}
