package com.casestudy.linkconverter.converter.factory.deeplinktourl;

/**
 * Factory interface for converting a mobile deep link into a standard web URL.
 */
public interface DeepLinkToUrlConverter {

    /**
     * Convert the given deep link into a web URL.
     *
     * @param deeplink the deep link string to convert
     * @return the corresponding web URL
     */
    String convert(String deeplink);

}
