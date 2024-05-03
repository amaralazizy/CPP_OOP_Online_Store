#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <stdexcept>

#include "productInCart.h"

// #include "db.h"

using namespace std;

class Cart: public BaseEntity
{
private:
    int userId;
    vector<int> productIds;
    static const string filename;

public:
    Cart();
    Cart(int id);
    // Getter functions
    int getUserId() const;

    // Setter functions
    bool add_toCart(Product selectedProduct, int quantity);

    // Functions for storing and reading carts from file
    static void writeData(const vector<Cart> &carts);
    static vector<Cart> readData();
};
