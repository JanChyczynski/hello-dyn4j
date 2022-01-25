package fr.auroden.hellodyn4j;

import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.*;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.world.World;

import java.util.List;

public class ClickHandler {
    private static final CategoryFilter ALL = new CategoryFilter(1, Long.MAX_VALUE);

    int clickCount = 0;
    double[] firstClick = new double[2];

    public SimulationBody mouseClicked(double x, double y) {
        clickCount ++;
        if (clickCount == 2){
            clickCount %= 2;

            BodyFixture bodyFixture = new BodyFixture(
                    new Rectangle(Math.abs(x - firstClick[0]) / GUI.SCALE, 0.5)
            );
            bodyFixture.setDensity(0.2);
            bodyFixture.setFriction(0.3);
            bodyFixture.setRestitution(0.2);
            bodyFixture.setFilter(ALL);
            bodyFixture.getShape().
                    rotate(-Math.atan(
                            (firstClick[1] - y) / (firstClick[0] - x)
                    ));

            SimulationBody line = new SimulationBody();
            line.addFixture(bodyFixture);
            line.setMass(MassType.INFINITE);
            line.translate( (x + firstClick[0]) / (2 * GUI.SCALE), -(y + firstClick[1]) / (2 * GUI.SCALE));
            System.out.println("added");
            return line;

        } else {
            firstClick[0] = x;
            firstClick[1] = y;
        }
        return null;
    }
}
