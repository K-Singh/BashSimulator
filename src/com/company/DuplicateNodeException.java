package com.company;

import java.io.FileInputStream;
/**
 * Exception thrown when a directory is adding a child that already has a child with the same name
 * @author Kirat Singh ID: 112320621 E-mail: kirat.singh@stonybrook.edu
 */
public class DuplicateNodeException extends Exception {

    public DuplicateNodeException(String s){
        super(s);
    }
}
