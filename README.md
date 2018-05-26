# doi4j
[![Build Status](https://travis-ci.org/globalbioticinteractions/doi4j.svg?branch=master)](https://travis-ci.org/globalbioticinteractions/nomer) [![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)
[![DOI](https://zenodo.org/badge/117019305.svg)](https://zenodo.org/badge/latestdoi/117019305)

Java library for parsing and presenting Digital Object Identifiers (DOIs).

According [doi.org](https://doi.org), the ```[...] DOI system provides a technical and social infrastructure for the registration and use of persistent interoperable identifiers, called DOIs, for use on digital networks.[...]```.

while the DOI syntax is pretty simple (e.g., 10.[registrant code]/[suffix], or 10.123/456), tricky encoding issues can arise when expressing them as URIs. For instance, here's an example published in section "2.5.2.3 Encoding Issues" of the the DOI handbook (also see https://www.doi.org/doi_handbook/2_Numbering.html#2.5.2.3):

```[...] Hexadecimal (%) encoding must be used for characters in a DOI that are not allowed, or have other meanings, in URLs or URNs. Hex encoding consists of substituting for the given character its hexadecimal value preceded by percent. Thus, # becomes %23 and https://doi.org/10.1000/456#789 is encoded as https://doi.org/10.1000/456%23789. The browser does not now encounter the bare #, which it would normally treat as the end of the URL and the start of a fragment, and so sends the entire string off to the DOI network of servers for resolution, instead of stopping at the #. Note that the DOI itself does not change with encoding, merely its representation in a URL. A DOI that has been encoded is decoded before being sent to the DOI Registry. At the moment the decoding is handled by the proxy server https://doi.org/. Only unencoded DOIs are stored in the DOI Registry database. For example, the number above is in the DOI Registry as "10.1000/456#789" and not "10.1000/456%23789". The percent character (%) must always be hex encoded (%25) in any URLs. [...]```

Because these encoding might not be easy to catch (e.g., URI presentation of doi ```10.1000/456#789``` is ```https://doi.org/10.1000/456%23789``` and *not* ```10.1000/456#789```), this library was created.

## Table of Contents

- [Install](#install)
- [Usage](#usage)
- [Examples](#examples)
- [Building](#building)
- [Contribute](#contribute)
- [License](#license)

## Install

### Maven, Gradle, SBT
doi4j is made available through a [maven](https://maven.apache.org) repository.

To include ```doi4j``` in your project, add the following sections to your pom.xml (or equivalent for sbt, gradle etc):
```
  <repositories>
    <repository>
        <id>depot.globalbioticinteractions.org</id>
        <url>https://depot.globalbioticinteractions.org/release</url>
    </repository>
  </repositories>

  <dependencies>
    <dependency>
      <groupId>org.globalbioticinteractions</groupId>
      <artifactId>doi4j</artifactId>
      <version>0.0.1</version>
    </dependency>
  </dependencies>
```

### Building

Please use [maven](https://maven.apache.org) version 3.3+ , otherwise you might find issues like [this one](https://github.com/globalbioticinteractions/nomer/issues/3).

* Clone this repository
* Run tests using `mvn test` (optional).
* Run `mvn package -DskipTests` to build (standalone) jar

## Examples

Please see examples below or the [unit tests](./src/test/java/org/globalbioticinteractions/doi/DOITest.java) for examples usage.

```java

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
doi.getPrintable();
// "doi:10.123/456"

// get DOI uri
doi.toURI().toString()
// "https://doi.org/10.123/456"
```


## Contribute

Feel free to join in. All welcome. Open an [issue](https://github.com/globalbioticinteractions/nomer/issues)!

## License

[MIT](LICENSE)
