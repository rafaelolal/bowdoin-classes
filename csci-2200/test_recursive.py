import os
import unittest

from gradescope_utils.autograder_utils.decorators import partial_credit, weight

from inversions import count_inversions

__unittest = True


class TestTask1Recursive(unittest.TestCase):

   # @weight(1)

    @partial_credit(1)
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 1"""

        vals = []

        result = count_inversions(vals)

        if result == 0:

            set_score(1)

        else:

            set_score(0)

    # @weight(1)

    @partial_credit(1)
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 2"""

        vals = [1]

        result = count_inversions(vals)

        if result == 0:

            set_score(1)

        else:

            set_score(0)

    @partial_credit(1)
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 3"""

        vals = [1, 2]

        result = count_inversions(vals)

        if result == 0:

            set_score(1)

        else:

            set_score(0)

    @partial_credit(1)
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 4"""

        vals = [1, 2, 3, 4, 5]

        result = count_inversions(vals)

        if result == 0:

            set_score(1)

        else:

            set_score(0)

    @partial_credit(1)
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 5"""

        vals = [2, 1]

        result = count_inversions(vals)

        if result == 1:

            set_score(1)

        else:

            set_score(0)

    @partial_credit(1)
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 6"""

        vals = [5, 4, 3, 2, 1]

        result = count_inversions(vals)

        if result == 10:

            set_score(1)

        else:

            set_score(0)

    @partial_credit(1)
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 7"""

        vals = [i for i in range(0, 1000)]

        result = count_inversions(vals)

        if result == 0:

            set_score(1)

        else:

            set_score(0)

    @partial_credit(1)
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 8"""

        vals = [i for i in range(1000, 0, -1)]

        result = count_inversions(vals)

        if result == 499500:

            set_score(1)

        else:

            set_score(0)

    @partial_credit(1)
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 9"""

        vals = [i for i in range(1000000, 0, -1)]

        result = count_inversions(vals)

        if result == 499999500000:

            set_score(1)

        else:

            set_score(0)

    @partial_credit(1)
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 10"""

        vals = [i for i in range(10000000, 0, -1)]

        result = count_inversions(vals)

        if result == 49999995000000:

            set_score(1)

        else:

            set_score(0)

    @partial_credit(1)
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 11"""

        vals = [4, 2, 3, 5, 1, 6, 7]

        result = count_inversions(vals)

        if result == 6:

            set_score(1)

        else:

            set_score(0)

    @partial_credit(1)
    def test_vals1(self, set_score=None):
        """Evaluate count_inversions case 12"""

        vals = [10, 2, 6, 9, 3, 5, 7, 6, 1, 4]

        result = count_inversions(vals)

        if result == 19:

            set_score(1)

        else:

            set_score(0)
