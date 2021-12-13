# groovy-dynamic-assertions demo

This is a sample project that uses Groovy Dynamic Assertions in tests.
As an example there is a movie database module that can be accessed by [MovieDatabaseFacade](src/main/groovy/com/github/khalicki/dynamicassertions/demo/MovieDatabaseFacade.groovy).

In [MovieDatabaseFacadeSpec](src/test/groovy/com/github/khalicki/dynamicassertions/demo/MovieDatabaseFacadeSpec.groovy) there are some examples how `DynamicAssertions.assert()` can be used to verify correct behavior of tested class.

It is important to note that there is no assertion class written in this subproject to support these assertions. All used assertions are dynamically resolved in runtime by dynamic assertions library. This approach saves you from writing supporting assertion classes for every class that is verified in tests.