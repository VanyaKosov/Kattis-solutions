import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        HashMap<Quest, Integer> questCounts = new HashMap<>();
        TreeSet<Quest> quests = new TreeSet<>((a, b) -> {
            if (a.energyRequired == b.energyRequired) {
                return a.goldRewarded - b.goldRewarded;
            }

            return a.energyRequired - b.energyRequired;
        });

        int commandCount = Integer.parseInt(READER.readLine());
        for (; commandCount > 0; commandCount--) {
            String[] info = READER.readLine().split(" ");
            char command = info[0].charAt(0);

            if (command == 'a') { // add
                int energy = Integer.parseInt(info[1]);
                int gold = Integer.parseInt(info[2]);
                Quest quest = new Quest(energy, gold);

                if (questCounts.containsKey(quest)) {
                    questCounts.put(quest, questCounts.get(quest) + 1);
                } else {
                    questCounts.put(quest, 1);
                    quests.add(quest);
                }
            } else { // query
                int energyAvailable = Integer.parseInt(info[1]);
                long coinsEarned = 0L;
                Quest quest;
                while ((quest = quests.lower(new Quest(energyAvailable, 1000000))) != null) {
                    if (energyAvailable < quest.energyRequired) {
                        break;
                    }

                    coinsEarned += quest.goldRewarded;
                    energyAvailable -= quest.energyRequired;

                    questCounts.put(quest, questCounts.get(quest) - 1);
                    if (questCounts.get(quest).equals(0)) {
                        questCounts.remove(quest);
                        quests.remove(quest);
                    }
                }

                WRITER.write(Long.toString(coinsEarned));
                WRITER.newLine();
            }
        }

        WRITER.close();
    }

    private static class Quest {
        public final int energyRequired;
        public final int goldRewarded;

        public Quest(int energyRequired, int goldRewarded) {
            this.energyRequired = energyRequired;
            this.goldRewarded = goldRewarded;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Quest otherQuest) {
                return energyRequired == otherQuest.energyRequired
                        && goldRewarded == otherQuest.goldRewarded;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return energyRequired * goldRewarded + energyRequired % goldRewarded;
        }
    }
}
