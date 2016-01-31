public class MaxHeap<Item extends Comparable<Item>> {

    private Item[] heap;
    private int N = 0; // Number of items in the heap
    
    // Create an empty MaxHeap with a default capacity of 64
    public MaxHeap() {
        this(64);
    }

    // Create an empty MaxHeap with a given default size
    public MaxHeap(int capacity) {
        heap = (Item[]) new Comparable[capacity];
    }

    // Create a new MaxHeap from a given array of items
    public MaxHeap(Item[] items) {
        N = items.length;
        heap = (Item[]) new Comparable[2*N];
        for (int i=0; i<N; i++) {
            heap[i] = items[i];
        }
        heapify();
    }

    // Insert an item into the heap, maintaining heap order
    public void insert(Item it) {
        // If the array is at capacity, resize it up.
        if (N >= heap.length) {
            Item[] temp = (Item[]) new Comparable[2*N];
            for (int i=0; i<N; i++)
                temp[i] = heap[i];
            heap = temp;
        }
        heap[N] = it;
        N++;
        swim(N);
    }

    // Return the max item from the heap
    public Item max() {
        return heap[0];
    }

    // Delete and return the max item from the heap
    public Item popMax() {
        // If the heap is below 1/4 capacity, resize it down. Do not reduce the
        // capacity smaller than 32 items.
        if (N < heap.length/4 && heap.length > 32) {
            Item[] temp = (Item[]) new Comparable[N/2];
            for (int i=0; i<N; i++)
                temp[i] = heap[i];
            heap = temp;
        }

        Item r = heap[0];

        swap(1, N);
        heap[N-1] = null;
        N--;
        sink(1);

        return r;
    }

    // Return true if the heap is empty (false otherwise)
    public boolean empty() {
        return N==0;
    }

    // Return the number of items in the heap
    public int size() {
        return N;
    }

    // Put the heap array into heap order
    private void heapify() {
        for (int i=N/2; i>=1; i--)
            sink(i);
    }

    /* Compare the ith and jth items in the heap.
     *
     * Use right-shifted indexing to simplify index arithmetic throughout the
     * class. So, for example, compare(1,3) compares the items at heap[0] and
     * heap[2].
     */
    private int compare(int i, int j) {
        return heap[i-1].compareTo(heap[j-1]);
    }

    /* Exchange two items in the heap.
     *
     * Use right-shifted indexing to simplify index arithmetic throughout the
     * class. So, for example, swap(3,10) exchanges the items at heap[2] and
     * heap[10].
     */
    private void swap(int i, int j) {
        Item tmp = heap[i-1];
        heap[i-1] = heap[j-1];
        heap[j-1] = tmp;
    }

    // Sink an item at the given index down as far as it will go.
    private void sink(int i) {
        if (2*i > N)
            return;
        int maxChild = 2*i;
        if (maxChild < N && compare(maxChild, maxChild+1) < 0)
            maxChild++;
        if (compare(maxChild, i) > 0) {
            swap(maxChild, i);
            sink(maxChild);
        }
    }

    // Swim an item at the given index as far up as it will go.
    private void swim(int i) {
        if (i==1)
            return;
        int parent = i/2;
        if (compare(i, parent) > 0) {
            swap(i, parent);
            swim(parent);
        }
    }

    public static void main(String[] args) {
        MaxHeap h = new MaxHeap(new Integer[]{14, 3, 8, 9, 10, 80, 60});
        h.insert(70);
        h.insert(25);
        h.insert(2);
        while(!h.empty())
            System.out.println(h.popMax());
    }
}
