// package com.java.core.newFeatures;

/// 1. A sealed class or interface restricts which classes or interfaces are allowed to extend or implement it.
///     You decide who is allowed to inherit your class or implement your interface.
/// 
/// 2. A non-sealed class or interface removes the inheritance restriction imposed by its sealed parent.
///     You allow anyone to inherit your class or implement your interface.
/// 
/// cannot inherit from final class ClassName
/// sealed class - we restrict to inherit from allowed classes and 
// those allowed classes should restrict by using final, sealed, and non-sealed. and 
// they can also be extended by mentioned sealed classes.
/// permists should be at last after extends, implements

//below we allowing to class B and C to inherit from A.
// we need to prefix final, sealed, non-sealed keyword before class B and class C.
// For Interface we use only sealed.

sealed class A extends Thread implements Cloneable permits B, C {

}

// 
final class B extends A {

}

final class C extends A {

}

class D {

}


// Interface / protocols
sealed interface Speakable permits Singable {

}

non-sealed interface Singable extends Speakable {

}


public class SealedClassDemo {

    public static void main(String[] args) {
        
    }

}
