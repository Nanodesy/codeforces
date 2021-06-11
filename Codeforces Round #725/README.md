# [Codeforces Round #725 (Div. 3)](https://codeforces.com/contest/1538)

## [Stone Game](https://codeforces.com/contest/1538/problem/A)

To begin with, it is important for us to find the positions of the maximum and minimum element, for this we traverse the array and write the positions of these elements.

    int min = 0;  
    int max = 0;  
      
    for (int i = 0; i < array.length; i++) {  
        if (array[max] < array[i]) max = i;  
        if (array[min] > array[i]) min = i;  
    }

Since, according to the conditions of the problem, it is not so important for us whether we first delete the maximum element, and then the maximum one, or vice versa, it is recommended, for convenience, to bring the positions of the maximum and minimum element to the leftmost element or the rightmost element.

    int l = min(max, min);  
    int r = max(max, min);

Next, you should consider the cases that may be present in the task:

 - **Located at the edges.** One of the elements is on the left and the other on the right, and they are far from each other. For example: `[1, 2, 3, 4, 5, 6, 7, 8, 9]`. 

In this situation, add up the index of the left element (do not forget to add one, since the index starts from zero) and the length of the array minus the index of the right element (there is no need to add one here).

    int leftRightWay = (l + 1) + (array.length - r);

*P.S. Since we operate not with the positions of the maximum and minimum element, but with the positions of the leftmost and rightmost element, it does not matter for us whether the maximum element is on the left (and the minimum on the right) or vice versa.*

 - **Both are at the left edge.** Both elements are to the left edge and not far from each other. For example: `[1, 9, 2, 3, 4, 5, 6, 7, 8]`

In this situation, we just take the index of the right element (adding 1, since this is the index that starts at zero).

    int leftWay = r + 1;

- **Both are at the right edge.**  Both elements are to the right edge and not far from each other. For example: `[2, 3, 4, 5, 6, 7, 8, 1, 9]`

We subtract the index of the left element from the length of the array, in fact, we get how many elements are from the edge of the array to the left element.

    int rightWay = array.length - l;

Finally, we will find the minimum value of the three calculated options:

    return min(leftRightWay, min(leftWay, rightWay));

Complexity: O(n)

