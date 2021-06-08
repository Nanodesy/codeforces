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

