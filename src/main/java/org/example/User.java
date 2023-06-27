package org.example;

public class User {

    private String name;
    private int age;

    private User() {

    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    private User(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public static User createUser(String name, int age) {
        return new User(name, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
