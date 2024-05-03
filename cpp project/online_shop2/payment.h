#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <stdexcept>

#include "baseEntity.h"

using namespace std;

// class Payment:DB {
class Payment:public BaseEntity
{
private:
    int userId;
    double value;
    static const string filename;

public:
    Payment(int id, int userId, double value);
    // Getter functions
    int getUserId() const;
    double getValue() const;

    // Setter functions
    void setUserId(int newUserId);
    void setValue(double newValue);

    // Functions for storing and reading payments from file
    static void writeData(const vector<Payment> &payments);
    static vector<Payment> readData();
    static string savePayment( int cartId);
};
