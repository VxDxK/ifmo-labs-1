// Example for Method Inheritance Factor (MIF)
// MIF measures the ratio of inherited methods to total methods

class BaseClass {
public:
    void baseMethod1() {}
    void baseMethod2() {}
    void baseMethod3() {}
};

class DerivedClass : public BaseClass {
public:
    // Inherited methods from BaseClass: baseMethod1, baseMethod2, baseMethod3
    void derivedMethod1() {}
    void derivedMethod2() {}
};


// DerivedClass has 5 methods total:
// - 3 inherited (baseMethod1, baseMethod2, baseMethod3)
// - 2 defined locally (derivedMethod1, derivedMethod2)
// Expected MIF: 3/5 = 0.6000
// (?) 3 / 13


