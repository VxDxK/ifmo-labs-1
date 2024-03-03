package org.vdk;

public class BinarySearchTree {
    Node root;

    BinarySearchTree() {
        root = null;
    }

    Node insert(Node node, int key) {
        if (node == null) {
            node = new Node(key);
            return node;
        }
        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        return node;
    }

    Node search(Node root, int key) {
        if (root == null || root.key == key)
            return root;
        if (root.key < key)
            return search(root.right, key);
        return search(root.left, key);
    }
}
