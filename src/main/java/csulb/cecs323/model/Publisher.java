package csulb.cecs323.model;
import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Publisher {

    /** The name of the publisher*/
    @Id
    @Column(nullable = false, length = 50)
    private String publisherName;

    /** The email that we can use to contact the publisher*/
    @Column(nullable = false,length = 55)
    private String email;

    /** The phone number that we can use to contact the publisher*/
    @Column(nullable = false, length = 20, unique = true)
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
        //b.setStyles(this);
    }

    public void removeBook(Book b) {
        book.remove(b);
        //b.setStyles(null);
    }

    public Publisher () {}

    public Publisher (String name, String email, String number)
    {
        publisherName = name;
        this.email = email;
        phoneNumber = number;
    }

    public String getPublisherName () { return publisherName; }

    public void setPublisherName (String publisherName) { this.publisherName = publisherName; }

    public String getEmail () { return email; }

    public void setEmail (String email) { this.email = email; }

    public String getPhoneNumber () { return phoneNumber; }

    public void setPhoneNumber (String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString ()
    {
        return "Publisher name: " + this.getPublisherName() + "\nEmail: " + this.getEmail()
                + "\nPhone Number: " + this.getPhoneNumber();
    }
}