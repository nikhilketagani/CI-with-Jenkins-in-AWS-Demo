package artifactory.test;


public class Calculator {
    public long add(long first, long second) {
        return first + second;
    }
    public long subtract(long first, long second) {
        return first - second;
    }
    public long multiply(long first, long second) {
        return first * second;
    }
    public long divide(long first, long second) {
    	if(second != 0){
        return first / second;
    	}
		return -1;
    }
}
