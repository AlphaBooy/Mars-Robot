package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import robot.Battery;
import robot.Laser;

public class TestMaterial {
    @Test
    @DisplayName("test Battery")
    public void testBattery(){
        Battery battery = new Battery();
        assertEquals("Battery{name='Default Battery', capacity=200, level=100}", battery.toString(), "The default battery isn't good");
        Battery myBattery = new Battery("My battery", 300, 100);
        assertEquals("Battery{name='My battery', capacity=300, level=100}", myBattery.toString());
        try {
            myBattery.chargeBattery(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(myBattery.toString(), "Battery{name='My battery', capacity=300, level=300}");
        myBattery.useBattery(50);
        assertEquals(myBattery.toString(), "Battery{name='My battery', capacity=300, level=250}");

    }

    @Test
    @DisplayName("Test laser")
    public void testLaser(){
        Laser laser = new Laser();
        assertEquals("Laser{name='Default Laser', power=100}", laser.toString());
        Laser myLaser = new Laser("My laser", 200);
        assertEquals("Laser{name='My laser', power=200}", myLaser.toString());
        assertEquals(100, laser.getPower());
        assertEquals(200, myLaser.getPower());
    }
}
