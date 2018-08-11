package Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

public class Controller {
	private Scanner scan = new Scanner(System.in);
	private String employeeName;
	private String customerId;
	private Persons person;
	private Order order;
	private Products products;
	private Employees employees = new Employees();
	private Employer employer = new Employer();

	public static void main(String[] args) {
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.OFF);
		Controller c = new Controller();
		c.start();
	}

	/**
	 * Gets employee name that handles the order
	 */
	private void start() {

		System.out.println("Type 1 for employee, type 2 for employer");
		String choice = scan.next();

		if (choice.equals("1")) {
			System.out.println("Enter employee name: ");
			if (scan.hasNext()) {
				employeeName = scan.next().toUpperCase();
			}
			employeeChoices();
		} else {
			System.out.println("Enter employer name: ");
			if (scan.hasNext()) {
				employeeName = scan.next().toUpperCase();
			}
			employerChoices();
		}
	}

	private void employerChoices() {
		System.out.println("STEP #1");
		System.out.println(
				"What would you like to do?\n1-Place order\n2-Update order\n3-Delete order\n4-Add comment\n5-Query orders based on date\n6-Query orders based on employee and date\n7-Query orders based on employee-name\nN-To quit");
		if (scan.hasNext()) {
			String choice = scan.next();
			if (choice.equals("N") || choice.equals("n")) {
				System.out.println("You've decided to cancel the proccess of ordering!");
				System.exit(0);
			} else if (choice.equals("1")) {
				System.out.println("You've decided to continue with the order!");
				findCustomer();
			} else if (choice.equals("2")) {
				System.out.println("You've decided to update the order!");
				updateOrder();
			} else if (choice.equals("3")) {
				System.out.println("You've decided to delete the order!");
				deleteOrder();
			} else if (choice.equals("4")) {
				System.out.println("You've decided to add comment for an employee");
				addComment();
			} else if (choice.equals("5")) {
				dateOrderQuery();
			} else if (choice.equals("6")) {
				EmployeeAndDateOrderQuery();
			} else if (choice.equals("7")) {
				queryOrdersByEmployee();
			}
		}
	}

	private void employeeChoices() {
		System.out.println("STEP #1");
		System.out.println("What would you like to do?\n1-Place order\n2-Update order\n3-Delete order\nN-To quit");
		if (scan.hasNext()) {
			String choice = scan.next();
			if (choice.equals("N") || choice.equals("n")) {
				System.out.println("You've decided to cancel the proccess of ordering!");
				System.exit(0);
			} else if (choice.equals("1")) {
				System.out.println("You've decided to continue with the order!");
				findCustomer();
			} else if (choice.equals("2")) {
				System.out.println("You've decided to update the order!");
				updateOrder();
			} else if (choice.equals("3")) {
				System.out.println("You've decided to delete the order!");
				deleteOrder();
			}
		}
	}
	/**
	 * Get all orders based on the dates in between
	 */
	private void dateOrderQuery() {
		// TODO Auto-generated method stub
		order = new Order();
		scan = new Scanner(System.in);
		System.out.println("Please enter the start-date for your order-query (FOLLOW THIS FORMAT: ex. 2017/05/20)");
		String startDate = scan.nextLine();
		System.out.println("Please enter the end-date for your order-query (FOLLOW THIS FORMAT: ex. 2017/05/20)");
		String endDate = scan.nextLine();
		order.queryByDate(startDate, endDate);
		String choice = scan.next();
		if(choice.equals("B") || choice.equals("b")){
			employerChoices();
		}
	}
	
	private void EmployeeAndDateOrderQuery() {
		// TODO Auto-generated method stub
		order = new Order();
		scan = new Scanner(System.in);
		System.out.println("Please enter the employee-name:");
		String name = scan.next().toUpperCase();
		System.out.println("Please enter the start-date for your order-query (FOLLOW THIS FORMAT: ex. 2017/05/20)");
		String startDate = scan.next();
		System.out.println("Please enter the end-date for your order-query (FOLLOW THIS FORMAT: ex. 2017/05/20)");
		String endDate = scan.next();
		order.queryByEmployeeAndDate(name, startDate, endDate);
		String choice = scan.next();
		if(choice.equals("B") || choice.equals("b")){
			employerChoices();
		}
	}
	
	private void queryOrdersByEmployee(){
		scan = new Scanner(System.in);
		order = new Order();
		System.out.println("Enter the name of the employee you want to query the orders of:");
		String employee = scan.nextLine();
		order.queryOrdersByEmployee(employee);
	}

	/**
	 * Find customer based on barcode (ID in this case, but if it was a real
	 * barcode, it would be associated with a unique id that would've been
	 * assigned to each customer that has been registered
	 */
	private void findCustomer() {
		person = new Persons();
		System.out.println("---------------------------------------------------------\nSTEP #2\n");
		System.out.println("Are you a customer? if so provide us with the id-number for a 10% off: ");
		System.out.println(
				"If you're not a customer, would you like to register to BeaverCoffee? - Type Y (Yes)/N (No)/C (Customer already)");

		String choice = scan.next();
		if (choice.equals("Y") || choice.equals("y")) {
			addNewCustomer();
		} else if (choice.equals("N") || choice.equals("n")) {
			System.out.println("You've decided to not register!\n");
			addOrder();
		} else if (choice.equals("C") || choice.equals("c")) {
			System.out.println("Provide the barcode-ID: ");
			if (scan.hasNext()) {
				customerId = scan.next();
				System.out.println(
						"\n---------------------------------------------------------\nIs this you? - TYPE Y/N\n");
				person.findCustomerByID(customerId);
				System.out.println("\n");
				String choice2 = scan.next();
				if (choice2.equals("Y") || choice2.equals("y")) {
					addOrder();
				} else if (choice2.equals("N") || choice2.equals("n")) {
					findCustomer();
				}
			}
		}
	}
	/**
	 * Add an order to the database
	 */
	private void addOrder() {
		ArrayList<String> orderList = new ArrayList<String>();
		boolean itemDone = false;
		order = new Order();
		products = new Products();
		System.out.println(
				"---------------------------------------------------------\nSTEP #3\n\nHere's the list of products:\n");
		products.getAllItems();
		String choice = scan.nextLine();
		System.out.println("\nMay I take your order? (ENTER PRODUCT NAME!!!) When you're done with the order, TYPE X");
		while (!itemDone) {
			 choice = scan.nextLine().toUpperCase();
			if (choice.equals("x") || choice.equals("X")) {
				itemDone = true;
				System.out.println("You have ordered: ");
				for (int i = 0; i < orderList.size(); i++) {
					System.out.println(orderList.get(i));
					products.getItem(orderList.get(i));
					products.updateProduct(orderList.get(i));
				}
				if (customerId == null) {
					customerId = "undefined";
				}
				order.addCustomerOrder(employeeName, customerId, orderList);
			} else {
				orderList.add(choice);

			}
		}
	}

	private void addNewCustomer() {
		Persons persons = new Persons();
		String customerName;
		String customerLastName;
		String customerAge;
		String customerAddress;
		String customerPersNumber;
		System.out.println(
				"---------------------------------------------------------\nYou've chosen to register to BeaverCoffee!\n");

		System.out.println("Enter your name: ");
		customerName = scan.next();
		System.out.println("Enter your lastname: ");
		customerLastName = scan.next();
		System.out.println("Enter your age: ");
		customerAge = scan.next();
		System.out.println("Enter your address: ");
		customerAddress = scan.next();
		System.out.println("Enter your personal-number: ");
		customerPersNumber = scan.next();
		try {
			Person pers = new Person(customerName, customerLastName, customerAge, customerAddress, customerPersNumber);
			persons.addPersonToDB(pers);
			System.out.println("Would you like to proceed with the order now? TYPE Y/N");
			String choice = scan.next();
			if (choice.equals("Y") || choice.equals("y")) {
				addOrder();
			} else if (choice.equals("n") || choice.equals("N")) {
				System.out.println("\nYou've decided to leave! :(");
				System.exit(0);
			}
		} catch (Exception ex) {
			System.out.println("Couldn't add person!");
		}
	}

	private void updateOrder() {
		order = new Order();
		order.printAllOrders();
		List<String> newOrderList = new ArrayList<String>();
		boolean itemDone = false;
		products = new Products();
		scan = new Scanner(System.in);
		System.out.println("Enter the id of the order you want to update");
		String orderId = scan.nextLine();
		System.out.println(
				"---------------------------------------------------------\nTYPE X when you are done. Here's the list of products:");
		products.getAllItems();
		while (!itemDone) {
			String choice = scan.nextLine();
			if (choice.equals("x") || choice.equals("X")) {
				itemDone = true;
				System.out.print("This is the new order: ");
				for (int i = 0; i < newOrderList.size(); i++) {
					System.out.println(newOrderList.get(i));
				}
			} else {
				newOrderList.add(choice);
			}
		}
		order.updateOrder(orderId, newOrderList);
	}

	private void deleteOrder() {
		order = new Order();
		order.printAllOrders();
		System.out.println("Enter the order ID that you want to delete");
		String choice = scan.next();
		order.deleteOrder(choice);
	}

	private void addComment() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String employerID = employer.findEmployerByName(employeeName);

		String comment = employerID + " - " + dateFormat.format(date) + " - ";
		employees.printAllEmployees();
		System.out.println("Type ID of the employee you want to comment");

		if (scan.hasNext()) {
			String employeeID = scan.next();
			System.out.println("Enter your comment, max 300 characters");
			String newComment = scan.next();
			if (newComment.length() > (300 - comment.length())) {
				comment = comment + newComment.substring(0, (300 - comment.length()));
			} else {
				comment = comment + newComment;
			}
			employees.addComment(employeeID, comment);
		}
	}
}
