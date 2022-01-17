# groovy-dynamic-assertions

![CI](https://github.com/khalicki/groovy-dynamic-assertions/actions/workflows/ci.yml/badge.svg)

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

When you start with list of objects start with `DynamicListAssertions`:

```groovy
DynamicListAssertions.assertThat(movies)
    .isNotEmpty()
```

Class `DynamicListAssertions` has different assertion methods and they are described later. Because complex object structures often have list fields you can change from `DynamicAssertions` to `DynamicListAssertions` and back again.

#### Assertions on single object

##### isNull

Asserts object under test has null value.

```groovy
.isNull()
```

##### isNotNull

Asserts object under test has not null value.

```groovy
.isNotNull()
```

##### isEqualTo

Asserts object under test is equal to given object.

```groovy
.isEqualTo(new Movie('When Harry met Sally'))
```

##### hasFieldName

Basic assertion that check field X has given value.

```groovy
.hasTitle('When Harry met Sally')
```

##### hasFieldNameThat on Object fields

You can check fields in nested objects using this assertion. It fails when property with given field name has null value.

```groovy
.hasMovieThat()
    .hasTitle('When Harry met Sally')
```

You can use this assertion multiple times to navigate to desired field.
```groovy
.hasMovieThat()
    .hasDirectorThat()
        .hasLastName('Reiner')
```

When you want to return from nested assertions to parent use `and()` method.
```groovy
.hasMovieThat()
    .hasDirectorThat()
        .hasLastName('Reiner')
    .and()
    .hasWriterThat()
        .hasLastName('Ephron')
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

You can return to parent assertions using `and()` method as well.

```groovy
.hasIngredientsThat()
    .contains('ham')
    .contains('pineapple')
.and()
.hasName('Pizza Hawaii')
```


##### hasEmptyFieldName on List fields

When you want to check that field with list is empty then you can use this assertion without nesting.

```groovy
.hasEmptyIngredients()
```

##### hasEmptyFieldName on String fields

Checks that field is empty String. String with null value is not treated as empty.

```groovy
.hasEmptyTitle()
```

#### Assertions on lists

##### isNull

Asserts list under test has null value.

```groovy
.isNull()
```

##### isNotNull

Asserts list under test has not null value.

```groovy
.isNotNull()
```

##### isEmpty

Asserts list under test is empty.

```groovy
.isEmpty()
```

##### isNotEmpty

Asserts list under test is not empty.

```groovy
.isNotEmpty()
```

##### contains

Asserts list under test contains given object. To compare objects `equals()` method is used.

```groovy
.contains(new Movie('Breaveheart'))
```

##### containsItemWithFieldName

Asserts that list contains object having given field name with provided value.

```groovy
.containsItemWithTitle('Breaveheart')
```

##### containsItemWithFieldNameThat

Same as `containsItemWithFieldName` but allows chaining assertions on list item matching criteria. If more elements satisfy criteria assertions are executed agains fist matching item. In order to go back list assertions use `and()` method.

```groovy
.containsItemWithTitleThat('Breaveheart')
    .hasDirector('Mel Gibson')
```