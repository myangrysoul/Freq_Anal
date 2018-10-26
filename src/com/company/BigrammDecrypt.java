package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BigrammDecrypt {
    Main main;

    private StringBuilder  oneLetterDecrypt (StringBuilder bigram){
        char c0= bigram.charAt(0);
        char c1=bigram.charAt(1);
        if(Character.isLetter(c0)&&(bigram.length()>1)){
            try {
                bigram.setCharAt(0, LetterDecrypt.getReplacement().get(c0));
            }
            catch (NullPointerException e){

            }
        }
        else if(Character.isLetter(c1)&&bigram.length()>1){
            try{
                bigram.setCharAt(1, LetterDecrypt.getReplacement().get(c1));
            }
            catch (NullPointerException e){

            }
        }
        return bigram;
    }


    private  boolean oneLetterPresent(StringBuilder bigram){
        if(bigram.length()>1) {
            return Character.isLetter(bigram.charAt(0)) || Character.isLetter(bigram.charAt(1));
        }
        else return false;
    }
    void bigramDecrypt(ArrayList<Bigramm> bigramms, ArrayList<Bigramm> bigramms1, String string){
        HashMap<String,String> zamena=new HashMap<>();
        for(int i=0;i<50;i++){
            zamena.put(bigramms1.get(i).getName(),bigramms.get(i).getName());
        }
        try {
            FileReader fileReader = new FileReader(new File("encrypt.txt"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter=new FileWriter(new File(string+".txt"));
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            String line ;
            StringBuilder builder=new StringBuilder();
            StringBuilder out=new StringBuilder();
            while ((line = bufferedReader.readLine())!= null) {
                line = line.toLowerCase();
                char[] inputLine = line.toCharArray();
                Character buf=inputLine[0];

                for(int i=1;i<inputLine.length+1;i++){
                    builder.append(buf);
                    if(inputLine.length-i>0){
                        buf=inputLine[i];
                        builder.append(buf);
                    }
                    else{
                        out.append(builder);
                        builder.setLength(0);
                        break;
                    }
                    String key=builder.toString();
                    if(zamena.containsKey(key)){
                        out.append(zamena.get(key));
                    }
                    else if(oneLetterPresent(builder)){
                        out.append(oneLetterDecrypt(builder));
                    }
                    else{
                        out.append(builder);
                    }
                    out.deleteCharAt(out.length()-1);
                    builder.setLength(0);
                }
                bufferedWriter.write(out.toString()+"\r\n");
                out.delete(0,line.length());
            }
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Main.procent(new File("source.txt"),new File("decrypt.txt"),"точности (биграммы)");

    }
}

