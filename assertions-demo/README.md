# groovy-dynamic-assertions demo

This is a sample project that uses Groovy Dynamic Assertions in tests.
As an example there is a movie database module that can be accessed by [MovieDatabaseFacade](assertions-demo/src/main/groovy/com/github/khalicki/dynamicassertions/demo/MovieDatabaseFacade.groovy).

In [MovieDatabaseFacadeSpec](assertions-demo/src/test/groovy/com/github/khalicki/dynamicassertions/demo/MovieDatabaseFacadeSpec.groovy) there are some examples how `DynamicAssertions.assert()` can be used to verify correct behavior of tested class.