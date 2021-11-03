/*
 * Licensed under the Academic Free License (AFL 3.0).
 *     http://opensource.org/licenses/AFL-3.0
 *
 *  This code is distributed to CSULB students in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, other than educational.
 *
 *  2018 Alvaro Monge <alvaro.monge@csulb.edu>
 *
 */

package csulb.cecs323.app;

// Import all of the entity classes that we have written for this application.
import csulb.cecs323.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class BookMain {

   private EntityManager entityManager;

   private static final Logger LOGGER = Logger.getLogger(BookMain.class.getName());

   public BookMain(EntityManager manager) {
      this.entityManager = manager;
   }

   /** Checks if the inputted value is an integer and
    * within the specified range (ex: 1-10)
    * @param low lower bound of the range.
    * @param high upper bound of the range.
    * @return the valid input.
    */
   public static int getIntRange( int low, int high ) {
      Scanner in = new Scanner( System.in );
      int input = 0;
      boolean valid = false;
      while( !valid ) {
         if( in.hasNextInt() ) {
            input = in.nextInt();
            if( input <= high && input >= low ) {
               valid = true;
            } else {
               System.out.println( "Invalid Range." );
            }
         } else {
            in.next(); //clear invalid string
            System.out.println( "Invalid Input." );
         }
      }
      return input;
   }

   public static void main(String[] args) {
      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Book");
      EntityManager manager = factory.createEntityManager();
      BookMain bookMain = new BookMain(manager);

      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();

      tx.begin();

      List<Publisher> publishers = new ArrayList<>();
      publishers.add(new Publisher("bob murph", "paint@me.com", "1234"));
      //List<Book> books = new ArrayList<>();
      //books.add(new Book("12345", "harry potty", 1999, publishers.get(0)));

      bookMain.createEntity(publishers);
      //bookMain.createEntity(books);

      //Display list of available options and have the user pick
      Scanner sc = new Scanner(System.in);
      System.out.println();
      System.out.println("--------------------Begin Program---------------");
      boolean cont = true;
      while (cont) {
         System.out.println("Choose an option: ");
         System.out.println("1. Add new objects.");
         System.out.println("2. List all the information about a specific Object.");
         System.out.println("3. Delete a Book.");
         System.out.println("4. Update a Book.");
         System.out.println("5. List the primary key of all the rows.");
         System.out.println("6. Exit.");
         int choice = getIntRange(1, 6);

         //If user chooses to add new objects
         if (choice == 1) {
            System.out.println("Choose an object: ");
            System.out.println("1. Add a new Authoring Entity instance.");
            System.out.println("2. Add a new Publisher.");
            System.out.println("3. Add a new Book.");
            int choice1 = getIntRange(1, 3);

            if (choice1 == 1) {
               System.out.println("Select an entity.: ");
               System.out.println("1. Writing Group");
               System.out.println("2. Individual Author");
               System.out.println("3. Ad Hoc Team");
               System.out.println("4. Add individual author to an existing Ad Hoc Team");
               int subOption2 = getIntRange(1, 4);

               if (subOption2 == 1) {
               } else if (subOption2 == 2) {
               } else if (subOption2 == 3) {
               }else if (subOption2 == 4) {
               }
            }
            //If user chooses to add new publisher
            else if (choice1 == 2) {
               Publisher pub = BookMain.addPublisher(manager);
               manager.persist(pub);
            }
            else {
               Book book = BookMain.addBook(manager, bookMain);
               manager.persist(book);
            }
         }

         //If user chooses to list all the information about a specific Object
         else if (choice == 2) {
            System.out.println("What object would you like to learn more about?: ");
            System.out.println("1. Publisher");
            System.out.println("2. Book");
            System.out.println("3. Writing Group");
            int subOption1 = getIntRange(1, 3);
            if(subOption1 == 1) {
               System.out.println("List of publishers.");
               for(int i = 0; i < publishers.size(); i++)
               {
                  System.out.println((i+1) + "." + publishers.get(i).getPublisherName());
               }
               System.out.println("Please select a publisher.");
               int pubChoice = getIntRange(1,publishers.size());

               System.out.println("Publisher Name: " + publishers.get(pubChoice - 1).getPublisherName());
               System.out.println("Publisher Number: " + publishers.get(pubChoice - 1).getPhoneNumber());
               System.out.println("Publisher Email: "+ publishers.get(pubChoice - 1).getPublisherEmail());
               System.out.println("Publisher Books: "+ publishers.get(pubChoice - 1).getBook());

            } else if (subOption1 == 2) {
               /**System.out.println("List of books.");
               for(int i = 0; i < books.size(); i++)
               {
                  System.out.println((i+1) + "." + books.get(i).getTitle());
               }
               System.out.println("Please select a book.");
               int bookChoice = getIntRange(1,publishers.size());

               System.out.println("Book Title: " + books.get(bookChoice - 1).getTitle());
               System.out.println("Book ISBN: " + books.get(bookChoice - 1).getISBN());
               System.out.println("Book Publisher: " + books.get(bookChoice - 1).getPublisherName());
               System.out.println("Book Publication Year: " + books.get(bookChoice - 1).getYearPublished()); */

            } else if (subOption1 == 3) {
            }
         }

         //If user chooses to delete a Book
         else if (choice == 3) {
            String ISBN = sc.nextLine();
            System.out.println("Enter the title of the book: ");
            String title = sc.nextLine();
            System.out.println("Enter the publisher's name of the book: ");
            String pname = sc.nextLine();
            Publisher pub = bookMain.getPublisher(pname);
            System.out.println("Enter the author's name of the book: ");
            String aname = sc.nextLine();
            AuthoringEntity author = bookMain.getAuthor(aname);
            Book b = bookMain.getBook(ISBN, title, pub, author);
            manager.remove(b);
         }

         //If user chooses to update a Book
         else if (choice == 4) {
            //fixme
            cont = false;
         }

         //If user chooses to list the primary key of all the rows
         else if (choice == 5) {
            System.out.println("Which row's PK would you like to see?: ");
            System.out.println("1. Publishers");
            System.out.println("2. Books");
            System.out.println("3. Authoring Entity");
            int subOption1 = getIntRange(1, 3);
            if(subOption1 == 1) {
            } else if (subOption1 == 2) {
            } else if (subOption1 == 3) {
            }
         }

         //Otherwise, quit the program
         else {
            System.out.println("Program ends");
            cont = false;
         }
      }

      tx.commit();
      LOGGER.fine("End of Transaction");
   } // End of the main method

   /**
    * Create and persist a list of objects to the database.
    * @param entities   The list of entities to persist.  These can be any object that has been
    *                   properly annotated in JPA and marked as "persistable."  I specifically
    *                   used a Java generic so that I did not have to write this over and over.
    */
   public <E> void createEntity(List <E> entities) {
      for (E next : entities) {
         LOGGER.info("Persisting: " + next);
         // Use the CarClub entityManager instance variable to get our EntityManager.
         this.entityManager.persist(next);
      }

      // The auto generated ID (if present) is not passed in to the constructor since JPA will
      // generate a value.  So the previous for loop will not show a value for the ID.  But
      // now that the Entity has been persisted, JPA has generated the ID and filled that in.
      for (E next : entities) {
         LOGGER.info("Persisted object after flush (non-null id): " + next);
      }
   } // End of createEntity member method

   /**
    * This as a simple map from a String to an instance of Publisher that has the
    * same name as the string that you pass in.
    * @param name       The name of the Publisher that you are looking for.
    * @return           The Publisher instance corresponding to that name.
    */

   public Publisher getPublisher (String name) {
      // Run the native query that we defined in the publisher entity to find the right publisher.
      List<Publisher> pub = this.entityManager.createNamedQuery("ReturnPubInfo",
              Publisher.class).setParameter(1, name).getResultList();
      if (pub.size() == 0) {
         // Invalid name passed in.
         return null;
      } else {
         // Return the publisher object that they asked for.
         return pub.get(0);
      }
   }// End of the getPublisher method

   /**
    * This as a simple map from a String to an instance of AuthoringEntity that has the
    * same name as the string that you pass in.
    * @param name       The name of the AuthoringEntity that you are looking for.
    * @return           The AuthoringEntity instance corresponding to that name.
    */

   public AuthoringEntity getAuthor (String name) {
      // Run the native query that we defined in the publisher entity to find the right publisher.
      List<AuthoringEntity> author = this.entityManager.createNamedQuery("ReturnAuthorInfo",
              AuthoringEntity.class).setParameter(1, name).getResultList();
      if (author.size() == 0) {
         // Invalid name passed in.
         return null;
      } else {
         // Return the author entity object that they asked for.
         return author.get(0);
      }
   }// End of the getAuthor method

   /**
    * Find the right Book with information that you pass in
    * @param ISBN       The ISBN of the Book that you are looking for.
    * @param title      The title of the Book that you are looking for.
    * @param pub        The publisher of the Book that you are looking for.
    * @param au         The author of the Book that you are looking for.
    * @return           The Book instance corresponding to that info.
    */

   public Book getBook (String ISBN, String title, Publisher pub, AuthoringEntity au) {
      // Run the native query that we defined in the Book entity to find the right book.
      List<Book> b = this.entityManager.createNamedQuery("ReturnBookInfo",
              Book.class).setParameter(1, ISBN).setParameter(2,title).
              setParameter(3,pub).setParameter(4,au).getResultList();
      if (b.size() == 0) {
         // Invalid name passed in.
         return null;
      } else {
         // Return the book object that they asked for.
         return b.get(0);
      }
   }// End of the getBook method

   /**
    * Prompt the user for the information needed to add a new publisher.
    * @param em   The application entity manager
    * @return     A new publisher
    * @apiNote    There is no way out of this without creating a Publisher instance
    */
   public static Publisher addPublisher(EntityManager em) {
      Scanner sc = new Scanner( System.in );
      System.out.println("Enter the name of the Publisher: ");
      String name = sc.nextLine();
      int existingPublisherName = Publisher.countPublisherName(em, name);
      while (existingPublisherName > 0) {
         System.out.println("Error! The publisher with that name has already existed");
         System.out.println("Enter the name of the Publisher: ");
         name = sc.nextLine();
         existingPublisherName = Publisher.countPublisherName(em, name);
      }

      System.out.println("Enter the email address of the Publisher: ");
      String email = sc.nextLine();
      int existingPublisherEmail = Publisher.countPublisherEmail(em, email);
      while (existingPublisherEmail > 0) {
         System.out.println("Error! The publisher with that email address has already existed");
         System.out.println("Enter the email address of the Publisher: ");
         email = sc.nextLine();
         existingPublisherEmail = Publisher.countPublisherEmail(em, email);
      }

      System.out.println("Enter the phone number of the Publisher: ");
      String phone = sc.nextLine();
      int existingPublisherPhone = Publisher.countPublisherPhone(em, phone);
      while (existingPublisherPhone > 0) {
         System.out.println("Error! The publisher with that phone number has already existed");
         System.out.println("Enter the phone number of the Publisher: ");
         phone = sc.nextLine();
         existingPublisherPhone = Publisher.countPublisherPhone(em, phone);
      }
      return new Publisher(name, email, phone);
   }

   /**
    * Prompt the user for the information needed to add a new book.
    * @param em   The application entity manager
    * @return     A new book
    * @apiNote    There is no way out of this without creating a Book instance
    */
   public static Book addBook(EntityManager em, BookMain bm) {
      Scanner sc = new Scanner( System.in );
      System.out.println("Enter the ISBN of the book: ");
      String ISBN = sc.nextLine();
      int existingBook = Book.count(em, ISBN);
      while (existingBook > 0) {
         System.out.println("Error! The book has already existed");
         System.out.println("Enter the ISBN of the book: ");
         ISBN = sc.nextLine();
         existingBook = Book.count(em, ISBN);
      }
      System.out.println("Enter the title of the book: ");
      String title = sc.nextLine();
      System.out.println("Enter the published year of the book: ");
      int year = sc.nextInt();
      System.out.println("Enter the publisher's name of the book: ");
      String pname = sc.nextLine();
      System.out.println("Enter the author's name of the book: ");
      String aname = sc.nextLine();
      return new Book (ISBN, title, year, bm.getPublisher(pname), bm.getAuthor(aname));
   }
} // End of BookMain class
