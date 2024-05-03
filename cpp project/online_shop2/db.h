#include <iostream>
#include <vector>

using namespace std;

class DB {
protected:
    // Constructor with filename parameter
    DB();

    // Virtual destructor
    virtual ~DB() = default;

    // virtual void saveNewEntry() = 0;
};