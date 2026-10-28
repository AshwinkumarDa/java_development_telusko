package com.java.core.packageDemo.validation;  // add full path here

import com.java.core.packageDemo.banking.BankAccount; //add full path here

/**
 * 
 * MainTester
# 1. Move back to your root source directory
    cd /Users/ashwinkumard/Documents/code/java/java_development_telusko

# 2. Compile using the wildcard shortcut
    // javac com/java/core/packageDemo/*//*.java //remove extra slash / before *.java.

# 3. Run
    // java com/java/core/packageDemo/validation/MainTester

**/

public class MainTester {
    public static void main(String[] args) {
        // Creating an instance of the bank account
        BankAccount myAccount = new BankAccount("Alice", "Savings", "BR-101", 5000.00);

        // --- TESTING ACCESSIBILITY ---

        // ✅ This works perfectly! (Public)
        System.out.println("Holder: " + myAccount.accountHolder); 

        // ❌ COMPILER ERROR: accountType has protected access in com.banking.BankAccount
       //  System.out.println("Type: " + myAccount.accountType); 

        // ❌ COMPILER ERROR: branchCode is not public in com.banking.BankAccount; cannot be accessed from outside package
        // System.out.println("Branch: " + myAccount.branchCode); 

        // ❌ COMPILER ERROR: balance has private access in com.banking.BankAccount
       //  System.out.println("Balance: " + myAccount.balance); 
    }
}
