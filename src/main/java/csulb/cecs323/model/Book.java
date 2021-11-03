package csulb.cecs323.model;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"title", "publisherName"}),
        @UniqueConstraint(columnNames = {"title", "authorEmail"})
})

@NamedNativeQueries({
        @NamedNativeQuery(name = "ReturnBookInfo", query = "Select * " +
                "FROM BOOKS " +
                "Where ISBN = ? AND title = ? AND publisherName = ? AND authorEmail=?",
                resultClass = Book.class),
        @NamedNativeQuery(name = "Book.count",
                query = "Select count(*) " +
                        "FROM BOOKS " +
                        "Where ISBN = ?"),
})

public class Book {

    /** A unique number associated with the book*/
    @Id
    @Column(nullable = false,length = 17)
    private String ISBN;

    /**The name of the book*/
    @Column(nullable = false, length = 80)
    private String title;

    /**The year the book was published */
    @Column(nullable = false)
    private int yearPublished;

    /**The name of the publisher */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisherName",referencedColumnName = "publisherName", nullable = false)
    private Publisher publisherName;

    /**The authors associate with the book*/
    @ManyToOne
    @JoinColumn(name = "authorEmail", referencedColumnName = "authorEmail")
    private AuthoringEntity authorEmail;

    public AuthoringEntity getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(AuthoringEntity authorEmail) {
        this.authorEmail = authorEmail;
    }

    public Book () {}

    public Book (String ISBN, String title, int year, Publisher name, AuthoringEntity email)
    {
        this.ISBN = ISBN;
        this.title = title;
        yearPublished = year;
        publisherName = name;
        authorEmail = email;
    }

    public String getISBN () { return ISBN; }

    public void setISBN (String ISBN) { this.ISBN = ISBN; }

    public String getTitle () { return title; }

    public void setTitle (String title) { this.title = title; }

    public int getYearPublished () { return yearPublished; }

    public void setYearPublished (int yearPublished) { this.yearPublished = yearPublished; }

    public Publisher getPublisherName () { return publisherName; }

    public void setPublisherName (Publisher publisherName) { this.publisherName = publisherName; }

    @Override
    public String toString ()
    {
        return "Title: " + this.getTitle() + "\nYear Published: " + this.getYearPublished()
                + "\nISBN: " + this.getISBN() + "\nPublisher: " + this.getPublisherName();
    }

    public static int count(EntityManager em, String ISBN) {
        Query query = em.createNamedQuery("Book.count").
                setParameter(1, ISBN);
        Integer count = ((Number) query.getSingleResult()).intValue();
        return count;
    }
}