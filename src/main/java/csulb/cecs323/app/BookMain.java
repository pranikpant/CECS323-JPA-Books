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
import java.util.logging.Logger;

public class BookMain {

   private EntityManager entityManager;

   private static final Logger LOGGER = Logger.getLogger(BookMain.class.getName());

   public BookMain(EntityManager manager) {
      this.entityManager = manager;
   }

   public static void main(String[] args) {
      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Book");
      EntityManager manager = factory.createEntityManager();
      BookMain book = new BookMain(manager);

      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();

      tx.begin();

      List<Publisher> publishers = new ArrayList<>();
      publishers.add(new Publisher("bob murph", "paint@me.com", "1234"));
      List<Book> books = new ArrayList<>();
      books.add(new Book("12345", "harry potty", 1999, publishers.get(0)));

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
    * @param name       The name of the autobody style that you are looking for.
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
} // End of CarClub class
