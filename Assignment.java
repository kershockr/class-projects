package AssignmentPackage;
import java.util.*;
import java.io.*;
public class Assignment
{
  public static void main(String[] args)
  {
    try
    {
      //populate and print the maze array
      Scanner in = new Scanner(new File("maze.txt"));
      char[][] mazeArray = new char[30][20];
      for(int i = 0; i < mazeArray.length; i++)
      {
        String line = in.next();
        for(int j = 0; j < line.length(); j++)
        {
          mazeArray[i][j] = line.charAt(j);
        }
      }
      printArray(mazeArray);
      
      StringStackClass[][] stackArray = new StringStackClass[30][20];
      initializeStackArray(stackArray);
      for(int r = 0; r < mazeArray.length; r++)
      {
        for(int c = 0; c < mazeArray[r].length; c++)
        {
          if(mazeArray[r][c] == '0')
          {

            if(r > 0 && mazeArray[r-1][c] == '0')
              stackArray[r][c].push(String.format("%02d %02d",(r - 1), c));
            if(c > 0 && mazeArray[r][c-1] == '0')
              stackArray[r][c].push(String.format("%02d %02d",r, (c - 1)));
            if(c < 19 && mazeArray[r][c+1] == '0')
              stackArray[r][c].push(String.format("%02d %02d",r, (c + 1)));
            if(r < 19 && mazeArray[r+1][c] == '0')
              stackArray[r][c].push(String.format("%02d %02d",(r + 1),  c));

          }
        }
      }

      //movement
      int startRow;
      int startCol;
      Scanner cin = new Scanner(System.in);
      startRow = getInt(cin, "Enter row coordinate of starting position: ");
      startCol = getInt(cin, "Enter column coordinate of starting position: ");

      while(startRow < 0 || startRow > 29 || startCol < 0 || startCol > 19 || mazeArray[startRow][startCol] != '0')
      {
        System.out.println("Invalid start position, try again.");
        startRow = getInt(cin, "Enter row coordinate of starting position: ");
        startCol = getInt(cin, "Enter column coordinate of starting position: ");
      }
      System.out.println("Valid starting point. Solving maze...");

      int positionRow = startRow;
      int positionCol = startCol;
      int nextPositionRow;
      int nextPositionCol;
      int prevPositionRow;
      int prevPositionCol;

      //while we aren't at the end and there is a way out of our current position

      while(mazeArray[positionRow][positionCol] != 'E' && !stackArray[positionRow][positionCol].isEmptyStack())
      {
        //if our position has no moves, break
        if(mazeArray[positionRow][positionCol] == 'E')
        {
          System.out.print("Maze Solved!");
          break;
        }
        /*
        if(stackArray[positionRow][positionCol].isEmptyStack())
        {
          System.out.print("No more moves!");
          printArray(mazeArray);
          break;
        }
        */

        String currentCoords = String.format("%02d %02d",positionRow, positionCol);
        String nextCoords = stackArray[positionRow][positionCol].peek();
        nextPositionRow = Integer.parseInt(nextCoords.substring(0,2));
        nextPositionCol = Integer.parseInt(nextCoords.substring(3,5));
        if(stackArray[nextPositionRow][nextPositionCol].peek().equals(currentCoords))
        {
          stackArray[nextPositionRow][nextPositionCol].pop();
        }
        stackArray[positionRow][positionCol].pop();
        positionRow = nextPositionRow;
        positionCol = nextPositionCol;

        System.out.println("Position = " + positionRow + "x" + positionCol);

        if(mazeArray[positionRow][positionCol] == '0')
        {
          mazeArray[positionRow][positionCol] = '+';
        }
        else if(mazeArray[positionRow][positionCol] == '+')
        {
          mazeArray[positionRow][positionCol] = '0';
        }


      }
      System.out.println("End Position = " + positionRow + "x" + positionCol);
      printArray(mazeArray);
  /*
      for(int t = 0; t < 20; t++)
      {
        if(stackArray[28][t].isEmptyStack())
        {
          System.out.print("No moves");
        }
        else
        {
          while (!stackArray[28][t].isEmptyStack())
          {
            System.out.print(stackArray[28][t].peek() + " ");
            stackArray[28][t].pop();
          }
        }
        System.out.println();
      }
  */
    } catch(FileNotFoundException ex)
    {
      System.out.println("File not found");
    }
  }
  
  public static void printArray(char[][] array)
  {
    for(int i = 0; i < array.length; i++)
      {
        for(int j = 0; j < array[i].length; j++)
        {
          System.out.print(array[i][j]);
        }
        System.out.println();
      }
  }

  public static void initializeStackArray(StringStackClass[][] s)
  {
    for(int i = 0; i < s.length; i++)
    {
      for(int j = 0; j < s[i].length; j++)
      {
        s[i][j] = new StringStackClass(4);
      }
    }
  }

  public static int getInt(Scanner cin, String prompt)
  {
    int input;
    System.out.print(prompt);
    while (!cin.hasNextInt())
    {
      System.out.print("Not an integer! try again! ");
      System.out.print(prompt);
      cin.next();
    }
    input = cin.nextInt();
    return input;
  }
}