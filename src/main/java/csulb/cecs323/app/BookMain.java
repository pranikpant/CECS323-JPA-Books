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

      //List<Publisher> publishers = new ArrayList<>();
      //List<Book> books = new ArrayList<>();

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
               //fixme
               System.out.println("Add new Author entity");
            }
            else if (choice1 == 2) {
               Publisher pub = BookMain.addPublisher(manager);
               manager.persist(pub);
            }
            else {

            }
         }

         //If user chooses to list all the information about a specific Object
         else if (choice == 2) {
            //fixme
            cont = false;
         }

         //If user chooses to delete a Book
         else if (choice == 3) {
            //fixme
            cont = false;
         }

         //If user chooses to update a Book
         else if (choice == 4) {
            //fixme
            cont = false;
         }

         //If user chooses to list the primary key of all the rows
         else if (choice == 5) {
            //fixme
            cont = false;
         }

         //Otherwise, quit the program
         else {
            System.out.println("Program ends");
            cont = false;
         }
      }

      /**List<Publisher> publishers = new ArrayList<>();
      publishers.add(new Publisher("bob murph", "paint@me.com", "1234"));
      List<Book> books = new ArrayList<>();
      books.add(new Book("12345", "harry potty", 1999, publishers.get(0)));

      bookMain.createEntity(publishers);
      bookMain.createEntity(books); */

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
    * Think of this as a simple map from a String to an instance of auto_body_styles that has the
    * same name, as the string that you pass in.  To create a new Cars instance, you need to pass
    * in an instance of auto_body_styles to satisfy the foreign key constraint, not just a string
    * representing the name of the style.
    * //@param        The name of the autobody style that you are looking for.
    * @return           The auto_body_styles instance corresponding to that style name.
    */
   /*
   public auto_body_styles getStyle (String name) {
      // Run the native query that we defined in the auto_body_styles entity to find the right style.
      List<auto_body_styles> styles = this.entityManager.createNamedQuery("ReturnAutoBodyStyle",
              auto_body_styles.class).setParameter(1, name).getResultList();
      if (styles.size() == 0) {
         // Invalid style name passed in.
         return null;
      } else {
         // Return the style object that they asked for.
         return styles.get(0);
      }
   }// End of the getStyle method
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
} // End of CarClub class
