#include "productInCart.h"

#include <iostream>
#include <fstream>
#include <sstream>
#include <stdexcept>

using namespace std;

const std::string ProductInCart::filename = "productincart.csv";

ProductInCart::ProductInCart(int _id, int _cartId, int _productId, int _count):
BaseEntity(_id), cartId(_cartId), productId(_productId), count(_count)
{}

// Getter functions
int ProductInCart::getCartId() const {
    return cartId;
}

int ProductInCart::getProductId() const {
    return productId;
}

int ProductInCart::getCount() const {
    return count;
}

Product ProductInCart::getProduct() const {
    return product;
}

// Setter functions
void ProductInCart::setCartId(int newCartId) {
    cartId = newCartId;
}

void ProductInCart::setProductId(int newProductId) {
    productId = newProductId;
}

void ProductInCart::setCount(int newCount) {
    count = newCount;
}

void ProductInCart::setProduct(Product p) {
    product = p;
}

// Functions for storing and reading product items in a cart from file
void ProductInCart::writeData(const std::vector<ProductInCart>& items) {
    /*
    std::ofstream file(filename);

    if (file.is_open()) {
        // Write header row
        file << "ID\tCart ID\tProduct ID\tCount\n";

        // Write each product item in cart to file
        for (const auto& item : items) {
            file << item.getId() << '\t'
                << item.getCartId() << '\t'
                << item.getProductId() << '\t'
                << item.getCount() << '\n';
        }

        file.close();
    }
    else {
        throw std::runtime_error("Unable to open file for writing.");
    }
    */
    ofstream file(filename);

    if (file.is_open()) {
        file << "id,cartId,productId,count\n"; // write header row

        for (const auto& productInCart : items) {
            file << productInCart.getId() << "," << productInCart.cartId << "," << productInCart.productId << "," << productInCart.count << "\n";
        }

        file.close();
    }
    else {
        cout << "Unable to open file";
    }
}

std::vector<ProductInCart> ProductInCart::readData() {
    /*
    std::vector<ProductInCart> items;

    std::ifstream file(ProductInCart::filename);

    if (file.is_open()) {
        std::string line;
        getline(file, line); // Skip header row.

        while (getline(file, line)) {
            ProductInCart item;
            std::istringstream iss(line);
            if (!(iss >> item.id >> item.cartId >> item.productId >> item.count)) {
                throw std::runtime_error("Invalid file format.");
            }

            items.push_back(item);
        }

        file.close();
    }
    else {
        throw std::runtime_error("Unable to open file for reading.");
    }

    return items;
    */
    vector<ProductInCart> productsInCart;
    ifstream file(filename);
    string line;

    if (file.is_open()) {
        getline(file, line); // skip header row

        while (getline(file, line)) {
            stringstream ss(line);
            string field;
            vector<string> fields;

            while (getline(ss, field, ',')) {
                fields.push_back(field);
            }

            int id = stoi(fields[0]);
            int cartId = stoi(fields[1]);
            int productId = stoi(fields[2]);
            int count = stoi(fields[3]);

            productsInCart.push_back(ProductInCart(id, cartId, productId, count));
        }

        file.close();
    }
    else {
        cout << "Unable to open file";
    }

    return productsInCart;
}