/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.sistemaaeropuerto.models;

import java.util.Comparator;

/**
 *
 * @author Grupo 1 - P10
 * @param <E>
 * @param <K>
 */

public class AVL<E,K> {
    
    // Atributos:
    
    private Comparator<K> cmp;
    private AVLNode<E,K> root;
    
    // Métodos:
    
    public AVL(Comparator<K> cmp){
        this.cmp = cmp;
        this.root = null;
    }

    private AVL(Comparator<K> cmp, AVLNode<E, K> root) {
        this.cmp = cmp;
        this.root = root;
    }

    public Comparator<K> getCmp() {
        return cmp;
    }

    public void setCmp(Comparator<K> cmp) {
        this.cmp = cmp;
    }

    public AVLNode<E, K> getRoot() {
        return root;
    }

    public void setRoot(AVLNode<E, K> root) {
        this.root = root;
    }
    
    private boolean isEmpty(){
        return this.root == null;
    }
    
    private int calculateHeight(){
        if(this.isEmpty()){
            return 0;
        }
        int leftHeight = 0;
        int rightHeight = 0;
        if(this.root.getLeft() != null){
            leftHeight = this.root.getLeft().calculateHeight();
        }
        if(this.root.getRight() != null){
            rightHeight = this.root.getRight().calculateHeight();
        }
        return 1 + Math.max(leftHeight, rightHeight);
    }
    
    private int calculateBalanceFactor(AVLNode<E,K> node){
        if(node == null){
            return 0;
        }
        int rightHeight = 0;
        int leftHeight = 0;
        if(node.getRight() != null){
            rightHeight = node.getRight().calculateHeight();
        }
        if(node.getLeft() != null){
            leftHeight = node.getLeft().calculateHeight();
        }
        return rightHeight - leftHeight;
    }
    
    public void printTree() {
        printTree("", true);
    }

    private void printTree(String prefix, boolean isTail) {
        if (this.isEmpty()) {
            System.out.println(prefix + (isTail ? "\\-- " : "|-- ") + "null");
            return;
        }
        System.out.println(prefix + (isTail ? "\\-- " : "|-- ") + this.root.getContent());
        boolean hasLeft = this.root.getLeft() != null && !this.root.getLeft().isEmpty();
        boolean hasRight = this.root.getRight() != null && !this.root.getRight().isEmpty();
        if (!hasLeft && !hasRight) {
            return;
        }
        if (hasRight) {
            this.root.getRight().printTree(prefix + (isTail ? "    " : "|   "), false);
        } else if (hasLeft) {
            System.out.println(prefix + (isTail ? "    " : "|   ") + "|-- null");
        }
        if (hasLeft) {
            this.root.getLeft().printTree(prefix + (isTail ? "    " : "|   "), true);
        } else if (hasRight) {
            System.out.println(prefix + (isTail ? "    " : "|   ") + "\\-- null");
        }
    }
    
    private AVLNode<E,K> rotateRight(AVLNode<E,K> node){
        if(node == null){
            return null;
        }
        AVLNode<E,K> nodo1 = node.getLeft().getRoot();
        if(nodo1.getRight() == null){
            nodo1.setRight(new AVL(this.cmp, null));
        }
        node.getLeft().setRoot(nodo1.getRight().getRoot());
        nodo1.getRight().setRoot(node);
        return nodo1;
    }
    
    private AVLNode<E,K> rotateLeft(AVLNode<E,K> node){
        if(node == null){
            return null;
        }
        AVLNode<E,K> nodo1 = node.getRight().getRoot();
        if(nodo1.getLeft() == null){
            nodo1.setLeft(new AVL(this.cmp, null));
        }
        node.getRight().setRoot(nodo1.getLeft().getRoot());
        nodo1.getLeft().setRoot(node);
        return nodo1;
    }
    
    private AVLNode<E,K> rotateRightLeft(AVLNode<E,K> node){
        if(node == null){
            return null;
        }
        node.setRight(new AVL<>(cmp, rotateRight(node.getRight().getRoot())));
        return rotateLeft(node);
    }
    
    private AVLNode<E,K> rotateLeftRight(AVLNode<E,K> node){
        if(node == null){
            return null;
        }
        node.setLeft(new AVL<>(cmp, rotateLeft(node.getLeft().getRoot())));
        return rotateRight(node);
    }
    
    public void insert(K clave, E contenido){
        this.root = this.insert(this.root, clave, contenido);
    }
    
    private AVLNode<E,K> insert(AVLNode<E,K> node, K key, E content){
        if(node == null){
            return new AVLNode(key, content);
        }
        if(this.cmp.compare(key, node.getKey()) < 0){
            if(node.getLeft() == null){
                node.setLeft(new AVL(this.cmp,new AVLNode(key, content)));
            }
            node.getLeft().setRoot(this.insert(node.getLeft().getRoot(), key, content));
        }
        if(this.cmp.compare(key, node.getKey()) > 0){
            if(node.getRight() == null){
                node.setRight(new AVL(this.cmp, new AVLNode(key, content)));
            }
            node.getRight().setRoot(this.insert(node.getRight().getRoot(), key, content));
        }
        int balanceFactor = this.calculateBalanceFactor(node);
        if(balanceFactor > 1){
            if(cmp.compare(key, node.getRight().getRoot().getKey()) < 0){
                return this.rotateRightLeft(node);
            }
            return this.rotateLeft(node);
        }
        if(balanceFactor < -1){
            if(cmp.compare(key, node.getLeft().getRoot().getKey()) > 0){
                return this.rotateLeftRight(node);
            }
            return this.rotateRight(node);
        }
        return node;
    }
    
    public E search(K key){
        if(this.isEmpty()){
            return null;
        }
        if(this.getRoot().getLeft() != null && this.cmp.compare(key, this.getRoot().getKey()) < 0){
            return this.getRoot().getLeft().search(key);
        }
        if(this.getRoot().getRight() != null && this.cmp.compare(key, this.getRoot().getKey()) > 0){
            return this.getRoot().getRight().search(key);
        }
        if(this.cmp.compare(key, this.getRoot().getKey()) == 0){
            return this.getRoot().getContent();
        }
        return null;
    }
    
    public void remove(K key){
        this.root = this.remove(this.root, key);
    }
    
    private AVLNode<E,K> remove(AVLNode<E,K> node, K key){
        if(node == null){
            return null;
        }
        if(node.getLeft() != null && this.cmp.compare(key, node.getKey()) < 0){
            node.getLeft().setRoot(this.remove(node.getLeft().getRoot(), key));
        }
        if(node.getRight() != null && this.cmp.compare(key, node.getKey()) > 0){
            node.getRight().setRoot(this.remove(node.getRight().getRoot(), key));
        }
        if(this.cmp.compare(key, node.getKey()) == 0){
            if(node.getLeft() == null && node.getRight() == null){
                return null;
            }
            if(node.getLeft() == null){
                return node.getRight().getRoot();
            }
            if(node.getRight() == null){
                return node.getLeft().getRoot();
            }
            AVLNode<E,K> successor = this.findMaxKey(node.getLeft().getRoot());
            node.setContent(successor.getContent());
            node.getLeft().setRoot(this.remove(node.getLeft().getRoot(), successor.getKey()));
        }
        return node;
    }
    
    private AVLNode<E,K> findMaxKey(AVLNode<E,K> node){
        while(node.getRight() != null){
            node = node.getRight().getRoot();
        }
        return node;
    }
    
}
