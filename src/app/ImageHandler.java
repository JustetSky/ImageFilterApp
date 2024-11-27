package app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

public class ImageHandler {
    public static BufferedImage load(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) throw new IOException("Файл не найден: " + filePath);
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(BufferedImage image, String filePath) {
        try {
            File outputFile = new File(filePath);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            ImageIO.write(image, "jpg", outputFile);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public static BufferedImage impulseNoise(BufferedImage image, double noiseRatio) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        Random random = ThreadLocalRandom.current();

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                if (random.nextDouble() < noiseRatio) {
                    int noiseColor = random.nextBoolean() ? 0 : 255;
                    int noiseRGB = (noiseColor << 16) | (noiseColor << 8) | noiseColor;
                    image.setRGB(x, y, noiseRGB);
                }
            }
        }
        return image;
    }

    public static BufferedImage medianFilter(BufferedImage imageNoise, int filterHeight, int filterWidth) {
        int width = imageNoise.getWidth();
        int height = imageNoise.getHeight();
        int filterHalfWidth = filterWidth / 2;
        int filterHalfHeight = filterHeight / 2;
        BufferedImage filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        for (int y = filterHalfHeight; y < height - filterHalfHeight; y++) {
            for (int x = filterHalfWidth; x < width - filterHalfWidth; x++) {
                int medianColor = calculateMedianColor(imageNoise, x, y, filterHeight, filterWidth);
                filteredImage.setRGB(x, y, medianColor);
            }
        }
        return filteredImage;
    }

    private static int calculateMedianColor(BufferedImage imageNoise, int x, int y, int filterHeight, int filterWidth) {
        int[] filterValues = new int[filterHeight * filterWidth];
        int id = 0;

        for (int dy = -filterHeight / 2; dy <= filterHeight / 2; dy++) {
            for (int dx = -filterWidth / 2; dx <= filterWidth / 2; dx++) {
                filterValues[id++] = imageNoise.getRGB(x + dx, y + dy);
            }
        }

        Arrays.sort(filterValues);
        return filterValues[filterValues.length / 2];
    }
}