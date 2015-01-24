package com.company;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This class stores and works with HTML elements
 * @author Ondřej Štorc
 * @version 2.0
 */
class HtmlFile {
    /*
     * This variable stores data with which this instance works
     */
    private final ArrayList<String> file;

    /**
     * Constructs a HtmlFile with the file.
     * @param file stores data with which will this instance operate
     */
    public HtmlFile(ArrayList<String> file) {
        this.file = parseFile(file);
    }

    /**
     * Parse file, to format for needs of this app.
     * What happens when file is null?
     *  Application will print NullPointerException stack trace {@link NullPointerException}
     *
     * @param file which is formatted as uncompressed HTML file (This means that each element is on a new line and if it is nested, it is indented using the tab)
     * @return a formatted array, where each row is stored in a format:  nesting:data
     */
    private ArrayList<String> parseFile(ArrayList<String> file) {
        return (ArrayList<String>) file.stream().filter(line -> line.trim().length() != 0).map(line -> String.format("%d:%s", calculateNesting(line), line.trim())).collect(Collectors.toList());
    }

    /**
     * Get content of pair element.
     * What happens when any of parameters is null or empty?
     *  If is any of parameters 'null', then the application will print NullPointerException stack trace {@link NullPointerException}
     *  If the first parameter is empty or null, then the application will return empty array
     *  If the second parameter is empty or null, then the application will attempt to find the same element
     * @param element tag of the element (<p>, <a href=''>, etc.) without brackets
     * @param attributes of the element you are looking for
     * @return content of element
     */
    public ArrayList<String> getContentOfElement(String element, String attributes) {
        String searchElement;
        if (!attributes.isEmpty()) searchElement = String.format("<%s %s>", element, attributes);
        else searchElement = String.format("<%s>", element);
        int nestedElements = 0;
        boolean elementFound = false;
        ArrayList<String> contentOfElement = new ArrayList<>();
        for (String line : file) {
            if (!elementFound) {
                if (line.contains(searchElement)) {
                    String temp;
                    elementFound = true;
                    nestedElements = Integer.parseInt(line.split(":")[0]);
                    temp = line.replace(String.format("%d:%s", nestedElements, searchElement), "");
                    if (!temp.isEmpty()) {
                        if (temp.contains(String.format("</%s>", element))) {
                            temp = temp.replace(String.format("</%s>", element), "");
                            contentOfElement.add(temp);
                            break;
                        }
                    }
                }
            } else {
                if (line.contains(String.format("%d:</%s>", nestedElements, element))) {
                    break;
                }
                contentOfElement.add(line.replace((line.split(":")[0] + ":"), ""));
            }
        }
        return contentOfElement;
    }

    /**
     * Get content of element's attribute.
     * What happens when any of parameters is null or empty?
     *  Method will return null
     * @param element tag of element (<p>, <a href=''>, etc.) without brackets
     * @param uniqueAttribute unique attribute, used to identify the element
     * @param dataAttribute from which attribute you want to get data
     * @return data of an attribute - very comprehensive
     */
    public String getContentOfAttribute(String element, String uniqueAttribute, String dataAttribute) {
        String searchElement = String.format("<%s %s %s=", element, uniqueAttribute, dataAttribute);
        for (String line : file) {
            if (line.contains(searchElement)) {
                String temp = line.replace(line.substring(0, line.indexOf(dataAttribute)) + dataAttribute + "=\"", "");
                temp = temp.substring(0, temp.indexOf('"'));
                return temp;
            }
        }
        return null;
    }

    /**
     * Calculate nesting of line, by counting '\t' on line.
     * What happens when line is null?
     *  Application will print NullPointerException stack trace {@link NullPointerException}
     * @param line for which you want to know how much is element nested, which is located on this line.
     * @return nesting level
     */
    private int calculateNesting(String line) {
        int nesting = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '\t') nesting++;
            else if (line.charAt(i) == '<') break;
        }
        return nesting;
    }
}
