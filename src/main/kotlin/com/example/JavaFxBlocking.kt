package com.example

import javafx.application.Application
import javafx.embed.swing.SwingFXUtils
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class JavaFxBlocking : Application() {
	override fun start(primaryStage: Stage) {
		val imageView = ImageView().apply {
			isPreserveRatio = true
			isSmooth = true
			fitWidth = 300.0
			fitHeight = 300.0
		}

		val image = runBlocking {
			getIcon()
		}
		imageView.image = SwingFXUtils.toFXImage(image, null)

		val button = Button().apply {
			graphic = imageView
		}

		val root = StackPane()
		root.children.add(button)
		primaryStage.scene = Scene(root, 300.0, 300.0)
		primaryStage.show()
	}

	private suspend fun getIcon(): BufferedImage = withContext(Dispatchers.IO) {
		println("Reading an image from thread ${Thread.currentThread().name}")
		this@JavaFxBlocking.javaClass.getResourceAsStream("logo1.png").buffered().use {
			ImageIO.read(it)
		}
	}

	private companion object {
		@JvmStatic
		fun main(vararg args: String) {
			Application.launch(JavaFxBlocking::class.java, *args)
		}
	}
}
