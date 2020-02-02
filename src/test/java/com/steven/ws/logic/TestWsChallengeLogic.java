package com.steven.ws.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestWsChallengeLogic {

	WsCodeChallengeLogic plainJava = new WsCodeChallengeLogic();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testDetermineRangeNullInput() {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage(
				"The zipCodeRanges provided is null, empty, or blank. Check your parameters and try again.");
		plainJava.retrieveMinimumRangeSetFromArray(null);
	}

	@Test
	public void testDetermineRangeEmptyInput() {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage(
				"The zipCodeRanges provided is null, empty, or blank. Check your parameters and try again.");
		plainJava.retrieveMinimumRangeSetFromArray("");
	}

	@Test
	public void testDetermineRangeBlankInput() {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage(
				"The zipCodeRanges provided is null, empty, or blank. Check your parameters and try again.");
		plainJava.retrieveMinimumRangeSetFromArray(" ");
	}

	@Test
	public void testDetermineRangeBadFormat_notAnArray() {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage(
				"The list provided is not a valid list or does not contain valid zip code values, expected format is 5 digit integer per entry, check your inputs and try again.");
		plainJava.retrieveMinimumRangeSetFromArray("not an expected format string");
	}

	@Test
	public void testDetermineRangeBadFormat_valuesInArrayBadFormat() {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage(
				"The list provided is not a valid list or does not contain valid zip code values, expected format is 5 digit integer per entry, check your inputs and try again.");
		plainJava.retrieveMinimumRangeSetFromArray("[94600,not a zip code] [94000,94133] [94133,94299] [00000, 12345]");
	}

	@Test
	public void testDetermineRangeBadFormat_valuesInArrayEmpty() {
		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[94600, ] [94000,94133] [94133,94299] [00000, 12345]");
		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("00000", "12345"));
		expectedList.add(Arrays.asList("94000", "94299"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueOrderedList() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[94133,94133] [94200,94299] [94226,94399]");
		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94133", "94133"));
		expectedList.add(Arrays.asList("94200", "94399"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueOrderedList2() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[94000,94133] [94134,94299] [94600,94699]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94000", "94133"));
		expectedList.add(Arrays.asList("94134", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueUnorderedList() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[94134,94299] [94600,94699] [94000,94133]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94000", "94133"));
		expectedList.add(Arrays.asList("94134", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueUnorderedList2() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[94600,94699] [94000,94133] [94134,94299] [00000, 12345]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("00000", "12345"));
		expectedList.add(Arrays.asList("94000", "94133"));
		expectedList.add(Arrays.asList("94134", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromOverlappingOrderedList() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[94133,94133] [94200,94299] [94600,94699]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94133", "94133"));
		expectedList.add(Arrays.asList("94200", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromOverlapingOrderedList2() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[94133,94133] [94133,94299] [94600,94699]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94133", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromOverlapingOrderedList3() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[94000,94133] [94133,94299] [94600,94699]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94000", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromOverlapingOrderedList6() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[94000,94134] [94133,94299] [94600,94699]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94000", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromListWithDuplicates() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[94133,94133] [94133,94133] [94226,94399]");
		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94133", "94133"));
		expectedList.add(Arrays.asList("94226", "94399"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueUnorderedListWithOverLappingValues() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[94600,94699] [94000,94133] [94133,94299] [00000, 12345]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("00000", "12345"));
		expectedList.add(Arrays.asList("94000", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeOneListContainsTwoOtherUniqueLists() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[10000,50000] [20000,30000] [30001,40000]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("10000", "50000"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeOneListContainsTwoOtherOverlappingLists() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[10000,50000] [20000,30000] [29999,40000]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("10000", "50000"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeOneListContainsTwoOtherOverlappingUnorderedLists() {

		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray(" [29999,40000] [10000,50000] [20000,30000]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("10000", "50000"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromValuesWithSpaces() {

		List<List<String>> returnList = plainJava.retrieveMinimumRangeSetFromArray(" [   00000  , 12345    ]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("00000", "12345"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueUnorderedListWithOverLappingValues2() {

		List<List<String>> returnList = plainJava.retrieveMinimumRangeSetFromArray("[94133,94299] [94134,94298]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94133", "94299"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueUnorderedListWithValuesInWrongOrder() {
		// higher value first in third array
		List<List<String>> returnList = plainJava
				.retrieveMinimumRangeSetFromArray("[94600,94699] [94133,94000] [94133,94299] [00000,12345]");

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("00000", "12345"));
		expectedList.add(Arrays.asList("94000", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}
}
