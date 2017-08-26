package rsd.dsp;

/**
 * KernelFactory is a Singleton class that is used to create or spawn
 * 2D int arrays, refered to as a kernel.<br>
 * KernelFactory has no fields and it's only purpose is to generate kernels on the fly.<br>
 * All of the kernels returned from the getXXX methods will be a 2D square int[][] array 
 * with a side length being an odd number equal to or greater than 3.<p>
 * 
 * Using any of the getXXX methods that do not have parameters will always return
 * a square matrix of side length 3, this is the default kernel size used
 * in image processing convolution operations.
 * 
 * @author Lance Dooley, Robotic Systems Design (rsd)
 * @since 2017
 */
public class KernelFactory2D 
{
	private KernelFactory2D() { }
	
	// this private static field is created as soon as the 
	// class is loaded in the JVM
	private static final KernelFactory2D instance = new KernelFactory2D();
	
	/**
	 * Returns a Singleton instance of this class.<br>
	 * @return The Singleton KernelFactory2D instance.
	 */
	public static KernelFactory2D getInstance() {
		return instance;
	}
	
	/**
	 * Creates a default square odd kernel of side length 3 and
	 * all values equal to 1.<br>
	 * @return int[][] The 2D integer kernel
	 * @throws IllegalArgumentException thrown if problem forming the kernel.
	 */
	public int[][] getKernelSmoothingUnweighted() throws IllegalArgumentException
	{
		/*
		int[][] kernel =  { {1,1,1}, 
							{1,1,1}, 
							{1,1,1} };
		*/
		
		return getKernelSmoothingUnweighted(3);
	}
	
	/**
	 * Creates a square odd kernel with all values equal to 1.<br>
	 * @param side Muse be odd positive value of 3 or greater.
	 * @return int[][] The 2D integer kernel
	 * @throws IllegalArgumentException thrown if side does not adhere to restrictions mentioned above.
	 */
	public int[][] getKernelSmoothingUnweighted(int side) throws IllegalArgumentException
	{
		verifySideCompatibility(side, "getKernelSmoothingUnweighted");

		// create matrix, with all values initiazed to zero.
		int[][] kernel = new int[side][side];
		
		// loop through each element and assign value of 1
		for( int x= 0; x < kernel.length; x++ )
		{
			for( int y = 0; y < kernel[x].length; y++ )
			{
				kernel[x][y] = 1;
			}
		}
		
		return kernel;
	}
	
	/**
	 * Creates a default square odd kernel of side length 3 that provides a default
	 * gaussian smoothing blur effect when convolved with a BufferedImage.<br>
	 * @return int[][] The 2D integer kernel
	 * @throws IllegalArgumentException thrown if problem forming the kernel.
	 */
	public int[][] getKernelSmoothingGaussianBlur() throws IllegalArgumentException
	{
		//int[][] kernel =  { {0,1,0}, 
		//					{1,2,1}, 
		//					{0,1,0} };
		return getKernelSmoothingGaussianBlur(3, 2);
	}
	
	/**
	 * Creates a square odd kernel of the named specificity.<br>
	 * @param side Must be odd positive value of 3 or greater or IllegalArgumentException is thrown.<br>
	 * @param centerWeight the integer value of the centerWeight
	 * @return int[][] The 2D integer kernel
	 * @throws IllegalArgumentException thrown if problem forming the kernel.
	 */
	public int[][] getKernelSmoothingGaussianBlur(int side, int centerWeight) throws IllegalArgumentException
	{
		//int[][] kernel =  { {0,1,0}, 
		//					{1,2,1}, 
		//					{0,1,0} };
		
		verifySideCompatibility(side, "getKernelSmoothingGaussianBlur");
		
		// create matrix, with all values initialized to zero.
		int[][] kernel = new int[side][side];
		
		// loop through each element and assign the correct values that scale to
		// the template shown above.
		// It is a cross shape with centerWeight appearing in the middle
		for( int x= 0; x < kernel.length; x++ )
		{
			for( int y = 0; y < kernel[x].length; y++ )
			{
				if( x == side/2 && y == side/2 )
					kernel[x][y] = centerWeight;
				else if( x == side/2 )
					kernel[x][y] = 1;
				else if( y == side/2 )
					kernel[x][y] = 1;
				else
					kernel[x][y] = 0;
			}
		}
		
		return kernel;
	}
	
	/**
	 * Creates a default square odd kernel of side length 3 that will create a sharpening effect when convolved
	 * with a BufferedImage.<br>
	 * @return int[][] The 2D integer kernel
	 * @throws IllegalArgumentException thrown if problem forming the kernel.
	 */
	public int[][] getKernelSharping() throws IllegalArgumentException
	{
		//int[][] kernel =  { {0, -1, 0}, 
		//					{-1, 5, -1}, 
		//					{0, -1, 0} };
		return getKernelSharping(3, 5);
	}
	
	
	/**
	 * Creates a square odd kernel that will create a sharpening effect when convolved
	 * with a BufferedImage.<br>
	 * @param side Must be odd positive value of 3 or greater or IllegalArgumentException is thrown.<br>
	 * @param centerWeight the integer value of the centerWeight
	 * @return int[][] The 2D integer kernel
	 * @throws IllegalArgumentException thrown if problem forming the kernel.
	 */
	public int[][] getKernelSharping(int side, int centerWeight) throws IllegalArgumentException
	{
		//int[][] kernel =  { {0, -1, 0}, 
		//					{-1, 5, -1}, 
		//					{0, -1, 0} };
		
		verifySideCompatibility(side, "getKernelSharping");
		
		// create matrix, with all values initialized to zero.
		int[][] kernel = new int[side][side];
		
		// loop through each element and assign the correct values that scale to
		// the template shown above.
		// It is a cross shape with centerWeight appearing in the middle
		for( int x= 0; x < kernel.length; x++ )
		{
			for( int y = 0; y < kernel[x].length; y++ )
			{
				if( x == side/2 && y == side/2 )
					kernel[x][y] = centerWeight;
				else if( x == side/2 )
					kernel[x][y] = -1;
				else if( y == side/2 )
					kernel[x][y] = -1;
				else
					kernel[x][y] = 0;
			}
		}
		
		return kernel;
	}
	
	/**
	 * Creates a square odd kernel of side length 3 that will create an intensified sharpening effect when convolved
	 * with a BufferedImage.<br>
	 * @return int[][] The 2D integer kernel
	 * @throws IllegalArgumentException thrown if problem forming the kernel.
	 */
	public int[][] getKernelSharpingIntensified() throws IllegalArgumentException
	{
		// instensified sharpening kernel
		/*
		int[][] kernel =  { {-1, -1, -1}, 
							{-1, 9, - 1}, 
							{-1, -1, -1} };
		*/
		return getKernelSharpingIntensified(3, 9);
	}
	
	/**
	 * Creates a square odd kernel that will create an intensified sharpening effect when convolved
	 * with a BufferedImage.<br>
	 * @param side Must be odd positive value of 3 or greater or IllegalArgumentException is thrown.<br>
	 * @param centerWeight the integer value of the centerWeight
	 * @return int[][] The 2D integer kernel
	 * @throws IllegalArgumentException thrown if the side parameter does not adhere to the restrictions as mentioned.
	 */
	public int[][] getKernelSharpingIntensified(int side, int centerWeight) throws IllegalArgumentException
	{
		verifySideCompatibility(side, "getKernelSharpingIntensified");
		
		// create matrix, with all values initialized to zero.
		int[][] kernel = new int[side][side];
		
		// loop through each element and assign the correct values that scale to
		// the template shown above.
		for( int x= 0; x < kernel.length; x++ )
		{
			for( int y = 0; y < kernel[x].length; y++ )
			{
				if( x == side/2 && y == side/2 )
					kernel[x][y] = centerWeight;
				else
					kernel[x][y] = -1;
			}
		}
		
		return kernel;
	}
	
	/**
	 * This method will verify if the matrix parameter is a 
	 * square odd matrix that has a side length of 3, 5, 7, etc ...<br>
	 * @param m
	 * @return
	 */
	private void verifySideCompatibility(int side, String name) throws IllegalArgumentException
	{		
		if( side < 0 )
			throw new IllegalArgumentException(name + " side cannot be negative");
		
		if( side == 1 )
			throw new IllegalArgumentException(name + " side must be odd number of 3 or greater");
		
		// if side is even, we fail the test
		if( side % 2 == 0 )
			throw new IllegalArgumentException(name + " side must not be even number");
				
	}
	
}
