// Example for Method Hiding Factor (MHF)
// MHF measures the ratio of hidden (private) methods to total methods

class ExampleMHF {
private:
    // Private methods (hidden) - these contribute to MHF
    void privateMethod1() {}
    void privateMethod2() {}
    void privateMethod3() {}
    
public:
    // Public methods (not hidden)
    void publicMethod1() {}
    void publicMethod2() {}
    void publicMethod3() {}
    void fefd() {}

};

// Expected MHF: 3/6 = 0.5000 (3 private methods out of 6 total)



