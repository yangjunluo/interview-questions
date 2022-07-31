import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Question {
    static class Job {
        int id;
        Set<String> contentSet;
        int score;

        public Job(int id, String content) {
            this.id = id;
            this.contentSet = new HashSet<>(Arrays.asList(content.split("\\s+")));
        }
    }

    static int bestCount = 10;
    static List<Job> jobList = new ArrayList<>();

    public static void storeDocument(final String document, final int documentNumber) {
        // TODO implement
        jobList.add(new Job(documentNumber, document));
    }

    private static int getMatchScore(Set<String> contentSet, Set<String> keySet) {
        int score = 0;
        for (String key : keySet) {
            if (contentSet.contains(key)) {
                score++;
            }
        }
        return score;
    }

    public static String performSearch(final String search) {
        // TODO implement
        Set<String> searchKey = new HashSet<>(Arrays.asList(search.split("\\s+")));
        PriorityQueue<Job> heap = new PriorityQueue<Job>(bestCount, (job1, job2) ->
        {
            if (job1.score == job2.score) {
                return job1.id - job2.id;
            } else {
                return job2.score - job1.score;
            }
        });
        for (Job job : jobList) {
            job.score = getMatchScore(job.contentSet, searchKey);
            if (job.score != 0) {
                heap.add(job);
            }
        }
        List<Job> res = new ArrayList<>();
        int i = 0;
        while (!heap.isEmpty() && i < bestCount) {
            res.add(heap.poll());
            i++;
        }
        if (res.size() == 0) {
            return "-1";
        }
        StringBuilder sb = new StringBuilder();
        for (Job job : res) {
            sb.append(job.id + " ");
        }
        return sb.toString();
    }

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
}

