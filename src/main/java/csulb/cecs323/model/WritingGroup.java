package csulb.cecs323.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("WritingGroup")
public class WritingGroup extends AuthoringEntity{
    /**The head writer of the writing group*/
    @Column
    private String headWriter;

    /**The year when the writing group formed*/
    @Column
    private int yearFormed;

    public WritingGroup() {}

    public WritingGroup (String authorEmail, String name, String writerName, int year)
    {
        super(authorEmail, name);
        headWriter = writerName;
        yearFormed = year;
    }

    public String getWriter () { return headWriter; }

    public void setWriter (String newName) { headWriter = newName; }

    public void setYearFormed (int newYear) { yearFormed = newYear; }

    public int getYearFormed () { return yearFormed; }

    @Override
    public String toString()
    {
        return "Name: " + this.getName() + "\nauthorEmail: " + this.getauthorEmail() + "\n Head Writer: " +
                this.getWriter() + "\nYear Formed: "  + this.getYearFormed();
    }
}
