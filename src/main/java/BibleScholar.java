import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BibleScholar {
    private static boolean isPunctuation(char ch)  {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ch);
        String str = stringBuilder.toString();

        String regEx = "[`~☆★!@#$%^&*()+=|{}':;,\\[\\]》·.<>/?~！@#￥%……（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (str.length() == 1 && m.matches()) {
            return true;
        }else {
            return false;
        }
    }

    private static boolean isNumber(char ch) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ch);
        String str = stringBuilder.toString();

        String regEx = "[1-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (str.length() == 1 && m.matches()) {
            return true;
        }else {
            return false;
        }
    }

    private HashMap<String, Integer> wordsFrequencyInformation() {
        HashMap<String, Integer> words = new HashMap<>(); // all words with their count
        List<String> stopWords = new ArrayList<>(); //stop words
        try {
            String fPath = this.getClass().getClassLoader().getResource("stopwords.txt").getFile();
            File f = new File(fPath);
            if (f.isFile() && f.exists()) {
                InputStreamReader r = new InputStreamReader(
                        new FileInputStream(f));//考虑到编码格式
                BufferedReader br = new BufferedReader(r);
                String lineTxt = null;
                while((lineTxt = br.readLine()) != null){
                    stopWords.add(lineTxt);
                }
                r.close();
            }else{
                System.out.println("找不到指定的文件");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String filePath = this.getClass().getClassLoader().getResource("kjv.txt").getFile();
            File file = new File(filePath);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    StringTokenizer st = new StringTokenizer(lineTxt, " ");
                    while (st.hasMoreTokens()) {
                        StringBuilder sb = new StringBuilder(st.nextToken());
                        if (isPunctuation(sb.charAt(sb.length() - 1))) {
                            sb.deleteCharAt(sb.length() - 1);
                        }

                        if (sb.length() >= 1 && !isNumber(sb.charAt(0))) {
                            String tmp = sb.toString();
                            tmp = tmp.toLowerCase();
                            if (!stopWords.contains(tmp)) {
                                if (words.containsKey(tmp)) {
                                    words.put(tmp, words.get(tmp) + 1);
                                } else {
                                    words.put(tmp, 1);
                                }
                            }
                        }
                    }
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return words;
    }
    public String[] resolve() {
        HashMap<String, Integer> words = wordsFrequencyInformation();
        class PQComperator implements Comparator<String> {
            @Override
            public int compare(String o1, String o2) {
                if (words.get(o1) > words.get(o2)) {
                    return 1;
                } else if (words.get(o1) < words.get(o2)) {
                    return -1;
                }
                return 0;
            }
        }
        MaxPQ<String> maxPQ = new MaxPQ<String>(new PQComperator());
        for (String key : words.keySet()) {
            maxPQ.insert(key);
        }
        MinPQ<String> minPQ = new MinPQ<>(new PQComperator());
        for (String key : words.keySet()) {
            minPQ.insert(key);
        }

        String[] re = new String[24];
        Iterator<String> iterator1 = maxPQ.iterator();
        for (int i = 0; i < 12; ++i) {
            String str = iterator1.next();
            re[i] = str + ":" + words.get(str).toString();
        }
        Iterator<String> iterator2 = minPQ.iterator();
        for (int i = 0;i < 12;++i) {
            String str = iterator2.next();
            re[12 + i] = str + ":" + words.get(str).toString();
        }
        return re;
    }
    public static void main(String[] args){
        BibleScholar bibleScholar = new BibleScholar();
        System.out.println(Arrays.toString(bibleScholar.resolve()));
    }

}