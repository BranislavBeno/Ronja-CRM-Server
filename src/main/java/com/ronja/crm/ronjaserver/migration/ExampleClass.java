package com.ronja.crm.ronjaserver.migration;

import java.util.stream.Stream;

public class ExampleClass {

    void main() {
        Stream<Animal> animals = Stream.of(new Dog(), new Cat());
        animals.forEach(ExampleClass::identify);
    }

    private interface Animal {}

    private static class Dog implements Animal {
        String getBreed() {
            return "shepherd";
        }
    }

    private static class Cat implements Animal {
        String getRace() {
            return "siamese";
        }
    }

    private static void identify(Animal animal) {
        String identity;
        if (animal instanceof Dog dog) {
            identity = dog.getBreed();
        } else if (animal instanceof Cat cat) {
            identity = cat.getRace();
        } else {
            identity = "unknown";
        }

      IO.println(identity);
    }
}
