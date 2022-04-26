package com.example.sqlite.la;

/**
 * Interface for Token Classes
 */
public interface Type {

    /**
     * Matches the character to the regular Expression of the Token Class.
     * @param character currently analyzed Character
     * @return true if it matches the Tokens regEx, else false
     */
    boolean matches(String character);

    /**
     * Returns Token Class Tag
     * @return tag of specific Token Class
     */
    String getTag();
}
