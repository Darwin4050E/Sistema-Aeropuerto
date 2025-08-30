/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author gabriel
 */
public class BSTree<E, K> implements Serializable{
    private BSTNode<E, K> root;
    private Comparator<K> cmp;
    
    public BSTree(Comparator<K> cmp) {
        this.root = null;
        this.cmp = cmp;
    }
    
    public BSTree(BSTNode<E, K> root, Comparator<K> cmp) {
        this.root = root;
        this.cmp = cmp;
    }

    public BSTNode<E, K> getRoot() {
        return root;
    }

    public void setRoot(BSTNode<E, K> root) {
        this.root = root;
    }

    public Comparator<K> getCmp() {
        return cmp;
    }

    public void setCmp(Comparator<K> cmp) {
        this.cmp = cmp;
    }
    
    public boolean isEmpty() {
        return this.root == null;
    }
    
    public boolean isLeaf() {
        if (!isEmpty()) {
            return this.root.getLeft() == null && this.root.getRight() == null;
        }
        
        return false;
    }
    
    public int countLevelsRecursive() {
        if (isEmpty()) {
            return 0;
        }
        
        if (isLeaf()) {
            return 1;
        }
        
        int leftLevels = 0;
        int rightLevels = 0;
        
        if (this.root.getLeft() != null) {
            leftLevels = this.root.getLeft().countLevelsRecursive();
        }
        
        if (this.root.getRight() != null) {
            rightLevels = this.root.getRight().countLevelsRecursive();
        }
        
        return 1 + Math.max(leftLevels, rightLevels);
    }
    
    public boolean isHeightBalancedRecursive() {
        if (isEmpty()) {
            return true;
        }
        
        int leftLevels = 0;
        int rightLevels = 0;
        
        if (this.root.getLeft() != null) {
            leftLevels = this.root.getLeft().countLevelsRecursive();
        }
        
        if (this.root.getRight() != null) {
            rightLevels = this.root.getRight().countLevelsRecursive();
        }
        
        boolean leftIsBalanced = true;
        if (this.root.getLeft() != null) {
            leftIsBalanced = this.root.getLeft().isHeightBalancedRecursive();
        }
        
        boolean rightIsBalanced = true;
        if (this.root.getRight() != null) {
            rightIsBalanced = this.root.getRight().isHeightBalancedRecursive();
        }
        
        return leftIsBalanced && rightIsBalanced && (Math.abs(leftLevels - rightLevels) <= 1);
    }
    
    
    // Imprimir el ABB en formato amigable
    public void printTree() {
        System.out.println();
        printTree("", this, false);
    }

    private void printTree(String prefix, BSTree<E, K> tree, boolean isLeft) {
        if (tree == null || tree.isEmpty()) {
            return;
        }

        System.out.println(prefix + (isLeft ? "├── " : "└── ") + tree.getRoot().getKey());

        BSTree<E, K> left = tree.getRoot().getLeft();
        BSTree<E, K> right = tree.getRoot().getRight();

        if ((left != null && !left.isEmpty()) || (right != null && !right.isEmpty())) {
            printTree(prefix + (isLeft ? "│   " : "    "), right, false);
            printTree(prefix + (isLeft ? "│   " : "    "), left, true);
        }
    }

    public void inOrder() {
        if (isEmpty()) {
            return;
        }
   
        if (root.getLeft() != null) {
            root.getLeft().inOrder();
        }
        
        System.out.println(root.getContent());
        
        if (root.getRight() != null) {
            root.getRight().inOrder();
        }
    }
    
    public boolean insert(E content, K key) {
        BSTNode<E, K> node = new BSTNode<>(content, key);
        if (this.isEmpty()) {
            this.setRoot(node);
            return true;
        }
        
        int cmpResult = this.cmp.compare(key, this.root.getKey());
        
        if (cmpResult < 0) {
            if (this.root.getLeft() != null) {
                return this.root.getLeft().insert(content, key);
            } else {
                this.root.setLeft(new BSTree<>(node, this.cmp));
                return true;
            }
        }
        
        if (cmpResult > 0) {
            if (this.root.getRight() != null) {
                return this.root.getRight().insert(content, key);
            } else {
                this.root.setRight(new BSTree<>(node, this.cmp));
                return true;
            }
        }
        
        return false;
    }
    
    
    public E find(K key) {
        if (this.isEmpty()) {
            return null;
        }
        
        int cmpResult = this.cmp.compare(key, this.root.getKey());
        
        if (cmpResult == 0) {
            return this.root.getContent();
        }
        
        if (cmpResult < 0 && this.root.getLeft() != null) {
            return this.root.getLeft().find(key);
        }
        
        if (cmpResult > 0 && this.root.getRight() != null) {
            return this.root.getRight().find(key);
        }
        
        return null;
    }
    
    public boolean delete(K key) {
        if (this.isEmpty()) {
            return false;
        }
        
        int cmpResult = this.cmp.compare(key, this.root.getKey());
        
        if (this.isLeaf() && cmpResult == 0) {
            this.setRoot(null);
            return true;
        } 
        
        if (cmpResult == 0) {
            if (this.hasOnlyChild()) {
                BSTree<E, K> child = (this.root.getLeft() != null) ? this.root.getLeft() : this.root.getRight();
                this.setRoot(child.getRoot()); 
            } else {
                BSTree<E, K> maxLeft = this.findLeftMax();
                this.swapData(maxLeft);
            }

            return true;
        }
        
        if (cmpResult < 0 && this.root.getLeft() != null) {
            return this.root.getLeft().delete(key);
        }
        
        if (cmpResult > 0 && this.root.getRight() != null) {
            return this.root.getRight().delete(key);
        }
        
        return false;
        
    }
    
    public boolean hasOnlyChild() {
        return (this.root.getLeft() == null && this.root.getRight() != null) || (this.root.getRight() == null && this.root.getLeft() != null);
    }
    
    private BSTree<E, K> findLeftMax() {
        BSTree<E, K> parent = this;
        BSTree<E, K> current = this.root.getLeft();
        
        // Buscamos la mayor de las claves del subarbol izquierdo
        while (current.getRoot().getRight() != null) {
            parent = current;
            current = current.getRoot().getRight();
        }
        
        // No se entró al bucle, verificamos si el hijo izquierdo no tiene hijos
        if (parent == this && current.isLeaf()) {
            this.root.setLeft(null);
            return current;
        }
        
        // Si encontró un nodo distinto al hijo izquierdo - Reestructuración del árbol
        if (current.getRoot().getLeft() != null) {
            current.setRoot(current.getRoot().getLeft().getRoot());
        } else { 
            parent.getRoot().setRight(null);
        }
        
        return current;
    }

    private void swapData(BSTree<E, K> child) {
        this.root.setContent(child.getRoot().getContent());
        this.root.setKey(child.getRoot().getKey());
    }
    
    public int calculateBalanceFactor() {
        if (isEmpty()) {
            return 0;
        }
        
        int leftHeight = 0;
        int rightHeight = 0;
        
        if (this.root.getLeft() != null) {
            leftHeight = this.root.getLeft().countLevelsRecursive();
        }
        
        if (this.root.getRight() != null) {
            rightHeight = this.root.getRight().countLevelsRecursive();
        }
        
        return Math.abs(rightHeight - leftHeight);
    }
    
    public void insert(E content, K key, Stack<PathNode<E,K>> stack) {
        BSTNode<E, K> node = new BSTNode<>(content, key);
        
        if (this.isEmpty()) {
            this.setRoot(node);
            return;
        }
        
        int cmpResult = this.cmp.compare(key, this.root.getKey());
        
        if (cmpResult == 0) return;
        
        stack.push(new PathNode<>(this, cmpResult > 0));
        
        if (cmpResult < 0) {
            if (this.root.getLeft() != null) {
                this.root.getLeft().insert(content, key, stack);
            } else {
                this.root.setLeft(new BSTree<>(node, this.cmp));
                return;
            }
            
        }
        
        if (cmpResult > 0) {
            if (this.root.getRight() != null) {
                this.root.getRight().insert(content, key, stack);
            } else {
                this.root.setRight(new BSTree<>(node, this.cmp));
                return;
            }
        }
    }
    
    public void insertToAVL(E content, K key) {
        Stack<PathNode<E, K>> stack = new Stack<>();
        this.insert(content, key, stack);
        
        if (stack.isEmpty()) { 
            return;
        }
        
        List<Boolean> path = new ArrayList<>(); // guarda el sentido de insercion (de abajo hacia arriba) - true para derecha y false para izquierda
        BSTree<E, K> disbalanced = null;
        
        while (!stack.isEmpty()) {
            PathNode<E, K> node = stack.pop();
            path.add(node.wentRight());
            
            if (node.getNode().calculateBalanceFactor() > 1) {
                disbalanced = node.getNode();
                break;
            }
        }
        
        if (disbalanced == null) return; // No se encontró un nodo desbalanceado
        
        balance(disbalanced, path);
       
    }
    
    private void balance(BSTree<E, K> disbalanced, List<Boolean> path) {
        if (path.size() < 2) return;
        
        boolean i1 = path.get(path.size() - 1); //primer movimiento desde Z
        boolean i2 = path.get(path.size() - 2); //movimiento del hijo de Z
        
        // camino de inserciones - de arriba hacia abajo
        if (!i1 && !i2) { // insercion izquierda - izquierda
            rotateRight(disbalanced, this.cmp);
        }
        
        if (i1 && i2) { // inserción derecha - derecha
            rotateLeft(disbalanced, this.cmp);
        }
        
        if (i1 && !i2) { // inserción derecha - izquierda
            rotateRightLeft(disbalanced, this.cmp); // Se soluciona con una rotacion derecha izquierda (al reves)
        }
        
        if (!i1 && i2) { //insercion izquierda - derecha
            rotateLeftRight(disbalanced, this.cmp);
        }
    }
    
    // Rotaciones
    // Modifican la estructura del árbol actualizando la raiz del arbol desbalanceado
    
    private static <E, K> void rotateRight(BSTree<E, K> n, Comparator<K> cmp) {
        if (n == null || n.getRoot().getLeft() == null) return;
        
        BSTree<E, K> n1 = n.getRoot().getLeft();
        BSTree<E, K> copyOfN = new BSTree<>(n.getRoot(), cmp);

        copyOfN.getRoot().setLeft(n1.getRoot().getRight());
        n1.getRoot().setRight(copyOfN);
        n.setRoot(n1.getRoot());
    }
    
    private static <E, K> void rotateLeft(BSTree<E, K> n, Comparator<K> cmp) {
        if (n == null || n.getRoot().getRight() == null) return;
        
        BSTree<E, K> n1 = n.getRoot().getRight();
        BSTree<E, K> copyOfN = new BSTree<>(n.getRoot(), cmp);

        copyOfN.getRoot().setRight(n1.getRoot().getLeft());
        n1.getRoot().setLeft(copyOfN);
        n.setRoot(n1.getRoot());
    }
    
    // Rotacion Derecha Izquierda- Camino Derecha Izquierda
    private static <E, K> void rotateRightLeft(BSTree<E, K> n, Comparator<K> cmp) {
        if (n == null || n.getRoot().getRight() == null) return;
        
        rotateRight(n.getRoot().getRight(), cmp);
        rotateLeft(n, cmp);
    }
    
    // Rotacion Izquierda Derecha - Camino Izquierda Derecha
    private static <E, K> void rotateLeftRight(BSTree<E, K> n, Comparator<K> cmp) {
        if (n == null || n.getRoot().getLeft() == null) return;

        rotateLeft(n.getRoot().getLeft(), cmp);
        rotateRight(n, cmp);
    }
}