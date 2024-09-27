package test.graphic;

import graphic.exception.NullImageException;

import java.awt.image.BufferedImage;

public class ImageComparator {

    public static boolean areImagesSimilar(BufferedImage image1, BufferedImage image2) {
        if (image1 == null || image2 == null) {
            throw new NullImageException("BufferedImage null");
        }
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            return false;
        }

        int width = image1.getWidth();
        int height = image1.getHeight();

        int numPixels = width * height;
        int numSimilarPixels = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb1 = image1.getRGB(x, y);
                int rgb2 = image2.getRGB(x, y);

                if (rgb1 == rgb2) {
                    numSimilarPixels++;
                }
            }
        }

        return numSimilarPixels == numPixels;
    }
}
