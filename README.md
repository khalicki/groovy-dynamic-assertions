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

#### Types of Assertions

##### hasFieldName

Basic assertion that check field X has given value.

```groovy
.hasTitle('When Harry met Sally')
```

##### hasFieldNameThat on List fields

Verifies a field is not null and is a list and allow chaining assertions on that list.

```groovy
.hasIngredientsThat()
    .contains('cheese')
    .contains('bacon')    
```

Allows also check that list is empty or its size:
```groovy
.hasIngredientsThat()
    .isEmpty()

.hasIngredientsThat()
    .hasSize(2)
```

##### hasEmptyFieldName on List fields

When you want to check that field with list is empty then you can use this assertion without nesting.

```groovy
.hasEmptyIngredients()
```