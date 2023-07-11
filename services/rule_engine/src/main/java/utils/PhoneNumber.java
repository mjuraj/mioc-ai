package utils;

public class PhoneNumber {

    /**
     * Generated from ChatGPT.
     * This function first removes all non-numeric characters except for the plus sign from the input string. 
     * It then checks if the resulting string has a length between 7 and 15 digits, as Croatian phone numbers fall within this range.
        If the input string doesn't start with a plus sign, the function adds the Croatian country code (+385) to the beginning of the string. 
        It then checks if the resulting string starts with the correct country code (+385), and if not, returns null.
        The function then extracts the area code and phone number from the cleaned input string. 
        The area code must be between 10 and 99, or 1 followed by a digit between 0 and 5. 
        The phone number must be between 5 and 9 digits long.
     * @param input Raw phone number input as a String
     * @return Returns the formatted phone number as a String or null if any of the required checks fail.
     */
    public static String convertToPhoneNumber(String input) {
        String cleanedInput = input.replaceAll("[^0-9+]", ""); // remove all non-numeric characters except the plus sign
        if (cleanedInput.length() < 7 || cleanedInput.length() > 15) { // Croatian phone numbers are between 7 and 15 digits long
            return null;
        }
        if (!cleanedInput.startsWith("+")) { // add country code if missing
            cleanedInput = "+385" + cleanedInput;
        }
        if (!cleanedInput.startsWith("+385")) { // invalid country code
            return null;
        }
        String areaCode = cleanedInput.substring(4, 6); // extract area code
        if (!areaCode.matches("^(1[0-5]|[2-9][0-9])$")) { // area code must be between 10 and 99, or 1 followed by a digit between 0 and 5
            return null;
        }
        String phoneNumber = cleanedInput.substring(6); // extract phone number
        if (phoneNumber.length() < 5 || phoneNumber.length() > 9) { // phone number must be between 5 and 9 digits long
            return null;
        }
        return cleanedInput; // return formatted phone number
    }
}
