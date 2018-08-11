package Project;

import java.util.ArrayList;
/**
 * 
 *	Just a class that populates the database with a products collection
 *	Run this first
 */
public class PopulateDatabase {
	
	public static void main(String[] args){
		Products products = new Products();
		
		ArrayList<Product> productArray = new ArrayList<Product>();
		
		//Populate products collection
		productArray.add(new Product("FLAVORING","IRISH CREAM" , 200.0, 5.0));
		productArray.add(new Product("FLAVORING","VANILLA" , 200.0, 5.0));
		productArray.add(new Product("FLAVORING","CARAMEL" , 200.0, 5.0));
		productArray.add(new Product("HOT DRINKS","HOT CHOCOLATE" , 200.0, 15.0));
		productArray.add(new Product("HOT DRINKS","CAPPUCCINO" , 200.0, 15.0));
		productArray.add(new Product("HOT DRINKS","LATTE" , 200.0, 15.0));
		productArray.add(new Product("HOT DRINKS","ESSPRESSO" , 200.0, 15.0));
		productArray.add(new Product("HOT DRINKS","BREWED COFFEE" , 200.0, 15.0));
		productArray.add(new Product("WHOLE-BEAN COFFEE","ESSPRESSO ROAST" , 200.0, 20.0));
		productArray.add(new Product("WHOLE-BEAN COFFEE","WHOLE-BEAN FRENCH ROAST" , 200.0, 20.0));
		productArray.add(new Product("WHOLE-BEAN COFFEE","WHOLE-BEAN LIGHT ROAST" , 200.0, 20.0));
		
		for(int i = 0; i < productArray.size(); i++){
			products.addProductToDB(productArray.get(i));
		}
	
	}

}
