package com.epam.fitness_club_registration.view;

import com.epam.fitness_club_registration.entity.FitnessClubMember;
import com.epam.fitness_club_registration.logic.Validator;
import com.epam.fitness_club_registration.logic.Writer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * The main class in which the application starts.
 * <p>
 * The application simulates the registration of a user in a Fitness club.
 * <p>
 * With the help of reflection, we refer to the fields of the
 * FitnessClubMember class.
 * The user needs to enter the value of each field.
 * <p>
 * There is an annotation @ValidDataEntering above each field of the class. Each
 * annotation field is used to output messages to the user and validate them.
 * <p>
 * The user enters the requested data into the console, the program checks it.
 * If the input is incorrect, the data is requested to be re-entered until
 * the correct value will be entered.
 * <p>
 * After validation, the entered value write to a file. And so it is with
 * each value of each field of the FitnessClubMember class
 * 1. After the user completes entering data, the program reads the data from
 * the  file and enters it into the List and Map.
 * <p>
 * 2. After writing the fields entered by the user to the file, an
 * FitnessClubMember object is created and this object write to a separate
 * file Members.bin
 * <p>
 * After writing all the objects to the file, we read the objects from the
 * file and add them
 * to the List. We display the List on the screen.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException {

        // The path to the file with the values of the fields of the
        // FitnessClubMember class
        String path = "D:\\ProjectsJWD\\fitness-club-registration\\Userdata" +
                ".txt";

        // The path to the file with FitnessClubMember objects
        String pathToObjectsFile = "D:\\ProjectsJWD\\fitness-club" +
                "-registration\\Members.bin";

        // Getting access to the fields of the FitnessClubMembers class
        Class fitnessClubMemberClass = FitnessClubMember.class;
        Field[] fields = fitnessClubMemberClass.getDeclaredFields();

        Validator validator = new Validator();
        Writer writer = new Writer();

        // The method in which the user is asked to enter data, they are
        // validated and written to a file
        validator.validateAndWrite(path, fields);

        // Возвращаем List из введенных данных, которые считываем из файла
        List<String> listOfUserData = writer.writeToListFromFile(path);
        System.out.println(listOfUserData);

        // We return a List from the entered data, which we read from the file
        Map<String, String> mapOfUserData = writer.writeToMapFromFile(path, fields);
        System.out.println(mapOfUserData);

        // Creating and writing FitnessClubMember objects to a file

        // The number of objects. The number of times you will need to enter
        // the values of all fields from the console to create an object
        int numberOfObjects = 3;

        // try with resources. As usual, we write data to a file. But then we
        // use this data to create an object and write the object to the
        // Members.bin file
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(pathToObjectsFile))) {

            for (int i = 0; i < numberOfObjects; i++) {

                validator.validateAndWrite(path, fields);

                FitnessClubMember fitnessClubMember =
                        writer.createObjectWithData(path);

                oos.writeObject(fitnessClubMember);
            }

        } catch (IOException e) {

        }

        // We read from the Members file.bin all objects and write them to
        // List<FitnessClubMember>
        List<FitnessClubMember> listOfMembers =
                writer.writeObjectsToListFromFile(pathToObjectsFile, numberOfObjects);

        System.out.println(listOfMembers);
    }
}
