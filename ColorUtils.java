import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
public class ColorUtils {

    public static String detectColor(Mat frame) {

        // HSV me convert karna (better color detection)
        Mat hsv = new Mat();
        Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);

        // Center pixel pick karenge (accurate detection)
        int x = frame.cols() / 2;
        int y = frame.rows() / 2;

        double[] hsvPixel = hsv.get(y, x);
        double H = hsvPixel[0];
        double S = hsvPixel[1];
        double V = hsvPixel[2];

        // ---- COLOR CONDITIONS -----

        // RED
        if ((H < 10 || H > 160) && S > 80 && V > 80)
            return "RED";

        // GREEN
        if (H > 35 && H < 85 && S > 80 && V > 80)
            return "GREEN";

        // BLUE
        if (H > 90 && H < 140 && S > 80 && V > 80)
            return "BLUE";

        // YELLOW
        if (H > 20 && H < 35 && S > 80 && V > 80)
            return "YELLOW";

        // WHITE
        if (S < 20 && V > 200)
            return "WHITE";

        // BLACK
        if (V < 40)
            return "BLACK";

        return "UNKNOWN";
    }
}
