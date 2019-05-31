package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class SameThreadAsync extends Application {
	@Override
	public void start(final Stage primaryStage) {
		final ImageView imageView = new ImageView();
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setFitWidth(300.0);
		imageView.setFitHeight(300.0);

		Platform.runLater(() -> {
			final BufferedImage image = getIcon();
			imageView.setImage(SwingFXUtils.toFXImage(image, null));
		});

		final Node label = new Label(null, imageView);

		final StackPane root = new StackPane();
		root.getChildren().add(label);
		primaryStage.setScene(new Scene(root, 300.0, 300.0));
		primaryStage.show();
	}

	private BufferedImage getIcon() {
		System.out.println("Reading an image from thread " + Thread.currentThread().getName());

		try (final InputStream in = new BufferedInputStream(getClass().getResourceAsStream("logo1.png"))) {
			return ImageIO.read(in);
		} catch (final IOException ioe) {
			ioe.printStackTrace();
			return new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);
		}
	}

	public static void main(final String ... args) {
		launch(args);
	}
}
