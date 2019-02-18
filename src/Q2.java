import java.io.*;
import java.util.*;

public class Q2
{
    // pass the input file path as the argument while running this code.
    static FileWriter output = null;

    public static void main(String[] args)
    {
        String line = "";
        String previousToken = null;
        int unigramsTotalCount = 0;
        int distinctUnigramTotalCount = 0;
        int bigramsTotalCount = 0;
        int distinctBigramTotalCount = 0;
        HashMap<String, Integer> unigramsMap = new HashMap<String, Integer>();
        HashMap<String, Integer> bigramsMap = new HashMap<String, Integer>();
        HashMap<String,Double> unigramMarginalProbabilities = new HashMap<String, Double>();
        HashMap<String,Double> bigramMarginalProbabilities = new HashMap<String, Double>();
        // create an output file
        try
        {
            output = new FileWriter("Q2_output",false);

            // read input file:
            File file = new File(args[0]);
            //user delimitter (space-.-space-space 0r .-space-'') :
            Scanner s = new Scanner(file).useDelimiter(" .  |. ''");
            int n =1;
            while(n!=0)
            //while(s.hasNext())
            {
                n--;
                // add . to the end of the sentence:
                String s1 = s.next() + " .";
                //System.getProperty("line.terminator") to get the line break in any OS:
                s1 = s1.replace(System.getProperty("line.separator")," ");
                //System.out.println(s1);
                // split by white spaces & tab :
                for (String token : s1.split("  | |\\t|\\r"))
                {
                    //System.out.println(previousToken+" | "+token);

                    unigramsTotalCount++;
                    //System.out.println(token +"  " +unigramsTotalCount);
                    if(unigramsMap.containsKey(token))
                    {
                        int count = unigramsMap.get(token);
                        unigramsMap.put(token,count+ 1 );
                    }
                    else
                    {
                        unigramsMap.put(token,1);
                        distinctUnigramTotalCount++;
                    }

                    if(previousToken != null)
                    {
                        bigramsTotalCount++;
                        String pair = previousToken + "~~~" + token;
                        if(bigramsMap.containsKey(pair))
                        {
                            int pairCount = bigramsMap.get(pair);
                            bigramsMap.put(pair, pairCount +1);
                        }
                        else
                        {
                            bigramsMap.put(pair, 1);
                            distinctBigramTotalCount++;
                        }
                    }
                    previousToken = token;
                }
                previousToken = null;
            }

            //calculate marginal probabilities:
            //for unigrams:
            unigramMarginalProbabilities = calculateMarginalProbabilities(unigramsMap,unigramsTotalCount);
            //for bigrams:
            bigramMarginalProbabilities = calculateMarginalProbabilities(bigramsMap,bigramsTotalCount);

            s.close();
            //System.out.println(unigramsTotalCount);
            //System.out.println(distinctUnigramTotalCount);
            //System.out.println(bigramsTotalCount);
            //System.out.println(distinctBigramTotalCount);
            //printHashMap(unigramsMap);
            printHashMap1(unigramMarginalProbabilities);

            printHashMap1(bigramMarginalProbabilities);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    // function to print HashMaps:
    private static void printHashMap(HashMap<String, Integer> map)
    {
        Iterator<String> itr = map.keySet().iterator();
        while (itr.hasNext())
        {
            String key = itr.next();
            int value = map.get(key);
            System.out.println(key + " : "+ value);
        }
    }

    private static void printHashMap1(HashMap<String, Double> map)
    {
        Iterator<String> itr = map.keySet().iterator();
        while (itr.hasNext())
        {
            String key = itr.next();
            double value = map.get(key);
            System.out.println(key + " : "+ value);
        }
    }

    private static HashMap<String, Double> calculateMarginalProbabilities(HashMap<String, Integer> map, int totalCount)
    {
        HashMap<String, Double> result = new HashMap<String, Double>();
        for(Map.Entry<String,Integer> entry : map.entrySet())
        {
            result.put(entry.getKey(), (double) entry.getValue()/totalCount);
        }
        return  result;
    }
}