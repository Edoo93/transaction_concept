package Project;

import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.UpdateResult;

/**
 *  
 *         MongoDB connection and retrieval of information of customers
 */
public class Persons {
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;
	String personID;

	public Persons() {
		mongoClient = new MongoClient("127.0.0.1", 27017);
		database = mongoClient.getDatabase("BeaverCoffee");
		collection = database.getCollection("customers");
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.OFF); 
	}

	/**
	 * 
	 * @param person
	 *            object to add to the customers collection
	 */
	public void addPersonToDB(Person p) {
		try {
			Document doc = new Document("name", p.getName().toUpperCase())
					.append("lastname", p.getLastName().toUpperCase()).append("age", p.getAge())
					.append("address", p.getAddress().toUpperCase())
					.append("idnumber", p.getPersNumber().toUpperCase());

			collection.insertOne(doc);
			System.out.println("\nThe customer/employee has successfully been added to the database!");
		} catch (Exception ex) {
			System.out.println("A customer failed to be added!");
			mongoClient.close();
		}
		mongoClient.close();
	}

	/**
	 * 
	 * @param person
	 *            object to find in the collection
	 */
	public void findCustomerByID(String customerId) {
		BasicDBObject searchQuery = new BasicDBObject();
		// searchQuery.put("name", p.getName().toUpperCase());
		// searchQuery.put("lastname", p.getLastName().toUpperCase());
		// searchQuery.put("idnumber", p.getPersNumber().toUpperCase());
		searchQuery.put("_id", new ObjectId(customerId));

		MongoCursor<Document> cursor = collection.find(searchQuery).iterator();
		if (!cursor.hasNext()) {
			System.out.println("Customer does not exist!");
			System.out.println("You're not eligable for a 10% off");
		} else {
			try {
				while (cursor.hasNext()) {
					// För att hämta ID:et från användaren, så vi kan ha en
					// connection mellan order och person
					Document obj = cursor.next();
					System.out.print("NAME: " + obj.get("name") + " | LASTNAME: " + obj.get("lastname")
							+ " | PERSONAL-NUMBER: " + obj.get("idnumber"));

				}
			} finally {
				cursor.close();
			}
		}
	}
	
	public void findCustomerByInfo(Person p) {
		BasicDBObject searchQuery = new BasicDBObject();
		 searchQuery.put("name", p.getName().toUpperCase());
		 searchQuery.put("lastname", p.getLastName().toUpperCase());
		 searchQuery.put("idnumber", p.getPersNumber().toUpperCase());
		//searchQuery.put("_id", new ObjectId("5913896729c3012500e17bb8"));

		MongoCursor<Document> cursor = collection.find(searchQuery).iterator();
		if (!cursor.hasNext()) {
			System.out.println("Customer does not exist!");
			System.out.println("You're not eligable for a 10% off");
		} else {
			try {
				while (cursor.hasNext()) {
					// För att hämta ID:et från användaren, så vi kan ha en
					// connection mellan order och person
					Document obj = cursor.next();
					System.out.print("CUSTOMER-ID: " + obj.get("_id") + 
							" | NAME: " + obj.get("name") + 
							" | LASTNAME: " + obj.get("lastname") +
							" | PERSONAL-NUMBER: " + obj.get("idnumber"));

				}
			} finally {
				cursor.close();
			}
		}
	}

	/**
	 * Prints the full list of customers
	 */
	public void printAllCustomers() {

		try {
			List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

			for (Document document : documents) {
				System.out.print(("CUSTOMER-ID: " + document.get("_id") + 
						" | NAME: " + document.get("name") + 
						" | LASTNAME: " + document.get("lastname") +
						" | PERSONAL-NUMBER: " + document.get("idnumber") 
						+ "\n"));
			}
		} catch (Exception ex) {
			System.out.println("Cannot retrieve information of customers");
			mongoClient.close();
		}
	
	}
}
