/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.models;

import java.io.Serializable;

/**
 *
 * @author Grupo 1 - P1
 */
public class BSTNode<E, K> implements Serializable{
    private E content;
    private K key;
    private BSTree<E, K> left;
    private BSTree<E, K> right;
    
    public BSTNode() {}
    
    public BSTNode(E content, K key) {
        this.content = content;
        this.key = key;
    }

    public E getContent() {
        return content;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public BSTree<E, K> getLeft() {
        return left;
    }

    public void setLeft(BSTree<E, K> left) {
        this.left = left;
    }

    public BSTree<E, K> getRight() {
        return right;
    }

    public void setRight(BSTree<E, K> right) {
        this.right = right;
    }
}
