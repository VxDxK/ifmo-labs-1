// Example for Coupling Factor (COF)
// COF measures the ratio of actual couplings between classes to maximum possible couplings

class ClassA {
public:
    void methodA() {}
};

class ClassB {
public:
    void methodB(ClassA& a) {
        a.methodA();  // Coupling: ClassB -> ClassA
    }
};

class ClassC {
public:
    void methodC(ClassA& a, ClassB& b) {
        a.methodA();  // Coupling: ClassC -> ClassA
        b.methodB(a); // Coupling: ClassC -> ClassB
    }
};

class ClassD {
    // No couplings
    void methodD(ClassA& a, ClassB& b) {
        a.methodA();  // Coupling: ClassC -> ClassA
        b.methodB(a); // Coupling: ClassC -> ClassB
    }
};

// Total classes: 4
// Maximum possible couplings: 4 * (4-1) = 12
// Actual couplings: ClassB->ClassA, ClassC->ClassA, ClassC->ClassB = 3
// Expected COF: 3/12 = 0.2500



