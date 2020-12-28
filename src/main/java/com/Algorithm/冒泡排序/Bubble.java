package com.Algorithm.冒泡排序;

public class Bubble {
    public int[] sort(int[] array){
        int temp=0;
        for(int i=0;i<array.length-1;i++){
            int flag=0;
            for (int j = 0; j < array.length-1; j++) {
                if (array[j+1]>array[j]){
                    temp=array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                    flag=1;
                }
                if (flag==0){
                    break;
                }
            }
        }
        return array;
    }
}
