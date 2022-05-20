# Data Format Converter Kata

## The Challenge

Your client uses a custom data transfer format, and they would like to translate messages in this custom  format into JSON.

Your job is to write a system that takes in data in this custom format and returns data that's either already in JSON or can easily be serialized into JSON (like a `List` or a `Map`).

## How To Get Started

The README contains a section below detailing [how to read and interpret the custom data format](#the-custom-format). You'll want to read this carefully to understand how the format works.

### Fixture files

In addition, the `/src/test/resources/fixtures/` directory contains several example messages. These messages are in files with `.txt` extensions.

The `/src/test/resources/fixtures/` directory includes a few files that have been pre-formatted for ease of reading; these take the form `*-formatted.txt`. Only use these files to help you to read and understand the data format and do not use them in tests; they contain newlines and whitespace that the actual messages don't contain.

Finally, for the tabular example messages, the `/src/test/resources/fixtures/` directory also contains a few `.json` files that represent what the data might look like in JSON format.

### Reading in the fixture files for tests

The `test` package contains a `CommonUtil` class that has a static method `getResourceContents()` to help you read in the fixture files for your tests.

For example, you might write:

```java
class ConversionTests {
    @Test
    void itConvertsMessage1() {
        String message1String = CommonUtil.getResourceContents("fixtures/message1.txt");
        // ...
    }
}
```

## The Custom Format

The custom data transfer format sends messages as a pipe-delimited string. Here's an example of a typical message:

```text
1|12345|1|0|4|3|id|firstName|lastName|12|8|8|43910731402917|Ada|Lovelace|57483912049384|Bob|Barker|25982738498172|Carlos|Correa|385938458217390|Danny|DeVito|
```

If we format this message a bit by adding whitespace and some comments to help explain what everything means, it might look like this:

```text
1|  // the message contains one response

// this is the first (and only) response
12345|1|0|  // the message number is 12345, its version is 1, and its status is 0a
4|3|        // the message contains tabular data, with 4 rows of 3 columns each

id              | firstName | lastName |    // the headers of each of the four columns
12              | 8         | 8        |    // the data types of each of the four columns
43910731402917  | Ada       | Lovelace |
57483912049384  | Bob       | Barker   |
25982738498172  | Carlos    | Correa   |
385938458217390 | Danny     | DeVito   |
```

The first four elements of a message will always be numeric, and they will always represent (in order):
- the number of responses in the message
- the message number
- the message version
- the message status

As mentioned above, this message represents tabular data. There are a few other types of data a message can represent:

### Boolean Data Message

Example:

```text
1|54321|1|0|true|
```

This message represents boolean data. We can tell it's a boolean data message because its fifth element isn't numeric but is rather a string value that is either `true` or `false`.

### Single-value Data Message

Example:

```text
1|23456|1|0|1|1|8|a text value|
```

This message looks a bit like tabular data; we could format it like so:

```text
1|  // this message contains only one response
23456|1|0|      // message number, version, status, just as above
1|1|            // one row, one column
                // <-- no column header
8|              // data type
a text value|   // the data
```

Note the lack of a column header; this is what distinguishes a single-value message from a tabular message.

### Error Message

Example:

```text
1|0|1|99|some error message|
```

As with a boolean data message, this message has no row or column count numbers. So its fifth cell is neither numeric (which would represent a row count) or a boolean value. It is therefore an error message. Note the non-zero status number, but _you cannot assume that an error will have a non-zero status_.

### Multiple Response Message

As mentioned above, the first element of the message represents the number of responses in the message. What if there is more than one response?

Example:

```text
2|0|1|0|3|4|id|firstName|lastName|email|12|8|8|8|74839201948567|Kent|Beck|kent.beck@example.com|67829084871982|Martin|Fowler|martin.fowler@example.com|90293856782948|Sandi|Metz|sandi.metz@example.com0|1|0|4|5|id|title|author|ISBN|publishDate|12|8|8|8|13|28348712948569|Practical Object-Oriented Design|Sandi Metz|978-0134456478|2018-08-22|84923834857234|Patterns of Enterprise Application Architecture|Martin Fowler|978-8131794029|2012-01-01|75829384982983|Test-Driven Development By Example|Kent Beck|978-0321146533|2002-11-08|34095823093845|Refactoring|Martin Fowler|978-0134757599|2018-11-30|
```

Based on the first element, we can see that this message contains two responses. If we format the message with some whitespace and comments, we can see how that works:

```text
2|  // the number of responses

// the first response, which is tabular
1111|1|0|
3|4|
id             | firstName | lastName | email                     |
12             | 8         | 8        | 8                         |
74839201948567 | Kent      | Beck     | kent.beck@example.com     |
67829084871982 | Martin    | Fowler   | martin.fowler@example.com |
90293856782948 | Sandi     | Metz     | sandi.metz@example.com    |

// the second response, which is also tabular
2222|1|0|
4|5|
id             | title                                           | author        | ISBN           | publishDate |
12             | 8                                               | 8             | 8              | 13          |
28348712948569 | Practical Object-Oriented Design                | Sandi Metz    | 978-0134456478 | 2018-08-22  |
84923834857234 | Patterns of Enterprise Application Architecture | Martin Fowler | 978-8131794029 | 2012-01-01  |
75829384982983 | Test-Driven Development By Example              | Kent Beck     | 978-0321146533 | 2002-11-08  |
34095823093845 | Refactoring                                     | Martin Fowler | 978-0134757599 | 2018-11-30  |
```

In this example, both responses happen to be tabular, but you cannot assume that the responses will be homogenous in type; i.e. a message may contain both a tabular response _and_ a boolean response.
