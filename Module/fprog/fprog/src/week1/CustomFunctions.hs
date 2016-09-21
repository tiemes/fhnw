customLastV1 xs = head (reverse xs)

customLastV2 xs = xs !! ((length xs) - 1)

customInitV1 xs = reverse (tail (reverse xs))

customInitV2 xs = reverse (drop 1 (reverse xs))
