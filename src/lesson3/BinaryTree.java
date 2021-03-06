package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     * Ресурсоемкость - O(h)
     * Трудоемкость - O(h)
     */
    @Override
    public boolean remove(Object o) {

        T t = (T) o;
        Node<T> result = delete(root, t);
        size--;
        return (result != null);
    }

    private Node<T> delete(Node<T> root, T t) {

        Node<T> node = root;

        if (t.compareTo(root.value) > 0) {
            node.right = delete(root.right, t);
        } else if (t.compareTo(root.value) < 0) {
            node.left = delete(node.left, t);
        } else if (node.right != null) {
            node.value = min(node.right).value;
            node.right = delete(node.right, node.value);
        } else if (node.left != null) {
            node.value = max(node.left).value;
            node.left = delete(node.left, node.value);
        } else {
            node = null;
        }
        return node;
    }


    private Node<T> min(Node<T> node) {
        if (node.left != null) {
            return min(node.left);
        }
        return node;
    }

    private Node<T> max(Node<T> node) {
        if (node.right != null) {
            return max(node.right);
        }
        return node;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current;

        Stack<Node<T>> stack = new Stack<>();

        private BinaryTreeIterator() {
            current = root;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        /**
         * Поиск следующего элемента
         * Средняя
         * Ресурсоемкость - O(h)
         * Трудоемкость - O(h)
         */
        private Node<T> findNext() {

            return stack.pop();

        }

        @Override
        public boolean hasNext() {

            if (stack.isEmpty()) {
                return false;
            } else return true;
        }

        @Override
        public T next() {

            current = findNext();

            Node<T> node = current;

            if (node == null) throw new NoSuchElementException();

            if (node.right != null) {
                node = node.right;

                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         * Ресурсоемкость - O(h)
         * Трудоемкость - O(h)
         */
        @Override
        public void remove() {
            if (current != null) {
                BinaryTree.this.remove(current.value);
            }
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
