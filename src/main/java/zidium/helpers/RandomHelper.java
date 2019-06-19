package zidium.helpers;

import java.util.Random;


public class RandomHelper {
    
    private static final RandomHelper _helper = new RandomHelper(); 
    
    public static RandomHelper getDefault(){
        return _helper;
    }
    
    private Random _random;
    
    public RandomHelper(){
        _random = new Random();
    }
    
    public void RandomHelp(long seed){
        _random = new Random(seed);
    }
    
    public int getInt(int min, int max){
        return min + _random.nextInt(max - min + 1);
    }
    
    public float getFloat(float min, float max){
        return min + (max - min) * _random.nextFloat();
    }
    
    public double getDouble(double min, double max){
        return min + (max - min) * _random.nextDouble();
    }
    
    public boolean getBoolean(){
        int val = getInt(0, 10000);
        return val % 2 == 0;
    }
}
