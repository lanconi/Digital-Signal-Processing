# Digital-Signal-Processing
Code files for Digital Signal Processing algorithms

Use these Java classes to execute convolution on BufferedImages.<br>
Here is an example of how to do it ...<p>
<pre>
 // bi is BufferedImage from somewhere in program			
 *  {@code 	// ...														
 *  {@code 	Convolve2D convolve2D = new Convolve2D();					
 *  {@code 	convolve2D.setBufferedImage(bi);							
 *  {@code 	KernelFactory2D kf2D = KernelFactory2D.getInstance();		
 *  {@code	int[][] kernel = kf2D.getKernelSmoothingGaussianBlur(3, 2);
 *  {@code	convolve2D.setKernel(kernel);								
 *  {@code	// ...														
 *  {@code	// execute the convolution and get the returned BufferedImage
 *  {@code	BufferedImage img = convolve2D.convolve();	
 </pre>
