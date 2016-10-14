-- Exercise 1

-- 2 :: Num a => a
val1 = 2

-- 4 :: Num a => a
val2 = 2 + 2

-- 2 :: Int
val3 = 2 :: Int

-- 2 :: Float
val4 = 2 :: Float

-- TODO
val5 = (2 + 2) :: Double

-- 2.0 :: Fractional a => a
val6 = 2.0

-- Typing error
-- val7 = 2.0 :: Int

-- 4.0 :: Fractional a => a
val8 = 2 + 2.0

-- Typing error
-- val9 = (2 :: Int) + (2 :: Double)

val10 = (2 :: Int) + 2

val11 = (2 , 2)

val12 = [2 , 2]

-- Typing error
-- val13 = [2 , 2.0]

-- Typing error
-- val14 = [2 :: Float , 2 :: Double ]

-- Exercise 2

-- f1 :: Num a => b -> a
f1 x = 2

-- f2 :: Num a => b -> a
f2 x = 2 * x

-- f3 :: Eq a => a -> a -> a-> Bool
f3 x y z = x == y && y == z

-- f4 :: Ord a => a -> a -> a -> Bool
f4 x y z = x < y && y < z

-- f5 :: Ord a => a -> a -> a-> -> Bool
f5 x y z = x == y && y < z

-- f6 :: Num a, Ord a => a -> a -> Bool
f6 x y = 2 * x < y

-- f7 :: (Num a, Ord a) => a -> a -> a
f7 x y = min (abs x) (negate y)

-- f8 :: Num t => a -> a > [a]
f8 x y = [x, y, 2]

-- f9 :: (Fractional a, Integral a) => a -> a -> a
f9 x y = x `div` y + x / y

-- f1 :: Num a => t -> a
val15 = f1

-- f1 :: Num a => t -> a
val16 = f1 'a'

-- f1 :: Num a => t -> a
val17 = f1 "a"

-- f1 :: Num a => t -> a
val18 = f1 f1

-- f2 :: Num a => a -> a
val19 = f2

-- f2 :: Num a => a
val20 = f2 2

-- f2 :: Fractional a => a
val21 = f2 2.0

-- Typing error
-- val22 = f2 'a'

-- (Char, Char) == (Char, Char) :: Bool
val23 = ('a', 'b') == ('c', 'd')

-- (Char, Char) == (Char, Char) :: Bool
val24 = ('a', 'b') < ('c', 'd')

-- Typing error
-- val25 = ('a', 'b') < ('c', 'd', 'e')

-- [Char] < [Char] :: Bool
val26 = ['a', 'b'] < ['c', 'd', 'e']

-- Typing error
-- val27 = f3

-- f3 (Char, Char) (Char, Char) (Char, Char) :: Bool
val28 = f3 ('a', 'b') ('a', 'b') ('a', 'b')

-- Typing error
-- val29 = f4

-- f4 :: (Num a, Ord) => a -> Bool
val30 = f4 2 2

-- Typing error
-- val31 = f5

-- f5 [Int] [] [Int] :: Bool
val32 = f5 [] [] [2 , 2]

-- f6 :: (Num a, Ord a) => a -> a -> Bool
val33 = f6

-- (f6) 2 :: (Num a, Ord a) => a -> Bool
val34 = (f6) 2

-- f7 :: (Num a, Ord a) => a -> a -> a
val35 = f7

-- Typing error
-- val36 = f7 (2 :: Int) (2 :: Integer)

-- f8 :: Num t => t -> t -> [t]
val37 = f8

-- f8 2 2.0 :: Fractional t => [t]
val38 = f8 2 2.0

-- Typing error
-- val39 = f9

-- Typing error
-- val40 = f9 2 2
