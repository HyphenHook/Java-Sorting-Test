//Author: Wen Bin Zhang
//Date: December 15, 2020
//Purpose: Sorting: Testing most of the sorts
//=========================================
import java.awt.*;
import java.util.Arrays;
import hsa.Console;


/*-------------------------------------------------------------
Class Sorting
This class is showing functionality of the SortMethods class

Fields:
    N/A
Methods:
    drawGraph - draws the graph given the speed vs size data
    run       - run the test simulation
-------------------------------------------------------------*/
public class Sorting
{
    static Console c;           // The output console

    public static void main (String[] args)
    {
	c = new Console (45, 200);
	SortMethods test = new SortMethods (100);
	int input = 0;

	c.println ("What is the initial starting size: ");
	int sizeOf = c.readInt ();
	while (sizeOf <= 0 || sizeOf > 6000)
	{
	    if(sizeOf <= 0)
	    {
		c.println ("Error: Size is less than or equal to 0");
	    }
	    else
	    {
		c.println("Error: Starting size capped at 6000");
	    }
	    c.println ("What is the initial starting size: ");
	    sizeOf = c.readInt ();
	}

	c.println ("Sorting Simulation");
	c.println ("1 - Randomized Array Simulation");
	c.println ("2 - Reverse Sorted Array Simulation");
	c.println ("Which simulation?");
	input = c.readInt ();
	while (input <= 0 || input > 2)
	{
	    c.println ("Error: Invalid choice");
	    c.println ("Which simulation?");
	    input = c.readInt ();
	}
	c.clear();
	boolean reverse;
	if (input == 1)
	{
	    c.println ("Randomized Simulation");
	    reverse = false;
	}
	else
	{
	    c.println ("Reverse Sorted Simulation");
	    reverse = true;
	}

	String[] column = {"Size", "Bubble", "Selection", "Insertion", "Quick", "Comb", "Shell", "Heap"};
	String topCol = "";
	for (int count = 0 ; count < column.length ; count++)
	{
	    int space = 0;
	    if (count == 2 || count == 3)
	    {
		space = 15 - column [count].length ();
	    }
	    else
	    {
		space = 10 - column [count].length ();
	    }
	    for (int spaces = 0 ; spaces <= space ; spaces++)
	    {
		topCol += " ";
	    }
	    topCol += column [count];
	}
	c.println (topCol);
	int[] [] testData = run (reverse, sizeOf);
	drawGraph (testData);
    } // main method

    /*----------------------------------------------
    Method drawGraph
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: Graph the data given
    Input:  data       - two dimensional data
    Output: None
    ----------------------------------------------*/
    public static void drawGraph (int[] [] data)
    {
	c.drawLine (1000, 50, 1000, 450);
	c.drawLine (1000, 450, 1420, 450);
	int arrayCount = 0;
	c.drawString ("0", 990, 460);
	for (int count = 0 ; count < 400 ; count += 40)
	{
	    c.drawLine (1040 + count, 450, 1040 + count, 460);
	    c.drawString (Integer.toString (data [arrayCount] [0]), 1035 + count, 470);
	    arrayCount++;
	}
	arrayCount = 500;
	for (int count = 20 ; count <= 400 ; count += 20)
	{
	    c.drawLine (1000, 450 - count, 990, 450 - count);
	    c.drawString (Integer.toString (arrayCount), 960, 455 - count);
	    arrayCount += 500;
	}
	c.drawString ("Size(#):", 1210, 490);
	c.drawString ("Time(ms):", 900, 250);
	c.drawString ("Legend:", 1500, 50);
	for (int count = 1 ; count < 8 ; count++)
	{
	    int startX = 1000;
	    int startY = 450;
	    if (count == 1)
	    {
		c.setColor (Color.red);
		c.drawString ("Bubble", 1500, 70);
	    }
	    else if (count == 2)
	    {
		c.setColor (Color.yellow);
		c.drawString ("Selection", 1500, 90);
	    }
	    else if (count == 3)
	    {
		c.setColor (Color.blue);
		c.drawString ("Insertion", 1500, 110);
	    }
	    else if (count == 4)
	    {
		c.setColor (Color.orange);
		c.drawString ("Quick", 1500, 130);
	    }
	    else if (count == 5)
	    {
		c.setColor (Color.cyan);
		c.drawString ("Comb", 1500, 150);
	    }
	    else if (count == 6)
	    {
		c.setColor (Color.magenta);
		c.drawString ("Shell", 1500, 170);
	    }
	    else if (count == 7)
	    {
		c.setColor (Color.green);
		c.drawString ("Heap", 1500, 190);
	    }

	    for (int time = 0 ; time < 10 ; time++)
	    {
		if(data[time][count] > 10000)
		{
		    data[time][count] = 10000;
		}
		if (data [time] [count] != -1)
		{
		    c.drawLine (startX, startY, 1000 + ((time + 1) * 40), (450 - (int) (0.04 * data [time] [count])));
		    startX = 1000 + ((time + 1) * 40);
		    startY = 450 - (int) (0.04 * data [time] [count]);
		}
	    }
	}
    }

    /*----------------------------------------------
    Method run
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: Run all the sorts to time
    Input:  reverse - boolean to check for worstCase or randomized
	    sizeOf  - the size of the data from user input
    Output: data    - two dimensional array that stores speed and size
    ----------------------------------------------*/
    public static int[] [] run (boolean reverse, int sizeOf)
    {
	boolean bubbleOn, selectionOn, insertionOn, quickOn, combOn, shellOn, heapOn;
	bubbleOn = true;
	selectionOn = true;
	insertionOn = true;
	quickOn = true;
	combOn = true;
	shellOn = true;
	heapOn = true;
	SortMethods test;
	int timeTaken = 0;
	int[] [] data = new int [10] [8];
	int spaces = 10;
	for (int count = 0 ; count < 10 ; count++)
	{
	    test = new SortMethods (sizeOf);
	    data [count] [0] = sizeOf;
	    for (int space = 0 + Integer.toString (sizeOf).length () ; space <= spaces ; space++)
	    {
		c.print (" ");
	    }
	    c.print (sizeOf);
	    for (int type = 1 ; type < 8 ; type++)
	    {
		if (reverse == false)
		{
		    test.reset ();
		}
		else
		{
		    test.resetWorst ();
		}
		if (type == 1)
		{
		    if (bubbleOn == true)
		    {
			timeTaken = test.bubble ();
		    }
		    else
		    {
			timeTaken = -1;
		    }
		    if (timeTaken >= 10000)
		    {
			bubbleOn = false;
		    }
		}
		else if (type == 2)
		{
		    if (selectionOn == true)
		    {
			timeTaken = test.selection ();
		    }
		    else
		    {
			timeTaken = -1;
		    }
		    if (timeTaken >= 10000)
		    {
			selectionOn = false;
		    }
		    spaces = 15;
		}
		else if (type == 3)
		{
		    if (insertionOn == true)
		    {
			timeTaken = test.insertion ();
		    }
		    else
		    {
			timeTaken = -1;
		    }
		    if (timeTaken >= 10000)
		    {
			insertionOn = false;
		    }
		    spaces = 15;
		}
		else if (type == 4)
		{
		    if (quickOn == true)
		    {
			timeTaken = test.quick ();
		    }
		    else
		    {
			timeTaken = -1;
		    }
		    if (timeTaken >= 10000)
		    {
			quickOn = false;
		    }
		}
		else if (type == 5)
		{
		    if (combOn == true)
		    {
			timeTaken = test.comb ();
		    }
		    else
		    {
			timeTaken = -1;
		    }
		    if (timeTaken >= 10000)
		    {
			combOn = false;
		    }
		}
		else if (type == 6)
		{
		    if (shellOn == true)
		    {
			timeTaken = test.shell ();
		    }
		    else
		    {
			timeTaken = -1;
		    }
		    if (timeTaken >= 10000)
		    {
			shellOn = false;
		    }
		}
		else if (type == 7)
		{
		    if (heapOn == true)
		    {
			timeTaken = test.heap ();
		    }
		    else
		    {
			timeTaken = -1;
		    }
		    if (timeTaken >= 10000)
		    {
			heapOn = false;
		    }
		}

		int length = Integer.toString (timeTaken).length ();
		if (timeTaken == -1)
		{
		    length = 6;
		}
		for (int space = 0 + length ; space <= spaces ; space++)
		{
		    c.print (" ");
		}
		if (timeTaken != -1)
		{
		    c.print (timeTaken);
		}
		else
		{
		    c.print (">10sec");
		}
		data [count] [type] = timeTaken;
		spaces = 10;
	    }
	    sizeOf *= 2;
	    c.println ();
	}
	return data;

    }
} // Sorting class


/*-------------------------------------------------------------
Class SortMethods
This class maintains a SortMethods

Fields:
    original      - the original array
    aClone        - a clone of the original array
    size          - the size of the array
Methods:
    Constructors
    reset         - reset the clone array to the original array
    resetWorst    - reset the clone array to the reverse sorted original array
    toString      - print the array
    sorted        - check if the array is sorted
    bubble        - sort via bubble sorting
    selection     - sort via selection sorting
    insertion     - sort via insertion sorting
    quick         - sort via quick sort
    quickSort     - the actual quick sort called by quick
    heap          - sort via heap sort
    heapSort      - the actual heap sort called by heap
    comb          - sort via comb sort
    shell         - sort via shell sort
-------------------------------------------------------------*/
class SortMethods
{
    protected int[] original;
    protected int[] aClone;
    protected int size;
    
    /*----------------------------------------------
    Constructor for class SortMethods
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: Constructor for class SortMethods
    Input:      s - the size of the array
    Output: None
    ----------------------------------------------*/
    public SortMethods (int s)
    {
	if (s < 0)
	{
	    s = 100;
	}
	size = s;
	original = new int [size];
	for (int count = 0 ; count < size ; count++)
	{
	    original [count] = (int) (Math.random () * size);
	}
    }

    /*----------------------------------------------
    Overloaded constructor for class SortMethods
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: Constructor for class SortMethods
    Input:  None
    Output: None
    ----------------------------------------------*/
    public SortMethods ()
    {
	this(0);
    }
    
    /*----------------------------------------------
    Method reset
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: Set the clone array to the clone of original array
    Input:  None
    Output: None
    ----------------------------------------------*/
    public void reset ()
    {
	aClone = (int[]) original.clone ();
    }

    /*----------------------------------------------
    Method resetWorst
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: Set the clone array to the clone of original array and reverse sort it
    Input:  None
    Output: None
    ----------------------------------------------*/
    public void resetWorst ()
    {
	reset ();
	for (int count = 0 ; count < size ; count++)
	{
	    aClone [count] *= -1;
	}
	Arrays.sort (aClone);
	for (int count = 0 ; count < size ; count++)
	{
	    aClone [count] *= -1;
	}
    }

    /*----------------------------------------------
    Method toString
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: prints the clone array
    Input:  None
    Output: the clone array in a string
    ----------------------------------------------*/
    public String toString ()
    {
	String output = "";
	for (int count = 0 ; count < size ; count++)
	{
	    output += aClone [count] + " ";
	}
	return output.trim ();
    }

    /*----------------------------------------------
    Method sorted
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: check if the array is sorted
    Input:  None
    Output: isSorted - boolean to see if it is sorted
    ----------------------------------------------*/
    public boolean sorted ()
    {
	boolean isSorted = true;
	for (int count = 1 ; count < size ; count++)
	{
	    if (aClone [count] < aClone [count - 1])
	    {
		isSorted = false;
	    }
	}
	return isSorted;
    }

    /*----------------------------------------------
    Method bubble
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: sorted array via bubble sort
    Input:  None
    Output: timeTaken - returns the time it takes
    ----------------------------------------------*/
    public int bubble ()
    {
	long start, stop, timeTaken;
	start = System.currentTimeMillis ();
	for (int count = 0 ; count < size ; count++)
	{
	    for (int count2 = 0 ; count2 < size - count - 1 ; count2++)
	    {
		if (aClone [count2] > aClone [count2 + 1])
		{
		    int temp = aClone [count2];
		    aClone [count2] = aClone [count2 + 1];
		    aClone [count2 + 1] = temp;
		}
	    }
	}
	stop = System.currentTimeMillis ();
	timeTaken = stop - start;
	return (int) timeTaken;
    }

    /*----------------------------------------------
    Method selection
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: sorted array via selection sort
    Input:  None
    Output: timeTaken - returns the time it takes
    ----------------------------------------------*/
    public int selection ()
    {
	long start, stop, timeTaken;
	start = System.currentTimeMillis ();
	for (int count = 0 ; count < size - 1 ; count++)
	{
	    int min = count;
	    for (int count2 = count + 1 ; count2 < size ; count2++)
	    {
		if (aClone [count2] < aClone [min])
		{
		    min = count2;
		}
	    }
	    int temp = aClone [min];
	    aClone [min] = aClone [count];
	    aClone [count] = temp;
	}
	stop = System.currentTimeMillis ();
	timeTaken = stop - start;
	return (int) timeTaken;
    }

    /*----------------------------------------------
    Method insertion
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: sorted array via insertion sort
    Input:  None
    Output: timeTaken - returns the time it takes
    ----------------------------------------------*/
    public int insertion ()
    {
	long start, stop, timeTaken;
	start = System.currentTimeMillis ();
	for (int count = 1 ; count < size ; count++)
	{
	    int key = aClone [count];
	    int count2 = count - 1;
	    while (count2 >= 0 && aClone [count2] > key)
	    {
		aClone [count2 + 1] = aClone [count2];
		count2--;
	    }
	    aClone [count2 + 1] = key;
	}
	stop = System.currentTimeMillis ();
	timeTaken = stop - start;
	return (int) timeTaken;
    }

    /*----------------------------------------------
    Method quick
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: sorted array via quick sort
    Input:  None
    Output: timeTaken - returns the time it takes
    ----------------------------------------------*/
    public int quick ()
    {
	long start, stop, timeTaken;
	start = System.currentTimeMillis ();
	quickSort (0, size - 1);
	stop = System.currentTimeMillis ();
	timeTaken = stop - start;
	return (int) timeTaken;
    }

    /*----------------------------------------------
    Method quickSort (protected)
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: actually sorting using the quick sort method
    Input:  x - the x position
	    y - the y position
    Output: None
    ----------------------------------------------*/
    protected void quickSort (int x, int y)
    {
	int left = x - 1;
	int right = y + 1;
	int pivot = aClone [(x + y) / 2];
	do
	{
	    left++;
	    right--;
	    while (left < y && aClone [left] < pivot)
	    {
		left++;
	    }
	    while (right > x && aClone [right] > pivot)
	    {
		right--;
	    }
	    if (left < right)
	    {
		int temp = aClone [left];
		aClone [left] = aClone [right];
		aClone [right] = temp;
	    }
	}
	while (left <= right);
	if (x < right)
	{
	    quickSort (x, right);
	}
	if (left < y)
	{
	    quickSort (left, y);
	}
    }

    /*----------------------------------------------
    Method heap
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: sorted array via heap sort
    Input:  None
    Output: timeTaken - returns the time it takes
    ----------------------------------------------*/
    public int heap ()
    {
	long start, stop, timeTaken;
	start = System.currentTimeMillis ();
	for (int count = size / 2 - 1 ; count >= 0 ; count--)
	{
	    heapSort (size, count);
	}
	for (int count = size - 1 ; count >= 0 ; count--)
	{
	    int temp = aClone [0];
	    aClone [0] = aClone [count];
	    aClone [count] = temp;
	    heapSort (count, 0);
	}
	stop = System.currentTimeMillis ();
	timeTaken = stop - start;
	return (int) timeTaken;
    }

    /*----------------------------------------------
    Method heapSort (protected)
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: actually sorting using the heap sort method
    Input:  sizeOf - the size of the current array
	    count  - the index of the array
    Output: None
    ----------------------------------------------*/
    protected void heapSort (int sizeOf, int count)
    {
	int largest = count;
	int left = 2 * count + 1;
	int right = 2 * count + 2;
	if (left < sizeOf && aClone [left] > aClone [largest])
	{
	    largest = left;
	}
	if (right < sizeOf && aClone [right] > aClone [largest])
	{
	    largest = right;
	}
	if (largest != count)
	{
	    int temp = aClone [count];
	    aClone [count] = aClone [largest];
	    aClone [largest] = temp;
	    heapSort (sizeOf, largest);
	}

    }

    /*----------------------------------------------
    Method comb
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: sorted array via comb sort
    Input:  None
    Output: timeTaken - returns the time it takes
    ----------------------------------------------*/
    public int comb ()
    {
	long start, stop, timeTaken;
	start = System.currentTimeMillis ();
	int gap = size;
	boolean inorder;
	do
	{
	    gap = (int) Math.max ((0.77 * gap), 1);
	    inorder = true;
	    for (int count = 0 ; count <= size - gap - 1 ; count++)
	    {
		int position = count + gap;
		if (aClone [count] > aClone [position])
		{
		    int temp = aClone [count];
		    aClone [count] = aClone [position];
		    aClone [position] = temp;
		    inorder = false;
		}
	    }
	}
	while (gap > 1 || inorder == false);
	stop = System.currentTimeMillis ();
	timeTaken = stop - start;
	return (int) timeTaken;
    }

    /*----------------------------------------------
    Method shell
    Author: Wen Bin Zhang
    Date: December 14, 2020
    Purpose: sorted array via shell sort
    Input:  None
    Output: timeTaken - returns the time it takes
    ----------------------------------------------*/
    public int shell ()
    {
	long start, stop, timeTaken;
	start = System.currentTimeMillis ();
	int gap = 1;
	while (gap <= size)
	{
	    gap = gap * 3 + 1;
	}

	do
	{
	    gap = gap / 3;
	    for (int count = gap ; count <= size - 1 ; count++)
	    {
		int hold = aClone [count];
		int count2 = count - gap;
		while (count2 >= 0 && aClone [count2] > hold)
		{
		    aClone [count2 + gap] = aClone [count2];
		    count2 = count2 - gap;
		}
		aClone [count2 + gap] = hold;
	    }
	}
	while (gap > 1);
	stop = System.currentTimeMillis ();
	timeTaken = stop - start;
	return (int) timeTaken;
    }
}
