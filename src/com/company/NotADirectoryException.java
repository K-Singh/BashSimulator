package com.company;

import java.io.FileInputStream;

/**
 * Exception thrown when a command used on directories is used on a file
 * @author Kirat Singh ID: 112320621 E-mail: kirat.singh@stonybrook.edu
 */
public class NotADirectoryException extends Exception {

    public NotADirectoryException(String s){
        super(s);
    }
}
