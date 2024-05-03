#include <fstream>
#include <vector>
#include <string>
#include <stdexcept>
#include <sstream>

#include "cart.h"

using namespace std;

//class User:DB {
class User : public BaseEntity{
private:
    string name;
    string password;
    string email;
    string userAddress;
    string creditCardNumber;
    int cartId;
    static string filename;

public:
		// Getter functions
        string getName() const;
        string getPassword() const;
        string getEmail() const;
        string getUserAddress() const;
        string getCreditCardNumber() const;
        int getCartId() const;

        // Setter functions
        void setName(const string newName);

        void setPassword(const string& newPassword);

        void setEmail(const string& newEmail);

        void setUserAddress(const string& newUserAddress);

        void setCreditCardNumber(const string& newCreditCardNumber);

        void setCartId(int newCartId);

        void login(User* res);
        bool signUp();
        Cart getUserCart();

        vector<ProductInCart> getProductsInCart();

        string pay(float total);

        bool removeFromCart(int productId);

        // Functions for storing and reading users from file
        static void writeData(const vector<User>& users);
        static vector<User> readData();

};