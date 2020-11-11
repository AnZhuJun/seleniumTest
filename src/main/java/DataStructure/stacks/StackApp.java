package DataStructure.stacks;

class StackX {
    private int maxSize;
    private int[] stackArray;
    private int top;

    public StackX(int maxSize){
        this.maxSize = maxSize;
        stackArray = new int[maxSize];
        top = -1;
    }

    public void push(int value){
        stackArray[++top] = value;
    }

    public int pop(){
        return stackArray[top--];
    }

    public int peek(){
        return stackArray[top];
    }
    public boolean isEmpty(){
        return top == -1;
    }

    public boolean isFull(){
        return top == maxSize-1;
    }
}

public class StackApp{
    public static void main(String[] args) {
        StackX stackX = new StackX(10);
        stackX.push(42);
        stackX.push(52);
        stackX.push(643);
        stackX.push(754);
        stackX.push(23);
        stackX.push(75);
        stackX.push(967);
        stackX.push(21);

        while(!stackX.isEmpty()){
            int temp = stackX.pop();
            System.out.print(temp + "  ");
        }
    }
}