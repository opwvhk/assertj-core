/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2022 the original author or authors.
 */
package org.assertj.core.api.recursive.assertion;

import static java.lang.String.format;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.recursive.assertion.RecursiveAssertionConfiguration.CollectionAssertionPolicy.ELEMENTS_ONLY;
import static org.assertj.core.api.recursive.assertion.RecursiveAssertionConfiguration.MapAssertionPolicy.MAP_VALUES_ONLY;
import static org.assertj.core.api.recursive.assertion.RecursiveAssertionConfiguration.OptionalAssertionPolicy.OPTIONAL_VALUE_ONLY;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.assertj.core.api.recursive.assertion.RecursiveAssertionConfiguration.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecursiveComparisonConfiguration_toString_Test {

  private Builder recursiveComparisonConfigurationBuilder;

  @BeforeEach
  void setup() {
    recursiveComparisonConfigurationBuilder = RecursiveAssertionConfiguration.builder();
  }

  @Test
  void should_show_a_complete_description() {
    // GIVEN
    recursiveComparisonConfigurationBuilder.withIgnorePrimitiveFields(true)
                                           .withIgnoreAllNullFields(true)
                                           .withIgnoredFields("foo", "bar", "foo.bar")
                                           .withIgnoredFieldsMatchingRegexes("f.*", ".ba.", "..b%sr..")
                                           .withIgnoredFieldsOfTypes(UUID.class, ZonedDateTime.class)
                                           .withRecursionIntoJavaClassLibraryTypes(true)
                                           .withCollectionAssertionPolicy(ELEMENTS_ONLY)
                                           .withMapAssertionPolicy(MAP_VALUES_ONLY)
                                           .withOptionalAssertionPolicy(OPTIONAL_VALUE_ONLY);
    // WHEN
    RecursiveAssertionConfiguration recursiveAssertionConfiguration = recursiveComparisonConfigurationBuilder.build();
    // THEN
    //@format:off
    then(recursiveAssertionConfiguration).hasToString(format("- all null fields were ignored in the assertion%n" +
                                                             "- the following fields were ignored in the comparison: foo, bar, foo.bar%n" +
                                                             "- the fields matching the following regexes were ignored in the comparison: f.*, .ba., ..b%%sr..%n" +
                                                             "- the following types were ignored in the assertion: java.util.UUID, java.time.ZonedDateTime%n" +
                                                             "- primitive fields were ignored in the recursive assertion%n" +
                                                             "- fields from Java Class Library types (java.* or javax.*) were included in the recursive assertion%n" +
                                                             "- the collection assertion policy was ELEMENTS_ONLY%n" +
                                                             "- the map assertion policy was MAP_VALUES_ONLY%n" +
                                                             "- the optional assertion policy was OPTIONAL_VALUE_ONLY%n"));
    //@format:on
  }

  @Test
  void should_show_a_complete_description_with_default_values() {
    // WHEN
    RecursiveAssertionConfiguration recursiveAssertionConfiguration = recursiveComparisonConfigurationBuilder.build();
    // THEN
    // @format:off
    then(recursiveAssertionConfiguration).hasToString(format("- fields from Java Class Library types (java.* or javax.*) were excluded in the recursive assertion%n" +
                                                             "- the collection assertion policy was ELEMENTS_ONLY%n" +
                                                             "- the map assertion policy was MAP_VALUES_ONLY%n"+
                                                             "- the optional assertion policy was OPTIONAL_VALUE_ONLY%n"));
    // @format:on
  }

}
