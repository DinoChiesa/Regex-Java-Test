# Regular Expression Testing in Java

This repo is a simple developer's aid in building and testing regex, implemented in Java. 
You would use this if you want to build your own regular expressions, and test them rigorously against a large-ish data set of sample data. 
The result of using this would be confidence that the regex you've selected will match input you expect it to match, and not match input you don't expect it to match. 

For example, suppose one aspect of user interaction allows the user to submit an address. You may want to scan that address for "Bobby Tables"-type attacks.  You can use this tool to test your proposed regex against a large list of inputs, to assure yourself the regex is correct and valid. At the end you can insert that validated regex into your own app. 

This uses Java 11 (minimum), Junit4, and the Maven surefire plugin to run tests.

## To Run Tests

```sh
mvn clean test
```

## Test your own Data

Modify the test data  in [the resources directory](./src/test/resources)
to run your own tests.

Any json file there that begins with "test" will be interpreted as a test for a
particular regex, by
[GenericRegexTest.java](./src/test/java/com/apigee/regex/GenericRegexTest.java).

There is a [sample test file](./src/test/resources/test1.json).


The addresses test is handled seperately, by
[AddressRegexTest.java](./src/test/java/com/apigee/regex/AddressRegexTest.java). You
can also modify the data in [the addresses json
file](./src/test/resources/addresses-us-all.json) for that.  The regex to use
for addresses is also in the JSON file.


## Credits

* Ethan Brown for the [address data](https://github.com/EthanRBrown/rrad)
