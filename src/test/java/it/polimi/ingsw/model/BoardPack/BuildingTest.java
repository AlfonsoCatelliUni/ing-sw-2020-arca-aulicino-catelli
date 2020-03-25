package it.polimi.ingsw.model.BoardPack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {



    @org.junit.jupiter.api.Test
    void isAvailable() {

        Building building = new Building(1, 2);
        Boolean available = building.isAvailable();

        assertEquals(true, available);

        building.increaseQuantity();
        building.increaseQuantity();
        available = building.isAvailable();

        assertEquals(false, available);

        building.decreaseQuantity();
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
        building.increaseQuantity();
        int placed = building.getPlacedNumber();

        assertEquals(1, placed);

        building.increaseQuantity();
        placed = building.getPlacedNumber();

        assertEquals(2, placed);

        building.increaseQuantity();
        placed = building.getPlacedNumber();

        assertEquals(2, placed);

    }


    @org.junit.jupiter.api.Test
    void decreaseQuantity() {

        Building building = new Building(1, 2);
        building.decreaseQuantity();
        int placed = building.getPlacedNumber();

        assertEquals(0, placed);

        building.decreaseQuantity();
        placed = building.getPlacedNumber();

        assertEquals(0, placed);

    }



}