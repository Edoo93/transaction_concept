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
public class Employer {
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;
	String employeeId;

	public Employer() {
		mongoClient = new MongoClient("127.0.0.1", 27017);
		database = mongoClient.getDatabase("BeaverCoffee");
		collection = database.getCollection("employer");
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.OFF);
	}

	/**
	 * 
	 * @param person
	 *            object to add to the customers collection
	 */
	public void addEmployerToDB(Person p) {
		try {
			Document doc = new Document("name", p.getName().toUpperCase())
					.append("lastname", p.getLastName().toUpperCase()).append("persNumber", p.getPersNumber())
					.append("position", p.getPosition().toUpperCase());

			collection.insertOne(doc);
			System.out.println("\nThe employer has successfully been added to the database!");
		} catch (Exception ex) {
			System.out.println("A employee failed to be added!");
			mongoClient.close();
		}
		mongoClient.close();
	}

	/**
	 * 
	 * @param person
	 *            object to find in the collection
	 */
	public String findEmployerByName(String employerName) {
		String employerId = "";
		BasicDBObject searchQuery = new BasicDBObject();

		searchQuery.put("name", employerName);
		MongoCursor<Document> cursor = collection.find(searchQuery).iterator();

		if (!cursor.hasNext()) {
			System.out.println("Employer does not exist!");
		} else {
			try {
				while (cursor.hasNext()) {
					// För att hämta ID:et från användaren, så vi kan ha en
					// connection mellan order och person
					Document obj = cursor.next();
					if (employerName.equals(obj.get("name"))) {
						employerId = obj.get("_id").toString();
					}
				}
			} finally {
				cursor.close();
			}
		}
		return employerId;
	}

	/**
	 * Prints the full list of customers
	 */
	public void printAllEmployers() {
		try {
			List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());
			for (Document document : documents) {
				System.out.print("EMPLOYER NAME: " + document.get("name") + " " + document.get("lastname")
				+ " | PERSONAL NUMBER: " + document.get("persNumber") + " | POSITION: "
				+ document.get("position") + "\n");
			}
		} catch (Exception ex) {
			System.out.println("Cannot retrieve information of employers");
			mongoClient.close();
		}

	}
}
