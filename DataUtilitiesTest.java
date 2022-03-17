package org.jfree.data;

import static org.junit.Assert.*; 
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jmock.*;
import org.junit.*;
import org.jfree.data.KeyedValues;
import org.jfree.data.DefaultKeyedValues;

public class DataUtilitiesTest {

	private Values2D values;
  
	//Mocking Values2D data to use when testing the methods
    @Before
    public void setUp() throws Exception { 
	    Mockery mockingContext = new Mockery();
	    values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {
	        {
	        	//creating 3x3 mocked object array
	            one(values).getRowCount();
	            will(returnValue(3));
	            one(values).getColumnCount();
	            will(returnValue(3));
	            
	            //setting values in first row [1.5, 2, 3.5]
	            one(values).getValue(0, 0);
	            will(returnValue(1.5));
	            one(values).getValue(0, 1);
	            will(returnValue(2));
	            one(values).getValue(0, 2);
	            will(returnValue(3.5));
	            
	            //setting values in second row [2, 4, 5]
	            one(values).getValue(1, 0);
	            will(returnValue(2));
	            one(values).getValue(1, 1);
	            will(returnValue(4));
	            one(values).getValue(1, 2);
	            will(returnValue(5));
	            
	            //setting values in third/last row [3, 6, 1.5]
	            one(values).getValue(2, 0);
	            will(returnValue(3));
	            one(values).getValue(2, 1);
	            will(returnValue(6));
	            one(values).getValue(2, 2);
	            will(returnValue(1.5));
	            
	            //null values set for column = -1 (out of bounds)
	            one(values).getValue(0, -1);
	            will(returnValue(null));
	            one(values).getValue(1, -1);
	            will(returnValue(null));
	            one(values).getValue(2, -1);
	            will(returnValue(null));
	            
	            //null values set for column = 3 (out of bounds)
	            one(values).getValue(0, 3);
	            will(returnValue(null));
	            one(values).getValue(1, 3);
	            will(returnValue(null));
	            one(values).getValue(2, 3);
	            will(returnValue(null));
	            
	            //null values set for row = -1 (out of bounds)
	            one(values).getValue(-1, 0);
	            will(returnValue(null));
	            one(values).getValue(-1, 1);
	            will(returnValue(null));
	            one(values).getValue(-1, 2);
	            will(returnValue(null));
	            
	            //null values set for row = 3 (out of bounds)
	            one(values).getValue(3, 0);
	            will(returnValue(null));
	            one(values).getValue(3, 1);
	            will(returnValue(null));
	            one(values).getValue(3, 2);
	            will(returnValue(null));
	        }
	    });
    }
  //==//Mutation//==//
    @Test
    public void test_clone_replaceEqualityCheckWithTrue105()
    {
        double [][] a = new double[5][5];
        a[0] = null;
        double[][] result = DataUtilities.clone(a);
        double[][] expected = new double[5][5] ;
        expected[0] = null;
        assertArrayEquals("testing the equal method mutation replaceEqualityCheckWithTrue at line 105", expected, result);
    }
//===//Mutation//==//
    @Test
    public void test_equal_equalToLessOrEqual81()
    {
        double [][] a = {{2},{3}, {5}, {6}};
        double [][] b = {{2},{4}};
        boolean result = DataUtilities.equal(a, b);
        boolean expected = false;
        assertEquals("testing the equal method mutation equalToLessOrEqual at line 81", expected, result);
    }

    @Test
    public void test_equal_equalToGreaterOrEqual()
    {
        double [][] a = {{2}};
        double [][] b = {{2},{4},{5}};
        boolean result = DataUtilities.equal(a, b);
        boolean expected = false;
        assertEquals("testing the equal method mutation equalToGreaterOrEqual at line 81", expected, result);
    }

    @Test
    public void test_equal_removedConditional81()
    {
        double [][] a = {{2},{4},{5}};
        double [][] b = {{2},{4}};
        boolean result = DataUtilities.equal(a, b);
        boolean expected = false;
        assertEquals("testing the equal method mutation removedConditional at line 81", expected, result);
    }

    @Test
    public void test_equal_removedConditional78()
    {
        double [][] a = {{2},{4}};
        double [][] b = null;
        boolean result = DataUtilities.equal(a, b);
        boolean expected = false;
        assertEquals("testing the equal method mutation removedConditional at line 78", expected, result);
    }

    @Test
    public void test_equal_substitutedZeroWithOne84()
    {
        double [][] a = {{4}, {2}};
        double [][] b = {{5}, {2}};
        boolean result = DataUtilities.equal(a, b);
        boolean expected = false;
        assertEquals("testing the equal method mutation substitutedZeroWithOne at line 84", expected, result);
    }

    // =====================================================================
    //Tests for calculateColumnTotal(Values2D data, int column): double
    // =====================================================================
    
    //This test covers the invalid class/partition for first input variable
    @Test (expected = IllegalArgumentException.class)
   	public void test_calculatedColumnTotal_invalidData() {
    	DataUtilities.calculateColumnTotal(null, 0);
    }
    

    //This test covers invalid class for second input variable, nothing to sum since column values are null
    @Test
   	public void test_calculatedColumnTotal_negativeColumn() {
    	double result = DataUtilities.calculateColumnTotal(values, -1);
    	assertEquals("Summing all the values in an out of bounds (neg) column is",  0, result, .000000001d);
    }
   
    //This test covers invalid class for second input variable where column index 
	//is larger than data size, nothing to sum since column values are null
    @Test
   	public void test_calculatedColumnTotal_outOfBoundsColumn() {
    	double result = DataUtilities.calculateColumnTotal(values, 3);
    	assertEquals("Summing all the values in an out of bounds column is", 0, result,  .000000001d);
    }
    
    //This test covers valid input for second variable but at boundary of valid and invalid
    @Test
  	public void test_calculatedColumnTotal_columnZero() {
  	    double result = DataUtilities.calculateColumnTotal(values, 0);
  	    assertEquals("Summing all the values in the first column is", 6.5, result, .000000001d);
  	}
   
	//This test covers valid input for second variable where it selects one sample within 
	//equivalence class not at the boundary
    @Test
	public void test_calculatedColumnTotal_validColumn() {
    	double result = DataUtilities.calculateColumnTotal(values, 1);
	    assertEquals("Summing all the values in the second column is", 12, result, .000000001d);
    }
    
    //This test covers valid input for second variable but at boundary of valid and invalid
	//index right at the maximum size of the data
    @Test
   	public void test_calculatedColumnTotal_columnAtSize() {
    	double result = DataUtilities.calculateColumnTotal(values, 2);
	    assertEquals("Summing all the values in the last column is",  10, result, .000000001d);
	
    }
    
    // =========================================================================================
    // Tests for calculateColumnTotal(Values2D data, int column, int[] validRows): double
    // =========================================================================================
    //=========LAB4=============
    @Test (expected = IllegalArgumentException.class)
    public void test_calculatedColumnTotal_NullPermitted(){
    	Values2D values= null;
    	int[] rows= {100};
    	double result = DataUtilities.calculateColumnTotal(values, 1, rows);
    }
    //=========END LAB4=============
    @Test
    public void test_calculatedColumnTotal_rowGreaterThanRowCount() {
    	int[] rows= {100};
    	double result = DataUtilities.calculateColumnTotal(values, 1, rows);
	    assertEquals("Summing all the values in the secound column for an out of bounds (neg) row", 0, result, .000000001d);
    }
    //This test covers invalid class for third input variable using a negative row number
    @Test
   	public void test_calculatedColumnTotal_negativeIntForRows() {	
    	int[] rows = {-1};
    	double result = DataUtilities.calculateColumnTotal(values, 1, rows);
	    assertEquals("Summing all the values in the secound column for an out of bounds (neg) row", 0, result, .000000001d);
    }
    
    //This test covers invalid class for third input variable where row index in valid rows is larger than data size
    @Test
    public void test_calculatedColumnTotal_outOfBoundsRow() {
    	int[] rows = {3};
    	double result = DataUtilities.calculateColumnTotal(values, 1, rows);
	    assertEquals("Summing all the values in the second column for an out of bounds row", 0, result, .000000001d);
    }
    	
    //This test covers valid input for third variable but at boundary of valid and invalid
    @Test
   	public void test_calculatedColumnTotal_rowZero() {
    	int[] rows = {0};
    	double result = DataUtilities.calculateColumnTotal(values, 1, rows);
	    assertEquals("Summing all the values in the second column for the first row", 2, result, .000000001d);
    }
    	
    //This test covers valid input for third variable, one sample within equivalence class not at boundary
    @Test
   	public void test_calculatedColumnTotal_allValidRows() {
    	
    	int[] rows = {1,2};
    	double result = DataUtilities.calculateColumnTotal(values, 1, rows);
	    assertEquals("Summing all the values in the second column for the middle and last row", 10, result, .000000001d);
    }
    
	//This test covers valid input for third variable but at boundary of valid and invalid
	//index right at the maximum size of the data
    @Test
   	public void test_calculatedColumnTotal_rowAtSize() {
    	int[] rows = {2};
    	double result = DataUtilities.calculateColumnTotal(values, 1, rows);
	    assertEquals("Summing the values in the second column for the last row", 6, result, .000000001d);
    }
    
    // ======================================================================
    // Tests cases for calculateRowTotal(Values2D data, int row): double
    // =======================================================================
    
    //This test covers the invalid class/partition for first input variable
    @Test (expected = IllegalArgumentException.class)
   	public void test_calculatedRowTotal_invalidData() {
    	DataUtilities.calculateRowTotal(null, 0);
    }
    
    //This test covers invalid class for second input variable
    @Test
   	public void test_calculatedRowTotal_negativeRow() {	
    	double result = DataUtilities.calculateRowTotal(values, -1);
    	assertEquals("Summing all the values in an out of bounds (neg) row is",  0, result, .000000001d);
    }
   
    //This test covers invalid class for second input variable where row index larger than data size 
    @Test
   	public void test_calculatedRowTotal_outOfBoundsRow() {
    	double result = DataUtilities.calculateRowTotal(values, 3);
    	assertEquals("Summing all the values in an out of bounds row is", 0, result, .000000001d);
    }
    
    //This test covers valid input for second variable but at boundary of valid and invalid
    @Test
  	public void test_calculatedRowTotal_rowZero() {
  	    double result = DataUtilities.calculateRowTotal(values, 0);
  	    assertEquals("Summing all the values in the first row is", 7, result, .000000001d);
  	}
   
    //This test covers valid input for second variable, one sample within equivalence class not at boundary
    @Test
	public void test_calculatedRowTotal_validRow() {	
    	double result = DataUtilities.calculateRowTotal(values, 1);
	    assertEquals("Summing all the values in the second row is",  11, result, .000000001d);
    }
    
    //This test covers valid input for second variable but at boundary of valid and invalid
	//index right at the maximum size of the data
    @Test
   	public void test_calculatedRowTotal_rowAtSize() {
    	double result = DataUtilities.calculateRowTotal(values, 2);
	    assertEquals("Summing all the values in the last row is", 10.5, result, .000000001d);
    }
    
    // ======================================================================================
    // Tests cases for calculateRowTotal(Values2D data, int row, int[] validCols): double
    // =======================================================================================
    @Test (expected = IllegalArgumentException.class)
    public void test_calculatedRowTotal_NullData() {
    	int[] col= {100};
    	Values2D values=null;
    	double result = DataUtilities.calculateRowTotal(values, 1, col);
    }
    @Test
    public void test_calculatedRowTotal_ColCountGreaterThanCol() {
    	int[] col= {100};
    	double result = DataUtilities.calculateRowTotal(values, 1, col);
        assertEquals("Testing for invalid class for second input variable, negative column number", 0.0, result, .000000001d);
    }
    
    //Invalid class for second input variable, negative column number
    @Test
    public void test_calculatedRowTotal_negativeIntForValidColumn()
    {  
        int[] col= {-1};
        double result = DataUtilities.calculateRowTotal(values, 1, col);
        assertEquals("Testing for invalid class for second input variable, negative column number", 0.0, result, .000000001d);
    }
    
    //Invalid class for third input variable where column index in valid columns is larger than data size
    @Test
    public void test_calculatedRowTotal_OutOfBoundsColumn()
    { 
        int[] col= {3};
        double result = DataUtilities.calculateRowTotal(values, 1, col);
        assertEquals("Testing for invalid class for third input variable where column index in valid columns is 3", 0.0, result, .000000001d);
    }
    
    //Valid input for second variable, one sample within equivalence class not at boundary
    @Test
    public void test_calculatedRowTotal_ColumnZero()
    {   
        int[] col= {0};
        double result = DataUtilities.calculateRowTotal(values, 1, col);
        assertEquals("Testing for valid input for second variable, one sample within equivalence class not at boundary", 2.0, result, .000000001d);
    }
    
    //Valid input for second variable, one sample within equivalence class not at boundary
    @Test
    public void test_calculatedRowTotal_allValidColumns()
    {  
        int[] col= {1,2};
        double result = DataUtilities.calculateRowTotal(values, 1, col);
        assertEquals("Testing for valid input for second variable, one sample within equivalence class not at boundary", 9.0, result, .000000001d);
    }
    
    //Valid input for second variable but at boundary of valid and invalid, index right at the maximum size of the data
    @Test
    public void test_calculatedRowTotal_ColumnAtSize()
    { 
        int[] col= {2};
        double result = DataUtilities.calculateRowTotal(values, 1, col);
        assertEquals("Testing for valid input for second variable but at boundary of valid and invalid, index right at the maximum size of the data", 5.0, result, .000000001d);
    }

    // ======================================================
    // Tests cases for createNumberArray(double[]):Number[]
    // ======================================================
    
    // This test case checks invalid argument
    // by supplying a null value for data (incoming argument).
    // the test case expects the method to throw an exception. 
    // exercise
    @Test(expected = IllegalArgumentException.class)
    public void test_createNumberArray_invalidData() {
        double[] data = null; 
        DataUtilities.createNumberArray(data);
    }

    // This test case checks valid argument for the createNumberArray(double[]):Number[]
    // by supplying a double array for the data (incoming argument).
    // this test case expects the method to properly convert the incoming double[] into
    // a Number[] object array
	@Test
	public void test_createNumberArray_validData() {
		double[] data = {4.14, 9.14, 10.0};	
	     Number[] result = DataUtilities.createNumberArray(data);
	     // verify
	     Number[] expected = {4.14, 9.14, 10.0};
	     assertArrayEquals("creation of Number[] array from double[] results in: ", expected, result);
	}

    // ===============================================================
    // Tests cases for createNumberArray2D(double[][]):Number[][]
    // ===============================================================

	// This test case checks invalid argument for the createNumberArray2D(double[][]):Number[][]
    // by supplying a null value for data (incoming argument).
    // the test case expects the method to throw an exception. 
	// exercise
	@Test(expected = IllegalArgumentException.class)
	public void test_createNumberArray2D_invalidData() {
        double[][] data = null; 
        DataUtilities.createNumberArray2D(data);
	}


	// This test case checks valid argument for the createNumberArray2D(double[][]):Number[][]
    // by supplying a double 2D array for the data (incoming argument).
    // this test case expects the method to properly convert the incoming double[][] into
    // a Number[][] object array
	@Test
	public void test_createNumberArray2D_validData() {
        double[][] data = {{4.14}, {9.14}, {10.0}};
		Number[][] result = DataUtilities.createNumberArray2D(data);
		// verify
		Number[][] expected = {{4.14}, {9.14}, {10.0}};
		assertArrayEquals("Creation of Number[][] array from double[][] results in: ", expected, result);
	}
	
	// =====================================================================
    // Tests cases for getCumulativePercentage(KeyedValues ): KeyedValues
    // =====================================================================
	//==========LAB 4=================
	@Test
	public void test_getCumulativePercentage_vGetValueIsNull() {
		   // setup
	     Mockery mockingContext = new Mockery();
	     final KeyedValues values = mockingContext.mock(KeyedValues.class);
	     mockingContext.checking(new Expectations() {
	         {
	        	 atLeast(2).of(values).getItemCount();
	             will(returnValue(2));
	             
	             atLeast(2).of(values).getValue(0); //index
	             will(returnValue(null)); //value at index 0
	             atLeast(2).of(values).getValue(1); //index
	             will(returnValue(2)); // value at index 1
	             
	             atLeast(2).of(values).getKey(0); // key at index 0
	             will(returnValue(0)); // key returned 
	             atLeast(2).of(values).getKey(1); //key at index 1
	             will(returnValue(1)); // key returned
	         }
	     });
	     KeyedValues result = DataUtilities.getCumulativePercentages(values);
	     System.out.println(result.getValue(0));
	     System.out.println(result.getValue(1));
	     final DefaultKeyedValues expected = new DefaultKeyedValues();
	     Integer key0 = 0;
	     Integer key1 = 1;
	     expected.addValue(key0, 0.0);
	     expected.addValue(key1, 1.0);
	  
	     // verify
	     assertEquals("Cumulation of percentage results in the following. ", expected, result);
	}
	
	
	//==========END LAB 4=================
	// This test case checks an invalid entry by supplying a null argument for the
    // data argument and expects the method to throw an exception in this case.
	@Test(expected = IllegalArgumentException.class)
	public void test_getCumulativePercentage_invalidData() {
        KeyedValues data = null; 
        DataUtilities.getCumulativePercentages(data);
	}

	// This method tests valid entry for the getCumulativePercentage method by suppling
    // non-null and unique values for the argument object which is the KeyedValues.
    // it uses jMock to mock the methods and objects used in this method. 
	@Test
	public void test_getCumulativePercentage_validData() {
           // setup
	     Mockery mockingContext = new Mockery();
	     final KeyedValues values = mockingContext.mock(KeyedValues.class);
	     mockingContext.checking(new Expectations() {
	         {
	        	 atLeast(2).of(values).getItemCount();
	             will(returnValue(2));
	             
	             atLeast(2).of(values).getValue(0); //index
	             will(returnValue(2)); //value at index 0
	             atLeast(2).of(values).getValue(1); //index
	             will(returnValue(2)); // value at index 1
	             
	             atLeast(2).of(values).getKey(0); // key at index 0
	             will(returnValue(0)); // key returned 
	             atLeast(2).of(values).getKey(1); //key at index 1
	             will(returnValue(1)); // key returned
	         }
	     });
	     KeyedValues result = DataUtilities.getCumulativePercentages(values);
	     
	     final DefaultKeyedValues expected = new DefaultKeyedValues();
	     Integer key0 = 0;
	     Integer key1 = 1;
	     expected.addValue(key0, 0.5);
	     expected.addValue(key1, 1);
	  
	     // verify
	     assertEquals("Cumulation of percentage results in the following. ", expected, result);
	}
       
	//======================================================================================
    // Tests cases for clone method clone(double[][] source): double[][]
    // =======================================================================================
	
    //This test covers the invalid class/partition for the input variable
    @Test (expected = IllegalArgumentException.class)
   	public void test_clone_invalidData() {
    	DataUtilities.clone(null);
    }
	
	// This method tests valid entry for the clone method by suppling
    // an empty initialized source array for the argument object
	@Test
	public void test_clone_validData_emptyArray() {
		double[][] source = new double[2][2];// = [[1,2,3][1,2,2]];
        double[][] result = DataUtilities.clone(source);
        assertArrayEquals("Testing for valid inputs in the clone method using an empty source array", source, result); //, .000000001d);
	}
	
	// This method tests valid entry for the clone method by suppling
    // non-null values for the argument object
	@Test
	public void test_clone_validData_setValues() {
		double[][] source = {{1,2.5},{1.5,2}};
        double[][] result = DataUtilities.clone(source);
        assertArrayEquals("Testing for valid class in the clone method using non-null values", source, result);
	}
	
	//======================================================================================
    // Tests cases for equal method
    // =======================================================================================

	 @Test
	    public void test_equal_invalidDataMutationTest()
	    {
	    	double [][] b = {{2},{3}};
	        boolean result = DataUtilities.equal(null, b);
	        boolean expected = false;
	        assertEquals("testing the equal method with both arrays null", expected, result);
	    }
	 
    // This method tests entry for the equal method by suppling
    // null values for the two arguments and checks if they are equal or not.
    @Test
    public void test_equal_invaliddata()
    {
        boolean result = DataUtilities.equal(null, null);
        boolean expected = true;
        assertEquals("testing the equal method with both arrays null", expected, result);
    }

    // This method tests entry for the equal method by suppling
    // the same, non-null values for the two arguments and checks if they are equal or not.
    @Test
    public void test_equal_validdata_matchingArray()
    {
        double [][] a = {{2},{3}};
        double [][] b = {{2},{3}};
        boolean result = DataUtilities.equal(a,b);
        boolean expected = true;
        assertEquals("testing the equal method with both arrays equal",expected, result);
    }

    // This method tests entry for the equal method by suppling
    // different, non-null values for the two arguments and checks if they are equal or not.
    @Test
    public void test_equal_validdata_unmatchingArray()
    {
        double [][] a = {{2},{3}};
        double [][] b = {{2},{4}};
        boolean result = DataUtilities.equal(a, b);
        boolean expected = false;
        assertEquals("testing the equal method with both arrays unequal", expected, result);
    }
}