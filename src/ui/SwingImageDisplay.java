package ui;

import model.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SwingImageDisplay extends JPanel implements ImageDisplay {

    private Image currentImage;

    @Override
    public Image current() {
        return currentImage;
    }

    @Override
    public void show(Image image) {
        this.currentImage = image;
        this.repaint();
    }

    public java.awt.Image imageOf(Image image) {
        try {
            return getScaledImage(this.getWidth(), this.getHeight(), ImageIO.read(image.stream()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private java.awt.Image getScaledImage(int w, int h, BufferedImage image) {
        int x = image.getWidth();
        int y = image.getHeight();

        if (x <= w && y <= h) return image;
        if (x <= w) return reduceHeight(h, image);
        if (y <= h) return reduceWidth(w, image);
        return reduceWidthAndHeight(w, h, image);
    }

    private java.awt.Image reduceHeight(int h, BufferedImage image) {
        return image.getScaledInstance(image.getWidth() * h / image.getHeight(), h, java.awt.Image.SCALE_SMOOTH);
    }

    private java.awt.Image reduceWidth(int w, BufferedImage image) {
        return image.getScaledInstance(w, image.getHeight() * w / image.getWidth(), java.awt.Image.SCALE_SMOOTH);
    }

    private java.awt.Image reduceWidthAndHeight(int w, int h, BufferedImage image) {
        return (image.getWidth() - w > image.getHeight() - h) ? reduceWidth(w, image) : reduceHeight(h, image);
    }

    private int getImageXLocation(java.awt.Image image) {
        return (this.getWidth() - image.getWidth(null)) / 2;
    }

    private int getImageYLocation(java.awt.Image image) {
        return (this.getHeight() - image.getHeight(null)) / 2;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (currentImage == null) return;
        java.awt.Image image = imageOf(currentImage);
        g.drawImage(image, getImageXLocation(image), getImageYLocation(image), null);
    }
}
