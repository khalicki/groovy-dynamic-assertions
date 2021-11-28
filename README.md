# groovy-dynamic-assertions

Library that helps to write fluent test's assertions in groovy without tons of boilerplate code.

## User guide

### Dynamic Assertions

Assertions that are checked in runtime but require no supporting code.

In order to assert on any class start with `assertThat` method from `DynamicAssertions`.

```groovy
DynamicAssertions.assertThat(new Movie('When Harry met Sally'))
    .hasTitle('When Harry met Sally')
```

Because assertions from `DynamicAssertions` are checked only in runtime your IDE can warn that method `hasTitle()` does not exist. Don't worry, it will work when you run your test using test runner.

Of course assertions can be chained, so you can write complex assertions that verifies that object under test contains all fields you expect.

```groovy
DynamicAssertions.assertThat(new Movie('When Harry met Sally', 'Rob Reiner'))
    .hasTitle('When Harry met Sally')
    .hasDirector('Rob Reiner')
```