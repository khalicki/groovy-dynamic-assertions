package com.github.khalicki.dynamicassertions.demo

import com.github.khalicki.dynamicassertions.demo.dto.MovieDto
import java.time.Duration

class MovieDatabaseFacade {

    MovieDto findById(Long id) {
        return new MovieDto(
                "When Harry Met Sally",
                1989,
                Duration.parse("PT1H35M")
        )
    }
}
