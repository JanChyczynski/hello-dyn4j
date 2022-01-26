package fr.auroden.hellodyn4j;

import javafx.scene.paint.Color;
import org.dyn4j.collision.Fixture;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Vector2;

import java.util.Random;

public class Circle extends javafx.scene.shape.Circle implements BodyListener{

    private static Random randomGenerator = new Random(System.currentTimeMillis());

    public Circle() {
        setSmooth(true);
        setFill(Color.color(randomGenerator.nextDouble() * 0.75,
                randomGenerator.nextDouble() * 0.75,
                randomGenerator.nextDouble() * 0.75));
    }

    @Override
    public void bodyUpdate(BodyEvent e) {
        if (e.getType() == BodyEvent.Type.BODY_UPDATE) {
            Body body = e.getSource();

            Vector2 position = body.getWorldCenter();
            double angle = body.getTransform().getRotationAngle();
            Fixture fixture = body.getFixture(0);
            Convex bodyShape = fixture.getShape();

            rotateProperty().set(-Math.toDegrees(angle));

            AABB aabb = bodyShape.createAABB();
			setCenterX((position.x + aabb.getMinX()) * GUI.SCALE);
			setCenterY((-position.y + aabb.getMinY()) * GUI.SCALE);
			setRadius(aabb.getWidth() * GUI.SCALE / 2);
        }
    }
}
