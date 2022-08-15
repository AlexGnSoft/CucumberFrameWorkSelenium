package utils;

import java.util.Random;

public class RandomDataGenerator {
    /**
     * Method is used to generate different random Strings, where
     * strLength - is a string length
     */
    public String randomString(int strLength){
        String randomString = "";
        if (strLength < 1) throw new IllegalArgumentException();
        //create a string for all characters
        String AlphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";

        //create a random string builder
        StringBuilder sb = new StringBuilder(strLength);
        //create an object of random string
        Random random = new Random();
        int index;

        for (int i = 0; i < strLength; i++) {
            index = random.nextInt(AlphaNumericStr.length());
            char rndChar = AlphaNumericStr.charAt(index);
            sb.append(rndChar);
        }
        return sb.toString();
    }
}
