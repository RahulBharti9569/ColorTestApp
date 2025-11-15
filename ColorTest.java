import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class ColorTest {

    public static void startCamera() {
        VideoCapture cap = new VideoCapture(0); // 0 → default camera

        if (!cap.isOpened()) {
            System.out.println("Camera Not Found!");
            return;
        }

        JFrame frame = new JFrame("Real-Time Color Detection");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel imgLabel = new JLabel();
        frame.add(imgLabel);
        frame.setVisible(true);

        Mat camFrame = new Mat();

        while (true) {
            cap.read(camFrame);
            if (camFrame.empty()) break;

            // Detect color using your method
            String detectedColor = ColorUtils.detectColor(camFrame);

            // NOTE: Here we force OpenCV Point (not AWT Point)
            Imgproc.putText(camFrame,
                    "Color: " + detectedColor,
                    new org.opencv.core.Point(20, 40),   // FIXED LINE
                    Imgproc.FONT_HERSHEY_SIMPLEX,
                    1.2,
                    new Scalar(0, 255, 0),
                    2
            );

            // Convert OpenCV Mat → BufferedImage
            ImageIcon icon = new ImageIcon(matToBufferedImage(camFrame));
            imgLabel.setIcon(icon);
            imgLabel.repaint();
        }

        cap.release();
        frame.dispose();
    }

    // Convert Mat to BufferedImage
    public static BufferedImage matToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_3BYTE_BGR;
        byte[] data = new byte[mat.rows() * mat.cols() * (int) mat.elemSize()];
        mat.get(0, 0, data);

        BufferedImage img = new BufferedImage(mat.cols(), mat.rows(), type);
        img.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);

        return img;
    }
}
