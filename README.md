# Movie Theater Seats Assigning System
### Project Description
This is a program which implements an algorithm for assigning seats within a movie theater to fulfill reservation requests.

Assume the movie theater has the seating arrangement of 10 rows x 20 seats.
In this program, I designed and wrote a seat assignment program to maximize both customer satisfaction and theater utilization.

### Usage
First we need to compile two .java file.
```
cd MovieSeatsArrangement/src
javac MSA/SeatsAssigning.java
javac MSA/Solution.java
```
Then just run Solution.java with input file path as the first argument.
```
java MSA/Solution YOUR/INPUT/FILE/PATH
```
System will print out the output file path and the assigning results are in that file.

### Input and Output Files Description
* **Input Description**
The file contains one line of input for each reservation request. The order of the lines in the file reflects the order in which the reservation requests were received. Each line in the file will be comprised of a reservation identifier, followed by a space, and then the number of seats requested. The reservation identifier will have the format: R####. The example input file rows are shown below.
*Example Input File Rows:*
R001 2
R002 4
R003 4
R004 3
...
* **Output Description**
The output file contains the seating assignments for each request. Each row in the file should include the reservation number followed by a space, and then a comma-delimited list of the assigned seats. The example output file rows are shown below.
*Example Output Rows:*
R001 I1,I2
R002 F16,F17,F18,F19
R003 A1,A2,A3,A4
R004 J4,J5,J6
...

# Assumption
This algorithm is based on several assumptions:
* Customers in the same booking request **always** intend to seat together
* Customers will **always** intend to seat at center part of one row unless they have no choice
* Customers will **always** intend to seat at last five rows unless they have no choice
* When two groups of customers are in the same row, they will separate as far as possible
* When customers only have the option to seat in the front five rows, they intend to seat as back as possible
* If there is no contiguous seats, customers in the same booking request will be assigned to seats that are available in sequence.
* If the number of customers in the same booking request exceeds one rows capacity, the system will split the customers into two groups and treat these two groups of customers separately.

# Demo
Suppose the movie theater seats now looks like this:
J: O O O O O O O O O O O O O O O O O O O O 
I: O O O O O O O O O O O O O O O O O O O O 
H: O O O O O O O O O O O O O O O O O O O O 
G: O O O O O O O O O O O O O O O O O O O O 
F: O O O O O O O O O O O O O O O O O O O O 
E: O O O O O O O O O O O O O O O O O O O O 
D: O O O O O O O O O O O O O O O O O O O O 
C: O O O O O O O O O O O O O O O O O O O O 
B: O O O O O O O O O O O O O O O O O O O O 
A: O O O O O O O O O O O O O O O O O O O O 

0 meats the seat is available while 1 means it's occupied.
When there comes in a booking order which includes 10 customers:
J: O O O O O O O O O O O O O O O O O O O O 
I: O O O O O O O O O O O O O O O O O O O O 
H: O O O O O O O O O O O O O O O O O O O O 
G: O O O O O O O O O O O O O O O O O O O O 
F: O O O O O X X X X X X X X X X O O O O O 
E: O O O O O O O O O O O O O O O O O O O O 
D: O O O O O O O O O O O O O O O O O O O O 
C: O O O O O O O O O O O O O O O O O O O O 
B: O O O O O O O O O O O O O O O O O O O O 
A: O O O O O O O O O O O O O O O O O O O O 

Then there comes another 12:
J: O O O O O O O O O O O O O O O O O O O O 
I: O O O O O O O O O O O O O O O O O O O O 
H: O O O O O O O O O O O O O O O O O O O O 
G: O O O O X X X X X X X X X X X X O O O O 
F: O O O O O X X X X X X X X X X O O O O O 
E: O O O O O O O O O O O O O O O O O O O O 
D: O O O O O O O O O O O O O O O O O O O O 
C: O O O O O O O O O O O O O O O O O O O O 
B: O O O O O O O O O O O O O O O O O O O O 
A: O O O O O O O O O O O O O O O O O O O O 

Suppose their comes anther 3 requests, after filling the last five rows:
J: O X X X X X X X X X X X X X X X X X X O 
I: O O X X X X X X X X X X X X X X X X O O 
H: O O O O X X X X X X X X X X X X O O O O 
G: O O O O X X X X X X X X X X X X O O O O 
F: O O O O O X X X X X X X X X X O O O O O 
E: O O O O O O O O O O O O O O O O O O O O 
D: O O O O O O O O O O O O O O O O O O O O 
C: O O O O O O O O O O O O O O O O O O O O 
B: O O O O O O O O O O O O O O O O O O O O 
A: O O O O O O O O O O O O O O O O O O O O 

Then here comes a request with the #customers = 3. We will arrange them in F and let them stay in the center of the available contiguous seats:
J: O X X X X X X X X X X X X X X X X X X O 
I: O O X X X X X X X X X X X X X X X X O O 
H: O O O O X X X X X X X X X X X X O O O O 
G: O O O O X X X X X X X X X X X X O O O O 
F: O O O O O X X X X X X X X X X O **X X X** O 
E: O O O O O O O O O O O O O O O O O O O O 
D: O O O O O O O O O O O O O O O O O O O O 
C: O O O O O O O O O O O O O O O O O O O O 
B: O O O O O O O O O O O O O O O O O O O O 
A: O O O O O O O O O O O O O O O O O O O O 

When there comes another two customers, they will pick up their seats begin from G:
J: O X X X X X X X X X X X X X X X X X X O 
I: O O X X X X X X X X X X X X X X X X O O 
H: O O O O X X X X X X X X X X X X O O O O 
G: O O O O X X X X X X X X X X X X O **X X** O 
F: O O O O O X X X X X X X X X X O X X X O 
E: O O O O O O O O O O O O O O O O O O O O 
D: O O O O O O O O O O O O O O O O O O O O 
C: O O O O O O O O O O O O O O O O O O O O 
B: O O O O O O O O O O O O O O O O O O O O 
A: O O O O O O O O O O O O O O O O O O O O 

Now there comes 5 customers. The only available contiguous seats in the last five rows are in F, so it looks like this:
J: O X X X X X X X X X X X X X X X X X X O 
I: O O X X X X X X X X X X X X X X X X O O 
H: O O O O X X X X X X X X X X X X O O O O 
G: O O O O X X X X X X X X X X X X O X X O 
F: ***X X X X X*** X X X X X X X X X X O X X X O 
E: O O O O O O O O O O O O O O O O O O O O 
D: O O O O O O O O O O O O O O O O O O O O 
C: O O O O O O O O O O O O O O O O O O O O 
B: O O O O O O O O O O O O O O O O O O O O 
A: O O O O O O O O O O O O O O O O O O O O 

When there comes another 10 customers, since there are no ten contiguous available seats, the system will assign **E** to them.
J: O X X X X X X X X X X X X X X X X X X O 
I: O O X X X X X X X X X X X X X X X X O O 
H: O O O O X X X X X X X X X X X X O O O O 
G: O O O O X X X X X X X X X X X X O X X O 
F: X X X X X X X X X X X X X X X O X X X O 
E: O O O O O ***X X X X X X X X X X*** O O O O O 
D: O O O O O O O O O O O O O O O O O O O O 
C: O O O O O O O O O O O O O O O O O O O O 
B: O O O O O O O O O O O O O O O O O O O O 
A: O O O O O O O O O O O O O O O O O O O O
