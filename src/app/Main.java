package app;

import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        File inputImage = new File("src/images/cat.jpg");

        if (inputImage.exists()) {
            System.out.println("\nИсходное изображение: " + inputImage);

            BufferedImage loadedImage = ImageHandler.load(inputImage.getAbsolutePath());

            //Добавляем импульсный шум (15%)
            BufferedImage noisedImage = ImageHandler.impulseNoise(loadedImage, 0.15);
            String noisedImagePath = "src/images/cat_noised.jpg";
            ImageHandler.save(noisedImage, noisedImagePath);
            System.out.println("Зашумленное изображение: " + noisedImagePath);

            //Применяем медианный фильтр
            BufferedImage filteredImage = ImageHandler.medianFilter(noisedImage, 3, 3);
            String filteredImagePath = "src/images/cat_filtered.jpg";
            ImageHandler.save(filteredImage, filteredImagePath);
            System.out.println("Изображение после применения фильтра: " + filteredImagePath);
        } else {
            System.out.println("Файл cat.jpg не найден.");
        }
    }
}