package it.polimi.ingsw.model.Board;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {



    @org.junit.jupiter.api.Test
    void isAvailable() {

        Building building = new Building(1, 2);
        Boolean available = building.isAvailable();

        assertEquals(true, available);

        building.increasePlacedQuantity();
        building.increasePlacedQuantity();
        available = building.isAvailable();

        assertEquals(false, available);

        building.decreasePlacedQuantity();
        available = building.isAvailable();

        assertEquals(true, available);


    }


    @org.junit.jupiter.api.Test
    void getIsDome() {

        Building buildingOne = new Building(1, 2);
        Boolean isDome = buildingOne.getIsDome();

        assertEquals(false, isDome);

        Building buildingTwo = new Building(4, 2);
        isDome = buildingTwo.getIsDome();

        assertEquals(true, isDome);

    }


    @org.junit.jupiter.api.Test
    void increaseQuantity() {

        Building building = new Building(1, 2);
        building.increasePlacedQuantity();
        int placed = building.getPlacedNumber();

        assertEquals(1, placed);

        building.increasePlacedQuantity();
        placed = building.getPlacedNumber();

        assertEquals(2, placed);

        building.increasePlacedQuantity();
        placed = building.getPlacedNumber();

        assertEquals(2, placed);

    }


    @org.junit.jupiter.api.Test
    void decreaseQuantity() {

        Building building = new Building(1, 2);
        building.decreasePlacedQuantity();
        int placed = building.getPlacedNumber();

        assertEquals(0, placed);

        building.decreasePlacedQuantity();
        placed = building.getPlacedNumber();

        assertEquals(0, placed);

    }



}