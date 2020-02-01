# wsCodeChallenge

BACKGROUND
Sometimes items cannot be shipped to certain zip codes, and the rules for these restrictions are stored as a series of ranges of 5 digit codes. For example if the ranges are:

`[94133,94133] [94200,94299] [94600,94699]`

Then the item can be shipped to zip code 94199, 94300, and 65532, but cannot be shipped to 94133, 94650, 94230, 94600, or 94299.

Any item might be restricted based on multiple sets of these ranges obtained from multiple sources.

PROBLEM
Given a collection of 5-digit ZIP code ranges (each range includes both their upper and lower bounds), provide an algorithm that produces the minimum number of ranges required to represent the same restrictions as the input.

NOTES
- The ranges above are just examples, your implementation should work for any set of arbitrary ranges
- Ranges may be provided in arbitrary order
- Ranges may or may not overlap
- Your solution will be evaluated on the correctness and the approach taken, and adherence to coding standards and best practices

EXAMPLES:
If the input = `[94133,94133] [94200,94299] [94600,94699]`
Then the output should be = `[94133,94133] [94200,94299] [94600,94699]`

If the input = `[94133,94133] [94200,94299] [94226,94399]`
Then the output should be = `[94133,94133] [94200,94399]`

Evaluation Guidelines:
Your work will be evaluated against the following criteria:
- Successful implementation
- Efficiency of the implementation
- Design choices and overall code organization
- Code quality and best practices


# Implementation 1
Background: Plain Java using only core java API, no libraries used for coding challenge.  

Options to run:

Can be run in command line with following instruction (prerequisite Java JDK 8 installed and in local computers Path):
* In terminal/command prompt navigate to root directory of the project (the same directory containing the pom.xml file)
* Make Directory "classes" if it does not exist
* Run command `javac -d "classes" "src/main/java/com/steven/ws/logic/WsCodeChallengeLogic.java"`
* Run command `javac -d "classes" -classpath "classes" "src/main/java/com/steven/ws/WsCodeChallengeApplication.java"`
* Move into directory `"classes"` (`cd classes` in most terminal applications)
* Run command `java com.steven.ws.WsCodeChallengeApplication "your input parameter"`
* Example: `java com.steven.ws.WsCodeChallengeApplication "[94133,94133] [94200,94299] [94600,94699]"`

If you have Maven 3 installed and configured on your computers path:
* In terminal/command prompt navigate to root directory of the project (the same directory containing the pom.xml file)
* Run command: `mvn clean install`
* Move into directory `"target/classes"` (`cd target/classes'` in most terminal applications)
* Run command `java com.steven.ws.WsCodeChallengeApplication "your input parameter"`
* Example: `java com.steven.ws.WsCodeChallengeApplication "[94133,94133] [94200,94299] [94600,94699]"`
