package com.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.awt.BorderLayout
import java.awt.BorderLayout.CENTER
import java.awt.Dimension
import java.awt.Image.SCALE_SMOOTH
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities

/**
 * Swing with coroutines -- not affected.
 */
internal object SwingBlocking {
	@JvmStatic
	fun main(vararg args: String) {
		SwingUtilities.invokeLater {
			val label = JLabel()

			val image = runBlocking {
				getIcon()
			}
			label.icon = ImageIcon(image.getScaledInstance(300, 300, SCALE_SMOOTH))

			val frame = JFrame().apply {
				(contentPane as JPanel).apply {
					layout = BorderLayout()
					add(label, CENTER)
				}
				defaultCloseOperation = EXIT_ON_CLOSE
				preferredSize = Dimension(300, 300)
				isResizable = false
			}
			frame.pack()
			frame.isVisible = true
		}
	}

	private suspend fun getIcon(): BufferedImage = withContext(Dispatchers.IO) {
		println("Reading an image from thread ${Thread.currentThread().name}")
		SwingBlocking::class.java.getResourceAsStream("logo1.png").buffered().use {
			ImageIO.read(it)
		}
	}
}
