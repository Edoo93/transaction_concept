package Project;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;
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
public class Employees {
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;

	public Employees() {
		mongoClient = new MongoClient("127.0.0.1", 27017);
		database = mongoClient.getDatabase("BeaverCoffee");
		collection = database.getCollection("employees");
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.OFF); 
	}

	/**
	 * 
	 * @param person
	 *            object to add to the customers collection
	 */
	public void addEmployeeToDB(Person p) {
		try {
			Document doc = new Document("name", p.getName().toUpperCase())
					.append("lastname", p.getLastName().toUpperCase())
					.append("persNumber", p.getPersNumber())
					.append("position", p.getPosition().toUpperCase())
					.append("startDate", p.getStartDate())
					.append("endDate", p.getEndDate())
					.append("time", p.getTime())
					.append("comment", "");

			collection.insertOne(doc);
			System.out.println("\nThe employee has successfully been added to the database!");
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
	public void findEmployeeByID(String employeeId) {
		BasicDBObject searchQuery = new BasicDBObject();
		// searchQuery.put("name", p.getName().toUpperCase());
		// searchQuery.put("lastname", p.getLastName().toUpperCase());
		// searchQuery.put("idnumber", p.getPersNumber().toUpperCase());
		searchQuery.put("_id", new ObjectId(employeeId));

		MongoCursor<Document> cursor = collection.find(searchQuery).iterator();
		if (!cursor.hasNext()) {
			System.out.println("Employee does not exist!");
		} else {
			try {
				while (cursor.hasNext()) {
					// För att hämta ID:et från användaren, så vi kan ha en
					// connection mellan order och person
					Document obj = cursor.next();
					System.out.print("NAME: " + obj.get("name") 
					+ " | LASTNAME: " + obj.get("lastname")
					+ " | PERSONAL-NUMBER: " + obj.get("persNumber")
					+ " | POSITION: " + obj.get("position")
					+ " | START DATE: " + obj.get("startDate")
					+ " | END DATE: " + obj.get("endDate")
					+ " | TIME %: " + obj.get("time")
							);

				}
			} finally {
				cursor.close();
			}
		}
	}

	public void findEmployeeByInfo(Person p) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", p.getName().toUpperCase());
		searchQuery.put("lastname", p.getLastName().toUpperCase());
		searchQuery.put("persNumber", p.getPersNumber().toUpperCase());
		searchQuery.put("position", p.getPosition().toUpperCase());
		searchQuery.put("startDate", p.getStartDate());
		searchQuery.put("startDate", p.getStartDate());
		searchQuery.put("time", p.getTime());
		//searchQuery.put("_id", new ObjectId("5913896729c3012500e17bb8"));

		MongoCursor<Document> cursor = collection.find(searchQuery).iterator();
		if (!cursor.hasNext()) {
			System.out.println("Employee does not exist!");
		} else {
			try {
				while (cursor.hasNext()) {
					// För att hämta ID:et från användaren, så vi kan ha en
					// connection mellan order och person
					Document obj = cursor.next();
					System.out.print("EMPLOYEE-ID: " + obj.get("_id") 
					+ " | NAME: " + obj.get("name")
					+ " | LASTNAME: " + obj.get("lastname")
					+ " | PERSONAL-NUMBER: " + obj.get("persNumber")
					+ " | POSITION: " + obj.get("position")
					+ " | START DATE: " + obj.get("startDate")
					+ " | END DATE: " + obj.get("endDate")
					+ " | TIME %: " + obj.get("time") + "\n");
				}
			} finally {
				cursor.close();
			}
		}
	}

	/**
	 * Prints the full list of customers
	 */
	public void printAllEmployees() {
		try {
			List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());
			for (Document document : documents) {
				System.out.print("ID: " + document.get("_id")
				+ " | EMPLOYEE NAME: " + document.get("name") 
				+ " " + document.get("lastname") 
				+ " | PERSONAL NUMBER: " + document.get("persNumber") 
				+ " | POSITION: " + document.get("position")
				+ " | START DATE: " + document.get("startDate")
				+ " | END DATE: " + document.get("endDate")
				+ " | TIME %: " + document.get("time")
				+ "\n");
			}
		} catch (Exception ex) {
			System.out.println("Cannot retrieve information of employees");
			mongoClient.close();
		}
		
	}

	public void addComment(String employeeID, String comment) {
		try {		
			Bson collectionDocument = new Document("_id", new ObjectId(employeeID));
			Bson newValue = new Document("comment", comment);
			Bson updateDocument = new Document("$set", newValue);
			collection.updateOne(collectionDocument, updateDocument);
			System.out.println("You have successfully added a comment!");
		} catch (Exception ex) {
			System.out.println("Failed to add comment");
		}

	}

}
