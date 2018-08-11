package Project;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.UpdateResult;

public class Products {
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;

	public Products() {
		mongoClient = new MongoClient("127.0.0.1", 27017);
		database = mongoClient.getDatabase("BeaverCoffee");
		collection = database.getCollection("products");
	}
	
	public void addProductToDB(Product p) {
		try {
			Document doc = new Document("category", p.getCategory().toUpperCase()).append("name", p.getName().toUpperCase()).append("litres", p.getLitres()).append("price", p.getPrice());

			collection.insertOne(doc);
		} catch (Exception ex) {
			System.out.println("A product failed to be added!");
		}
	}
	
	public void getItem(String itemToFind){
		double thePrice = 0;
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", itemToFind.toUpperCase());
		
		MongoCursor<Document> cursor = collection.find(searchQuery).iterator();
		while(cursor.hasNext()){
			Document obj = cursor.next();
			thePrice = obj.getDouble("price");
			System.out.println("PRICE: " + thePrice);
		}
	}
	
	public void getAllItems(){
		try {
			List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

			for (Document document : documents) {
				System.out.print("CATEGORY: " + document.get("category") + " | PRODUCT: " + document.get("name") + " | LITRES AVAILABLE: " + document.get("litres") + " | PRICE: " + document.get("price") + "\n");

			}
		} catch (Exception ex) {
			System.out.println("Cannot retrieve information of the products");
		}
	}
	
	public void getProductList(List<String> order) {
		try {
			for (int i = 0; i < order.size(); i++){
				order.get(i);
			}
		} catch (Exception ex) {
		}
	}
	
	public void updateProduct(String product){
		double litres = 0;
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", product.toUpperCase());
		
		MongoCursor<Document> cursor = collection.find(searchQuery).iterator();
		if(cursor.hasNext()){
			Document obj = cursor.next();
			litres = obj.getDouble("litres") - 0.3;
			System.out.println("LITRES AVAILABLE: " + litres);
		}
		collection.updateOne(eq("name", product.toUpperCase()), new Document("$set", new Document("litres", litres )));
		litres = 0;
	}
}
