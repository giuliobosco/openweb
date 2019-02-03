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

/**
 * Http status.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-02-03)
 */
public class HttpStatus {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Informational http status (1xx).
     */
    public static final int INFORMATIONAL = 1;

    /**
     * Success http status (2xx).
     */
    public static final int SUCCESS = 2;

    /**
     * Redirection http status (3xx).
     */
    public static final int REDIRECTION = 3;

    /**
     * Client error http status (4xx).
     */
    public static final int CLIENT_ERROR = 4;

    /**
     * Server error http status.
     */
    public static final int SERVER_ERROR = 5;

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Http status code.
     */
    private int code;

    /**
     * Http status description.
     */
    private String description;

    /**
     * Http status kind.
     */
    private int kind;

    /**
     * Continue status.
     */
    public static final HttpStatus CONTINUE = new HttpStatus(100, "Continue", INFORMATIONAL);

    /**
     * Continue status.
     */
    public static final HttpStatus SWITCHING_PROTOCOLS = new HttpStatus(101, "Switching Protocols", INFORMATIONAL);

    /**
     * OK status.
     */
    public static final HttpStatus OK = new HttpStatus(200, "OK", SUCCESS);

    /**
     * Created status.
     */
    public static final HttpStatus CREATED = new HttpStatus(201, "Created", SUCCESS);

    /**
     * No content status.
     */
    public static final HttpStatus NO_CONTENT = new HttpStatus(204, "No Content", SUCCESS);

    /**
     * Bad reqeust status.
     */
    public static final HttpStatus BAD_REQUEST = new HttpStatus(400, "Bad Reqeust", CLIENT_ERROR);

    /**
     * Unauthorized status.
     */
    public static final HttpStatus UNAUTHORIZED = new HttpStatus(401, "Unauthorized", CLIENT_ERROR);

    /**
     * Forbidden status.
     */
    public static final HttpStatus FORBIDDEN = new HttpStatus(403, "Forbidden", CLIENT_ERROR);

    /**
     * Not Found status.
     */
    public static final HttpStatus NOT_FOUND = new HttpStatus(404, "Not Found", CLIENT_ERROR);

    /**
     * Conflict status.
     */
    public static final HttpStatus CONFLICT = new HttpStatus(409, "Conflict", CLIENT_ERROR);

    /**
     * Internal server error status.
     */
    public static final HttpStatus INTERNAL_SERVER_ERROR = new HttpStatus(500, "Internal Server Error", SERVER_ERROR);

    /**
     * Not Implemented status.
     */
    public static final HttpStatus NOT_IMPLEMENTED = new HttpStatus(501, "Not Implemented", SERVER_ERROR);

    /**
     * Bad Gateway status.
     */
    public static final HttpStatus BAD_GATEWAY = new HttpStatus(502, "Bad Gateway", SERVER_ERROR);

    /**
     * Service Unavailable status.
     */
    public static final HttpStatus SERVICE_UNAVAILABLE = new HttpStatus(503, "Service Unavailable", SERVER_ERROR);

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the http status code.
     *
     * @return Http status code.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Get the http status description.
     *
     * @return Http status description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get the http status kind.
     *
     * @return Http status kind.
     */
    public int getKind() {
        return this.kind;
    }

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the http status.
     *
     * @param code Http status code.
     * @param description Http status description.
     * @param kind Http status kind
     */
    private HttpStatus(int code, String description, int kind) {
        this.code = code;
        this.description = description;
        this.kind = kind;
    }

    // -------------------------------------------------------------------------------- Help Methods
    // ----------------------------------------------------------------------------- General Methods
    // --------------------------------------------------------------------------- Static Components

}
