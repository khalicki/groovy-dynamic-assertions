package com.github.khalicki.dynamicassertions.demo.dto

import groovy.transform.TupleConstructor
import java.time.Duration

@TupleConstructor
class MovieDto {
    String title
    Integer releaseYear
    Duration runningTime
}
