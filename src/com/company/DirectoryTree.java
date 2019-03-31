package com.company;

/**
 * The DirectoryTree class represents a file tree made of DirectoryNodes. The file tree has various functions inside it
 * that can be used to add, move, find and print various nodes in the tree.
 *  @author Kirat Singh ID: 112320621 E-mail: kirat.singh@stonybrook.edu
 */
public class DirectoryTree {

    private DirectoryNode root;
    private DirectoryNode cursor;
    private String dirPath;

    private static boolean foundNode = false;

    /**
     * Constructs the DirectoryTree by creating and setting the root and cursor
     */
    public DirectoryTree(){
        DirectoryNode newNode = new DirectoryNode(false, "root");
        root = newNode;
        cursor = newNode;
        dirPath = "root";
    }

    /**
     * Returns the cursor to the root of the tree
     */
    public void resetCursor(){
        cursor = root;
    }

    /**
     * Changes the current directory of the cursor to the one at the given path. '..' Moves the cursor up one directory.
     * Absolute paths move the directory to the path given, while relative paths start at the cursor and move it along
     * there.
     * @param path
     *      The string representing the path that the cursor goes along.
     * @throws NotADirectoryException
     *      Thrown if the cursor attempts to move into a file rather than a directory
     */
    public void changeDirectory(String path) throws NotADirectoryException {
        String[] paths;
        DirectoryNode traverser = cursor;
        String tempPath = "";
       // System.out.println(path);
      //  System.out.println(cursor.getName());
        //if (cursor.isFile())
       //     throw new NotADirectoryException("Files cannot have children!");
        if(path.equalsIgnoreCase("/")){
            resetCursor();
            dirPath = "root";
        } else if (path.equalsIgnoreCase("..")) {
            if(cursor == root){
                System.out.println("ERROR: Already at root directory.");
                return;
            }
           // System.out.println("mememememe");
          //  System.out.println(dirPath);
            String newPath = dirPath.substring(0, dirPath.indexOf(cursor.getName()));
        //    System.out.println(newPath);

            changeDirectory(newPath);
        } else if (path.contains("/")) {
            paths = path.split("/");
            int start = 0;
            if (paths[0].equalsIgnoreCase("root") || paths[1].equalsIgnoreCase("root")) {
                traverser = root;
                tempPath = "root";
                start = 1;
            }
            if(paths[0].equalsIgnoreCase("") && paths.length > 1){
                start++;
            }
            for (int i = start; i < paths.length; i++) {
                //System.out.println("Current name " + i + ":" + paths[i]);
                if (traverser != null) {
                    DirectoryNode tempNode = traverser.findChildByName(paths[i]);
                    if(tempNode != null) {

                        if(tempNode.isFile()){
                            throw new NotADirectoryException("ERROR: Cannot change directory into file");
                        }
                        traverser = tempNode;
                        tempPath += "/" + traverser.getName();
                    }else {
                        System.out.println("ERROR: No such directory named \'"+path+"\'");
                    }
                //    System.out.println(tempPath);
                } else {

                //    System.out.println(tempPath);
                //    System.out.println(path);
                    return;
                }
            }if(traverser != null) {
                cursor = traverser;
            }else{
                System.out.println("ERROR: No such directory named \'"+path+"\'");
            }
            if(tempPath.contains("root"))
                dirPath = tempPath;
            else
                dirPath += tempPath;
        } else {
            DirectoryNode nxtDir = cursor.findChildByName(path);
            if (nxtDir != null) {
                if (nxtDir.isFile()) {
                    throw new NotADirectoryException("ERROR: Cannot change directory into file.");
                }
                cursor = nxtDir;
                dirPath += "/" + cursor.getName();
            } else {
                System.out.println("ERROR: No such directory named \'"+path+"\'");
                //  System.out.println(tempPath);
                //  System.out.println(path + "why");
                return;
            }
        }
        //System.out.println(dirPath);
    }

    /**
     * Makes a directory with the given name
     * @param name
     *      The name of the directory
     * @throws IllegalArgumentException
     *      Thrown if the name given is invalid
     * @throws FullDirectoryException
     *      Thrown if the directory is being added to an already full directory
     * @throws NotADirectoryException
     *      Thrown attempting to add a directory to file
     * @throws DuplicateNodeException
     *      Thrown when a node with the given name already exists
     */
    public void makeDirectory(String name) throws IllegalArgumentException, FullDirectoryException, NotADirectoryException, DuplicateNodeException{
        if(name.contains(" ") || name.contains("/")){
            throw new IllegalArgumentException("ERROR: That name is invalid!");
        }
        DirectoryNode newNode = new DirectoryNode(false, name);
        cursor.addChild(newNode);
    }

    /**
     * Makes a file with the given name
     * @param name
     *      The name of the file
     * @throws IllegalArgumentException
     *      Thrown if the name given is invalid
     * @throws FullDirectoryException
     *      Thrown if the file is being added to an already full directory
     * @throws NotADirectoryException
     *      Thrown attempting to add a file to file
     * @throws DuplicateNodeException
     *      Thrown when a node with the given name already exists
     */
    public void makeFile(String name) throws IllegalArgumentException, FullDirectoryException, NotADirectoryException, DuplicateNodeException{
        if(name.contains(" ") || name.contains("/")){
            throw new IllegalArgumentException("ERROR: That name is invalid!");
        }
        DirectoryNode newNode = new DirectoryNode(true, name);
        cursor.addChild(newNode);
    }

    /**
     * @return
     *      Returns the absolute path to the node that the cursor points to
     */
    public String presentWorkingDirectory(){
        return dirPath;
    }

    /**
     * Recursively finds a node with the given name.
     * @param name
     *      The name of the node being searched for
     * @param start
     *      The starting point for the recursive calls
     * @param path
     *      The path of the starting point
     */
    public void findNode(String name, DirectoryNode start, String path){
        for(DirectoryNode child : start.getChildren()){
            if(child.getName().equalsIgnoreCase(name)){
                System.out.println(path + "/" + child.getName());
                foundNode = true;
            }else if(!child.getChildren().isEmpty()){
                findNode(name, child, path+"/"+child.getName());
            }
        }
    }

    /**
     * Lists the nodes in the current directory
     * @return
     *      The nodes in the current directory
     */
    public String listDirectory(){
        String s = "";
        if(cursor.getChildren() != null) {
            if (cursor.getChildren().size() > 0) {
                for (DirectoryNode n : cursor.getChildren()) {
                    s += " " + n.getName();
                }
            }
        }
        return s.trim();
    }

    /**
     * Uses a preorder traversal to list all nodes in the tree recursively
     * @param start
     *      The start of the recursive calls
     * @param indents
     *      The number of indents to print the current node in the tree
     */
    public void printDirectoryTree(DirectoryNode start, int indents){
        String name;
        int spaces = 2*indents + 1;
        if(start.isFile()) {
            name = String.format("%-" + spaces + "s%-3s","", " - " + start.getName());
        }else{
            name = String.format("%-" + spaces + "s%-3s","", "|- " + start.getName());
        }
        if(start == cursor){
            //name += "  <-----";
        }
        System.out.println(name);

        for(DirectoryNode child : start.getChildren()){
            printDirectoryTree(child, indents+1);
        }
    }

    /**
     * Moves a node from one absolute path to another
     * @param pathOne
     *      The path that the node to be moved is in
     * @param pathTwo
     *      The path that the node will be in after the method completes successfully
     * @throws NotADirectoryException
     *      Thrown if the second path is not a directory
     * @throws FullDirectoryException
     *      Thrown if the second path is full
     * @throws DuplicateNodeException
     *      Thrown if the second path already contains a node with that name.
     */
    public void moveNode(String pathOne, String pathTwo) throws NotADirectoryException, FullDirectoryException, DuplicateNodeException{
        DirectoryNode traverserOne = root;
        DirectoryNode traverserTwo = root;
        DirectoryNode tempCursor = root;
        DirectoryNode parent = root;
        String[] paths;


        if(pathOne == null || (!pathOne.equalsIgnoreCase("root") && !pathOne.contains("/"))){
            throw new NotADirectoryException("ERROR: That path is invalid!");
        }

        if(pathTwo == null || (!pathTwo.equalsIgnoreCase("root") && !pathTwo.contains("/"))){
            throw new NotADirectoryException("ERROR: That path is invalid!");
        }

        paths = pathOne.split("/");
        if(paths.length <= 0 || paths[0].equalsIgnoreCase("")){
            throw new NotADirectoryException("ERROR: That path is invalid!");
        }
        if(paths.length > 1) {
            for (int i = 1; i < paths.length; i++) {
                if (traverserOne != null) {
                    for (DirectoryNode child : traverserOne.getChildren()) {
                        if (child.getName().equalsIgnoreCase(paths[i])) {
                            tempCursor = child;
                            if (i == paths.length - 2) {
                                parent = child;
                            }
                            break;
                        }
                    }
                    traverserOne = tempCursor;
                } else {
                    System.out.println("ERROR: The first given path could not be found!");
                    return;
                }
            }
        }
        if(traverserOne.getName().equalsIgnoreCase(paths[paths.length-1])) {

            paths = pathTwo.split("/");
            if(paths.length <= 0 || paths[0].equalsIgnoreCase("")){
                throw new NotADirectoryException("ERROR: That path is invalid!");
            }
            if(paths.length > 1) {

                for (int i = 1; i < paths.length; i++) {
                    if (traverserTwo != null) {
                        for (DirectoryNode child : traverserTwo.getChildren()) {
                            if (child.getName().equalsIgnoreCase(paths[i])) {
                                tempCursor = child;
                                break;
                            }
                        }
                        traverserTwo = tempCursor;
                    } else {
                        System.out.println("ERROR: The second given path could not be found!");
                        return;
                    }
                }
            }
            if (traverserTwo.isFile()) {
                throw new NotADirectoryException("ERROR: Files and Directories cannot be moved into a file");
            }

            if(traverserTwo.getName().equalsIgnoreCase(paths[paths.length-1])) {
                DirectoryNode clone = traverserOne.cloneNode();
                traverserTwo.addChild(clone);

                parent.getChildren().remove(traverserOne);
                parent.setNumChildren(parent.getNumChildren()-1);

                if(cursor == null){
                    resetCursor();
                }
            }
        }
    }


    /**
     * @return
     *      Returns the root of the tree
     */
    public DirectoryNode getRoot() {
        return root;
    }

    /**
     * @return
     *      Returns if a node was found while using the find method
     */
    public static boolean isFoundNode() {
        return foundNode;
    }

    /**
     * Sets if a node was found or not
     * @param foundNode
     *      True or false if the node was found.
     */
    public static void setFoundNode(boolean foundNode) {
        DirectoryTree.foundNode = foundNode;
    }
}
