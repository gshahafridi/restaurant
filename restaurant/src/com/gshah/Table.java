package com.gshah;

import java.util.ArrayList;
import java.util.List;

public class Table {
    public final int size; // number of chairs
    private final int id;  // unique ID for the table
    private final List<ClientsGroup> seatedGroups = new ArrayList<>();

    public Table(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public boolean canSeatGroup(int groupSize) {
        return getAvailableSeats() >= groupSize;
    }

    public int getAvailableSeats() {
        return size - seatedGroups.stream().mapToInt(g -> g.size).sum();
    }

    public void addGroup(ClientsGroup group) {
        seatedGroups.add(group);
    }

    public void removeGroup(ClientsGroup group) {
        seatedGroups.remove(group);
    }

    public boolean isEmpty() {
        return seatedGroups.isEmpty();
    }

}
