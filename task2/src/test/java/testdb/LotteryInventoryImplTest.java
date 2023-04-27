package testdb;

import item.Item;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LotteryInventoryImplTest {

    TestLotteryInventory lotteryInventory = new TestLotteryInventory();

    @Test
    public void gradeA() {

        //given
        List<Item> items = lotteryInventory.getGradeA();

        //then
        assertEquals(items.size(), 5);

    }

    @Test
    public void gradeB() {

        //given
        List<Item> items = lotteryInventory.getGradeB();

        //then
        assertEquals(items.size(), 5);

    }

    @Test
    public void isProbabilityOfGradeA_90Percent() {

        double percentage = 90.0;
        int runs = 10000;
        int count = 0;

        for (int i = 0; i < runs; i++) {
            Optional<Item> optionalItem = lotteryInventory.pickAnyA();

            if (optionalItem.isPresent()) {
                if (optionalItem.get().getGrade() == Item.Grade.A) {
                    count++;
                }
            }
        }

        double actual = (double) count / runs * 100;

        assertTrue(percentage <= actual);

    }

    @Test
    public void isProbabilityOfGradeB_10Percent() {

        double percentage = 10.0;
        int runs = 10000;
        int count = 0;

        for (int i = 0; i < runs; i++) {
            Optional<Item> optionalItem = lotteryInventory.pickAnyB();

            if (optionalItem.isPresent()) {
                if (optionalItem.get().getGrade() == Item.Grade.B) {
                    count++;
                }
            }
        }

        double actual = (double) count / runs * 100;

        assertTrue(percentage <= actual);

    }

    @Test
    public void isProbabilityToPickGradeB_AfterNotPickingA_1Percent() {

        double percentage = 1.0;
        int runs = 10000;
        int count = 0;

        for (int i = 0; i < runs; i++) {
            Optional<Item> optionalItem = lotteryInventory.pick();

            if (optionalItem.isPresent()) {
                if (optionalItem.get().getGrade() == Item.Grade.B) {
                    count++;
                }
            }
        }

        double actual = (double) count / runs * 100;

        assertTrue(percentage - 0.1 <= actual || percentage + 0.1 <= actual);

    }

    @Test
    public void GradeBPickedUp_Only3Times() {

        int count = 0;

        LocalDateTime drawTime = LocalDateTime.now();

        while (lotteryInventory.getCountB() != 0) {
            Optional<Item> item = lotteryInventory.pickBWithCount(drawTime);
            if (item.isPresent()) {
                count++;
            }
        }

        assertEquals(count, 3);

    }

}