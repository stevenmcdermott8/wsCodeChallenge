package com.steven.ws.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WsCodeChallengeLogic {

	private static final String ZIP_CODE_FORMAT = "[0-9]{5}";

	/**
	 * 
	 * Given a String of 5-digit ZIP code ranges (each range includes both their upper and lower bounds), provides a
	 * list of ranges representing the minimum number of ranges required to represent the same restrictions as the
	 * input.
	 * 
	 * <pre>
	 * Takes a String in the format of a multidimensional array containing zip codes in the following format:
	 * 	- [94133,94133] [94200,94299] [94600,94699]
	 *  - Note that arrays are space delimited.
	 *  - ZipCode value formats are evaluated with @see ZIP_CODE_FORMAT
	 * 
	 * Example input: "[94133,94133] [94200,94299] [94600,94699]"
	 * 
	 * Unexpected behavior may occur if format is not followed.
	 * </pre>
	 * 
	 * @param zipCodeRanges
	 * @return
	 */
	public List<List<String>> retrieveMinimumRangeSetFromArray(String zipCodeRanges) {

		if (zipCodeRanges == null || zipCodeRanges.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The zipCodeRanges provided is null, empty, or blank. Check your parameters and try again.");
		}

		List<List<String>> listToEvaluate = convertStringList(zipCodeRanges);

		// attempt to sanitize the data to help proceed with the rest of the logic
		attemptToCleanData(listToEvaluate);

		// validate that list contains expected format for logic to succeed
		validateListFormat(listToEvaluate);

		// sort lists by lower range value in each sub list
		sortListByFirstEntry(listToEvaluate);

		// check list for duplicate ranges and remove duplicates
		listToEvaluate = removeDuplicates(listToEvaluate);

		if (listToEvaluate.size() == 1) {
			return listToEvaluate;
		}

		List<List<String>> returnList = new ArrayList<>();

		for (int i = 0; i < listToEvaluate.size(); i++) {

			List<String> currentList = listToEvaluate.get(i);
			List<String> nextList = null;
			if (i < listToEvaluate.size() - 1) {
				nextList = listToEvaluate.get(i + 1);
			}

			if (!listContainsValue(returnList, currentList)) {
				if (nextList != null && !listsOverlap(currentList, nextList)) {
					// if currentList does not overlap with next list return currentList
					returnList.add(currentList);
				} else if (nextList != null) {
					// if there is a list to compare to, compare for highest range of values and return total range
					returnList.add(getMaximumRangeOfTwoRanges(currentList, nextList));
				} else if (!listsOverlap(currentList, listToEvaluate.get(i - 1))) {
					// if comparing the last item in the list, check previous item for overlap to avoid duplicate ranges
					returnList.add(currentList);
				}
			}

		}

		return returnList;

	}

	/**
	 * Attemps to clean data in each entry of the list. Will replace any character that is not a numeric digit with
	 * empty space.
	 * 
	 * @param listToClean
	 *            the list to clean
	 */
	private void attemptToCleanData(List<List<String>> listToClean) {
		listToClean.stream().forEach(list -> {
			list.stream().forEach(item -> list.set(list.indexOf(item), item.replaceAll("[^0-9]+", "")));
		});

	}

	/**
	 * First sorts each sublist to ensure lower value is first in each sublist, then sorts the input multidimensional
	 * List by the first value in each List within it.
	 * 
	 * @param listToSort
	 *            the list to Sort
	 */
	private void sortListByFirstEntry(List<List<String>> listToSort) {

		listToSort.stream().forEach(list -> {
			String firstValue = list.get(0);
			String secondValue = list.get(1);
			if (Integer.parseInt(firstValue) > Integer.parseInt(secondValue)) {
				list.set(0, secondValue);
				list.set(1, firstValue);
			}
		});

		listToSort.sort((List<String> o1, List<String> o2) -> o1.get(0).compareTo(o2.get(0)));
	}

	/**
	 * Checks if a multidimensional List contains another provided List.
	 * 
	 * @param lists
	 *            the lists to check
	 * @param value
	 *            the list value to check against lists parameter
	 * @return true if the multidimensional list contains the list, otherwise false.
	 */
	private boolean listContainsValue(List<List<String>> lists, List<String> value) {
		boolean listContainsRange = false;
		for (List<String> list : lists) {
			if (listsOverlap(list, value)) {
				listContainsRange = true;
				break;
			}
		}

		return listContainsRange;
	}

	/**
	 * Converts a {String in expected array format: [94133,94133] [94200,94299] [94600,94699], to a multidimensional
	 * List of Integers. If the array is not in the expected format, the items will not be added to the array.
	 * 
	 * @param arrayString
	 *            the input string in array format.
	 * @return the converted list.
	 */
	private List<List<String>> convertStringList(String arrayString) {

		String[] arrayParts = arrayString.split("\\] \\[");
		List<List<String>> returnList = new ArrayList<>();

		for (int i = 0; i < arrayParts.length; i++) {

			String[] innerArrayParts = arrayParts[i].replace(" ", "").replaceAll("\\[|\\]", "").split(",");

			if (innerArrayParts.length == 2) {
				List<String> valueList = new ArrayList<>(Arrays.asList(innerArrayParts));
				returnList.add(valueList);
			}
		}

		return returnList;
	}

	/**
	 * Checks a multidimensional list to determine if the list contains duplicate entries.
	 * 
	 * @param lists
	 *            the multidimensional list to be checked.
	 * @return the list containing only unique values.
	 */
	private List<List<String>> removeDuplicates(List<List<String>> lists) {
		List<List<String>> returnList = new ArrayList<>();
		for (List<String> list : lists) {
			if (!returnList.contains(list)) {
				returnList.add(list);
			}
		}

		return returnList;
	}

	/**
	 * Checks if a multidimensional array contains any values that do not match a 5 digit zipCode format. Validation of
	 * zipCode format uses @see ZIP_CODE_FORMAT Throws IllegalArgumentException if value does not match expected format,
	 * otherwise no action is taken.
	 * 
	 * @param unacceptableZipCodeRange
	 *            The multidimensional array to be validated.
	 */
	private void validateListFormat(List<List<String>> listToCheck) {
		boolean hasMissingValue = false;

		if (listToCheck != null && listToCheck.isEmpty()) {
			hasMissingValue = true;
		}

		if (!hasMissingValue) {
			for (List<String> list : listToCheck) {
				for (String item : list) {
					if (item == null || !item.matches(ZIP_CODE_FORMAT)) {
						hasMissingValue = true;
						break;
					}
				}

			}
		}

		if (hasMissingValue) {
			throw new IllegalArgumentException(
					"The list provided is not a valid list or does not contain valid zip code values, expected format is 5 digit integer per entry, check your inputs and try again.");
		}
	}

	/**
	 * Checks if the values of two lists overlap. Expected values are String that are parseable to Integers.
	 * 
	 * @param list1
	 *            The first list to check.
	 * @param list2
	 *            The second list to check.
	 * 
	 * @return true if the lists ranges overlap, false if the ranges do not, NumberFormatExceptionif the strings do not
	 *         contain parsable integers.
	 */
	private boolean listsOverlap(List<String> list1, List<String> list2) {
		int maxOfLowerRange = 0;
		int minOfHigherRange = 0;
		if (list1 != null && list1.size() == 2 && list2 != null && list2.size() == 2) {
			int list1A = Integer.parseInt(list1.get(0));
			int list1B = Integer.parseInt(list1.get(1));
			int list2A = Integer.parseInt(list2.get(0));
			int list2B = Integer.parseInt(list2.get(1));
			maxOfLowerRange = Math.max(list1A, list2A);
			minOfHigherRange = Math.min(list1B, list2B);
		}

		return maxOfLowerRange <= minOfHigherRange;
	}

	/**
	 * Checks the two input lists to determine the maximum range between the lowermost and uppermost values in the
	 * provided lists. Lists are expected to only contain 2 items each and values to be parseabe Integers.
	 * 
	 * If the above conditions are not met, an empty list is returned.
	 * 
	 * @param list1
	 *            The first list to check.
	 * @param list2
	 *            The second list to check.
	 * @return The list containing the largest range of the two list's values, If the lists provided are not in the
	 *         expected format, an empty list is returned. NumberFormatException if the strings do not contain parsable
	 *         integers.
	 */
	private List<String> getMaximumRangeOfTwoRanges(List<String> list1, List<String> list2) {
		List<String> largerRange = new ArrayList<>();
		if (list1 != null && list1.size() == 2 && list2 != null && list2.size() == 2) {
			int list1A = Integer.parseInt(list1.get(0));
			int list1B = Integer.parseInt(list1.get(1));
			int list2A = Integer.parseInt(list2.get(0));
			int list2B = Integer.parseInt(list2.get(1));

			String min = list1A < list2A ? list1.get(0) : list2.get(0);
			String max = list1B > list2B ? list1.get(1) : list2.get(1);

			largerRange.add(min);
			largerRange.add(max);
		}
		return largerRange;
	}

}
