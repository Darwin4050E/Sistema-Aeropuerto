/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.sistemaaeropuerto.models;

/**
 *
 * @author Grupo 1 - P10
 * @param <E>
 * @param <K>
 */

public class AVLNode<E,K> {
    
    // Atributos:
    
    private K key;
    private E content;
    private AVL<E,K> left;
    private AVL<E,K> right;
    
    // Método:

    public AVLNode(K key, E content) {
        this.key = key;
        this.content = content;
        this.left = null;
        this.right = null;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public E getContent() {
        return content;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public AVL<E, K> getLeft() {
        return left;
    }

    public void setLeft(AVL<E, K> left) {
        this.left = left;
    }

    public AVL<E, K> getRight() {
        return right;
    }

    public void setRight(AVL<E, K> right) {
        this.right = right;
    }
    
}
