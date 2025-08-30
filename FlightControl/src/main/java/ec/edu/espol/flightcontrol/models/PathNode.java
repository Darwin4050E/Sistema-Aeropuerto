/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.models;

import java.io.Serializable;

/**
 *
 * @author gabriel
 */
public class PathNode<E, K> implements Serializable{
    private BSTree<E, K> node;
    private boolean wentRight;

    public PathNode(BSTree<E, K> node, boolean wentLeft) {
        this.node = node;
        this.wentRight = wentLeft;
    }

    public BSTree<E, K> getNode() {
        return node;
    }

    public void setNode(BSTree<E, K> node) {
        this.node = node;
    }

    public boolean wentRight() {
        return wentRight;
    }

    public void setWentRight(boolean wentRight) {
        this.wentRight = wentRight;
    }
}
