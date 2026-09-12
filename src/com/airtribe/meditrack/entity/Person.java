package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.Validator;

public abstract class Person {

    private String id;
    private String name;
    private int age;

    public Person(String id, String name, int age) throws InvalidDataException {
        Validator.validateId(id);
        Validator.validateName(name);
        Validator.validateAge(age);

        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidDataException {
        Validator.validateName(name);
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws InvalidDataException {
        Validator.validateAge(age);
        this.age = age;
    }

    public abstract String getEntityType();

    public abstract String getDescription();

    public String getFullInfo() {
        return String.format(
            "ID: %s | Name: %s | Age: %d",
            id, name, age
        );
    }

    @Override
    public String toString() {
        return getFullInfo();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Person that = (Person) obj;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
