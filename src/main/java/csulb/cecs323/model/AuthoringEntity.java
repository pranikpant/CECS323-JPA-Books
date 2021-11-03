package csulb.cecs323.model;

import javax.persistence.*;
import java.util.ArrayList;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class AuthoringEntity {
    @Id
    @Column(nullable = false,length = 60)
    private String email;

    @Column(nullable = false, length = 45)
    private String name;

    @OneToMany (mappedBy = "email",
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

    public AuthoringEntity () {}

    public AuthoringEntity (String email, String name)
    {
        this.email = email;
        this.name = name;
    }

    public String getEmail () { return email; }

    public void setEmail (String email) { this.email = email; }

    public String getName () { return name; }

    public void setName (String name) { this.name = name; }

    @Override
    public String toString()
    {
        return "Name: " + this.getName() + "\nEmail: " + this.getEmail();
    }
}