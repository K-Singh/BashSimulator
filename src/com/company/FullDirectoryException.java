package com.company;

import java.io.FileInputStream;
/**
 * Exception thrown when a directory is full but a command is attempting to add a child.
 * @author Kirat Singh ID: 112320621 E-mail: kirat.singh@stonybrook.edu
 */
public class FullDirectoryException extends Exception {

    public FullDirectoryException(String s){
        super(s);
    }
}
