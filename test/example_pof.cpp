// Example for Polymorphism Factor (POF)
// POF measures the ratio of polymorphic (virtual) methods to total methods

class BaseClass {
public:
    // Virtual methods (polymorphic)
    virtual void virtualMethod1() {}
    virtual void virtualMethod2() {}
    
    // Non-virtual methods
    void nonVirtualMethod() {}
};

class DerivedClass1 : public BaseClass {
public:
    // Override virtual methods
    void virtualMethod1() override {}
    void virtualMethod2() override {}
    
    void additionalMethod() {}
};

class DerivedClass2 : public BaseClass {
public:
    // Override virtual methods
    void virtualMethod1() override {}
    
    void anotherMethod() {}
};

class DerivedClass3 : public BaseClass {
public:
    // Override virtual methods
    void virtualMethod1() override {}

    void anotherMethod() {}
};


// Total methods across all classes: 7
// Polymorphic methods (virtual and overridden): 2 (virtualMethod1, virtualMethod2)
// Expected POF: 2/7 ≈ 0.2857

//  6

// (4) / (9)

