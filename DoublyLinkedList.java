import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Quang Nguyen
 * @version 1.0
 * @userid qnguyen305
 * @GTID 903770019
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index < 0");
        } else if (index > this.size) {
            throw new IndexOutOfBoundsException("index > size");
        }

        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        }

        //create a new node storing the data
        //create 2 vars to store 2 nodes, one before the index and one after the index
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
        DoublyLinkedListNode<T> nodeBeforeIndex = null;
        DoublyLinkedListNode<T> nodeAfterIndex = null;
        //If the index is 0 then set the new node next = the current head
        //Then set the head of the linked list to the new node
        //Else If the index is size then set the new node previous = the current tail
        //Then set the tail of the linked list to the new node
        //Else find the node before the index and the node after the index
        //Set the new node previous to the node before the index and new node next to the node after the index
        if (this.size <= 0) {
            newNode.setNext(nodeAfterIndex);
            newNode.setPrevious(nodeBeforeIndex);
            this.head = newNode;
            this.tail = newNode;
        } else {
            if (index == 0) {
                this.head.setPrevious(newNode);
                newNode.setNext(this.head);
                this.head = newNode;
            } else if (index == this.size) {
                this.tail.setNext(newNode);
                newNode.setPrevious(this.tail);
                this.tail = newNode;
            } else {
                var currentNode = head;
                var midPoint = this.size / 2;
                var i = 0;
                while (i <= index) {
                    if (i == index) {
                        nodeBeforeIndex = currentNode.getPrevious();
                        nodeAfterIndex = currentNode;
                    }
                    currentNode = currentNode.getNext();
                    i++;
                }

                newNode.setNext(nodeAfterIndex);
                nodeAfterIndex.setPrevious(newNode);
                newNode.setPrevious(nodeBeforeIndex);
                nodeBeforeIndex.setNext(newNode);
            }
        }


        this.size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index can not be smaller than 0");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("index can not be larger than or equal to list size");
        }
        var currentNode = head;
        var data = currentNode.getData();

        if (index == 0) {
            head = head.getNext();
            if (head != null) {
                head.setPrevious(null);
            }
        } else if (index == size - 1) {
            currentNode = tail;
            data = currentNode.getData();
            tail = tail.getPrevious();
            if (tail != null) {
                tail.setNext(null);
            }
        } else {
            var i = 0;
            while (i != index) {
                currentNode = currentNode.getNext();
                i++;
            }
            data = currentNode.getData();
            var nodeBeforeIndex = currentNode.getPrevious();
            var nodeAfterIndex = currentNode.getNext();

            nodeBeforeIndex.setNext(nodeAfterIndex);
            nodeAfterIndex.setPrevious(nodeBeforeIndex);
        }

        this.size--;
        return data;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty list");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty list");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index can not be smaller than 0");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("index can not be larger than or equal to list size");
        }
        var currentNode = head;
        if (index == size - 1) {
            currentNode = tail;
        } else if (index != 0 && index != size - 1) {
            var i = 0;
            while (i != index) {
                currentNode = currentNode.getNext();
                i++;
            }
        }
        var data = currentNode.getData();
        return data;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size() <= 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        if (isEmpty()) {
            throw new NoSuchElementException("data not found");
        }
        var currentNode = tail;

        while (!currentNode.getData().equals(data) && currentNode != head) {
            currentNode = currentNode.getPrevious();
        }
        if (currentNode == head && currentNode.getData() != data) {
            throw new NoSuchElementException("data not found");
        } else if (currentNode == head && currentNode.getData() == data) {
            head = currentNode.getNext();
            head.setPrevious(null);
        } else if (currentNode == tail && currentNode.getData() == data) {
            tail = currentNode.getPrevious();
            tail.setNext(null);
        } else {
            var nodeBeforeData = currentNode.getPrevious();
            var nodeAfterData = currentNode.getNext();

            nodeBeforeData.setNext(nodeAfterData);
            nodeAfterData.setPrevious(nodeBeforeData);
        }
        this.size--;
        return currentNode.getData();

    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        //create an array with size of linked list
        Object[] arr = new Object[this.size];
        //create a var to keep track of the current node, initialize current node to head
        var currentNode = head;
        //Iterate through the array
        //Set the current node data in the current array index
        //if current node data is not null (indicate pass the last node)
        for (int i = 0; i < arr.length; i++) {
            if (currentNode.getData() != null) {
                arr[i] = currentNode.getData();
                currentNode = currentNode.getNext();
            } else {
                break;
            }
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
