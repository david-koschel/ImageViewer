package controller;

import model.Image;
import persistence.ImageLoader;
import ui.ImageDisplay;
import ui.SwingImageDisplay;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private ImageDisplay imageDisplay;
    private final int totalImages;
    private final JLabel imageNumberLabel = new JLabel();
    private final JLabel imageNameLabel = new JLabel();

    public MainFrame(ImageLoader imageLoader) {
        this.setTitle("Image Viewer");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.totalImages = imageLoader.getTotalImages();
        this.getContentPane().add(imageDisplay(imageLoader.load()));
        this.getContentPane().add(toolbar(), BorderLayout.SOUTH);
        this.getContentPane().add(name(), BorderLayout.NORTH);
        this.setVisible(true);
    }

    private JPanel name() {
        JPanel panel = new JPanel();
        panel.add(imageNameLabel);
        return panel;
    }

    private JPanel toolbar() {
        JPanel panel = new JPanel();
        if (totalImages > 1) {
            panel.add(prevButton());
            panel.add(nextButton());
            panel.add(imageNumberLabel);
        }
        if (totalImages == 1) panel.add(new JLabel("Showing only image"));
        return panel;
    }

    private JButton nextButton() {
        JButton button = new JButton(">");
        button.addActionListener(e -> {
            imageDisplay.show(imageDisplay.current().next());
            updateInfoLabels();
        });
        return button;
    }

    private JButton prevButton() {
        JButton button = new JButton("<");
        button.addActionListener(e -> {
            imageDisplay.show(imageDisplay.current().prev());
            updateInfoLabels();
        });
        return button;
    }

    private void updateInfoLabels() {
        this.imageNumberLabel.setText(String.format("Image %d of %d", imageDisplay.current().pos() + 1, totalImages));
        this.imageNameLabel.setText(imageDisplay.current().name());
    }

    private JPanel imageDisplay(Image image) {
        if (totalImages < 1) {
            JPanel panel = new JPanel();
            panel.add(new JLabel("There are no images :("));
            return panel;
        }
        SwingImageDisplay sid = new SwingImageDisplay();
        this.imageDisplay = sid;
        sid.show(image);
        this.updateInfoLabels();
        return sid;
    }
}
