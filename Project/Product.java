package Project;
/**
 * 
 *	Class to add new products
 */
public class Product {
	
	private String name;
	private double kilo;
	private double litres;
	private double price;
	private String category;
	
	public Product(String category, String name, double litres, double price){
		this.category = category;
		this.name = name;
		this.litres = litres;
		this.price = price;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getKilo() {
		return kilo;
	}

	public void setKilo(double kilo) {
		this.kilo = kilo;
	}

	public double getLitres() {
		return litres;
	}

	public void setLitres(double litres) {
		this.litres = litres;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
