import com.google.common.base.Joiner;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.*;

class AllCardCombinationsTest {

    private static final Joiner commaJoiner = Joiner.on(", ");

    @Test
    void testAllCombinations_1() throws Exception {
        List<Integer[]> actual = AllCardCombinations.allCombinations(Arrays.asList(1));
        Collections.sort(actual, (a, b) -> {
            int result = 0;
            int pos = 0;
            while (result == 0) {
                result = a[pos] - b[pos];
                ++pos;
            }

            return result;
        });

        List<Integer[]> expected = new ArrayList<>();
        expected.add(new Integer[]{1});

        assertItems(expected, actual);

    }

    @Test
    void testAllCombinations_2() throws Exception {
        List<Integer[]> actual = AllCardCombinations.allCombinations(Arrays.asList(1, 2));
        Collections.sort(actual, (a, b) -> {
            int result = 0;
            int pos = 0;
            while (result == 0) {
                result = a[pos] - b[pos];
                ++pos;
            }

            return result;
        });

        List<Integer[]> expected = new ArrayList<>();
        expected.add(new Integer[]{1, 2});
        expected.add(new Integer[]{2, 1});

        assertItems(expected, actual);

    }

    @Test
    void testAllCombinations_3() throws Exception {
        List<Integer[]> actual = AllCardCombinations.allCombinations(Arrays.asList(2, 3, 1));
        Collections.sort(actual, (a, b) -> {
            int result = 0;
            int pos = 0;
            while (result == 0) {
                result = a[pos] - b[pos];
                ++pos;
            }

            return result;
        });

        List<Integer[]> expected = new ArrayList<>();
        expected.add(new Integer[]{1, 2, 3});
        expected.add(new Integer[]{1, 3, 2});
        expected.add(new Integer[]{2, 1, 3});
        expected.add(new Integer[]{2, 3, 1});
        expected.add(new Integer[]{3, 1, 2});
        expected.add(new Integer[]{3, 2, 1});

        assertItems(expected, actual);

    }

    @Test
    void testAllCombinations_Speed() throws Exception {

        for (int loop = 0; loop < 5; ++loop) {

            var items = new ArrayList<Integer>();
            for (int i = 0; i < 11; ++i) {
                items.add(i);
            }

            List<Integer[]> actual = AllCardCombinations.allCombinations(items);
            Assert.assertEquals(39916800, actual.size());
        }

    }

    private void assertItems(List<Integer[]> expectedList, List<Integer[]> actualList) {

        StringBuilder expected = new StringBuilder();
        expectedList.forEach(items -> {
            expected.append(commaJoiner.join(items));
            expected.append('\n');
        });
        StringBuilder actual = new StringBuilder();
        actualList.forEach(items -> {
            actual.append(commaJoiner.join(items));
            actual.append('\n');
        });

        Assert.assertEquals(expected.toString(), actual.toString());

    }

}