package com.epam.fitness_club_registration.logic;

import com.epam.fitness_club_registration.annotations.ValidDataEntering;
import com.epam.fitness_club_registration.entity.TypeOfField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Scanner;

/**
 * class Validator
 * <p>
 * Used for validating user input data
 */
public class Validator {

    /**
     * The method passes through each Field field of the Fitness Club Member
     * class Using the annotation fields ValidDataEntering outputs the message
     * to the user about the need to enter data and requirements for them.
     * <p>
     * Accesses the validateUserData method to verify the input data,
     * gets the Field value entered correctly from this method. It writes
     * it to a file using the writeToFile method(class Writer)
     *
     * @param path   the path to the data file Userdata.txt
     * @param fields Fields[]
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    public void validateAndWrite(String path, Field[] fields) throws FileNotFoundException, ClassNotFoundException {

        File file = new File(path);
        PrintWriter pw = new PrintWriter(file);

        TypeOfField typeOfField;
        Writer writer = new Writer();

        for (Field field : fields) {
            String messageForUser =
                    field.getAnnotation(ValidDataEntering.class)
                            .messageForUser();
            typeOfField =
                    field.getAnnotation(ValidDataEntering.class).typeOfField();
            String validUserData = validateUserData(field,
                    typeOfField, messageForUser);

            writer.writeToFile(validUserData, pw);
        }
        pw.close();
    }

    /**
     * Метод проверяет валидность вводимого значения.
     * <p>
     * В зависмости от поля typeOfField аннотации ValidDataEntering
     * вызывается соответствующий метод для проверки вводимого значения:
     * <p>
     * The method checks the validity of the entered value.
     * <p>
     * Depending on the typeOfField field of the ValidDataEntering annotation
     * the appropriate method is called to check the entered value:
     * - checkString() if typeOfField.STRING
     * - checkInt() if typeOfField.INT
     * - checkENUM() if typeOfField.ENUM
     *
     * @param field          Field field
     * @param typeOfField    typeOfField.STRING, typeOfField.INT, typeOfField.ENUM
     * @param messageForUser A message to the user about the need to enter
     *                       data and requirements for them
     * @return Valid field value entered by the user
     * @throws ClassNotFoundException
     */
    public String validateUserData(Field field, TypeOfField typeOfField,
                                   String messageForUser) throws ClassNotFoundException {

        System.out.println(messageForUser);
        String validData;

        switch (typeOfField) {

            case STRING:
                validData = checkString(field, messageForUser);
                break;

            case INT:
                validData = checkInt(field, messageForUser);
                break;

            case ENUM:
                validData = checkEnum(field, messageForUser);
                break;

            default:
                return "";
        }
        return validData;
    }

    /**
     * If typeOfField.STRING.
     * <p>
     * Check the value entered by the user for compliance with the
     * annotation field requiredReqEx().
     * <p>
     * If the value matches, then we return the entered value, if not
     * matches, then we inform user about the incorrect data entry and ask
     * user to enter it again
     *
     * @param field          Field field
     * @param messageForUser A message to the user about the need to enter
     *                       data and requirements for
     * @return Valid field value entered by the user
     */
    public String checkString(Field field, String messageForUser) {
        Scanner sc = new Scanner(System.in);
        String requiredRegEx =
                field.getAnnotation(ValidDataEntering.class).requiredRegEx();
        while (true) {
            String enteredMessage = sc.nextLine();
            if (enteredMessage.matches(requiredRegEx)) {
                return enteredMessage;
            } else {
                System.out.println("You have written the wrong " +
                        "data. Please retry. " + messageForUser);
            }
        }
    }

    /**
     * <p>
     * If typeOfField.INT.
     * <p>
     * Check the value entered by the user, whether it is numeric
     * <p>
     * If the value is numeric, then check it for occurrence in
     * the range from min to max, which we take from the annotation fields.
     * We return the entered value, if everything matches.
     * <p>
     * If the value is not numeric or does not enter the range, then it is
     * reported about incorrect input and we ask you to enter the data again
     *
     * @param field          Field field
     * @param messageForUser A message to the user about the need to enter
     *                       data and requirements for them
     * @return Valid field value entered by the user
     */
    public String checkInt(Field field, String messageForUser) {
        Scanner sc = new Scanner(System.in);
        int min =
                field.getAnnotation(ValidDataEntering.class).min();
        int max =
                field.getAnnotation(ValidDataEntering.class).max();
        while (true) {
            if (sc.hasNextInt()) {
                int enteredInt = sc.nextInt();
                if (enteredInt <= max && enteredInt >= min) {
                    String enteredMessage = Integer.toString(enteredInt);
                    sc.nextLine();
                    return enteredMessage;
                } else {
                    System.out.println("You have written the wrong " +
                            "data. Please retry. " + messageForUser);
                }
            } else {
                sc.nextLine();
                System.out.println("You have written the wrong " +
                        "data. Please retry. " + messageForUser);
            }
        }
    }

    /**
     * If typeOfField.ENUM.
     * Get the Class reference to this Enum field.
     * <p>
     * The user enters the data, we remove the space at the edges, change
     * text to uppercase.
     * <p>
     * Check the entered value with each Enum constant (received with
     * using the getEnumConstants() method
     * <p>
     * If the check passes, we return the value. If not, we inform user about
     * incorrectly entered data and we ask user to enter it again
     *
     * @param field          Field field
     * @param messageForUser A message to the user about the need to enter
     *                       data and requirements for them
     * @return Valid field value entered by the user
     */
    public String checkEnum(Field field, String messageForUser) throws ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        String pathOfEntityClasses = "com.epam" +
                ".fitness_club_registration.entity.";

        String enumName =
                field.getAnnotation(ValidDataEntering.class).nameEnum();

        Class enumClass =
                Class.forName(pathOfEntityClasses + enumName);

        boolean validateIsOk = false;
        while (true) {
            String enteredMessage = sc.nextLine();
            String enteredMessageEnum =
                    enteredMessage.trim().toUpperCase();

            for (Object obj : enumClass.getEnumConstants()) {
                Enum e = (Enum) obj;
                if (enteredMessageEnum.equals(e.name())) {
                    validateIsOk = true;
                    break;
                }
            }
            if (validateIsOk) {
                return enteredMessageEnum;
            } else {
                System.out.println("You have written the wrong " +
                        "data. Please retry. " + messageForUser);
            }
        }
    }
}

