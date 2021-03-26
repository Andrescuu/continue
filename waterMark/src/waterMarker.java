import com.sun.org.apache.bcel.internal.generic.NEW;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class waterMarker {

    static void addWatermark(File pixelImageFile, File watermarkImageFile, File sourceImageFile, File destImageFile) {
        try {

            BufferedImage[] sprites = new BufferedImage[30];
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);
            BufferedImage watermarkImage = ImageIO.read(watermarkImageFile);
            BufferedImage pixelImage = ImageIO.read(pixelImageFile);

            // initializes necessary graphic properties
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
            AlphaComposite alphaChannel;


            // Assigns height and width and
            // calculates the coordinate where the image is painted
            // theWidth and theHeight are the dimensions for the image to be marked
            // waterWidth and waterHeight are dimensions for the watermark

            File testImageFile = new File("c:/JobStuff/wimage_test.png");

            int theWidth = sourceImage.getWidth();
            int theHeight = sourceImage.getHeight();

            System.out.println(theWidth);
            System.out.println(theHeight);

            int waterWidth = watermarkImage.getWidth();
            int waterHeight = watermarkImage.getHeight();
            int tracker = 0;
            int counter = 0;

            int pixelWidth = pixelImage.getWidth();
            int pixelHeight = pixelImage.getHeight();

            int horizontalVar = 10;
            int verticalVar = 10;
            int setter = -waterWidth + waterWidth/6;
            System.out.println(setter);
            // while the verticalVar is lower than original image height
            // we increase the verticalVar.
            // we do the same with horizontalVal except when it reaches theWidth
            // we reset horizontalVal

            while (verticalVar < theHeight) {
                if (horizontalVar > theWidth) {
                    horizontalVar = 10;
                    verticalVar += 5*waterHeight;
                    tracker = 1 - tracker;
                    if (tracker == 1){
                        horizontalVar = (setter);

                    }
                }

                // draw on the water mark
                alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.01f);
                g2d.setComposite(alphaChannel);
                g2d.drawImage(pixelImage, horizontalVar, verticalVar + 3 , null);

                alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f);
                g2d.setComposite(alphaChannel);
                g2d.drawImage(watermarkImage, horizontalVar, verticalVar, null);

                if (horizontalVar < theWidth - 100 & horizontalVar > 0 & verticalVar < theHeight - 15){
                    sprites[counter] = sourceImage.getSubimage(
                            horizontalVar,
                            verticalVar + 3,
                            100,
                            15
                    );
                    counter += 1;
                }

                if (horizontalVar == setter & verticalVar < theHeight - 15){
                    sprites[counter] = sourceImage.getSubimage(
                            0,
                            verticalVar + 3,
                            (100 + setter),
                            15
                    );
                    counter += 1;

                }


                horizontalVar += 2*waterWidth;







            }

            // export image
            System.out.println("Water mark created");
            ImageIO.write(sourceImage, "png", destImageFile);
            g2d.dispose();
            System.out.println("test mark created");
            ImageIO.write(sprites[2], "png", testImageFile);

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public static void main(String[] args) {
        File sourceImageFile = new File("c:/JobStuff/workImage.jpg");
        File destImageFile = new File("c:/JobStuff/zoneWatermarked.png");
        File watermarkImageFile = new File("c:/JobStuff/waterMark.png");
        File pixelImageFile = new File("c:/JobStuff/lastTry.png");

        addWatermark(pixelImageFile,watermarkImageFile, sourceImageFile, destImageFile);
    }
}