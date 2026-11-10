import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Team {

    final String name;
    final int points;
    final int goalDiff;

    Team(String name, int points, int goalDiff) {
        this.name = name;
        this.points = points;
        this.goalDiff = goalDiff;
    }

    @Override
    public String toString() {
        return name + "(" + points + "," + goalDiff + ")";
    }
}

class Leaderboard<T> {

    private final List<T> items = new ArrayList<>();
    private final Comparator<? super T> order;

    Leaderboard(Comparator<? super T> order) {
        this.order = order;
    }

    void add(T item) {
        items.add(item);
    }

    // Accepts a list of T or any subtype of T
    void addAll(List<? extends T> newItems) {
        items.addAll(newItems);
    }

    List<T> ranking() {

        // Create a copy so the original list remains unchanged
        List<T> result = new ArrayList<>(items);

        // Sort the copied list using the supplied comparator
        result.sort(order);

        return result;
    }

    void displayOriginalOrder() {
        System.out.println(items);
    }
}

public class LeaderboardDemo {

    public static void main(String[] args) {

        // Correct comparator:
        // 1. Points: high to low
        // 2. Goal difference: high to low
        // 3. Name: alphabetical order

        Comparator<Team> byRank =
            Comparator.comparingInt((Team t) -> t.points)
                .reversed()
                .thenComparing(
                    Comparator.comparingInt(
                        (Team t) -> t.goalDiff
                    ).reversed()
                )
                .thenComparing(t -> t.name);

        Leaderboard<Team> board =
            new Leaderboard<>(byRank);

        // First test dataset
        board.add(new Team("Panthers", 9, 4));
        board.add(new Team("Eagles", 9, 6));
        board.add(new Team("Cobras", 6, 1));
        board.add(new Team("Bisons", 9, 4));

        System.out.println("Correct Ranking:");
        System.out.println(board.ranking());

        System.out.println("\nOriginal Insertion Order:");
        board.displayOriginalOrder();

        // Second dataset: designed to expose a faulty comparator
        Leaderboard<Team> secondBoard =
            new Leaderboard<>(byRank);

        secondBoard.add(new Team("Tigers", 8, 2));
        secondBoard.add(new Team("Lions", 8, 7));
        secondBoard.add(new Team("Bears", 8, 4));

        System.out.println("\nSecond Dataset - Correct Ranking:");
        System.out.println(secondBoard.ranking());

        // Faulty comparator: compares only points
        Comparator<Team> faultyComparator =
            (a, b) -> Integer.compare(b.points, a.points);

        Leaderboard<Team> faultyBoard =
            new Leaderboard<>(faultyComparator);

        faultyBoard.add(new Team("Tigers", 8, 2));
        faultyBoard.add(new Team("Lions", 8, 7));
        faultyBoard.add(new Team("Bears", 8, 4));

        System.out.println("\nSecond Dataset - Faulty Ranking:");
        System.out.println(faultyBoard.ranking());
    }
}