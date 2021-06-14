# [Codeforces Round #724 (Div. 2)](https://codeforces.com/contest/1536)

Language: Java 11.
With help of [Codeforces Round #724 — Editorial](https://codeforces.com/blog/entry/91520).

## [Omkar and Bad Story](https://codeforces.com/contest/1536/problem/A)

We first claim that if any negative number exists in `a`, then no solution exists. Denote `p` as the smallest number in `a` and `q` as another arbitrary number in the array (as `n≥2`, `q` always exists). Clearly, `|q−p|=q−p>0`. However, because `p` is negative, `q−p>q`. As such, adding `q−p` to the output array would create the pair `(q−p,p)` with difference `q−2p>q−p`. We have the same problem as before; thus, it is impossible to create a _nice_ array if there exists a negative number in `a`. 

After we deal with this case, we now claim that `b=[0,1,2,...,100]` is a valid _nice_ array for any `a` that contains no negative numbers. It is easy to verify that this is a valid _nice_ array. And since in this case, every element of `a` is nonnegative and distinct, it is always possible to rearrange and add elements to `a` to obtain `b`. Since we are already going through the array (in order to determine if there are negative numbers), it is more convenient to immediately determine the maximum element in the array and output from zero to the maximum value as a result.

    int maxNumber = 0;  
      
    for (int number : array) {  
        if (number < 0)  
            return "NO";  
      maxNumber = Math.max(maxNumber, number);  
    }  
      
    StringBuilder sb = new StringBuilder("YES\n");  
    sb.append(maxNumber + 1).append("\n");  
    for (int i = 0; i <= maxNumber; i++) {  
        sb.append(i).append(" ");  
    }  
    return sb.toString().trim();

Complexity: O(n)

## [Prinzessin der Verurteilung](https://codeforces.com/contest/1536/problem/B)

It is important to understand that having a word length limit of 1000 characters, it makes no sense for us to come up with an algorithm that would iterate over all possible substring sizes, since following the pigeonhole principle, the maximum size of the substring for which we need to search is 3.

To understand this, consider the following example:

- Substring size - 1, number of combinations - 26 (all letters of the English alphabet), maximum number of substrings in the input string: 1000
- Substring size - 2, number of combinations - 26^2^ = 678, maximum number of substrings in the input string: 999
- Substring size - 3, number of combinations - 26^3^ = 17576, maximum number of substrings in the input string: 998.

Thus, even without considering that we need to find the minimum substring, we can confidently say that for a substring size of 3, the maximum number of substrings is 998, and the maximum number of possible combinations of a substring of size 3 consisting of English alphabet characters is 17576. Which means that we will definitely get a substring that will not be in the input string.

Thus, the solution boils down to the fact that we first check whether a combination of a substring of size 1 is in the input string (if not, then this is our answer), then substrings of size 2, then substrings of size 3 (at this stage, we will definitely find a substring , which is not in the input line).

    private static String solveTask(String s) {
        HashSet<String> substrings = new HashSet<>(1000);
        for (int len = 1; len <= 3; len++) {
            for (int i = 0; i < s.length() - len + 1; i++) {
                substrings.add(s.substring(i, i + len));
            }
    
            String stringToFind = findString(substrings, len);
            if (stringToFind != null) return stringToFind;
        }
    
        throw new IllegalArgumentException("Input string should be less than 1000 characters");
    }

For a more efficient calculation, the input substring is first split into substrings, which in turn are stored in a HashSet, so that the substrings are received instantly. Searching for substrings in a method is done as follows:

    private static String findString(HashSet<String> substrings, int len) {
        if (len == 1) {
            for (char a = 'a'; a <= 'z'; a++) {
                String stringToFind = String.valueOf(a);
                if (!substrings.contains(stringToFind)) {
                    return stringToFind;
                }
            }
        }

        if (len == 2) {
            for (char a = 'a'; a <= 'z'; a++) {
                for (char b = 'a'; b <= 'z'; b++) {
                    String stringToFind = a + "" + b;
                    if (!substrings.contains(stringToFind)) {
                        return stringToFind;
                    }
                }
            }
        }

        if (len == 3) {
            for (char a = 'a'; a <= 'z'; a++) {
                for (char b = 'a'; b <= 'z'; b++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        String stringToFind = a + "" + b + "" + c;
                        if (!substrings.contains(stringToFind)) {
                            return stringToFind;
                        }
                    }
                }
            }
        }

        return null;
    }

Complexity: O(n)

## [Diluc and Kaeya](https://codeforces.com/contest/1536/problem/C)

To understand the solution to this problem, it is best to start disassembling it in geometric form. Let the vertical axis be the number of *K* characters, the horizontal axis the number of *D* characters. For each of the prefixes, draw points in accordance with the axes and connect them to each other. Next, from the origin, draw a line to each point.

Example for string: `DKDKDDDDK`. Lines from the origin are drawn only for important points (the rest are simplified for the sake of simplicity of the graph)

![plot](img/plot.png?raw=true)

You can notice that the number of parts into which the string can be divided so that the ratio of characters in it is the same is equal to the number of points that the line crosses from the origin to the prefix point (including it).

Thus, the following conclusion can be drawn: for each prefix, label it with a pair `(a,b)` where *a* = frequency of *D* in this prefix and *b* = frequency of *K* in this prefix. Divide *a* and *b* by `gcd(a,b)`. If we iterate over all prefixes from to left, we can notice that the answer for the prefix equals the # of occurrences of this pair we have seen so far!

So, this can be realized with this code:

        HashMap<String, Integer> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        int d = 0;
        int k = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'D') {
                d++;
            } else {
                k++;
            }
            String ratio = getRatio(d, k);
            map.put(ratio, map.containsKey(ratio) ? map.get(ratio) + 1 : 1);
            sb.append(map.get(ratio)).append(" ");
        }
        return sb.toString();

Where ratio is found like this:

    private static String getRatio(final int d, final int k) {
        int a = d;
        int b = k;

        if (a == 0) {
            b = 1;
        } else if (b == 0) {
            a = 1;
        } else {
            int gcd = gcd(a, b);
            a /= gcd;
            b /= gcd;
        }
        return a + ":" + b;
    }

    private static int gcd(int a, int b) {
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        return a == 0 ? b : gcd(b % a, a);
    }

Complexity: O(n)

## [Omkar and Medians](https://codeforces.com/contest/1536/problem/D)

The main idea of ​​the problem is to deal with the restrictions imposed on us by adding a new element (or changing the position of the old one) as a median. Knowing the position of the median, we have only a few positions for the new element to become the median:

1. Past median position
2. Position to the left of the median position, provided that the new selected value is greater than or equal to the value to the left of the median.
3. Position to the right of the median, provided that the new value is less than or equal to the value to the right of the median.

The first case is straightforward. We add some abstract numbers (abstract because we want to be able to add values ​​both before and after this abstract value) from the left and right edges.

The second case can be broken down into two sub-situations:
- If our value is greater than the value of the element to the left of the median, then we need to add our value to the left of the median, as well as add an abstract value from the left edge.
- If our value is equal to the value of the element to the left of the median, then in order to shift this element to the median position, we need to add two abstract elements from the left edge.

The opposite logic works for the third case as well.

If we implement this task through a doubly linked list, in which we always have a link to the median element, then everything becomes simpler, since we can simply not add abstract values ​​(they will not play their role in this task, since we can imagine that this value is equal to infinity).

Let's revise our cases in the format of working with a doubly linked list:

1. If the previous position is median, then we do nothing.
2. If our value is greater than the left element of the median, then we add this value; if the value is equal, then we simply shift the median cursor to the left (the variable storing the link to the current median is overwritten by the left node).
3. The same as in the 2nd point, but opposite.

Complexity: O(n)

## [Omkar and Forest](https://codeforces.com/contest/1536/problem/E)

The program itself that needs to be written in order to solve the problem does not cause any particular difficulties, but the solution of this problem is quite difficult.

Imagine picking some subset of ‘#’ and making them 0. Then there is exactly one way to make all the remaining ‘#’ positive integers. To see why, imagine multisource BFS with all 0 as the sources. After the BFS, each ‘#’ will be equal to the minimum distance from itself to any 0 cell. Difference between adjacent cells will be at most 1. Proof can be shown by contradiction: if two cells with difference ≥2 existed, then the larger of these cells is not labeled with the shortest distance to a source (since the distance from the smaller cell +1 will be a better choice). Because of the nature of BFS, we can also ensure the second condition is also satisfied, since the only cells that have no neighbor strictly smaller will be the source cells. This is the only valid assignment because if we make any number larger, there will exist a pair of cells with difference ≥2. If we try to make any number smaller, there will exist a cell with positive karma that has no strictly smaller neighbor.

Understanding this fact gives us the opportunity to say that the number of combinations is the number of placements with repetitions n^k^, where n is the number of elements to permute (in our situation we have two elements - zero and any positive number, since we it does not matter which number it will be, the number of permutations will always be equal to one), and k is the number of positions for permutation (in our case, this is the number #).

But this formula will not work in a situation where there are no zeros in the original data (that is, all elements are #). To do this, subtract 1 from the standard result (obtained by the formula above) (since this is an option in which there are any non-zero numbers in the table, which contradicts the second condition (Assume there is a valid grid with no zero. Then there exists a smallest number x on this grid, with x> 0. The problem states, that for each x> 0 there has to be a neighbor strictly smaller than x. Contradiction, x is already the smallest value.)).

Thus, the implementation of the program solution can be represented as follows:

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(reader.readLine());
        int T = Integer.parseInt(st.nextToken());
        while (T-- > 0) {
            st = new StringTokenizer(reader.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            long counter = 0;
            for (int i = 0; i < n; i++) {
                String s = reader.readLine();
                for (char c : s.toCharArray()) {
                    if (c == '#') counter++;
                }
            }

            long result = modPow2(counter);
            if ((long) n * m == counter) {
                System.out.println(result - 1);
            } else {
                System.out.println(result);
            }
        }
    }

    private static long modPow2(long exponent) {
        long result = 1;
        while (exponent-- > 0) {
            result = (result * 2) % 1_000_000_007L;
        }
        return result;
    }

Complexity: O(nm)

## [Omkar and Akmar](https://codeforces.com/contest/1536/problem/F)

First you need to understand who will win if the two players play optimally. The interesting thing is that 2nd player, Omkar, always wins no matter what either player does. The easiest way to see this is by considering ending states of the board. An ending state with an even number of letters means that the 2nd player wins (because the first player is the next player and there are no more moves), and an ending state with an odd number of letters means that the 1st player wins.

An ending state must be in the form ABABA... or BABA..., where there are 0 or 1 empty cells in between each letter and the letters form an alternating pattern. If there is more than 1 empty cell in between two cells, then a player will be able to play a letter, thus it is not a valid ending state.

If an ending state has two of the same letters next to each other, then it is not a valid ending state. Either they are next to each other, which is illegal, or there is at least one empty cell in between them, which means that a player can play the other letter in between.

Since the ending state must form an alternating pattern, there must be an even number of states. Thus, the 2nd player, Omkar, always wins. 

In this situation, we need to directly find the number of possible games. This can be done by iterating over the number of moves and counting the number of ways to play a game with that number of moves.

We want to find the number of games that end in x moves on a board of size n.

The first step is to calculate the total number of ending states. If x=n, the total number of ending states is just 2 because you can either have ABABA... or BABAB...

Otherwise, a game that ends in x moves will consist of x letters, for example A|B|A|B|... where a | is a possible location of a single empty cell (there cannot be multiple empty cells next to each other or else it would not be a valid ending state). There are x possible places where there can be an empty cell, and n−x empty cells, so there are (n | n-x) ways to choose places to put empty cells. Due to the circular nature of the board, you need to account for the case where the first cell on the board is an empty cell (the previous formula only works if the first cell is not empty). If you set the first cell to be empty, there are not x−1 possible places to put an empty cell and n−x−1 remaining empty cells, so you have to add (x-1 | n-x-1). Multiply the answer by 2 to account for starting with an A or B.

Finally, multiply by x! to account for all the ways you can reach each ending configuration.

Thus, if x=n, there are 2⋅x! optimal games, otherwise there are 2 × ((n | n-x) + (n-x-1)) × x! optimal games.

Add up the number of games that end in x moves for all even x from [n/2] to n, inclusive.

Complexity: O(n)

