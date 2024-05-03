#include <vector>
#include <string>

#include "product.h"

//#include "db.h"
using namespace std;
//class ProductInCart: DB{
class ProductInCart: public BaseEntity{
private:
    int cartId;
    int productId;
    int count;
    Product product;
    static const std::string filename;

public:
    ProductInCart(int id, int cartId, int productId, int count);
    // Getter functions

    int getCartId() const;

    int getProductId() const;

    int getCount() const;
    Product getProduct() const;

    // Setter functions
    void setCartId(int newCartId);

    void setProductId(int newProductId);

    void setCount(int newCount);
    void setProduct(Product p);

    // Functions for storing and reading product items in a cart from file
    //static void writeData(const std::vector<ProductInCart>& items);
    //static vector<ProductInCart> readData();

    static void writeData(const vector<ProductInCart>& items);
    static vector<ProductInCart> readData();
};