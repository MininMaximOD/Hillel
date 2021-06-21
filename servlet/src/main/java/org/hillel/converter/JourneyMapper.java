package org.hillel.converter;

import org.hillel.controller.dto.JourneyDto;
import org.hillel.persistence.entity.JourneyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Mapper
public interface JourneyMapper {

    @Mapping(target = "vehicle", source = "vehicle")
    JourneyDto journeyToJourneyDto(JourneyEntity journey);

    @Mapping(target = "vehicle", source = "vehicle")
    JourneyEntity journeyDtoToJourney(JourneyDto dto);

    default String getShortDate(Instant instant) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .withLocale(Locale.UK)
                        .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    default Instant getInstantDate(String string){
       return LocalDateTime.parse(                   // Parse as an indeterminate `LocalDate`, devoid of time zone or offset-from-UTC. NOT a moment, NOT a point on the timeline.
               string ,        // This input uses a poor choice of format. Whenever possible, use standard ISO 8601 formats when exchanging date-time values as text. Conveniently, the java.time classes use the standard formats by default when parsing/generating strings.
                DateTimeFormatter.ofPattern( "yyyy-MM-dd'T'HH:mm" , Locale.US )  // Use single-character `M` & `d` when the number lacks a leading padded zero for single-digit values.
        )                                      // Returns a `LocalDateTime` object.
                .atZone(                               // Apply a zone to that unzoned `LocalDateTime`, giving it meaning, determining a point on the timeline.
                        ZoneId.of( "UTC" )     // Always specify a proper time zone with `Contintent/Region` format, never a 3-4 letter pseudo-zone such as `PST`, `CST`, or `IST`.
                )                                      // Returns a `ZonedDateTime`. `toString` → 2018-05-12T16:30-04:00[UTC].
                .toInstant();                        // Extract a `Instant` object, always in UTC by definition.

    }
}
