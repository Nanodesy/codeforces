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

