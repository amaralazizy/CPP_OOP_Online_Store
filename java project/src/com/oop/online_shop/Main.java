package com.oop.online_shop;
import java.util.Scanner;
import java.util.List;

public class Main {
	private static User user;
	private static Scanner scanner = new Scanner(System.in);

	private static void showCart(){
		List<ProductInCart> productsInCart = user.getProductsInCart();
		if(productsInCart.size() == 0){
			System.out.println("Cart is empty!");
			return;
		}
		System.out.println("Products In Cart: ");
		int counter = 1;
		double total = 0;
		for (ProductInCart productInCart : productsInCart) {
			Product product = productInCart.getProduct();
			double productTotal = productInCart.getCount() * product.getPrice();
			System.out.println(counter + ") " + productInCart.getCount() + " of " + product.getName() + "\t\t" + productInCart.getCount() + " × " + product.getPrice() + " = " + productTotal + "$");
			total += productTotal;
			counter++;
		}

		System.out.println("TOTAL:\t" + total);

		System.out.println("\n1.Proceed Payment");
		System.out.println("2.Remove product from cart");
		System.out.print("Choose a number ( enter 0 to go back ): ");
		int number = scanner.nextInt();
		switch (number){
			case 1:
				System.out.println(user.pay((float)total));
				for (ProductInCart productInCart : productsInCart) {
					productInCart.getProduct().decreaseProductQuantity(productInCart.getCount());
				}
				break;

			case 2:
				System.out.println("Enter the number of product to remove from cart: ");
				number = scanner.nextInt();
				if(user.removeFromCart(productsInCart.get(number-1).getProduct().getId())) System.out.println("Product removed successfully!");
				System.out.println("Here is the cart after edit:");
				showCart();
				break;
		}
	}

	private static void userLogin(){
		System.out.println("Login Please:");
		do {
			user = new User();

			System.out.println("Enter Email: ");
			scanner = new Scanner(System.in);
			String email = scanner.nextLine();
			user.setEmail(email);

			System.out.println("Enter Password: ");
			scanner = new Scanner(System.in);
			String pass = scanner.nextLine();
			user.setPass(pass);

			user = user.Login();

			if (user == null) System.out.println("Wrong email or password. Try Again: ");
		} while (user == null);
		System.out.println("Logged in successfully!\n");
	}

	private static void userSignup(){
		System.out.println("Write your full name");
		user.setName(scanner.nextLine());

		System.out.println("Write your Email");
		user.setEmail(scanner.nextLine());

		System.out.println("Enter password");
		user.setPass(scanner.nextLine());

		System.out.println("Enter address");
		user.setAddress(scanner.nextLine());

		System.out.println("Enter credit card number");
		user.setCreditCardNumber(scanner.nextLine());

		if(user.signUp()) {
			System.out.println("Signed up successfully!\n");
			userLogin();
		}
	}
	private static void userLoginOrSignup(){
		user = new User();

		System.out.println("Press 1 to Login");
		System.out.println("Press 2 to Signup");

		scanner = new Scanner(System.in);
		int dec = scanner.nextInt();

		if(dec == 1) userLogin();
		else if (dec == 2) userSignup();
	}

	private static void userAddToCart(Product selectedProduct){
		System.out.println(selectedProduct.getQuantity() + " Left in stock.");
		System.out.println("How many do you want? ");

		// Make sure that the user select valid quantity value
		int wantedQuantity = 1;
		do{
			wantedQuantity = scanner.nextInt();
			if(wantedQuantity > selectedProduct.getQuantity())
				System.out.println("Please Enter a count that is less than " + selectedProduct.getQuantity());
		}while(wantedQuantity > selectedProduct.getQuantity());

		if(user == null) userLoginOrSignup();

		Cart userCart = user.getUserCart();
		if (userCart.add_toCart(selectedProduct, wantedQuantity)) {
			System.out.println(selectedProduct.getName() + " Added to Cart Successfully");
		} else System.out.println("Unknown error adding product to cart");
	}

	private static void productsList() {
		int number = 0;

		do {
			System.out.println("");
			System.out.println("Click on the number of the product to show description");
			List<Product> productList = Product.getAllProducts();
			//For loop to display all products from database
			for (Product product : productList) {
				System.out.println(product.getId() + ") " + product.getName());
			}

			//choosing a number to show the product description
			Scanner scanner = new Scanner(System.in);
			System.out.print("Chose a number ( enter 0 to go back ): ");
			number = scanner.nextInt();

			if (number == 0) return;

			//for loop to show the products description
			Product selectedProduct = null;
			for (Product product : productList) {

				if (number == product.getId()) {
					selectedProduct = product;
					System.out.println("Description: " + product.getDescription());
					System.out.println("Price: " + product.getPrice());
					System.out.println("Quantity: " + product.getQuantity());
					System.out.println("User Rating: " + product.getUserRating());
					break;
				}
			}

			System.out.println("\n1. to add to cart");
			System.out.println("2. to continue shopping");
			System.out.print("Chose a number ( enter 0 to go back ): ");

			number = scanner.nextInt();
			if(number == 1) userAddToCart(selectedProduct);

		}while(number == 2);
	}

	private static void loggedIn(){
		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.println("\nWhat do you want to do? ");
			System.out.println("1. Continue shopping");
			System.out.println("2. Show cart");
			System.out.println("3. Logout");

			int decision = scanner.nextInt();
			switch (decision){
				case 1:
					productsList();
					break;
				case 2:
					showCart();
					break;
				case 3:
					do {
						productsList();
					} while (user == null);
					loggedIn();
					break;
			}
		}
	}

	public static void main(String[] args) {
		// connect the database
		DBConnection.connect();
		//Instances 
		Scanner scanner = new Scanner(System.in);
		//Cart c1 = new Cart();
		//Beginning of the program 
		System.out.println("*********************************************************************");
		System.out.println("\t\t\tWelcome to TickTock Watches");
		System.out.println("*********************************************************************");

		System.out.println("1. Login");
		System.out.println("2. Signup");
		System.out.println("3. Start shopping");

		int decision = scanner.nextInt();
		switch (decision){
			case 1:
				userLogin();
				loggedIn();
				break;
			case 2:
				userSignup();
				loggedIn();
				break;
			case 3:
				do {
					productsList();
				} while (user == null);
				loggedIn();
				break;
		}

	    // Disconnect database
	 	DBConnection.closeConnection();
	
	}

}
