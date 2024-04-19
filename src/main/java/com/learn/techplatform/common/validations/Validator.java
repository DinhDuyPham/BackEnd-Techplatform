package com.learn.techplatform.common.validations;

import com.learn.techplatform.common.exceptions.ApplicationException;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.DateUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);

    public static <T> boolean checkNotNullAndNotEmptyList(List<T> t) {
        return t != null && !t.isEmpty();
    }

    public static <T> boolean checkNullOrEmptyList(List<T> t) {
        return t == null || t.isEmpty();
    }

    public static <T> boolean checkNullOrEmptyString(T t) {
        return t == null || t.toString().trim().isEmpty();
    }

    public static <T> boolean checkNotNullAndNotEmptyString(T t) {
        return t != null && !t.toString().trim().isEmpty();
    }

    public static <T> boolean checkNotNull(T t) {
        return t != null;
    }

    public static <T> boolean checkNull(T t) {
        return t == null;
    }

    public static <T> boolean checkEmptyString(T t) {
        return t.toString().trim().isEmpty();
    }

    public static <T> boolean checkEmptyObject(List<T> t) {
        return t.isEmpty();
    }


    /**
     * Validate string have to match with a pattern
     * @param text
     * @param pattern
     * @param restAPIStatus
     * @param restStatusMessage
     */
    public static void mustMatch(String text, String pattern, RestAPIStatus restAPIStatus, RestStatusMessage restStatusMessage) {
        Pattern accountNumPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = accountNumPattern.matcher(text.trim());
        boolean matches = matcher.matches();
        if (!matches) {
            throw new ApplicationException(restAPIStatus, restStatusMessage);
        }
    }

    /**
     * Validate a string have to in the list of strings
     * @param text
     * @param texts
     * @param restAPIStatus
     * @param restStatusMessage
     */
    public static  void mustIn(String text, String[] texts, RestAPIStatus restAPIStatus, RestStatusMessage restStatusMessage){
        if (!Arrays.asList(texts).contains(text)) {
            throw new ApplicationException(restAPIStatus, restStatusMessage);
        }
    }

    public static <T, K> void sizeNotEqual(List<T> t, List<K> k, String message) {
        if (t.size() != k.size()) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, message);
        }
    }

    public static <T> void nullAndEmptyString(T t, RestAPIStatus RestAPIStatus, String message) {

        if (t == null || t.toString().isEmpty()) {
            throw new ApplicationException(RestAPIStatus, message);
        }
    }

    /**
     * Validate an object must be null
     *
     * @param message
     * @throws ApplicationException if {@code obj} is NOT null
     */
    public static void mustNull(Object obj, RestAPIStatus RestAPIStatus, String message) {

        if (obj != null) {
            throw new ApplicationException(RestAPIStatus, message);
        }
    }
    /**
     * Validate an object must be null
     *
     * @param message
     * @throws ApplicationException if {@code obj} is NOT null
     */
    public static void mustNull(Object obj, RestAPIStatus RestAPIStatus, RestStatusMessage message) {

        if (obj != null) {
            throw new ApplicationException(RestAPIStatus, message);
        }
    }




    /**
     * Validate an object must not null
     *
     * @param obj
     * @param RestAPIStatus
     * @param message
     */
    public static void notNull(Object obj, RestAPIStatus RestAPIStatus, String message) {
        if (obj == null) {
            throw new ApplicationException(RestAPIStatus, message);
        }
    }

    public static void notNull(Object obj, RestAPIStatus RestAPIStatus, RestStatusMessage message) {
        if (obj == null) {
            throw new ApplicationException(RestAPIStatus, message);
        }
    }

    /**
     * Validate list object not null & not empty
     *
     * @param obj
     * @param message
     */

    public static void notNullAndNotEmpty(List<?> obj, RestAPIStatus RestAPIStatus, String message) {

        if (obj == null || obj.isEmpty()) {
            throw new ApplicationException(RestAPIStatus, message);
        }
    }

    /**
     * Validate object not null & not empty
     *
     * @param obj
     * @param message
     */

    public static void notNullAndNotEmpty(Object obj, RestAPIStatus RestAPIStatus, String message) {

        if (obj == null || "".equals(obj)) {
            throw new ApplicationException(RestAPIStatus, message);
        }
    }

    /**
     * Validate object not null & not empty
     *
     * @param obj
     * @param RestStatusMessage
     */

    public static void notNullAndNotEmpty(Object obj, RestAPIStatus RestAPIStatus, RestStatusMessage RestStatusMessage) {

        if (obj == null || "".equals(obj)) {
            throw new ApplicationException(RestAPIStatus, RestStatusMessage);
        }
    }


    /**
     * Validate list object must null & must empty
     *
     * @param obj
     * @param apiStatus
     * @param message
     */
    public static void mustNullAndMustEmpty(List<?> obj, RestAPIStatus apiStatus, String message) {
        if (obj != null && !obj.isEmpty()) {
            throw new ApplicationException(apiStatus, message);
        }
    }

    /**
     * Validate a string must equal with another string
     *
     * @param str1
     * @param str2
     * @param RestAPIStatus
     * @param message
     */
    public static void mustEquals(String str1, String str2, RestAPIStatus RestAPIStatus, String message) {
        if (!str1.equals(str2)) {
            throw new ApplicationException(RestAPIStatus, message);

        }
    }

    /**
     * Validate a string must equal with another string
     *
     * @param str1
     * @param str2
     * @param RestAPIStatus
     * @param message
     */
    public static void mustEquals(String str1, String str2, RestAPIStatus RestAPIStatus, RestStatusMessage message) {
        if (!str1.equals(str2)) {
            throw new ApplicationException(RestAPIStatus, message);

        }
    }


    /**
     * Validate numbers have to greater or equal a given number
     *
     * @param baseNumber
     * @param restAPIStatus
     * @param message
     * @param numbers
     */
    public static void mustGreaterThanOrEqual(int baseNumber, RestAPIStatus restAPIStatus, String message, int... numbers) {
        for (int i : numbers) {
            if (i < baseNumber) {
                throw new ApplicationException(restAPIStatus, message);
            }
        }
    }

    /**
     * Validate numbers have to greater a given number
     *
     * @param baseNumber
     * @param restAPIStatus
     * @param message
     * @param numbers
     */
    public static void mustGreaterThan(int baseNumber, RestAPIStatus restAPIStatus, String message, int... numbers) {
        for (int i : numbers) {
            if (i <= baseNumber) {
                throw new ApplicationException(restAPIStatus, message);
            }
        }
    }

    /**
     * Validate numbers have to less than a given number
     *
     * @param baseNumber
     * @param restAPIStatus
     * @param message
     * @param numbers
     */
    public static void mustLessThan(int baseNumber, RestAPIStatus restAPIStatus, String message, int... numbers) {


        for (int i : numbers) {
            if (i >= baseNumber) {
                throw new ApplicationException(restAPIStatus, message);
            }
        }
    }

    /**
     * Validate numbers have to equal a given number
     *
     * @param baseNumber
     * @param restAPIStatus
     * @param message
     * @param numbers
     */
    public static void mustEqual(int baseNumber, RestAPIStatus restAPIStatus, String message, int... numbers) {
        for (int i : numbers) {
            if (i > baseNumber || i < baseNumber) {
                throw new ApplicationException(restAPIStatus, message);
            }
        }
    }

    public static void mustTrue(boolean bool,  RestAPIStatus restAPIStatus, RestStatusMessage restStatusMessage)  {
        if(!bool) {
            throw new ApplicationException(restAPIStatus, restStatusMessage);
        }
    }

    // format email
    public static void validateEmail(String emailAddress) {
        boolean isEmailFormat = isEmailFormat(emailAddress);
        if (!isEmailFormat) {
            throw new ApplicationException(RestAPIStatus.ERR_INVALID_EMAIL_FORMAT, RestStatusMessage.INVALID_EMAIL_FORMAT);
        }
    }

    public static boolean isEmailFormat(String valueToValidate) {
        // Regex

        String regexExpression = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
        Pattern regexPattern = Pattern.compile(regexExpression);
        if (valueToValidate != null) {
            if (valueToValidate.indexOf("@") <= 0) {
                return false;
            }
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(valueToValidate);
            return matcher.matches();
        } else { // The case of empty Regex expression must be accepted
            Matcher matcher = regexPattern.matcher("");
            return matcher.matches();
        }
    }

    public static boolean isPhoneNumber (String phone) {
        phone = phone.replaceAll(" ", "").replace("-", "")
                .replace("+", "")
                .replace("(", "")
                .replace(")", "");
        String regex = "\\d{1,32}";
        return phone.matches(regex);
    }

    //validate just only numbers
    public static void validatePhoneNumber(String phone) {
        phone = phone.replaceAll(" ", "").replace("-", "")
                .replace("+", "")
                .replace("(", "")
                .replace(")", "");
        boolean isPhone = isPhoneNumber(phone);
        if (!isPhone) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "Invalid Phone Number format");
        }
    }

    public static void validateNumbersOnly(String input) {
        if (!input.matches("\\d+")) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "ZipCode is valid format !");
        }
    }

    public static void validatePinCard(String cardPin) {
        Pattern pattern = Pattern.compile("^(?!([0-9])\\1+$)(?!123|234|345|456|567|678|789)\\d+$");
        Matcher matcher = pattern.matcher(cardPin);

        boolean matchFound = matcher.find();
        if (!matchFound) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "Cannot be all the same digits or all sequential digits.");
        }
    }


    public static void validateDateDifference(String dateFrom, String dateTo, int dateDifference) {
        Date dateFromCovert = DateUtil.addDayToDate(DateUtil.convertStringToDateAndFormatDate(dateFrom), dateDifference);
        Date dateToCovert = DateUtil.convertStringToDateAndFormatDate(dateTo);
        if (dateToCovert.after(dateFromCovert)) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "Should not be greater than current date");
        }
    }

//    public static void checkFileExtensionType(MultipartFile multipartFile) {
//        String extension = AppUtil.getFileExtension(multipartFile);
//        String[] extensions = {"png", "pjp", "jpg", "jpeg", "jfif", "pjpeg", "ico", "pdf", "docx", "doc"};
//        if (!Arrays.asList(extensions).contains(extension)) {
//            throw new ApplicationException(RestAPIStatus.INVALID_FILE);
//        }
//    }

    public static boolean isNullAndEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean isExpiryTime(long time) {
        return DateUtil.convertToUTC(new Date()).getTime() >= time;
    }

    public static void validateExpiryTime(long time, RestAPIStatus apiStatus, RestStatusMessage statusMessage) {
        boolean isExpiry = isExpiryTime(time);
        if(isExpiry) {
            throw new ApplicationException(apiStatus, statusMessage);
        }
    }

}
