package com.ronja.crm.ronjaserver.migration;

import java.util.stream.Stream;

public class ExampleClass {

    public static void main(String[] args) {
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
        if (animal instanceof Dog) {
            Dog dog = (Dog) animal;
            identity = dog.getBreed();
        } else if (animal instanceof Cat) {
            Cat cat = (Cat) animal;
            identity = cat.getRace();
        } else {
            identity = "unknown";
        }

        System.out.println(identity);
    }
}
