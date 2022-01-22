/**********************************************************************
 * This is free and unencumbered software released into the public domain.
 * <p>
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 * <p>
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 * <p>
 * For more information, please refer to <http://unlicense.org>
 **********************************************************************/

package fr.auroden.hellodyn4j;

import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import java.util.Random;

public class HelloDyn4J extends Application {
	Stage primStage;


	@Override
	public void start(Stage primaryStage) {
		this.primStage = primaryStage;
		this.primStage.setTitle("HelloDyn4J");

		Group root = new Group();

		Scene scene = new Scene(root, 600, 600);
		this.primStage.setScene(scene);

		// Creating the world
		World world = new World();
		world.setGravity(new Vector2(0., -10.));

		GUI gui = new GUI(world, root);

		Scheduler scheduler = new Scheduler(world);

		createGround(world);
		populate(world);

		scheduler.start();
		this.primStage.show();

		Camera camera = new ParallelCamera();
		camera.setLayoutX(-1000);
		camera.setLayoutY(-2000);
		camera.setScaleX(3);
		camera.setScaleY(3);

		scene.setCamera(camera);

		scene.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.SECONDARY) {
				populate(world);
			} else {
				createBoxAt(world, e.getX(), e.getY());
			}
		});

		//scheduler.stop();
	}

	private void createGround(World world) {
		// Create a ground.
		Rectangle groundRect = new Rectangle(100., 20.);

		BodyFixture groundFixture = new BodyFixture(groundRect);

		Body ground = new Body();
		ground.addFixture(groundFixture);
		ground.setMass(MassType.INFINITE);
		ground.translate(0., 0.);

		world.addBody(ground);
	}

	private void createBoxAt(World world, double x, double y) {
		// create a box
		Rectangle rectShape = new Rectangle(2.0, 2.0);

		BodyFixture boxFixture = new BodyFixture(rectShape);
		boxFixture.setDensity(0.2);
		boxFixture.setFriction(0.3);
		boxFixture.setRestitution(0.2);

		Body box = new Body();
		box.addFixture(boxFixture);
		box.setMass(MassType.NORMAL);
		box.translate(x / GUI.SCALE, -y / GUI.SCALE);

		world.addBody(box);
	}

	private void populate(World world) {
		// Random generator
		Random rand = new Random(System.currentTimeMillis());

		for (int i = 0; i < 12; i++) {
			Rectangle rectShape = new Rectangle(1f + rand.nextFloat() * 4, 1f + rand.nextFloat() * 4);

			BodyFixture boxFixture = new BodyFixture(rectShape);
			boxFixture.setDensity(0.2);
			boxFixture.setFriction(0.3);
			boxFixture.setRestitution(0.2);

			Body box = new Body();
			box.addFixture(boxFixture);
			box.setMass(MassType.NORMAL);
			box.translate(rand.nextInt(40) - 20, rand.nextInt(30) + 70);
			box.setAngularVelocity((rand.nextFloat() - 0.5f) * 16);

			world.addBody(box);
		}
	}
}
