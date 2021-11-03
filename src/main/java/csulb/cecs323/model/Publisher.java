package csulb.cecs323.model;
import javax.persistence.*;
import java.util.ArrayList;

@Entity
@NamedNativeQueries({
        @NamedNativeQuery(name = "Publisher.countName",
                query = "SELECT count(*) " +
                        "FROM Publisher " +
                        "WHERE publisherName = ?"),
        @NamedNativeQuery(name = "Publisher.countEmail",
                query = "SELECT count(*) " +
                        "FROM Publisher " +
                        "WHERE publisherEmail = ?"),
        @NamedNativeQuery(name = "Publisher.countPhone",
                query = "SELECT count(*) " +
                        "FROM Publisher " +
                        "WHERE phoneNumber = ?"),
        @NamedNativeQuery(name = "ReturnPubInfo",
                query = "Select * " +
                        "FROM Publisher " +
                        "WHERE name = ?",
                resultClass = Book.class),
})
public class Publisher {

    /** The name of the publisher*/
    @Id
    @Column(nullable = false, length = 80)
    private String publisherName;

    /** The authorEmail that we can use to contact the publisher*/
    @Column(nullable = false,length = 80, unique = true)
    private String publisherEmail;

    /** The phone number that we can use to contact the publisher*/
    @Column(nullable = false, length = 24, unique = true)
    private String phoneNumber;

    @OneToMany (mappedBy = "publisherName",
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
        b.setPublisherName(this);
    }

    public void removeBook(Book b) {
        book.remove(b);
        b.setPublisherName(null);
    }

    public Publisher () {}

    public Publisher (String name, String email, String number)
    {
        publisherName = name;
        this.publisherEmail = email;
        phoneNumber = number;
    }

    public String getPublisherName () { return publisherName; }

    public void setPublisherName (String publisherName) { this.publisherName = publisherName; }

    public String getPublisherEmail () { return publisherEmail; }

    public void setPublisherEmail (String email) { this.publisherEmail = email; }

    public String getPhoneNumber () { return phoneNumber; }

    public void setPhoneNumber (String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString ()
    {
        return "Publisher name: " + this.getPublisherName() + "\nauthorEmail: " + this.getPublisherEmail()
                + "\nPhone Number: " + this.getPhoneNumber();
    }

    public static int countPublisherName(EntityManager em, String name) {
        Query query = em.createNamedQuery("Publisher.countName").
                    setParameter(1, name);
        Integer count = ((Number) query.getSingleResult()).intValue();
        return count;
    }

    public static int countPublisherEmail(EntityManager em, String email) {
        Query query = em.createNamedQuery("Publisher.countEmail").
                setParameter(1, email);
        Integer count = ((Number) query.getSingleResult()).intValue();
        return count;
    }

    public static int countPublisherPhone(EntityManager em, String phone) {
        Query query = em.createNamedQuery("Publisher.countPhone").
                setParameter(1, phone);
        Integer count = ((Number) query.getSingleResult()).intValue();
        return count;
    }
}