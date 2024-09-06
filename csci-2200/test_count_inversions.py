import os
import unittest

from gradescope_utils.autograder_utils.decorators import visibility, weight

from inversions import count_inversions

__unittest = True


class TestCountInversions(unittest.TestCase):

    def setUp(self):

        pass

    @weight(1)
    # @visibility('hidden')
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 1"""

        vals = []

        result = count_inversions(vals)

        self.assertEqual(result, 0)

    @weight(1)
    # @visibility('hidden')
    def test_vals2(self, set_score=None):
        """Evaluate count_inversions case 2"""

        vals = [1]

        result = count_inversions(vals)

        self.assertEqual(result, 0)

    @weight(1)
    # @visibility('hidden')
    def test_vals3(self, set_score=None):
        """Evaluate count_inversions case 3"""

        vals = [1, 2]

        result = count_inversions(vals)

        self.assertEqual(result, 0)

    @weight(1)
    # @visibility('hidden')
    def test_vals4(self, set_score=None):
        """Evaluate count_inversions case 4"""

        vals = [1, 2, 3, 4, 5]

        result = count_inversions(vals)

        self.assertEqual(result, 0)

    @weight(1)
    # @visibility('hidden')
    def test_vals5(self, set_score=None):
        """Evaluate count_inversions case 5"""

        vals = [2, 1]

        result = count_inversions(vals)

        self.assertEqual(result, 1)

    @weight(1)
    # @visibility('hidden')
    def test_vals6(self, set_score=None):
        """Evaluate count_inversions case 6"""

        vals = [5, 4, 3, 2, 1]

        result = count_inversions(vals)

        self.assertEqual(result, 10)

    @weight(1)
    # @visibility('hidden')
    def test_vals7(self, set_score=None):
        """Evaluate count_inversions case 7"""

        vals = [i for i in range(0, 1000)]

        result = count_inversions(vals)

        self.assertEqual(result, 0)

    @weight(1)
    # @visibility('hidden')
    def test_vals8(self, set_score=None):
        """Evaluate count_inversions case 8"""

        vals = [i for i in range(1000, 0, -1)]

        result = count_inversions(vals)

        self.assertEqual(result, 499500)

    @weight(1)
    # @visibility('hidden')
    def test_vals9(self, set_score=None):
        """Evaluate count_inversions case 9"""

        vals = [i for i in range(1000000, 0, -1)]

        result = count_inversions(vals)

        self.assertEqual(result, 499999500000)

    @weight(1)
    # @visibility('hidden')
    def test_vals10(self, set_score=None):
        """Evaluate count_inversions case 10"""

        vals = [i for i in range(10000000, 0, -1)]

        result = count_inversions(vals)

        self.assertEqual(result, 49999995000000)

    @weight(1)
    # @visibility('hidden')
    def test_vals11(self, set_score=None):
        """Evaluate count_inversions case 11"""

        vals = [4, 2, 3, 5, 1, 6, 7]

        result = count_inversions(vals)

        self.assertEqual(result, 6)

    @weight(1)
    # @visibility('hidden')
    def test_vals12(self, set_score=None):
        """Evaluate count_inversions case 12"""

        vals = [15, 10, 2, 6, 9, 3, 5, 7, 1, 4]

        result = count_inversions(vals)

        self.assertEqual(result, 32)
