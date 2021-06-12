# [Codeforces Round #724 (Div. 2)](https://codeforces.com/contest/1536)

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

