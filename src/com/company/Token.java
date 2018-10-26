package com.company;

import java.util.ArrayList;

class Token {

    private int  repeat;
    private char name;

    Token(int repeat, char name){
        this.name = name;
        this.repeat = repeat;
    }
    int getRepeat(){
        return repeat;
    }

    char getName() {
        return name;
    }

    void setRepeat(int repeat) {
        this.repeat = repeat;
    }
    static ArrayList<Token> sortToken (ArrayList<Token> token){
        token.sort(new Sort());
        for(Token tokens:token){
            System.out.println(tokens.getRepeat()+"-" +tokens.getName());
        }
        return token;

    }
}
