/*
 * Copyright (C) 2023 Fauzan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package my.com.vic3.spring.assessment1.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Fauzan
 */
public class JSONUtil {

    /**
     * Method to assign new value in db entity from json
     *
     * @param newValue
     * @param oldValue
     * @return null if new value is empty, oldValue if newValue is nul, newValue
     * if not empty
     */
    public static String nullOrString(String newValue, String oldValue) {
        if (newValue != null) {
            if (newValue.isEmpty()) {
                return null;
            } else {
                return newValue;
            }
        } else {
            return oldValue;
        }
    }

    /**
     * Method to assign new value in db entity from json
     *
     * @param newValue
     * @param oldValue
     * @return null if new value is empty, oldValue if newValue is null,
     * newValue if not empty
     * @throws Exception
     */
    public static BigInteger nullOrBigInteger(String newValue, BigInteger oldValue) throws Exception {
        if (newValue != null) {
            if (newValue.isEmpty()) {
                return null;
            } else {
                BigInteger newInteger = new BigInteger(newValue);
                return newInteger;
            }
        } else {
            return oldValue;
        }
    }

    /**
     * Method to assign new value in db entity from json
     *
     * @param newValue
     * @param oldValue
     * @return null if new value is empty, oldValue if newValue is null,
     * newValue if not empty
     * @throws Exception
     */
    public static BigDecimal nullOrBigDecimal(String newValue, BigDecimal oldValue) throws Exception {
        if (newValue != null) {
            if (newValue.isEmpty()) {
                return null;
            } else {
                BigDecimal newDecimal = new BigDecimal(newValue);
                return newDecimal;
            }
        } else {
            return oldValue;
        }
    }

    /**
     * Method to assign new value in db entity from json
     *
     * @param newValue
     * @param oldValue
     * @return null if new value is empty, oldValue if newValue is null,
     * newValue if not empty
     * @throws Exception
     */
    public static Long nullOrLong(String newValue, Long oldValue) throws Exception {
        if (newValue != null) {
            if (newValue.isEmpty()) {
                return null;
            } else {
                Long newLong = new Long(newValue);
                return newLong;
            }
        } else {
            return oldValue;
        }
    }

    /**
     * Method to assign new value in db entity from json
     *
     * @param newValue
     * @param oldValue
     * @return null if new value is empty, oldValue if newValue is null,
     * newValue if not empty
     * @throws Exception
     */
    public static Date nullOrDate(String newValue, Date oldValue) throws Exception {
        if (newValue != null) {
            if (newValue.isEmpty()) {
                return null;
            } else {
                Date newDate = DateTimeUtil.getDateFromString(newValue, APIConstant.DATE_PATTERN);
                return newDate;
            }
        } else {
            return oldValue;
        }
    }

    /**
     * Method to assign new value in db entity from json
     *
     * @param newValue
     * @param oldValue
     * @return null if new value is empty, oldValue if newValue is null,
     * newValue if not empty
     * @throws Exception
     */
    public static Date nullOrDateTime(String newValue, Date oldValue) throws Exception {
        if (newValue != null) {
            if (newValue.isEmpty()) {
                return null;
            } else {
                Date newDate = DateTimeUtil.getDateFromString(newValue, APIConstant.DATE_TIME_PATTERN);
                return newDate;
            }
        } else {
            return oldValue;
        }
    }

    /**
     * Convert date to string
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        if (date != null) {
            return DateTimeUtil.getTimeString(date, APIConstant.DATE_PATTERN);
        } else {
            return null;
        }

    }

    /**
     * Convert dateTime to string
     *
     * @param date
     * @return
     */
    public static String dateTimeToString(Date date) {
        if (date != null) {
            return DateTimeUtil.getTimeString(date, APIConstant.DATE_TIME_PATTERN);
        } else {
            return null;
        }

    }

    /**
     * Convert db key field SNAKE_CASE to JSON key camelCase
     *
     * @param key
     * @return
     */
    public static String fieldToJSONKey(String key) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
    }

    /**
     * Convert JSON key camelCase to db key field SNAKE_CASE
     *
     * @param key
     * @return
     */
    public static String jSONToFieldKey(String key) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, key);
    }

    /**
     * Convert null to emptyString
     *
     * @param string
     * @return
     */
    public static String nullToEmpty(String string) {
        return string == null ? "" : string;
    }

    /**
     * Return String representation of an object or null if object is null. To
     * prevent null pointer exeception if object is null
     *
     * @param object
     * @return String or null
     */
    public static String toString(Object object) {
        return object != null ? object.toString() : null;
    }

    /**
     * Return String representation of an object or null if object is null. To
     * prevent null pointer exeception if object is null
     *
     * @param object
     * @return String or null
     */
    public static String stringValueOf(Object object) {

        return object != null ? String.valueOf(object) : null;
    }

    /**
     * Convert json object to string
     *
     * @param jsonObject
     * @return String or null
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    public static String jsonToString(Object jsonObject) throws JsonProcessingException {

        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

    }

}

