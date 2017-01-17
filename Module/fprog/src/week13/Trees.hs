data Tree a = Leaf a | Node [Tree a]

tree1 = Node [Node [Leaf 3, Leaf 4, Leaf 5], Leaf 6]
--                  Tree Int
--                 [Tree Int]                Tree Int
--            Tree Int
--           [Tree Int]
--      Tree Int

sumT :: Num a => Tree a -> a
sumT (Leaf a) = a
sumT (Node (xs)) = sum [sumT x | x <- xs]

flattenT :: Tree a -> [a]

occursT :: Eq a => a -> Tree a -> Bool

lookupT :: Eq a => a -> Tree (a, b) -> Maybe b
