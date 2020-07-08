package algorithm.test;

import java.util.LinkedList;

public class Test2 {
    private static int longestValidParentheses(String s) {
        int len = s.length();
        int window = (len & 1) == 1 ? len - 1 : len;
        while(window != 0){
            for(int i = 0; window >= 2 && i + window - 1 < len; i++){
                if(isValid(s, i, i + window - 1)){
                    return window;
                }
            }
            window -= 2;
        }

        return 0;
    }

    private static boolean isValid(String s, int start, int end){
        LinkedList<Character> stack = new LinkedList<>();
        for(int i = start; i <= end; i++){
            if(s.charAt(i) == '('){
                stack.add('(');
            }else{
                if(stack.size() != 0){
                    stack.removeLast();
                }else{
                    return false;
                }
            }
        }
        return stack.size() == 0;
    }

    public static void main(String[] args) {
        System.out.println(longestValidParentheses("(()"));
    }
}
