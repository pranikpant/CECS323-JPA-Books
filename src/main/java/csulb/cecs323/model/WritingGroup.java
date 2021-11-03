package csulb.cecs323.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("1")
public class WritingGroup extends AuthoringEntity{

    private String headWriter;
    private int yearFormed;

    public WritingGroup() {}

    public WritingGroup (String email, String name, String writerName, int year)
    {
        super(email, name);
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
        return "Name: " + this.getName() + "\nEmail: " + this.getEmail() + "\n Head Writer: " +
                this.getWriter() + "\nYear Formed: "  + this.getYearFormed();
    }
}