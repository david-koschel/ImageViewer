package persistence;

import model.Image;

import java.io.*;
import java.util.Arrays;

public class FileImageLoader implements ImageLoader {

    private final static String[] imageExtensions = new String[]{".jpg", ".png", ".jpeg", ".bmp"};
    private final File[] files;
    private final int totalImages;

    public FileImageLoader(File folder) {
        FileFilter fileFilter = pathname -> Arrays.stream(imageExtensions)
                .anyMatch(extension -> pathname.getName().toLowerCase().endsWith(extension));
        this.files = folder.listFiles(fileFilter);
        this.totalImages = files == null ? 0 : files.length;
    }

    public int getTotalImages() {
        return totalImages;
    }

    public Image imageAt(int i) {
        return new Image() {
            @Override
            public String name() {
                return files[i].getName();
            }

            @Override
            public InputStream stream() {
                try {
                    return new BufferedInputStream(new FileInputStream(files[i]));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public Image next() {
                return i == totalImages - 1 ? imageAt(0) : imageAt(i + 1);
            }

            @Override
            public Image prev() {
                return i == 0 ? imageAt(totalImages - 1) : imageAt(i - 1);
            }

            @Override
            public int pos() {
                return i;
            }
        };
    }

    @Override
    public Image load() {
        return imageAt(0);
    }
}
