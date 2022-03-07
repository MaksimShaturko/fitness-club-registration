package com.epam.fitness_club_registration.logic;

import com.epam.fitness_club_registration.entity.FitnessClubMember;
import com.epam.fitness_club_registration.entity.Gender;
import com.epam.fitness_club_registration.entity.NeedPersonalProgram;
import com.epam.fitness_club_registration.entity.Subscription;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * class Writer.
 * <p>
 * Used for:
 * - writing data to a file
 * - writing data from a file to a List
 * - writing data from a file to a Map
 * - recording data from a file with FitnessClubMember objects in a List
 * - creating a FitnessClubMember object
 * <p>
 * The application simulates the registration of a user in a Fitness club.
 */
public class Writer {

    /**
     * Writing data to a file
     *
     * @param validUserData The correct value of the field entered by the user
     * @param pw            PrintWriter passed from validateAndWrite (Validator)
     */
    public void writeToFile(String validUserData, PrintWriter pw) {
        pw.println(validUserData);
    }

    /**
     * Write to the List from the file
     *
     * @param path path to file Userdata.txt
     * @return List<String> with user data
     * @throws FileNotFoundException
     */
    public List<String> writeToListFromFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scFromFile = new Scanner(file);
        List<String> listOfUserData = new ArrayList<>();

        while (scFromFile.hasNextLine()) {
            String sss = scFromFile.nextLine();
            listOfUserData.add(sss);
        }
        return listOfUserData;
    }

    /**
     * Writing to Map from a file
     *
     * @param path   file path Userdata.txt
     * @param fields array Field[]
     * @return Map<String, String> with user data
     * @throws FileNotFoundException
     */
    public Map<String, String> writeToMapFromFile(String path,
                                                  Field[] fields) throws FileNotFoundException {
        File file = new File(path);
        Scanner scFromFile = new Scanner(file);
        Map<String, String> mapOfUserData = new HashMap<>();
        String key;
        String value;

        for (Field field : fields) {
            key = field.getName();
            value = scFromFile.nextLine();
            mapOfUserData.put(key, value);
        }

        return mapOfUserData;
    }

    /**
     * Creating a FitnessClubMember object using data from a file
     *
     * @param path path to the file Userdata.txt
     * @return link to the FitnessClubMember object
     * @throws FileNotFoundException
     */
    public FitnessClubMember createObjectWithData(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner sc = new Scanner(file);

        FitnessClubMember fitnessClubMember =
                new FitnessClubMember();

        fitnessClubMember.setFirstName(sc.nextLine());
        fitnessClubMember.setLastName(sc.nextLine());
        fitnessClubMember.setAge(Integer.parseInt(sc.nextLine()));
        fitnessClubMember.setWeight(Integer.parseInt(sc.nextLine()));
        fitnessClubMember.setNeedPersonalProgram(NeedPersonalProgram.valueOf(sc.nextLine()));
        fitnessClubMember.setGender(Gender.valueOf(sc.nextLine()));
        fitnessClubMember.setSubscription(Subscription.valueOf(sc.nextLine()));

        return fitnessClubMember;
    }

    /**
     * Read objects FitnessClubMember from the file and write to the List
     *
     * @param pathToObjectsFile path to the file Members.bin
     * @param numberOfObjects   the number of objects written to the file
     *                          Members.bin
     * @return List<FitnessClubMember>
     */
    public List<FitnessClubMember> writeObjectsToListFromFile(String pathToObjectsFile, int numberOfObjects) {

        List<FitnessClubMember> list = new ArrayList<>();

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(pathToObjectsFile))) {
            for (int i = 0; i < numberOfObjects; i++) {

                list.add((FitnessClubMember) ois.readObject());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

}

