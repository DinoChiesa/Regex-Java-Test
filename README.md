# Regular Expression Testing in Java

Example of testing regular expressions in Java.

This uses Java8, Junit4, and the Maven surefire plugin to run tests.

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
