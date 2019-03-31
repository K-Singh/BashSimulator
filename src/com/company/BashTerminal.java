package com.company;

import java.util.Scanner;

/**
 * The main driver class. This class drives the program and operates a bash shell that allows users to manipulate
 * a file tree.
 *  @author Kirat Singh ID: 112320621 E-mail: kirat.singh@stonybrook.edu
 */
public class BashTerminal {

    /**
     * The main method drives the bash terminal and accepts various commands allowing users to edit the directory
     * tree.
     */
    public static void main(String[] args) {
	    System.out.println("Starting bash terminal");
	    DirectoryTree tree = new DirectoryTree();

	    String operation = "";
	    Scanner s = new Scanner(System.in);
	    while (!operation.equalsIgnoreCase("exit")){
	        System.out.print("[esmemey@cse214]: $");
	        operation = s.nextLine();
	        String[] commands = operation.split(" ");
	        if(commands.length > 3){
	            System.out.println("ERROR: Command not found");
            }
	        switch (commands[0].toLowerCase()){
                case "touch":
                    if(commands.length > 1 && !commands[1].equalsIgnoreCase("")) {
                        try {
                            tree.makeFile(commands[1]);
                        }catch(FullDirectoryException | DuplicateNodeException | IllegalArgumentException | NotADirectoryException e){
                            System.out.println(e.getMessage());
                        }
                    }else{
                        System.out.println("ERROR: Your command is missing an argument");
                    }
                    break;
                case "ls":
                    if(commands.length > 1){
                        if(commands[1].equalsIgnoreCase("-R")){
                            tree.printDirectoryTree(tree.getRoot(), 0);
                        }else{
                            System.out.println("ERROR: Option " + commands[1] +" not found");
                        }
                    }else {
                        System.out.println(tree.listDirectory());
                    }
                    break;
                case "mkdir":
                    if(commands.length > 1 && !commands[1].equalsIgnoreCase("")) {
                        try {
                           tree.makeDirectory(commands[1]);
                        }catch(FullDirectoryException | IllegalArgumentException | DuplicateNodeException | NotADirectoryException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case "cd":
                    if(commands.length > 1 && !commands[1].equalsIgnoreCase("")) {
                       try {
                           tree.changeDirectory(commands[1]);
                       }catch(NotADirectoryException e){
                           System.out.println(e.getMessage());
                       }
                    }
                    break;
                case "pwd":
                    System.out.println(tree.presentWorkingDirectory());
                    break;
                case "find":
                    if(commands.length > 1 && !commands[1].equalsIgnoreCase("")) {
                        tree.findNode(commands[1], tree.getRoot(), "root");
                        if(!DirectoryTree.isFoundNode()){
                            System.out.println("ERROR: No such file or directory \'" + commands[1] +"\' was found!");
                        }

                        DirectoryTree.setFoundNode(false);
                    }
                    break;
                case "mv":
                    if(commands.length > 2 && !commands[1].equalsIgnoreCase("") && !commands[2].equalsIgnoreCase("")) {
                       try{
                           tree.moveNode(commands[1], commands[2]);
                       }catch(NotADirectoryException | DuplicateNodeException | FullDirectoryException e){
                           System.out.println(e.getMessage());
                       }
                    }else{
                        System.out.println("ERROR: Your command is missing an argument.");
                    }
                    break;
                case "exit":
                    System.out.println("Program terminating");
                    break;
                case "":
                    break;
                default:
                    System.out.println("ERROR: That command does not exist");
                    break;
            }
        }
    }
}
