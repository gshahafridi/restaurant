package com.gshah;

import java.util.Arrays;
import java.util.List;

public class RestaurantTest {
    public static void main(String[] args) {
        // Create some tables
        List<Table> tables = Arrays.asList(
                new Table(1, 2),
                new Table(2, 4),
                new Table(3, 6)
        );

        // Initialize the restaurant manager with tables
        RestManager restManager = new RestManager(tables);

        // Test case 1: Group of 2 arrives
        System.out.println();
        ClientsGroup group1 = new ClientsGroup(2);
        restManager.onArrive(group1); // Should be seated at table 1

        // Test case 2: Group of 4 arrives
        ClientsGroup group2 = new ClientsGroup(4);
        restManager.onArrive(group2); // Should be seated at table 2

        // Test case 3: Group of 6 arrives
        ClientsGroup group3 = new ClientsGroup(6);
        restManager.onArrive(group3); // Should be seated at table 3

        // Test case 4: Group of 3 arrives, no table available
        ClientsGroup group4 = new ClientsGroup(3);
        restManager.onArrive(group4); // Should be added to the queue

        // Test case 5: Group of 2 arrives, no table available but can share
        ClientsGroup group5 = new ClientsGroup(2);
        restManager.onArrive(group5); // Should be added to the queue

        // Test case 6: Group of 2 leaves from table 1
        restManager.onLeave(group1); // Should remove group1 and seat group5 from queue to table 1

        // Test case 7: Group of 4 leaves from table 2
        restManager.onLeave(group2); // Should remove group2 and seat group4 from queue to table 2

        // Test case 8: Lookup group that is seated
        Table group3Table = restManager.lookup(group3);
        System.out.println("Group 3 is seated at table: " + (group3Table != null ? group3Table.getId() : "none"));

        // Test case 9: Lookup group that has left
        Table group1Table = restManager.lookup(group1);
        System.out.println("Group 1 is seated at table: " + (group1Table != null ? group1Table.getId() : "none"));

        // Test case 10: Group of 6 leaves from table 3
        restManager.onLeave(group3); // Should remove group3

        // Test case 11: Group of 3 leaves
        restManager.onLeave(group4); // Group of 3 leaves from table 2

        // Group 4 leaves so the restaurant should be empty now
    }
}
