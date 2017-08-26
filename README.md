# Digital-Signal-Processing
Code files for Digital Signal Processing algorithms

Use these Java classes to execute convolution on BufferedImages.<br>
Here is an example of how to do it ...<p>
<pre>
 // bi is BufferedImage from somewhere earlier in program ...
							
 try {
   Convolve2D convolve2D = new Convolve2D();					
   convolve2D.setBufferedImage(bi);							
   KernelFactory2D kf2D = KernelFactory2D.getInstance();		
   int[][] kernel = kf2D.getKernelSmoothingGaussianBlur(3, 2);
   convolve2D.setKernel(kernel);								
										
   // execute the convolution and get the returned BufferedImage
   BufferedImage img = convolve2D.convolve();	
} catch( Exception ex ) {
  // do something with the Exception ...
}
</pre>
