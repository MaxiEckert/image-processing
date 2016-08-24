import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ImageViewer {
	private JLabel imageView;
	
	public void show(Mat image){
		show(image, "");
	}

	public void show(Mat image,String windowName){
		setSystemLookAndFeel();
		
		JFrame frame = createJFrame(windowName);
        
        Image loadedImage = toBufferedImage(image);
        loadedImage = loadedImage.getScaledInstance(480, 640, java.awt.Image.SCALE_SMOOTH);
        imageView.setIcon(new ImageIcon(loadedImage));
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		
	}

	private JFrame createJFrame(String windowName) {
		JFrame frame = new JFrame(windowName);
		imageView = new JLabel();
		final JScrollPane imageScrollPane = new JScrollPane(imageView);
        imageScrollPane.setPreferredSize(new Dimension(480, 640));
        frame.add(imageScrollPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		return frame;
	}

	private void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	public Image toBufferedImage(Mat matrix){
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if ( matrix.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		System.out.println("Bildbreite: " + matrix.width());
		System.out.println("Bildhöhe: " + matrix.height());
		
		Core.flip(matrix.t(), matrix, 1);
		
		System.out.println("Bildbreite: " + matrix.width());
		System.out.println("Bildhöhe: " + matrix.height());
		
		// Create an empty image in matching format
		BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), type);

		// Get the BufferedImage's backing array and copy the pixels directly into it
		byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		matrix.get(0, 0, data);
		
		return image;
	}

}