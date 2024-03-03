package org.vdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class BinarySearchTreeTest {
    BinarySearchTree tree;

    @BeforeEach
    void buildTree() {
        tree = new BinarySearchTree();
        tree.root = tree.insert(tree.root, 50);
        tree.insert(tree.root, 30);
        tree.insert(tree.root, 20);
        tree.insert(tree.root, 40);
        tree.insert(tree.root, 70);
        tree.insert(tree.root, 60);
        tree.insert(tree.root, 80);
    }

    @Test
    void searchTest() {
        assertNull(tree.search(tree.root, 6));
        assertNotNull(tree.search(tree.root, 60));
    }
}