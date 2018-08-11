package Project;

import static com.mongodb.client.model.Filters.eq;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.UpdateResult;

public class Order {
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;
	Products pbc;
	Persons person;
	
	/**
	 * Constructor that opens a connection to the database/collection
	 */
	public Order() {
		mongoClient = new MongoClient("127.0.0.1", 27017);
		database = mongoClient.getDatabase("BeaverCoffee");
		collection = database.getCollection("orders");
		person = new Persons();
	}
	/**
	 * add customerOrder with employee name, customerId and the arraylist order
	 * @param employee name
	 * @param customerId the id the customer has if he's registered
	 * @param order the orderlist
	 */
	public void addCustomerOrder(String employee, String customerId, ArrayList<String> order) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();

		try {
			Document doc = new Document("employee", employee).append("cust_Id", customerId).append("order", order)
					.append("date", dateFormat.format(date));
			collection.insertOne(doc);

			System.out.println("\nThe order has successfully been added to the database!");
		} catch (Exception ex) {
			System.out.println("An order failed to be added!");
		}
	}
	/**
	 * Query orders based on start/end date
	 * @param startTime the start date
	 * @param endTime the end date
	 */
	public void queryByDate(String startTime, String endTime) {
		try {
			BasicDBObject query = new BasicDBObject("date", new BasicDBObject("$gte", startTime).append("$lt", endTime));

			MongoCursor<Document> cursor = collection.find(query).iterator();
			while (cursor.hasNext()) {
				Document obj = cursor.next();
				System.out.println("ID: " + obj.get("_id") + " | EMPLOYEE: " + obj.get("employee") + " | CUSTOMER-ID: " + obj.get("cust_Id") + " | ORDER: " + obj.get("order") + " | DATE: " + obj.get("date"));
			}
		} catch (Exception ex) {

		}
	}
	
	public void queryByEmployeeAndDate(String name, String startTime, String endTime) {
		try {
			BasicDBObject query = new BasicDBObject("date", new BasicDBObject("$gte", startTime).append("$lt", endTime));
			query.put("employee", name);
			
			MongoCursor<Document> cursor = collection.find(query).iterator();
			while (cursor.hasNext()) {
				Document obj = cursor.next();
				System.out.println("ID: " + obj.get("_id") + " | EMPLOYEE: " + obj.get("employee") + " | CUSTOMER-ID: " + obj.get("cust_Id") + " | ORDER: " + obj.get("order") + " | DATE: " + obj.get("date"));
			}
		} catch (Exception ex) {

		}
	}
	
	public void queryOrdersByEmployee(String employee) {
		String emp = employee.toUpperCase();
		try {
			BasicDBObject query = new BasicDBObject("employee", emp);

			MongoCursor<Document> cursor = collection.find(query).iterator();
			while (cursor.hasNext()) {
				Document obj = cursor.next();
				System.out.println("ID: " + obj.get("_id") + " | EMPLOYEE: " + obj.get("employee") + " | CUSTOMER-ID: " + obj.get("cust_Id") + " | ORDER: " + obj.get("order") + " | DATE: " + obj.get("date"));
			}
		} catch (Exception ex) {

		}
	}
	/**
	 * Prints all orders
	 */
	public void printAllOrders() {
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());
		for (Document document : documents) {
			System.out.println(document);
		}
	}
	/**
	 * Delete orders
	 * @param id = deletes order based on id
	 */
	public void deleteOrder(String id) {
		try {
			collection.deleteOne(new Document("_id", new ObjectId(id)));
			System.out.println("You have successfully deleted the order!");
		} catch (Exception ex) {
			System.out.println("Failed to delete the order");
		}
	}
	/**
	 * updates order in the collection
	 * @param orderId = the order id that will be updated
	 * @param newOrder = provides a new list that will be updated
	 */
	public void updateOrder(String orderId, List<String> newOrder) {
		System.out.println(newOrder);
		try {
			Bson collectionDocument = new Document("_id", new ObjectId(orderId));
			Bson newValue = new Document("order", newOrder);
			Bson updateDocument = new Document("$set", newValue);
			collection.updateMany(collectionDocument, updateDocument);
			System.out.println("You have successfully updated the order!");
		} catch (Exception ex) {
			System.out.println("Failed to update the order");
		}
	}
}
