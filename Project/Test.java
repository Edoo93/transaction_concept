package Project;

import java.text.ParseException;

public class Test {
	
	public static void main(String[] args) throws ParseException{
		Product product = new Product("FLAVORING","IRISH CREAM" , 200.0, 15.0);
		Products products = new Products();
		Employees employees = new Employees();
		Employer employer = new Employer();
		//Check customer addition etc
		Persons connection = new Persons();
		Order order = new Order();
		Products pbc = new Products();
		Person p = new Person("c", "b" ,"q", "w", "e", "t", "y");
//		employer.addEmployerToDB(p);
//		employees.addEmployeeToDB(p);
		//connection.addPersonToDB(p);
		//connection.printAllCustomers();
		//connection.findCustomerByInfo(p3);
		//connection.findCustomerByID(p3);
	//	products.addProductToDB(product);
		//order.addCustomerOrder();
//		connection.printAllCustomers();
//		products.getAllItems();
//		employees.printAllEmployees();
//		employer.printAllEmployers();
		//order.queryByDate("2017/05/22", "2017/05/25");
		products.updateProduct("CAPPUCCINO");
	}

}
