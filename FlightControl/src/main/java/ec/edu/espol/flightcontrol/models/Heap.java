/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.flightcontrol.models;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Grupo 1 - P1
 */

public class Heap<E> implements Serializable {
    
    // Atributos:
    
    private Comparator<E> cmp;
    private E[] array;
    private int capacity = 100;
    private int effectiveSize;
    private boolean isMax;
    
    // Métodos:
    
    public Heap(Comparator<E> cmp, boolean isMax){
        this.cmp = cmp;
        this.array = (E[]) new Object[capacity];
        this.effectiveSize = 0;
        this.isMax = isMax;
    }
    
    public Heap(Comparator<E> cmp, boolean isMax, List<E> list) {
        this.cmp = cmp;
        this.isMax = isMax;
        this.effectiveSize = list.size();
        this.capacity = Math.max(this.capacity, this.effectiveSize);
        this.array = (E[]) new Object[this.capacity];

        for (int i = 0; i < this.effectiveSize; i++) {
            this.array[i] = list.get(i);
        }

        this.makeHeap(); 
    }

    public Comparator<E> getCmp() {
        return cmp;
    }

    public void setCmp(Comparator<E> cmp) {
        this.cmp = cmp;
    }

    public E[] getArray() {
        return array;
    }

    public void setArray(E[] array) {
        this.array = array;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getEffectiveSize() {
        return effectiveSize;
    }

    public void setEffectiveSize(int effectiveSize) {
        this.effectiveSize = effectiveSize;
    }

    public boolean isIsMax() {
        return isMax;
    }

    public void setIsMax(boolean isMax) {
        this.isMax = isMax;
    }
    
    public boolean isFull(){
        return this.effectiveSize == this.capacity;
    }
    
    public boolean isEmpty(){
        return this.effectiveSize == 0;
    }
    
    public void addCapacity(){
        E[] temp = (E[]) new Object[this.capacity * 2];
        for(int i = 0; i < this.effectiveSize; i++){
            temp[i] = array[i];
        }
        this.array = temp;
        this.capacity = this.capacity * 2;
    }
    
    public int getLeftIndex(int rootIndex){
        if(rootIndex < 0 || rootIndex >= this.effectiveSize){
            return -1;
        }
        return rootIndex * 2 + 1;
    }
    
    public int getRightIndex(int rootIndex){
        if(rootIndex < 0 || rootIndex >= this.effectiveSize){
            return -1;
        }
        return rootIndex * 2 + 2;
    }
    
    public int getExtremIndex(int rootIndex){
        if(rootIndex < 0 || rootIndex >= this.effectiveSize){
            return -1;
        }
        int extremIndex = rootIndex;
        int leftIndex = this.getLeftIndex(rootIndex);
        int rightIndex = this.getRightIndex(rootIndex);
        if(leftIndex < this.effectiveSize && cmp.compare(this.array[leftIndex], this.array[extremIndex]) > 0){
            extremIndex = leftIndex;
        }
        if(rightIndex < this.effectiveSize && cmp.compare(this.array[rightIndex], this.array[extremIndex]) > 0){
            extremIndex = rightIndex;
        }
        return extremIndex;
    }
    
    public void swap(int sourceIndex, int targetIndex){
        if(sourceIndex < 0 || targetIndex < 0){
            return;
        }
        E temp = this.array[sourceIndex];
        this.array[sourceIndex] = this.array[targetIndex];
        this.array[targetIndex] = temp;
    }
    
    public void heapify(int rootIndex){
        if(rootIndex < 0 || rootIndex >= this.effectiveSize){
            return;
        }
        int extremIndex = this.getExtremIndex(rootIndex);
        if(extremIndex != rootIndex){
            this.swap(rootIndex, extremIndex);
            this.heapify(extremIndex);
        }
    }
    
    public void makeHeap(){
        for(int i = (this.effectiveSize / 2) - 1; i >= 0; i--){
            this.heapify(i);
        }
    }
    
    public void offer(E element){
        if(element == null){
            return;
        }
        if(this.isFull()){
            this.addCapacity();
        }
        this.array[this.effectiveSize++] = element;
        this.makeHeap();
    }
    
    public E poll(){
        if(this.isEmpty()){
            return null;
        }
        E temp = this.array[0];
        this.swap(0, effectiveSize - 1);
        this.array[effectiveSize--] = null;
        this.heapify(0);
        return temp;
    }
    
}
