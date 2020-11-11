package DataStructure.stacks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class StackChar{
    private int maxSize;
    private char[] arrayChar;
    private int top;

    public StackChar(int maxSize){
        this.maxSize = maxSize;
        arrayChar = new char[maxSize];
        top = -1;
    }

    public void push(char value){
        arrayChar[++top] = value;
    }

    public char pop(){
        return arrayChar[top--];
    }

    public char peek(){
        return arrayChar[top];
    }

    public boolean isEmpty(){
        return top == -1;
    }
}

class Reverse{
    private String input;
    private String output;

    public Reverse(String input){
        this.input = input;
    }

    public String doRev(){
        int stackSize = input.length();
        StackChar stackChar = new StackChar(stackSize);

        for(int i = 0 ; i < input.length(); i++){
            stackChar.push(input.charAt(i));
        }

        output="";

        while(!stackChar.isEmpty()){
            output = output + stackChar.pop();
        }

        return output;
    }
}

public class ReverseApp {
    public static void main(String[] args) throws IOException{
        String input,output;

        while(true){
            System.out.println("Enter a String: ");
            System.out.flush();
            input = getString();

            if(input.equals(""))
                break;

            Reverse reverse =  new Reverse(input);
            output = reverse.doRev();
            System.out.println("Reverse: " + output);
        }
    }

    public static String getString() throws IOException{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }
}
