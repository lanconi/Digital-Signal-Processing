package rsd.dsp;

import java.io.File;
import java.io.FileInputStream;
import java.awt.image.BufferedImage;


public class ConvolveTestHarness 
{
	public static void main(String[] args)
	{
		File file = new File( "koreaninstrument.jpg" );
		BufferedImage bi = readImage( file );
		
		// now cycle through each of the different types of
		// convolutions and save each one to a file
		
		Convolve2D convolve2D = new Convolve2D();
		convolve2D.setBufferedImage(bi);
		
		KernelFactory2D kf2D = KernelFactory2D.getInstance();		
		int[][] kernel1 = kf2D.getKernelSmoothingUnweighted();
		int[][] kernel2 = kf2D.getKernelSmoothingGaussianBlur();
		int[][] kernel3 = kf2D.getKernelSharping();
		int[][] kernel4 = kf2D.getKernelSharpingIntensified();
		int[][] kernel5 = kf2D.getKernelSharpingIntensified(5, 25);

		try {
			convolve2D.setKernel(kernel1);							
			BufferedImage img1 = convolve2D.convolve();	
			writeImageToFile(img1, new File("output1.jpg"));
			
			convolve2D.setKernel(kernel2);							
			BufferedImage img2 = convolve2D.convolve();	
			writeImageToFile(img2, new File("output2.jpg"));
			
			convolve2D.setKernel(kernel3);							
			BufferedImage img3 = convolve2D.convolve();	
			writeImageToFile(img3, new File("output3.jpg"));
			
			convolve2D.setKernel(kernel4);							
			BufferedImage img4 = convolve2D.convolve();	
			writeImageToFile(img4, new File("output4.jpg"));
			
			convolve2D.setKernel(kernel5);							
			BufferedImage img5 = convolve2D.convolve();	
			writeImageToFile(img5, new File("output5.jpg"));
			
		} catch( Exception ex ) {
			
		}
	}
	
	public static BufferedImage readImage(File file)
	{		
		BufferedImage originalImage = null;
		
		try {
			originalImage = javax.imageio.ImageIO.read(
									new FileInputStream( file ));
		} catch( Exception ex ) {
			originalImage = null;
			ex.printStackTrace();
		}
		return originalImage;
	}
	
	public static void writeImageToFile( BufferedImage bi, File file)
	{				
		try {
			javax.imageio.ImageIO.write( bi, "JPG", file);
			
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}
}
