package rsd.dsp;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * Convolve2D is an Object Oriented class that carries out image processing<br>
 * convolution on a BufferedImage by using a specified kernel.<br>
 * The kernel will be a matrix of dimension 3x3, 5x5, 7x7, etc ...<br>
 * Use the KernelFactory2D class to create well formatted kernels<br>
 * Example code showing how to use Convolve2D and KernelFactory2D to carry out
 * a convolution on a BufferedImage.<p>
 *  {@code 	// bi is BufferedImage from somewhere in program			}<br>
 *  {@code 	// ...														}<br>
 *  {@code 	Convolve2D convolve2D = new Convolve2D();					}<br>
 *  {@code 	convolve2D.setBufferedImage(bi);							}<br>
 *  {@code 	KernelFactory2D kf2D = KernelFactory2D.getInstance();		}<br>
 *  {@code	int[][] kernel = kf2D.getKernelSmoothingGaussianBlur(3, 2);	}<br>
 *  {@code	convolve2D.setKernel(kernel);								}<br>
 *  {@code	// ...														}<br>
 *  {@code	// execute the convolution and get the returned BufferedImage	}<br>
 *  {@code	BufferedImage img = convolve2D.convolve();						}<br>
 */
public class Convolve2D 
{
	// private member fields that can only be accessed through getters and setters.
	// this is to provide data integrity, especially for the kernel, which
	// must adhere to correct dimensionality.
	private BufferedImage bi;
	private int[][] kernel;
	
	/** Default constructor.
	 */
	public Convolve2D() { }
	
	/**
	 * Convolve2D constructor with arguments as shown.<br>
	 * @param bi A reference to a BufferedImage object.
	 * @param kernel A reference to a square 2D int array, whose side length must
	 * 		  be a positive odd value of 3 or greater.<br>
	 * @throws IllegalArgumentException thrown if bi or kernel are null or if kernel does not have the proper dimension.
	 */
	public Convolve2D( BufferedImage bi, int[][] kernel) throws IllegalArgumentException
	{ 
		if( bi == null )
			throw new IllegalArgumentException("BufferedImage reference is null");
		
		if( verifyKernelSquareOdd(kernel) == false )
			throw new IllegalArgumentException("matrix not square odd or less than side length 3");
		
		// if arguments are legal, then continue with assignments
		this.bi = bi;
		this.kernel = kernel;
	}
	
	/**
	 * Constructor with only the BufferedImage. Caution when using this, because the programmer
	 * will have to create a kernel and use the setKernel method on this object to create a 
	 * canonical Convolve2D object.<br>
	 * @param bi A reference to a BufferedImage 
	 * @throws IllegalArgumentException thrown if bi is null.
	 */
	public Convolve2D( BufferedImage bi ) throws IllegalArgumentException
	{ 
		if( bi == null )
			throw new IllegalArgumentException("BufferedImage reference is null");
	
		this.bi = bi;
	}
	
	/**
	 * Sets the BufferedImage field for this object.<br>
	 * @param bi A reference to a BufferedImage object.<br>
	 * @throws IllegalArgumentException thrown if bi is null
	 */
	public void setBufferedImage(BufferedImage bi) throws IllegalArgumentException
	{
		if( bi == null )
			throw new IllegalArgumentException("BufferedImage reference is null");
		
		this.bi = bi;
	}
	
	/**
	 * Sets the kernel for this object.<br>
	 * @param kernel A reference to a square 2D int array, whose side length must
	 * 		  be a positive odd value of 3 or greater.<br>
	 * @throws IllegalArgumentException thrown kernel is null or does not have the proper dimension.
	 */
	public void setKernel(int[][] kernel) throws IllegalArgumentException
	{		
		if( verifyKernelSquareOdd(kernel) == false )
			throw new IllegalArgumentException("matrix not square odd or less than side length 3");
		
		this.kernel = kernel;
	}
	
	/**
	 * This method will verify if the matrix parameter is a 
	 * square odd 2D int array whose side length must is a positive odd value of 3 or greater.<br>
	 * @param m int[][] array representing the kernel.
	 * @return true if kernel is valid, false if not valid.
	 */
	private boolean verifyKernelSquareOdd(int[][] k)
	{		
		// if matrix is null, we fail the test
		if( k == null )
			return false;
		
		// if matrix is even, we fail the test
		if( k.length % 2 == 0 )
			return false;
		
		// if matrix is only length 1, we fail the test
		if( k.length == 1 )
			return false;
		
		// lastly, check if matrix is square
		for( int i = 0; i < k.length; i++ )
		{
			if( k[i].length != k.length )
				return false;
		}
		
		// all tests passed, return true
		return true;
	}
	
	/**
	 * Return's a copy of the BufferedImage, while not
	 * affecting the original BufferedImage.<br>
	 * Converts each pixel to gray by taking the average of the rgb value for
	 * each pixel and assigning that to the pixel.<br>
	 * The alpha value is retained.<br>
	 * The BufferedImage object does not have to be the object stored in this Convolve2D
	 * object's field. We did this for maximum flexibility.<br>
	 * However,this method is called internally from the convolve method, because all
	 * BufferedImage are first converted to grayscale before any convolving operation.
	 * @param bi A reference to a BufferedImage object.
	 * @return BufferedImage A new deep copy that is converted to grayscale.
	 */
	public BufferedImage convertToGrayScalePixelByPixel(BufferedImage bi)
	{
		BufferedImage img = creatDeepCopyBufferedImage(bi);
		
	    for (int x = 0; x < img.getWidth(); ++x)
	    {
		    for (int y = 0; y < img.getHeight(); ++y)
		    {
		        int rgb 	= img.getRGB(x, y);
		        int alpha 	= (rgb >> 24) 	& 0xFF;
		        int red 	= (rgb >> 16) 	& 0xFF;
		        int green 	= (rgb >> 8) 	& 0xFF;
		        int blue 	= (rgb & 0xFF);
	
		        // get the average value for r + g + b
		        int grayLevel = (red + green + blue) / 3;
		        
		        // reassign the same value for each r, g, b
		        int gray = 	( alpha << 24) 	+ 
		        			( grayLevel << 16) 	+ 
		        			( grayLevel << 8) 	+ 
		        			  grayLevel; 
		        
		        // set the pixel to the gray value
		        img.setRGB(x, y, gray);
		    }
	    }
	    
	    return img;
	}
	
	/**
	 * Creates a deep copy of the BufferedImage and returns it, while not
	 * affecting the original BufferedImage.<br>
	 * @param bi A reference to a BufferedImage object.
	 * @return BufferedImage a new copy of the bi
	 */
	public BufferedImage creatDeepCopyBufferedImage(BufferedImage bi) 
	{
	    ColorModel cm 					= bi.getColorModel();
	    boolean isAlphaPremultiplied 	= cm.isAlphaPremultiplied();
	    
	    WritableRaster raster = 
	    		bi.copyData(bi.getRaster().createCompatibleWritableRaster());
	    
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	

	/**
	 * Convolves the BufferedImage using the kernel.<br>
	 * An Exception is thrown if either of the BufferedImage or kernel 
	 * parameters have not been initialized.<br>
	 * @return BufferedImage the convolved BufferedImage
	 * @throws Exception thrown if BufferedImage or kernel have not be set for this object.
	 */
	public BufferedImage convolve() throws Exception
	{
		if( bi == null || kernel == null )
			throw new Exception("missing critical values for convolution.");
		
		// first convert the image to gray scale
		BufferedImage grayImg = convertToGrayScalePixelByPixel(bi);
		
		// create a copy of the image
		BufferedImage convolvedImg = creatDeepCopyBufferedImage(grayImg);
		
		// We will run the convolution on the grayImg, pixel by pixel
		// The output will be placed into the corresponding pixel location 
		// of the copy.
		
    	// create a temporary matrix that will represent
    	// a tile of the matrix we are working on and
    	// prime it with 1s.
    	// it should always be 3x3, 5x5, 7x7, etc ...
    	// the same dimension as the kernel
		// we will reuse this array for every single pixel
    	int[][] tile = createLogicalOnesArray2D(kernel.length);
		
		// double for loop to access each pixel and
		// perform the convolution calculation
	    for (int x = 0; x < grayImg.getWidth(); ++x)
	    {
		    for (int y = 0; y < grayImg.getHeight(); ++y)
		    {
		    	// TODO
		    	// the creation of the tile matrix will
		    	// be softcoded to match the same dimension
		    	// as that of the kernel		    	
				
				// pass the tile and grayImg by reference and fill
				// the proper values for the tile, based around the
				// pixel at [x,y]
				fillTile(grayImg, x, y, tile);
				
				// get the sum of the products fore each element to each corresponding 
				// element for the kernel and the tile 
				int sum 	= 0;
				int divisor = 0;
				for( int k = 0; k < kernel.length; k++ )
				{
					for( int m = 0; m < kernel[k].length; m++ )
					{
						//if( kernel[k][m] != 0 )
						//	divisor += 1;
						// the divisor is the sum of the kernel
						divisor += kernel[k][m];
						
						// only use the low byte for tile[k][m] because
						// it be the same value as the r and g values
						sum += kernel[k][m] * ( tile[k][m] & 0xFF );
					}
				}
				
				// divide the sum by divisor
				int newVal = sum / divisor;
				
				// this newVal must be between 0 and 255
				if( newVal < 0 )
					newVal = 0;
				
				if( newVal > 255 )
					newVal = 255;
		    	
		        //int rgb 	= grayImg.getRGB(x, y);
		        //int alpha 	= (rgb >> 24) & 0xFF;
		    	// get the blue value for the pixel at hand, and
		    	// the 8 pixels around it
		        //int val 	= (rgb & 0xFF);
		        
		        // now put the newVal into one integer
		        // reassign the same value for each r, g, b because it
		        // is  gray scale
		        int finalVal =  (newVal << 16) + (newVal << 8) + newVal; 
		        
		        // set the pixel to the gray value
		        convolvedImg.setRGB(x, y, finalVal);
		    }
	    }
	    
	    return convolvedImg;
	}
	
	/**
	 * createLogicalOnesArray2D<br>
	 * Creates a 2D int array that is square, with all values equal to 1.<br>
	 * @param sideLength
	 * @return
	 */
	private int[][] createLogicalOnesArray2D(int sideLength)
	{
		int[][] logicalMatrix = new int[sideLength][sideLength];
		
	    for (int x = 0; x < sideLength; ++x)
	    {
		    for (int y = 0; y < sideLength; ++y)
		    {
		    	logicalMatrix[x][y] = 1;
		    }
	    }
	    
	    return logicalMatrix;
	}
	
	
	/**
	 * fillTile	 
	 * Construct the proper values for an image's tile, based around the 
	 * pixel at [x,y] in the BufferedImage.
	 * If the pixel [x,y] is on the border of the image, then the tile
	 * values will remain unchanged from what it was initialized at, 1
	 * @param bi
	 * @param tile
	 * @param x
	 * @param y
	 */
	private void fillTile(BufferedImage bi, int x, int y, int[][] tile )
	{
		// note the center of the tile is at [1,1]
		// the center of the tile will correspond to the [x,y] for bi
		
		// get the length of one side of the tile and create a slide value
		// which will be a number corresponding to the center value of the
		// side length
		// This slide value is used to adjust the pixel relativity in the 
		// double for loop below.
		int slide = tile.length / 2;
		
		for( int i = 0; i < tile.length; i++ )
		{
			for( int j = 0; j < tile[i].length; j++ )
			{
				// determine adjusted values for x and y
				int xAdjusted = x - slide + i;
				int yAdjusted = y - slide + j;
				
				if( xAdjusted < 0 || xAdjusted >= bi.getWidth() ||
					yAdjusted < 0 || yAdjusted >= bi.getHeight() )
				{
					// just use the value of the pixel at  x, y
					tile[i][j] = bi.getRGB(x, y);
				} else {
					tile[i][j] = bi.getRGB(xAdjusted, yAdjusted);
				}
			}
		}
	}
}
