import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TwitterApp {
    public static void main(String[] args) {
        String userPath = args[0];
        String tweetsPath = args[1];

        TreeMap<String, String> twitterUsers = users(userPath);

        for (Map.Entry<String, String> entry : twitterUsers.entrySet()) {
            tweets(tweetsPath, entry.getKey(), entry.getValue());
        }
    }

    //get twitter users method
    public static TreeMap<String, String> users(String usersFilePath) {

        TreeMap<String, String> users = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(usersFilePath))){

            String line;

            while ((line = br.readLine()) != null) {

                // process the line from the twitter users path.
                String[] user = line.split(" follows ");
                users.put(user[0], user[1]);

                // getting users from the follower list
                List<String> follower = Arrays.asList(user[1].split(", "));

                follower.forEach(userFollower -> {
                    if(!users.containsKey(userFollower)) {
                        users.put(userFollower, " ");
                    }
                });
            }

        } catch (Exception error) {
            System.out.println("Error " + error + " when reading the twitter user file, try again.");
        }
        return users;
    }

    // getting tweets method.
    public static void tweets(String tweetsFilePath, String user, String follower) {

        // getting follower list by splitting with comma
        List<String> followerList = Arrays.asList(follower.split(", "));

        // printing user's name on the console.
        System.out.println(user);

        try (BufferedReader br = new BufferedReader(new FileReader(tweetsFilePath))){

            String line;

            while ((line = br.readLine()) != null) {

                // getting tweets feeds as per user or follower splitting with greater sign.
                String[] tweets = line.split("> ");

                if(tweets[0].equalsIgnoreCase(user)) {

                    // printing @user and associated tweets as required.
                    System.out.println("\t" + "@" + user + ": " + tweets[1]);

                } else if(followerList.contains(tweets[0])) {

                    System.out.println("\t" + "@" + tweets[0] + ": " + tweets[1]);

                }
            }
        } catch (Exception error) {
            System.out.println("Error " + error + " when reading the tweets file, try again.");
        }
    }
}
