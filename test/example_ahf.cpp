// Example for Attribute Hiding Factor (AHF)
// AHF measures the ratio of hidden (private) attributes to total attributes

class ExampleAHF {
private:
    // Private attributes (hidden) - these contribute to AHF
    int privateAttr1;
    int privateAttr2;
    double privateAttr3;
    char privateAttr4;
    int ds;

public:
    // Public attributes (not hidden)
    int publicAttr1;
    int publicAttr2;
    int publicAttr3;
};

class F {
public:
    int z;
    int sff;
    double sfaf;
private:
    int a;
};

// Expected AHF: 4/7 = 0.5714 (4 private attributes out of 7 total)



