package com.github.khalicki.dynamicassertions.data

class ObjectWithTwoObjectField {
    private ObjectWithTitleField firstMovie
    private ObjectWithTitleField secondMovie

    ObjectWithTwoObjectField(ObjectWithTitleField firstMovie, ObjectWithTitleField secondMovie) {
        this.firstMovie = firstMovie
        this.secondMovie = secondMovie
    }
}
