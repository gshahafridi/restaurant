package com.gshah;

import java.util.*;

public class RestManager {
    private final List<Table> tables;
    private final Queue<ClientsGroup> waitingClientGroups = new LinkedList<>();
    private final Map<ClientsGroup, Table> seatedGroups = new HashMap<>();

    public RestManager(List<Table> tables) {
        this.tables = tables;
    }
    /**
     * new client(s) show up
     * @param group clientGroup
     */
    public void onArrive(ClientsGroup group) {
        Table availableTable = findTable(group.size);
        if (availableTable != null) {
            seatGroup(group, availableTable);
        } else {
            waitingClientGroups.offer(group);
            System.out.println("Client Group of: " + group.size + " added to queue.");
        }
    }

    /**
     * client(s) leave, either served or simply abandoning the queue
     * @param group client group
     */
    public void onLeave(ClientsGroup group) {
        Table seatedTable = seatedGroups.remove(group);
        if (seatedTable != null) {
            seatedTable.removeGroup(group);
            System.out.println("Client Group of: " + group.size + " left table " + seatedTable.getId());
            seatWaitingGroups();
        } else {
            waitingClientGroups.remove(group);
            System.out.println("Client Group of: " + group.size + " removed from queue.");
        }
    }
    /**
     *
     * @param group client group
     * @return return table where a given client group is seated,
     * or return null if it is still queueing or has already left
     */
    public Table lookup(ClientsGroup group) {
        return seatedGroups.get(group);
    }

    /**
     * First look for empty tables then look for partially filled tables
     * @param groupSize client group size
     * @return suitable table for client group
     */
    private Table findTable(int groupSize) {
        return tables.stream()
                .filter(table -> table.isEmpty() && table.size >= groupSize)
                .findFirst()
                .orElseGet(() -> tables.stream()
                        .filter(table -> table.canSeatGroup(groupSize))
                        .findFirst()
                        .orElse(null));
    }

    /**
     * Seat the client at particular table
     * @param group Client Group
     * @param table Table where client group seated.
     */
    private void seatGroup(ClientsGroup group, Table table) {
        table.addGroup(group);
        seatedGroups.put(group, table);
        System.out.println("Client Group of: " + group.size + " seated at table " + table.getId());
    }

    /**
     * Seat waiting groups from the queue.
     */
    private void seatWaitingGroups() {
        waitingClientGroups.removeIf(group -> {
            Table availableTable = findTable(group.size);
            if (availableTable != null) {
                seatGroup(group, availableTable);
                return true;
            }
            return false;
        });
    }
}

