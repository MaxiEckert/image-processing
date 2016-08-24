import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Test
{
   public static void main( String[] args )
   {
      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
      Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
      System.out.println( "mat = " + mat.dump() );
      
      String filePath = "src/main/resources/IMG_4090.JPG";
      Mat newImage = Imgcodecs.imread(filePath);

      Imgproc.cvtColor(newImage, newImage, Imgproc.COLOR_BGR2GRAY);


      if(newImage.dataAddr()==0){
			System.out.println("Couldn't open file " + filePath);
		}else{
			ImageViewer imageViewer = new ImageViewer();
			imageViewer.show(newImage, "Loaded image");
}
   }
}