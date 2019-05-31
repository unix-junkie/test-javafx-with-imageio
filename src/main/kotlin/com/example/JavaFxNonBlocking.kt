package com.example

import javafx.application.Application
import javafx.embed.swing.SwingFXUtils
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class JavaFxNonBlocking : Application() {
	override fun start(primaryStage: Stage) {
		val imageView = ImageView().apply {
			isPreserveRatio = true
			isSmooth = true
			fitWidth = 300.0
			fitHeight = 300.0
		}

		GlobalScope.launch(Dispatchers.JavaFx) {
			val image = getIcon()

			imageView.image = SwingFXUtils.toFXImage(image, null)
		}

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
		this@JavaFxNonBlocking.javaClass.getResourceAsStream("logo1.png").buffered().use {
			ImageIO.read(it)
		}
	}

	private companion object {
		@JvmStatic
		fun main(vararg args: String) {
			Application.launch(JavaFxNonBlocking::class.java, *args)
		}
	}
}
