package DataStructure.stacks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class BracketChecker{
    private String input;

    public BracketChecker(String input){
        this.input = input;
    }

    public void check(){
        int stackSize = input.length();
        StackChar stackChar = new StackChar(stackSize);

        for(int j=0;j<input.length();j++){
            char ch = input.charAt(j);
            switch (ch){
                case '{':
                case '[':
                case '(':
                    stackChar.push(ch);
                    break;
                case '}':
                case ']':
                case ')':
                    if (!stackChar.isEmpty()){
                        char chx = stackChar.pop();
                        if((ch == '}' && chx!='{') || (ch == ']' && chx!='[') || (ch == ')' && chx!='(')){
                            System.out.println("Error: " + ch + " at " + j);
                        }
                    }else {
                        System.out.println("Error: " + ch + " at " + j);
                    }
                    break;
                default:
                    break;
            }
        }

        if(!stackChar.isEmpty())
        {
            System.out.println("Error: missing right delimiter!");
        }
    }
}

public class BracketsApp {
    public static void main(String[] args) throws IOException{
        String input;
        while(true)
        {
            System.out.println("Enter String containing delimiters: ");
            System.out.flush();
            input = getString();

            if(input.equals(""))
                break;

            BracketChecker bracketChecker = new BracketChecker(input);
            bracketChecker.check();
        }
    }

    public static String getString() throws IOException
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }
}
