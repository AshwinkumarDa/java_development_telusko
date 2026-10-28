package com.java.core.packageDemo.banking;  // add full path here 

public class BankAccount {
    // 1. PUBLIC: Accessible to anywhere
    public String accountHolder;

    // 2. PROTECTED: Accessible in same package + subclasses
    protected String accountType;

    // 3. DEFAULT (No keyword): Accessible ONLY in same package
    String branchCode;

    // 4. PRIVATE: Accessible ONLY inside this class
    private  double balance;

    // Constructor to initialize data
    public BankAccount(String holder, String type, String branch, double initialBalance) {
        this.accountHolder = holder;
        this.accountType = type;
        this.branchCode = branch;
        this.balance = initialBalance;
    }
}
