// Example for Attribute Inheritance Factor (AIF)
// AIF measures the ratio of inherited attributes to total attributes

class BaseClass {
public:
    int baseAttr1;
    int baseAttr2;
    double baseAttr3;
};

class DerivedClass : public BaseClass {
public:
    // Inherited attributes from BaseClass: baseAttr1, baseAttr2, baseAttr3
    int derivedAttr1;
    int derivedAttr2;
};

// DerivedClass has 5 attributes total:
// - 3 inherited (baseAttr1, baseAttr2, baseAttr3)
// - 2 defined locally (derivedAttr1, derivedAttr2)
// Expected AIF: 3/5 = 0.6000



