package org.globalbioticinteractions.doi;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DOITest {

    @Test
    public void constructor() {
        DOI doi = new DOI("123", "456");
        assertThat(doi.getDirectoryIndicator(), is("10"));
        assertThat(doi.getRegistrantCode(), is("123"));
        assertThat(doi.getSuffix(), is("456"));
        assertThat(doi.getPrefix(), is("10.123"));
        assertThat(doi.toString(), is("10.123/456"));
        assertThat(doi.toPrintableDOI(), is("doi:10.123/456"));
        assertThat(doi.toURI().toString(), is("https://doi.org/10.123/456"));
    }

    @Test(expected = NullPointerException.class)
    public void throwOnNullRegistrant() {
        new DOI(null, "bla");
    }

    @Test(expected = NullPointerException.class)
    public void throwOnNullSuffix() {
        new DOI("foo", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwOnEmptySuffix() {
        new DOI("foo", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwOnEmptyRegistrantCodeSuffix() {
        new DOI("", "foo");
    }

    @Test
    public void createWithURI() throws URISyntaxException, MalformedDOIException {
        URI doiURI = new URI("https://doi.org/10.123/456");
        DOI doi = DOI.create(doiURI);
        assertThat(doi.toURI(), is(doiURI));
    }

    @Test
    public void createWithDxURI() throws URISyntaxException, MalformedDOIException {
        URI doiURI = new URI("https://example.org/10.123/456");
        DOI doi = DOI.create(doiURI);
        assertThat(doi.getSuffix(), is("456"));
        assertThat(doi.getPrefix(), is("10.123"));
        assertThat(doi.toURI(), is(URI.create("https://doi.org/10.123/456")));
    }

    @Test
    public void createWithPrintable() throws URISyntaxException, MalformedDOIException {
        String printableCanonical = "doi:10.123/456";
        DOI doi = DOI.create(printableCanonical);
        assertThat(doi.toPrintableDOI(), is(printableCanonical));
        assertThat(doi.getPrefix(), is("10.123"));
        assertThat(doi.getSuffix(), is("456"));
    }

    @Test
    public void createWithPrintableCaseInsensitive() throws URISyntaxException, MalformedDOIException {
        DOI doi = DOI.create("DOI:10.123/456");
        assertThat(doi.toPrintableDOI(), is("doi:10.123/456"));
    }

    @Test
    public void createWithPrintableCaseInsensitive2() throws URISyntaxException, MalformedDOIException {
        DOI doi = DOI.create("DoI:10.123/456");
        assertThat(doi.toPrintableDOI(), is("doi:10.123/456"));
    }

    @Test
    public void createFromPlainDOI() throws MalformedDOIException, URISyntaxException {
        DOI doi = DOI.create("10.1644/1545-1410(2001)683<0001:sa>2.0.co;2");
        assertThat(doi.toPrintableDOI(), is("doi:10.1644/1545-1410(2001)683<0001:sa>2.0.co;2"));
        assertThat(doi.getPrefix(), is("10.1644"));
        assertThat(doi.getSuffix(), is("1545-1410(2001)683<0001:sa>2.0.co;2"));
    }

    @Test(expected = MalformedDOIException.class)
    public void throwOnMalformedURI() throws MalformedDOIException, URISyntaxException {
        DOI.create("http://dx.doi.org/10.1577/1548-8659(1993)122<0378:fotgsi>2.3.co;2");
    }

    @Test
    public void doiToString() throws URISyntaxException, MalformedDOIException {
        DOI doi = new DOI("123", "456");
        assertThat(doi.toString(), is("10.123/456"));
    }

    @Test(expected = MalformedDOIException.class)
    public void throwOnInvalidDirectoryIndicator() throws MalformedDOIException {
        DOI.create("9.1000/123456");
    }

    @Test(expected = MalformedDOIException.class)
    public void throwOnMissingSuffix() throws MalformedDOIException {
        DOI.create("10.1038.issn.1476-4687");
    }

    @Test(expected = MalformedDOIException.class)
    public void throwOnMissingRegistrantAndSuffix() throws MalformedDOIException {
        DOI.create("10");
    }

    @Test(expected = MalformedDOIException.class)
    public void throwOnFoo() throws MalformedDOIException {
        DOI.create("foo");
    }

    @Test
    public void toURIWithHash() throws URISyntaxException, MalformedDOIException, MalformedURLException {
        DOI doi = DOI.create("10.1000/123#456");
        assertThat(doi.toURI().toString(), is("https://doi.org/10.1000/123%23456"));
    }

    @Test
    public void toURIWithAngularBrackets() throws URISyntaxException, MalformedDOIException, MalformedURLException {
        DOI doi = DOI.create("10.1206/0003-0090(2000)264<0083:>2.0.co;2");
        assertThat(doi.toURI().toURL().toString(), is("https://doi.org/10.1206/0003-0090(2000)264%3C0083:%3E2.0.co;2"));
    }

    @Test
    public void toURIWithResolver() throws URISyntaxException, MalformedDOIException, MalformedURLException {
        DOI doi = DOI.create("10.1000/123456");
        URI resolver = URI.create("https://example.org");
        assertThat(doi.toURI(resolver).toString(), is("https://example.org/10.1000/123456"));
    }

    @Test
    public void toURIWithNullResolver() throws URISyntaxException, MalformedDOIException, MalformedURLException {
        DOI doi = DOI.create("10.1000/123456");
        assertThat(doi.toURI(null).toString(), is("https://doi.org/10.1000/123456"));
    }


    @Test
    public void toPrintable() throws URISyntaxException, MalformedDOIException {
        assertThat(DOI.create("10.1000/123456").toPrintableDOI(), is("doi:10.1000/123456"));
        assertThat(DOI.create("10.1000/123#456").toPrintableDOI(), is("doi:10.1000/123#456"));
        assertThat(DOI.create("10.1206/0003-0090(2000)264<0083:>2.0.co;2").toPrintableDOI(), is("doi:10.1206/0003-0090(2000)264<0083:>2.0.co;2"));
    }

    @Test
    public void toPrintableWithHash() throws URISyntaxException, MalformedDOIException {
        assertThat(DOI.create("10.1000/123#456").toPrintableDOI(), is("doi:10.1000/123#456"));
    }

    @Test
    public void toPrintableWithAngularBrackets() throws MalformedDOIException {
        assertThat(DOI.create("10.1206/0003-0090(2000)264<0083:>2.0.co;2").toPrintableDOI(), is("doi:10.1206/0003-0090(2000)264<0083:>2.0.co;2"));
    }

    @Test
    public void easyDOIEncodingMistakeToMakeWithURIClass() throws URISyntaxException {
        // e.g., from https://www.doi.org/syntax.html:
        // "Hex encoding consists of substituting for the given character its hex value preceded by percent. Thus, # becomes %23 and
        // http://dx.doi.org/10.1000/456#789
        // is encoded as
        // http://dx.doi.org/10.1000/456%23789
        // a DOI util is needed to avoid"

        URI actual = new URI("https://resolv.org/10.1000/123#456");
        assertThat(actual.getPath(), is("/10.1000/123"));
        assertThat(actual.getFragment(), is("456"));
        assertThat(actual, is(not(URI.create("https://resolv.org/10.1000/123%23456"))));
        // but
        assertThat(new URI("https", "resolv.org", "/10.1000/123#456", null), is(URI.create("https://resolv.org/10.1000/123%23456")));
    }

    @Test
    public void createFromURIWithHash() throws MalformedDOIException {
        assertThat(DOI.create("https://doi.org/10.1000/123%23456").toString(), is("10.1000/123#456"));
    }

    @Test
    public void createFromURIWithAngularBrackets() throws MalformedDOIException {
        assertThat(DOI.create("https://doi.org/10.1206/0003-0090(2000)264%3C0083:%3E2.0.co;2").toString(), is("10.1206/0003-0090(2000)264<0083:>2.0.co;2"));
    }

    @Test(expected = MalformedDOIException.class)
    public void fromURL4() throws MalformedDOIException {
        String doiString = "http://dx.doi.org/10.1898/1051-1733(2004)085<0062:dcabso>2.0.co;2";
        DOI.create(doiString);
    }

    @Test
    public void escaping() throws MalformedDOIException {
        String originalDOI = "10.1898/1051-1733(2004)085<0062:dcabso>2.0.co;2";
        DOI doi = DOI.create(originalDOI);
        assertThat(doi.toString(), is(originalDOI));
        URI uri = doi.toURI(URI.create("http://dx.doi.org/"));
        assertThat(uri.toString(), is(not("http://dx.doi.org/10.1898/1051-1733(2004)085<0062:dcabso>2.0.co;2")));
        assertThat(uri.toString(), is("http://dx.doi.org/10.1898/1051-1733(2004)085%3C0062:dcabso%3E2.0.co;2"));
    }

    @Test
    public void whitespace() throws MalformedDOIException {
        DOI doi = DOI.create("10.some/some citation");
        assertThat(doi.getSuffix(), is("some citation"));
        assertThat(doi.toURI(), is(URI.create("https://doi.org/10.some/some%20citation")));
    }

    @Test
    public void equals() throws MalformedDOIException {
        DOI doi1 = DOI.create("https://doi.org/10.1/ABC");
        DOI doi2 = DOI.create("https://doi.org/10.1/AbC");
        DOI doi3 = DOI.create("https://doi.org/10.1/AbCD");

        assertThat(doi1, is(doi2));
        assertThat(doi2, is(not(doi3)));
        assertThat(doi1, is(not(doi3)));
    }

    @Test
    public void hashCaseInsensitive() throws MalformedDOIException {
        DOI doi1 = DOI.create("https://doi.org/10.1/ABC");
        DOI doi2 = DOI.create("https://doi.org/10.1/AbC");
        DOI doi3 = DOI.create("https://doi.org/10.1/AbCD");

        assertThat(doi1.hashCode(), is(doi2.hashCode()));
        assertThat(doi2.hashCode(), is(not(doi3.hashCode())));
        assertThat(doi1.hashCode(), is(not(doi3.hashCode())));
    }

    @Test
    public void compareToCaseInsensitive() throws MalformedDOIException {
        DOI doi1 = DOI.create("https://doi.org/10.1/ABC");
        DOI doi2 = DOI.create("https://doi.org/10.1/AbC");
        DOI doi3 = DOI.create("https://doi.org/10.1/AbCD");

        assertThat(doi1.compareTo(doi2), is(0));
        assertThat(doi2.compareTo(doi3), is(not(0)));
        assertThat(doi1.compareTo(doi3), is(not(0)));
    }

    @Test
    public void commonlyUsedPrefixDOI() {
        assertTrue(DOI.isCommonlyUsedDoiPrefix("doi:"));
    }

    @Test
    public void commonlyUsedPrefixSecureDOI() {
        assertTrue(DOI.isCommonlyUsedDoiPrefix("https://doi.org/"));
    }

    @Test
    public void commonlyUsedPrefixDxDOI() {
        assertTrue(DOI.isCommonlyUsedDoiPrefix("http://dx.doi.org/"));
    }

    @Test
    public void notCommonlyUsedPrefixDxDOI() {
        assertFalse(DOI.isCommonlyUsedDoiPrefix("http://example.org"));
    }
}