// Comprehensive example demonstrating all MOOD metrics

// Forward declaration
class OtherClass;

class Base {
private:
    int basePrivateAttr;
    
public:
    int basePublicAttr;
    virtual void virtualMethod() {}
    void baseMethod() {}
};

class Derived : public Base {
private:
    void privateMethod() {}
    int derivedPrivateAttr;
    
public:
    int derivedPublicAttr;
    void virtualMethod() override {}
    void derivedMethod() {}
    
    void useOtherClass(OtherClass& other) {
        other.method();  // Coupling
    }
};

class OtherClass {
public:
    void method() {}
    void useBase(Base& b) {
        b.baseMethod();  // Coupling
    }
};

// This example demonstrates all metrics:
// - MHF: Ratio of private methods
// - AHF: Ratio of private attributes
// - MIF: Ratio of inherited methods
// - AIF: Ratio of inherited attributes
// - POF: Ratio of polymorphic methods
// - COF: Ratio of class couplings

