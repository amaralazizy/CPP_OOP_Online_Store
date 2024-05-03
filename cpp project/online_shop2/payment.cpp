#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <stdexcept>
//#include "payment.h"
#include "productInCart.h"

Payment::Payment(int _id, int _userId, double _value) : BaseEntity(_id), userId(_userId), value(_value){};
// Getter functions
int Payment::getUserId() const
{
	return userId;
}

double Payment::getValue() const
{
	return value;
}

// Setter functions
void Payment::setUserId(int newUserId)
{
	userId = newUserId;
}

void Payment::setValue(double newValue)
{
	value = newValue;
}

// Functions for storing and reading payments from file
void Payment::writeData(const vector<Payment> &payments)
{
	/*
	ofstream file(filename);

	if (file.is_open()) {
		// Write header row
		file << "ID\tUser ID\tValue\n";

		// Write each payment to file
		for (const auto& payment : payments) {
			file << payment.getId() << '\t'
				<< payment.getUserId() << '\t'
				<< payment.getValue() << '\n';
		}

		file.close();
	}
	else {
		throw runtime_error("Unable to open file for writing.");
	}*/
	ofstream file(filename);

	if (file.is_open())
	{
		file << "id,userId,value\n"; // write header row

		for (const auto &payment : payments)
		{
			file << payment.getId() << "," << payment.userId << "," << payment.value << "\n";
		}

		file.close();
	}
	else
	{
		cout << "Unable to open file";
	}
}

vector<Payment> Payment::readData()
{
	/*
	vector<Payment> payments;

	ifstream file(filename);

	if (file.is_open()) {
		string line;
		getline(file, line); // Skip header row.

		while (getline(file, line)) {
			Payment payment;

			istringstream iss(line);
			if (!(iss >> payment.id >> payment.userId >> payment.value)) {
				throw runtime_error("Invalid file format.");
			}

			payments.push_back(payment);
		}

		file.close();
	}
	else {
		throw runtime_error("Unable to open file for reading.");
	}

	return payments;*/
	vector<Payment> payments;
	ifstream file(filename);
	string line;

	if (file.is_open())
	{
		getline(file, line); // skip header row

		while (getline(file, line))
		{
			stringstream ss(line);
			string field;
			vector<string> fields;

			while (getline(ss, field, ','))
			{
				fields.push_back(field);
			}

			int id = stoi(fields[0]);
			int userId = stoi(fields[1]);
			double value = stod(fields[2]);

			payments.push_back(Payment(id, userId, value));
		}

		file.close();
	}
	else
	{
		cout << "Unable to open file";
	}

	return payments;
}
string Payment::savePayment(int cartId)
{
	// vector <Payment> payments = readData();
	vector<ProductInCart> ProductsinCart = ProductInCart::readData();
	for (ProductInCart cart : ProductsinCart)
	{
		static int count = 0;
		if (cart.getCartId() == cartId)
		{
			ProductsinCart.erase(ProductsinCart.begin() + count);
		}
		else
			count++;
	}
	ProductInCart::writeData(ProductsinCart);
	return "Payment saved successfully!";
}
const string Payment::filename = "payments.csv";