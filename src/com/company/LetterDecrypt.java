package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

class LetterDecrypt {
    private static HashMap<Character,Character> zamena=new HashMap<>();



    void decrypt (ArrayList<Token> token1, ArrayList<Token> token2){
        filling(token1,token2);
        try {
            FileReader fileReader = new FileReader(new File("encrypt.txt"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter=new FileWriter(new File("decrypt.txt"));
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            String line ;
            StringBuilder builder=new StringBuilder();
            while ((line = bufferedReader.readLine())!= null) {
                line = line.toLowerCase();
                char[] inputLine = line.toCharArray();
                for (char anInputLine : inputLine) {
                    if(zamena.containsKey(anInputLine)){
                        builder.append(zamena.get(anInputLine));
                    }
                    else{
                        builder.append(anInputLine);
                    }
                }
                bufferedWriter.write(builder.toString()+"\r\n");
                builder.delete(0,line.length());
            }
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    static HashMap<Character,Character> getReplacement (){
        return zamena;
    }

    private  void filling (ArrayList<Token> token1, ArrayList<Token> token2){
        for(int i=0;i<token1.size();i++){
            zamena.put(token2.get(i).getName(),token1.get(i).getName());
        }
    }
}


