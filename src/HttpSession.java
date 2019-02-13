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

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author giuliobosco
 * @version 1.0 (2019-02-01)
 */
public class HttpSession extends Thread {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Get http method string.
     */
    public static final String GET = "GET";

    /**
     * Post http method string.
     */
    public static final String POST = "POST";

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Session socket.
     */
    private Socket socket;

    // --------------------------------------------------------------------------- Getters & Setters
    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create http session with the session socket.
     *
     * @param socket Session socket.
     */
    public HttpSession(Socket socket) {
        this.socket = socket;
    }

    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Read a file from the file system.
     *
     * @param filePath Path of the file to read.
     * @return File as bytes array.
     * @throws IOException Error while reading from the file system.
     */
    private byte[] fileRender(String filePath) throws IOException {
        if (filePath.equals("/")) {
            filePath += "index.html";
        }
        filePath = "www" + filePath;

        Path path = Paths.get(filePath);
        Path p = Paths.get(".");

        if (Files.exists(path) && !Files.notExists(path)) {
            return Files.readAllBytes(path);
        } else {
            Path error404 = Paths.get("error", "404.html");
            if (Files.exists(error404) && !Files.notExists(error404)) {
                return Files.readAllBytes(error404);
            } else {
                throw new IOException("404 error page not found!");
            }
        }
    }

    /**
     * Get the http header.
     *
     * @param contentType Content of the file.
     * @param fileLength File length.
     * @return Http header in bytes
     */
    private byte[] getHttpHeader(String contentType, int fileLength) {
        String header = "HTTP/1.0 200 OK\n" +
                "Allow: GET\n" +
                "MIME-Version: 1.0\n" +
                "Server : HMJ Basic HTTP Server\n" +
                "Content-Type: " + contentType + "\n" +
                "Content-Length: " + fileLength +
                "\n\n";

        byte[] bytes = new byte[header.length()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) header.charAt(i);
        }

        return bytes;
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Run the http session.
     */
    @Override
    public void run() {
        try {
            InputStreamReader input = new InputStreamReader(socket.getInputStream());
            BufferedReader client = new BufferedReader(input);
            OutputStream out = socket.getOutputStream();

            String line;
            while ((line = client.readLine()) != null) {
                System.out.println(line);
                if (line.contains(GET) || line.contains(POST)) {
                    String filePath;
                    String[] attributes;
                    if (line.contains("?")) {
                        filePath = line.substring(line.indexOf(' ') + 1, line.indexOf('?'));
                        String attributesLine = line.substring(line.indexOf('?') + 1, line.lastIndexOf(' '));
                        attributes = attributesLine.split("&");
                    } else {
                        filePath = line.substring(line.indexOf(' ') + 1, line.lastIndexOf(' '));
                        attributes = new String[0];
                    }

                    byte[] rederedFile = fileRender(filePath);
                    byte[] header = getHttpHeader("text/html", rederedFile.length);

                    for (byte b : header) {
                        out.write(b);
                    }

                    for (byte b : rederedFile) {
                        out.write(b);
                    }

                }
            }

            out.close();
            client.close();
            input.close();
            socket.close();
        } catch (IOException ioe) {

        }
    }


    // --------------------------------------------------------------------------- Static Components

}
