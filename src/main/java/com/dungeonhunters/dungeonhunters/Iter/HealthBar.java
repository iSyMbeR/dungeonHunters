package com.dungeonhunters.dungeonhunters.Iter;
import java.util.Iterator;
import com.dungeonhunters.dungeonhunters.Iter.HealthBox;

public class HealthBar {
    private HealthBox health[][];
    private int y_max, x_max;
    public HealthBar(int currentHealth){
        int x=0,y=0;
        y_max = (currentHealth/100);
        if(currentHealth%100>0)y_max++;
        if(y_max==0)y_max=1;
        x_max = 10;
        health = new HealthBox[y_max][x_max];
        for(int i = 0;i<y_max;i++){
            for(int j = 0; j<x_max;j++){
                if(currentHealth>=10) {
                    health[i][j] = new HealthBox(10);
                    currentHealth-=10;
                }else{
                    health[i][j] = new HealthBox(currentHealth);
                    currentHealth=0;
                }
            }
        }
    }
    public Iterator<HealthBox> iterator(){
        return new CustomIterator(0,0);
    }
    private class CustomIterator implements Iterator<HealthBox>{
        private int x,y;
        public CustomIterator(int x, int y) {
            this.x=x;
            this.y=y;
        }
        @Override
        public boolean hasNext() {
            if(x==x_max){
                x=0;
                y++;
                if(y>y_max-1) return false;
            }
            return health[y][x].getValue() > 0;
        }

        @Override
        public HealthBox next() {
            return health[y][x++];
        }
    }

}
