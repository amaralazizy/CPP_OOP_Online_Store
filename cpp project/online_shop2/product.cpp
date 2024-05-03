#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <stdexcept>
#include <sstream>
#include "product.h"

using namespace std;

string Product::filename = "products.csv";

Product::Product() {}
Product::Product(int id, string nameproduct, string Descreiption, double Price, int Quantity, float UserRating):BaseEntity(id)
{
	this->name = nameproduct;
	this->description = Descreiption;
	this->price = Price;
	this->quantity = Quantity;
	this->userRating = UserRating;
}
// Getter functions
string Product::getName() const
{
	return name;
}

string Product::getDescription() const
{
	return description;
}

double Product::getPrice() const
{
	return price;
}

int Product::getQuantity() const
{
	return quantity;
}

float Product::getUserRating() const
{
	return userRating;
}

// Setter functions
void Product::setName(const string &newName)
{
	name = newName;
}

void Product::setDescription(const string &newDescription)
{
	description = newDescription;
}

void Product::setPrice(double newPrice)
{
	price = newPrice;
}

void Product::setQuantity(int newQuantity)
{
	quantity = newQuantity;
}

void Product::setUserRating(float newUserRating)
{
	userRating = newUserRating;
}

bool Product::decreaseProductQuantity(int num)
{
	vector<Product> products = readData();
	int counter = 0;
	for (Product product : products)
	{
		if (this->getId() == product.getId())
		{
			products[counter].quantity -= num;
			writeData(products);
			return true;
		}
		counter++;
	}
	return false;
}

// Functions for storing and reading products from file
void Product::writeData(const vector<Product> &products)
{
	/*
	ofstream file(Product::filename);

	if (file.is_open()) {
		// Write header row
		file << "ID\tName\tDescription\tPrice\tQuantity\tUser Rating\n";

		// Write each product to file
		for (const auto& product : products) {
			file << product.getId() << '\t'
				<< product.getName() << '\t'
				<< product.getDescription() << '\t'
				<< product.getPrice() << '\t'
				<< product.getQuantity() << '\t'
				<< product.getUserRating() << '\n';
		}

		file.close();
	}
	else {
		throw runtime_error("Unable to open file for writing.");
	}*/
	ofstream csvFile(filename);
	if (csvFile)
	{
		csvFile << "id,name,description,price,quantity,user_rating" << endl;
		for (const auto &product : products)
		{
			csvFile << product.getId() << ","
					<< product.getName() << ","
					<< product.getDescription() << ","
					<< product.getPrice() << ","
					<< product.getQuantity() << ","
					<< product.getUserRating() << endl;
		}
		csvFile.close();
	}
	else
	{
		cout << "Error opening file: " << filename << endl;
	}
}

vector<Product> Product::readData()
{
	/*vector<Product> products;

	ifstream file(Product::filename);
	if (file.is_open()) {
		string line;
		getline(file, line); // Skip header row.

		while (getline(file, line)) {
			Product product;

			istringstream iss(line);
			if (!(iss >> product.id >> product.name >> product.description
				>> product.price >> product.quantity >> product.userRating)) {
				throw runtime_error("Invalid file format.");
			}

			products.push_back(product);
		}

		file.close();
	}
	else {
		throw runtime_error("Unable to open file for reading.");
	}
	*/
	vector<Product> products;
	ifstream inputFile(filename);
	if (inputFile)
	{
		string line;
		if (getline(inputFile, line))
		{ // read header line, discard
			while (getline(inputFile, line))
			{ // read data lines
				stringstream ss(line);
				string idStr, name, description, priceStr, quantityStr, ratingStr;
				if (getline(ss, idStr, ',') &&
					getline(ss, name, ',') &&
					getline(ss, description, ',') &&
					getline(ss, priceStr, ',') &&
					getline(ss, quantityStr, ',') &&
					getline(ss, ratingStr))
				{
					int id = stoi(idStr);
					double price = stod(priceStr);
					int quantity = stoi(quantityStr);
					float rating = stof(ratingStr);
					Product product;
					product.setId(id);
					product.setName(name);
					product.setDescription(description);
					product.setPrice(price);
					product.setQuantity(quantity);
					product.setUserRating(rating);
					products.push_back(product);
				}
			}
		}
		inputFile.close();
	}
	else
	{
		cout << "Error opening file: " << filename << endl;
	}
	return products;
}
