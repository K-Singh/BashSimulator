package com.company;

import java.util.ArrayList;

/**
 * The DirectoryNode class represents a directory or file in the file tree. Nodes have a max number of children
 * equal to 10. Nodes also have an array list relating that stores the children inside them.
 * @author Kirat Singh ID: 112320621 E-mail: kirat.singh@stonybrook.edu
 */
public class DirectoryNode {

    private String name;
    private ArrayList<DirectoryNode> children;
    private boolean isFile;
    private final int CHILD_LIMIT = 10;
    private int numChildren;

    /**
     * Constructs the DirectoryNode
     * @param file
     *      A boolean value representing whether or not the node is a file or not
     * @param n
     *      The name of the file/directory
     */
    public DirectoryNode(boolean file, String n){
        name = n;
        isFile = file;
        numChildren = 0;
        children = new ArrayList<DirectoryNode>();
    }

    /**
     * Adds a child to the node, only if the child is a directory. Children are added from left to right, or rather
     * starting from index 0 to 9 in the children array list.
     * @param child
     *      The DirectoryNode to be added as the child.
     * @throws FullDirectoryException
     *      Thrown when the directory the child is being added to is full
     * @throws NotADirectoryException
     *      Thrown when the node the child is trying to be added to is not a file
     * @throws DuplicateNodeException
     *      Thrown when there is an instance of the child already in the directory
     */
    public void addChild(DirectoryNode child) throws FullDirectoryException, NotADirectoryException, DuplicateNodeException{
        if(!isFile){
            for(DirectoryNode others : children){
                if(others.getName().equalsIgnoreCase(child.getName())){
                    throw new DuplicateNodeException("ERROR: A file or directory with that name already exists in that" +
                            " directory");
                }
            }
            if(isFull()){
                throw new FullDirectoryException("ERROR: Present directory is full");
            }else{
                children.add(child);
                numChildren++;
            }
        }else
            throw new NotADirectoryException("ERROR: Cannot change directory in file");
    }

    /**
     * Finds a child in the children of this instance of DirectoryNode with the given name
     * @param s
     *      The name to search for
     * @return
     *      The child if found, null if not.
     */
    public DirectoryNode findChildByName(String s){
        if(isFile)
            return null;

        for(DirectoryNode n : children){
            if(n.getName().equalsIgnoreCase(s)){
                return n;
            }
        }

        return null;
    }

    /**
     * @return
     *      Returns if the directory is full
     */
    public boolean isFull(){
        if(numChildren == CHILD_LIMIT)
            return true;
        return false;
    }

    /**
     * Clones this node
     * @return
     *      Returns an exact copy of this node
     */
    public DirectoryNode cloneNode(){
        DirectoryNode clone = new DirectoryNode(isFile, name);
        clone.setNumChildren(numChildren);
        ArrayList<DirectoryNode> childrenClone = (ArrayList<DirectoryNode>)children.clone();
        clone.setChildren(childrenClone);

        return clone;

    }

    /**
     * Sets the name
     * @param name
     *      The new name for the node
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     *      Returns the name of the node
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     *      Returns if the node is a file or not
     */
    public boolean isFile() {
        return isFile;
    }

    /**
     * @return
     *      Returns the arraylist containing the children of the node
     */
    public ArrayList<DirectoryNode> getChildren() {
        return children;
    }

    /**
     * Sets the new arraylist containing the children of this node
     * @param children
     *      The new arraylist to be set
     */
    public void setChildren(ArrayList<DirectoryNode> children) {
        this.children = children;
    }

    /**
     * Sets the number of children in the node
     * @param numChildren
     *      The new number of children in the node
     */
    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    /**
     * @return
     *      Returns the number of children in this node
     */
    public int getNumChildren() {
        return numChildren;
    }
}
