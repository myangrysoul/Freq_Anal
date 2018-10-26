package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String [] args) {
        ArrayList<Token> token1, token2;
        ArrayList<Bigramm> bigram1, bigram2;
        Main main = new Main();
        BigrammDecrypt bigramDecrypt1 = new BigrammDecrypt();
        LetterDecrypt letterDecrypt = new LetterDecrypt();
        Encryption encryption = new Encryption();
        encryption.encrypt();

        token1 = main.numOfRepeat(new File("VOINAiMIR.txt"), new File("result_voina.txt"));
        token2 = main.numOfRepeat(new File("encrypt.txt"), new File("result_encr.txt"));

        letterDecrypt.decrypt(token1, token2);

        bigram1 = bigramRepeat(new File("VOINAiMIR.txt"), new File("resBigramm.txt"));
        bigram2 = bigramRepeat(new File("encrypt.txt"), new File("resBigEncr.txt"));

        bigramDecrypt1.bigramDecrypt(bigram1, bigram2, "d0");

        bigram2 = bigramRepeat(new File("d0.txt"), new File("resBigEncr.txt"));
        bigramDecrypt1.bigramDecrypt(bigram1, bigram2, "Decrypt1");
        procent(new File("source.txt"),new File("Decrypt1.txt"), "точности частотного анализа");


    }


    private static ArrayList<Bigramm> bigrammsSort(ArrayList<Bigramm> bigramms){

        bigramms.sort(new Sort1());
        for(Bigramm bigramm:bigramms){
            System.out.println(bigramm.getRepeat()+"-"+bigramm.getName());
        }
        return bigramms;
    }



    private ArrayList <Token> numOfRepeat(File input,File output){
        HashMap<Character,Token> NumOfRepeat = new HashMap<>();
        initAlph(NumOfRepeat);
        try {
            FileReader fileReader = new FileReader(input);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line ;
            long start = System.nanoTime();
            while ((line = bufferedReader.readLine())!= null){
                line = line.toLowerCase();
                char [] inputLine = line.toCharArray();
                for (char anInputLine : inputLine) {
                    Token token = NumOfRepeat.get(anInputLine);
                    if (NumOfRepeat.containsValue(token)&&token.getName()==anInputLine) {
                        token.setRepeat(token.getRepeat()+1);
                    }
                }
            }
            long finish = System.nanoTime();
            FileWriter writer = new FileWriter(output);
            BufferedWriter writer1 = new BufferedWriter(writer);

            float count = counter(NumOfRepeat);

            System.out.println("Всего букв в тексте: "+count);
            for(Token token: NumOfRepeat.values()){
                System.out.println(token.getName() + " : " + token.getRepeat());
                writer1.write(token.getName() + " : " + (token.getRepeat()/count*100)+" %\r\n");
            }
            writer1.close();
            bufferedReader.close();
            System.out.println("Execution time = "+(finish-start)/Math.pow(10,6)+" ms");
        } catch (IOException e){
            e.printStackTrace();
        }
        return Token.sortToken(new ArrayList<>(NumOfRepeat.values()));
    }
    private void initAlph(HashMap<Character, Token> map) {
        for (char c = 'а'; c <= 'я'; c++) {
            map.put(c, new Token(0, c));
        }
    }

    private float counter(HashMap<Character,Token> map){
        float n = 0;
        for(Token token: map.values()){
            n = n + token.getRepeat();
        }
        return n;
    }
    private static ArrayList<Bigramm> bigramRepeat(File input,File output){
        HashMap<String,Bigramm> bigramRepeat = new HashMap<>();
        ArrayList<Character> alph = new ArrayList<>();
        float count = 0;
        for(char c = 'а';c<='я';c++){
            alph.add(c);
        }
        try {
            FileReader fileReader = new FileReader(input);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line ;
            StringBuilder builder = new StringBuilder();
            long start = System.nanoTime();
            while ((line = bufferedReader.readLine())!= null){
                line = line.toLowerCase();
                char [] inputLine = line.toCharArray();
                for (int j = 0;j<inputLine.length-1;j++) {
                    if(alph.contains(inputLine[j])&&alph.contains(inputLine[j+1])){
                        builder.append(inputLine[j]);
                        builder.append(inputLine[j+1]);
                        String key = builder.toString();
                        if(bigramRepeat.containsKey(key)){
                            Bigramm bigramm = bigramRepeat.get(key);
                            bigramm.setRepeat(bigramm.getRepeat()+1);
                            count++;
                        }
                        else{
                            bigramRepeat.put(key,new Bigramm(1,builder.toString()));
                            count++;
                        }
                        builder.delete(0,builder.length());
                    }
                }
            }
            long finish = System.nanoTime();
            FileWriter writer = new FileWriter(output);
            BufferedWriter writer1 = new BufferedWriter(writer);
            System.out.println("Всего биграмм = "+count);
            for(Bigramm bigramm: bigramRepeat.values()){
                System.out.println(bigramm.getName()+" : "+(bigramm.getRepeat()/count)*100+" %");
                writer1.write(bigramm.getName()+" : "+(bigramm.getRepeat()/count)*100+"% \r\n");
            }
            writer1.close();
            bufferedReader.close();
            System.out.println("Execution time = "+(finish-start)/Math.pow(10,6)+" ms");
        } catch (IOException e){
            e.printStackTrace();
        }
        return bigrammsSort(new ArrayList<>(bigramRepeat.values()));
    }
    static void procent(File file,File file1, String string){
        ArrayList<Character> alphabet = new ArrayList<>();
        for(char c = 'а';c <= 'я'; c++){
            alphabet.add(c);
        }
        try {
            FileReader fReader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(fReader);
            FileReader fReader1 = new FileReader(file1);
            BufferedReader buffer1 = new BufferedReader(fReader1);
            String inputLine;
            String inputLine1;
            float count = 0;
            float countAll = 0;
            while((inputLine = buffer.readLine())!=null) {
                inputLine = inputLine.toLowerCase();
                inputLine1 = buffer1.readLine();
                for (int h = 0; h < inputLine.length(); h++) {
                    char c = inputLine.charAt(h);
                    char d = inputLine1.charAt(h);
                    if(alphabet.contains(c)) {
                        if(c==d) {
                            count++;
                        }
                        countAll++;
                    }
                }
            }
            buffer1.close();
            buffer.close();
            System.out.println("Процент "+string+ " = "+count/countAll*100+" %");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

