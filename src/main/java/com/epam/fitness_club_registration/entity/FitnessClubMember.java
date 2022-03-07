package com.epam.fitness_club_registration.entity;

import com.epam.fitness_club_registration.annotations.ValidDataEntering;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * class FitnessClubMember
 *
 * class contains data fields that the user needs to fill in in order to
 * register in the fitness club and become its member:
 * - String firstName
 * - String lastName
 * - int age
 * - int weight
 * - NeedPersonalProgram needPersonalProgram
 * - Gender gender
 * - Subscription subscription
 *
 * Each field of class is marked with an annotation @ValidDataEntering with
 * values of the annotation's fields that are used in the application work
 *
 */
public class FitnessClubMember implements Serializable {
    @ValidDataEntering(messageForUser = "Enter your first name (Not " +
            "more than 30 symbols. Symbols to " +
            "use: \"Aa-Zz\" and \"-\") >>>", requiredRegEx = "[a-zA-Z\\-]{1," +
            "30}", typeOfField = TypeOfField.STRING)
    private String firstName;

    @ValidDataEntering(messageForUser = "Enter your last name (Not " +
            "more than 30 symbols. Symbols to " +
            "use: \"Aa-Zz\" and \"-\") >>>", requiredRegEx = "[a-zA-Z\\-]{1," +
            "30}", typeOfField = TypeOfField.STRING)
    private String lastName;

    @ValidDataEntering(messageForUser = "Enter your age (Use only digits " +
            "from 10 to 100) >>>", requiredRegEx = "[1-9]\\d[0]?",
            typeOfField = TypeOfField.INT, min = 10, max = 100)
    private int age;

    @ValidDataEntering(messageForUser =
            "Enter your weight (Use only digits from 16 to 200) >>>",
            requiredRegEx = "", typeOfField = TypeOfField.INT, min = 16, max
            = 200)
    private int weight;

    @ValidDataEntering(messageForUser =
            "Do you want to have an own program of training? Enter only " +
                    "\"Yes\" or \"No\" >>>", requiredRegEx = "",
            typeOfField = TypeOfField.ENUM, nameEnum = "NeedPersonalProgram")
    private NeedPersonalProgram needPersonalProgram;

    @ValidDataEntering(messageForUser =
            "What is your gender? Enter only " +
                    "\"Male\" or \"Female\" >>>", requiredRegEx = "",
            typeOfField = TypeOfField.ENUM, nameEnum = "Gender")
    private Gender gender;

    @ValidDataEntering(messageForUser =
            "What is subscription do you want? Enter only \"Month\" or " +
            "\"Quarter\", or \"Annual\" >>>",
            requiredRegEx = "", typeOfField = TypeOfField.ENUM,
            nameEnum = "Subscription")
    private Subscription subscription;

    /**
     * Constructor initializes variables
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param weight
     * @param needPersonalProgram YES or NO
     * @param gender MALE or FEMALE
     * @param subscription MONTH or QUARTER or ANNUAL
     */
    public FitnessClubMember(String firstName, String lastName, int age,
                             int weight, NeedPersonalProgram needPersonalProgram,
                             Gender gender, Subscription subscription) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.weight = weight;
        this.needPersonalProgram = needPersonalProgram;
        this.gender = gender;
        this.subscription = subscription;
    }

    public FitnessClubMember(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public NeedPersonalProgram NeedPersonalProgram() {
        return needPersonalProgram;
    }

    public void setNeedPersonalProgram(NeedPersonalProgram needPersonalProgram) {
        this.needPersonalProgram = needPersonalProgram;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FitnessClubMember that = (FitnessClubMember) o;

        return age == that.age && weight == that.weight
                && needPersonalProgram == that.needPersonalProgram
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && gender == that.gender && subscription == that.subscription;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, weight,
                            needPersonalProgram, gender, subscription);
    }

    @Override
    public String toString() {
        return "FitnessClubMember{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", needPersonalProgram=" + needPersonalProgram +
                ", gender=" + gender +
                ", subscription=" + subscription +
                '}';
    }
}
