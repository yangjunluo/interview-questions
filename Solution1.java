import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Question {
    /*
    Optimization notes: Due to hackerrank global timeout definitions per language, programs
    except for the most optimized will have a tough time passing the last test case.
    This is known and you will not be penalized for it.
    Using Java Streams will usually guarantee you won't pass the last test case.
   */
    public static void main(String args[]) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int N = Integer.parseInt(br.readLine());
        // Read documents
        for (int i = 0; i < N; i++) {
            storeDocument(br.readLine(), i);
        }
        final BufferedOutputStream output = new BufferedOutputStream(System.out);
        final int M = Integer.parseInt(br.readLine());
        // Read searches
        for (int j = 0; j < M; j++) {
            output.write((performSearch(br.readLine()) + "\n").getBytes());
        }
        output.flush();
        output.close();
        br.close();
    }

    static HashMap<String, HashMap<Integer, Integer>> map = new HashMap<>();
    public static void storeDocument(String string, int id) {
        String[] strs = string.split("\\s+");
        for (String str : strs) {
            HashMap<Integer, Integer> countMap = map.getOrDefault(str, new HashMap<>());
            countMap.put(id, countMap.getOrDefault(id, 0) + 1); //for duplicate word in each line, "a a b"
            map.put(str, countMap);
        }
    }

    public static String performSearch(String string) {
        String[] strs = string.split("\\s+");
        HashMap<Integer, Integer> queryMap = new HashMap<>();
        for (String str : strs) {
            HashMap<Integer, Integer> countMap = map.getOrDefault(str, new HashMap<>());
            for (Integer row : countMap.keySet()) {
                int newcount = countMap.get(row);
                int count = queryMap.getOrDefault(row, 0);
                count += newcount;
                queryMap.put(row, count);
            }
        }

        List<int[]> list = new ArrayList<>();
        for (int row : queryMap.keySet()) {
            list.add(new int[]{row, queryMap.get(row)});
        }

        Collections.sort(list, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[1] != b[1]) {
                    return b[1] - a[1];
                } else {
                    return a[0] - b[0];
                }
            }
        });

        StringBuilder sb = new StringBuilder();
        for (int[] item : list) {
            sb.append(item[0]);
            sb.append(" ");
        }

        return sb.toString();
    }
}
