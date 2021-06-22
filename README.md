# doi4j
[![Build Status](https://travis-ci.com/globalbioticinteractions/doi4j.svg?branch=master)](https://travis-ci.com/globalbioticinteractions/doi4j) [![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme) ![Maven Central](https://img.shields.io/maven-central/v/org.globalbioticinteractions/doi4j) [![DOI](https://zenodo.org/badge/134984751.svg)](https://zenodo.org/badge/latestdoi/134984751)

Java library for parsing and printing Digital Object Identifiers (DOIs).

According [doi.org](https://doi.org), the ```[...] DOI system provides a technical and social infrastructure for the registration and use of persistent interoperable identifiers, called DOIs, for use on digital networks.[...]```.

At first glance, the DOI syntax is pretty simple (e.g., 10.[registrant code]/[suffix], or 10.123/456), but tricky encoding issues can arise when expressing them as URIs. Because these encodings might not be easy to handle (e.g., a well-formed URI presentation of doi ```10.1000/456#789``` is ```https://doi.org/10.1000/456%23789``` and *not* ```https://doi.org/10.1000/456#789```), this library was created.

Please see  section "2.5.2.3 Encoding Issues" of the the DOI handbook (also see https://www.doi.org/doi_handbook/2_Numbering.html#2.5.2.3) for more information.



## Table of Contents

- [Install](#install)
- [Examples](#examples)
- [Building](#building)
- [Contribute](#contribute)
- [License](#license)

## Install

### Maven, Gradle, SBT
doi4j is made available through a [maven](https://maven.apache.org) repository.

To include ```doi4j``` in your project, add the following sections to your pom.xml (or equivalent for sbt, gradle etc):
```
  <dependencies>
    <dependency>
      <groupId>org.globalbioticinteractions</groupId>
      <artifactId>doi4j</artifactId>
      <version>0.1.0</version>
    </dependency>
  </dependencies>
```


## Examples

Please see examples below or the [unit tests](./src/test/java/org/globalbioticinteractions/doi/DOITest.java) for examples usage.

```java
import org.globalbioticinteraction.doi.DOI;

// create a doi using registrant and suffix
DOI doi = new DOI("123", "456");

// or use commonly used string presentations
doi = DOI.create("https://doi.org/10.123/456");
doi = DOI.create("doi:10.123/456");
doi = DOI.create("10.123/456");

// or use a (properly encoded) URI
doi = DOI.create(URI.create("https://doi.org/10.123/456"));

// get "pure" doi
doi.toString();
// "10.123/456"

// show "printable" doi
doi.toPrintable();
// "doi:10.123/456"

// get DOI uri
doi.toURI().toString()
// "https://doi.org/10.123/456"
```

### Building

Please use [maven](https://maven.apache.org) version 3.3+ , otherwise you might find issues like [this one](https://github.com/globalbioticinteractions/nomer/issues/3).

* Clone this repository
* Run tests using `mvn test` (optional).
* Run `mvn package` to build (standalone) jar

## Contribute

Feel free to join in. All welcome. Open an [issue](https://github.com/globalbioticinteractions/doi4j/issues)!

## License

[MIT](LICENSE)
