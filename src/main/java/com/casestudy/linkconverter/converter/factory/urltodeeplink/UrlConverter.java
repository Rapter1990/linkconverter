package com.casestudy.linkconverter.converter.factory.urltodeeplink;

/**
 * Factory interface for converting a web URL into a mobile deep link.
 */
public interface UrlConverter {

    String convert(String url);

}
