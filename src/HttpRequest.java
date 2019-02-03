/*
 * The MIT License
 *
 * Copyright 2019 giuliobosco.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpCookie;
import java.net.Socket;

/**
 * Http request object.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-02-03)
 */
public class HttpRequest {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Http request get method.
     */
    public static final String GET_METHOD = "GET";

    /**
     * Http request port method.
     */
    public static final String POST_METHOD = "POST";

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Request socket.
     */
    private Socket socket;

    /**
     * Request method.
     * Get or post.
     */
    private String method;

    /**
     * Http request attributes.
     */
    private HttpRequestAttribute[] attributes;

    /**
     * Requested file path.
     */
    private String filePath;

    /**
     * Request host.
     */
    private String host;


    /**
     * Cookie request.
     */
    private HttpCookie[] cookies;

    /**
     * Http request connection.
     */
    private String connection;

    /**
     * Http request upgrade insicure requests.
     */
    private String uir;

    /**
     * Http request accept.
     */
    private String accept;

    /**
     * Http request user agent.
     */
    private String userAgent;

    /**
     * Http request referer.
     */
    private String referer;

    /**
     * Http request accept language.
     */
    private String language;

    /**
     * Http request accept encoding.
     */
    private String encoding;

    /**
     * Other http request values.
     */
    private String other;

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * get the request socket.
     *
     * @return Request socket.
     */
    public Socket getSocket() {
        return this.socket;
    }

    /**
     * Get the Request method.
     *
     * @return Request method.
     */
    public String getMethod() {
        return this.method;
    }

    /**
     * Get the http request attributes.
     *
     * @return Http request attributes.
     */
    public HttpRequestAttribute[] getAttributes() {
        return this.attributes;
    }

    /**
     * Get the requested file path.
     *
     * @return Requested file path.
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Get the request host.
     *
     * @return Request host.
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Get the cookie request.
     *
     * @return Cookie request.
     */
    public HttpCookie[] getCookies() {
        return this.cookies;
    }

    /**
     * Get the http request connection.
     *
     * @return Http request connection.
     */
    public String getConnection() {
        return this.connection;
    }

    /**
     * Get the http request upgrade insicure requests.
     *
     * @return Http request upgrade insicure requests.
     */
    public String getUir() {
        return this.uir;
    }

    /**
     * Get the http reqeust accept.
     *
     * @return Http request accept.
     */
    public String getAccept() {
        return this.accept;
    }

    /**
     * Get the http request user agent.
     *
     * @return Http request user agent.
     */
    public String getUserAgent() {
        return this.userAgent;
    }

    /**
     * Get the http request referer.
     *
     * @return Http request referer.
     */
    public String getReferer() {
        return this.referer;
    }

    /**
     * Get the http request accept language.
     *
     * @return Http request accept language.
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     * Get the http request accept encoding.
     *
     * @return Http request accept encoding.
     */
    public String getEncoding() {
        return this.encoding;
    }

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the http request from the buffered reader on the input incoming from the client.
     *
     * @param client Client input buffered reader.
     */
    public HttpRequest(BufferedReader client) {
        try {
            String line;
            this.other = "";
            // check for all lines content
            while ((line = client.readLine()) != null) {
                if (line.startsWith(GET_METHOD)) {
                    // initialize with get http method.
                    initGet(line);
                } else if (line.startsWith(POST_METHOD)) {

                } else if (line.startsWith("Host:")) {
                    initHost(line);
                } else if (line.startsWith("Cookie:")) {
                    initCookie(line);
                } else if (line.startsWith("Connection:")) {
                    initConnection(line);
                } else if (line.startsWith("Upgrade-Insecure-Requests")) {
                    initUir(line);
                } else if (line.startsWith("Accept:")) {
                    initAccpet(line);
                } else if (line.startsWith("User-Agent:")) {
                    initUserAgent(line);
                } else if (line.startsWith("Referer:")) {
                    initRefer(line);
                } else if (line.startsWith("Accept-Language:")) {
                    initLanguage(line);
                } else if (line.startsWith("Accept-Encoding:")) {
                    initEncoding(line);
                } else {
                    this.other += line + "\n";
                }
            }
        } catch (IOException ignored) {

        }
    }

    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Initialize the get request.
     * Initialize the method, the file path and the attributes.
     *
     * @param line Get Http request line.
     */
    private void initGet(String line) {
        // set the method
        this.method = GET_METHOD;
        if (line.contains(HttpRequestAttribute.ATTRIBUTE_DIVIDER)) {
            // if request contains attributes setup the file path from the first space to the "?"
            this.filePath = line.substring(
                    line.indexOf(' ') + 1,
                    line.indexOf(HttpRequestAttribute.ATTRIBUTE_DIVIDER));

            // set up the request attributes
            String attributes = line.substring(
                    line.indexOf(HttpRequestAttribute.ATTRIBUTE_DIVIDER) + 1,
                    line.indexOf(' ')
            );
            this.attributes = HttpRequestAttribute.getAttribute(attributes);
        } else {
            // if request do not contains attributes set the file from the first space to the
            // last space
            this.filePath = line.substring(line.indexOf(' ') + 1, line.lastIndexOf(' '));
        }
    }

    /**
     * Initialize host request.
     *
     * @param line Host http request line.
     */
    private void initHost(String line) {
        this.host = getFromSpace(line);
    }

    /**
     * Initialize the connection.
     *
     * @param line Connection http request line.
     */
    private void initCookie(String line) {
        // remove line title
        String cookie = line.substring(line.indexOf(' '));
        // split cookies in strings
        String[] cookies = line.split(";");

        // initialize cookies array with number of cookies
        this.cookies = new HttpCookie[cookies.length];

        for (int i = 0; i < this.cookies.length; i++) {
            // for each cookie crete a new HttpCookie, use as name from the first char to the first
            // - and as value from the first - to the end of the string.
            this.cookies[i] = new HttpCookie(
                    cookies[i].substring(0, cookies[i].indexOf("-")),
                    cookies[i].substring(cookies[i].indexOf("-"))
            );
        }
    }

    /**
     * Initialize the connection http request.
     *
     * @param line Connection http request line.
     */
    private void initConnection(String line) {
        this.connection = getFromSpace(line);
    }

    /**
     * Initialize the upgrade insecure request.
     *
     * @param line Upgrade insecure request line.
     */
    private void initUir(String line) {
        this.uir = getFromSpace(line);
    }

    /**
     * Initialize the http request accept.
     *
     * @param line Http request accept line.
     */
    private void initAccpet(String line) {
        this.accept = getFromSpace(line);
    }

    /**
     * Initialize the user agent.
     *
     * @param line Http request user agent line.
     */
    private void initUserAgent(String line) {
        this.userAgent = getFromSpace(line);
    }

    /**
     * Initialize the http request referer.
     *
     * @param line Http request referer line.
     */
    private void initRefer(String line) {
        this.referer = getFromSpace(line);
    }

    /**
     * Initialize the http request language.
     *
     * @param line Http request language line.
     */
    private void initLanguage(String line) {
        this.language = getFromSpace(line);
    }

    /**
     * Initialize the http encoding reqeust.
     *
     * @param line Http encoding request line.
     */
    private void initEncoding(String line) {
        this.encoding = getFromSpace(line);
    }

    /**
     * Get the string after the first space.
     *
     * @param value String to substring.
     * @return Substringed string.
     */
    private String getFromSpace(String value) {
        return value.substring(value.indexOf(' '));
    }

    // ----------------------------------------------------------------------------- General Methods
    // --------------------------------------------------------------------------- Static Components

}
