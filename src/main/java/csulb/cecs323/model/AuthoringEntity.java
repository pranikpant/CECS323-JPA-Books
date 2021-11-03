package csulb.cecs323.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="AUTHORING_ENTITY_TYPE")
@NamedNativeQueries({
        @NamedNativeQuery(name = "ReturnAuthorInfo",
                query = "Select * " +
                        "FROM AuthoringEntity " +
                        "WHERE name = ?",
                resultClass = AuthoringEntity.class),
        @NamedNativeQuery(name = "AuthorEntityCount",
                query = "Select count(*) " +
                        "FROM AuthoringEntity " +
                        "WHERE authorEmail = ?")
})
public abstract class AuthoringEntity {

    /**The email of the author*/
    @Id
    @Column(nullable = false,length = 30)
    private String authorEmail;

    /**The name of the author*/
    @Column(nullable = false, length = 80)
    private String name;

    @OneToMany (mappedBy = "authorEmail",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private ArrayList<Book> book = new ArrayList<>();

    public ArrayList<Book> getBook() {
        return book;
    }

    public void setBook(ArrayList<Book> book) {
        this.book = book;
    }

    public void addBook(Book b) {
        book.add(b);
        b.setAuthorEmail(this);
    }

    public void removeBook(Book b) {
        book.remove(b);
        b.setAuthorEmail(null);
    }

    public AuthoringEntity () {}

    public AuthoringEntity (String authorEmail, String name)
    {
        this.authorEmail = authorEmail;
        this.name = name;
    }

    public String getauthorEmail () { return authorEmail; }

    public void setauthorEmail (String authorEmail) { this.authorEmail = authorEmail; }

    public String getName () { return name; }

    public void setName (String name) { this.name = name; }

    @Override
    public String toString()
    {
        return "Name: " + this.getName() + "\nauthorEmail: " + this.getauthorEmail();
    }

    public static int countAuthorEntityEmail(EntityManager em, String email) {
        Query query = em.createNamedQuery("AuthorEntityCount").
                setParameter(1, email);
        Integer count = ((Number) query.getSingleResult()).intValue();
        return count;
    }
}