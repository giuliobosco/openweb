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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Request a file to the file system.
 *
 * @author giuliobosco
 * @version 1.0 (2019-02-01)
 */
public class FileRequest {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Supported file kind.
     * First column has the file kind (text, image, ...) the second column has the file kind (html,
     * css, txt) and the third column has the file extension.
     */
    public static final String[][] FILE_KINDS = {
            {"text", "txt", "txt"},                     // text file
            {"text", "html", "html"},                   // html file
            {"text", "html", "htm"},                    // htm file
            {"text", "json", "json"},                   // json file
            {"text", "xml", "xml"},                     // xml file
            {"text", "js", "js"},                       // javascript file
            {"text", "css", "css"},                     // css file
            {"image", "jpg", "jpg"},                    // image jpg file
            {"image", "jpg", "jpeg"},                   // image jpeg file
            {"image", "jpg", "jpe"},                    // image jpe file
            {"image", "gif", "gif"},                    // image gif file
            {"image", "png", "png"},                    // image png file
            {"image", "ico", "ico"},                    // image ico file
            {"video", "mov", "mov"},                    // video mov file
            {"video", "mov", "qt"},                     // video qt file
            {"video", "mpeg", "mpeg"},                  // mpeg mpeg file
            {"video", "mpeg", "mpg"},                   // mpeg mpg file
            {"video", "mpeg", "mpe"},                   // mpeg mpe file
            {"audio", "basic", "au"},                   // audio au file
            {"audio", "basic", "snd"},                  // audio snd file
            {"audio", "x-wave", "wav"},                 // wav file
            {"application", "octet-stream", "class"}    // java class file
    };

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * File request status.
     */
    private HttpStatus status;

    /**
     * File extension.
     * Example: txt, html.
     */
    private String fileExtension;

    /**
     * File kind.
     * Example: text, image, video.
     */
    private String fileKind;

    /**
     * File length.
     */
    private int fileLenght;

    /**
     * Requested file.
     */
    private byte[] file;

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the file request status.
     *
     * @return File request status.
     */
    public HttpStatus getStatus() {
        return this.status;
    }

    /**
     * Get the file extension.
     *
     * @return File extension.
     */
    public String getFileExtension() {
        return this.fileExtension;
    }

    /**
     * Get the file kind.
     *
     * @return File kind.
     */
    public String getFileKind() {
        return this.fileKind;
    }

    /**
     * Get the file lenght.
     *
     * @return File length.
     */
    public int getFileLenght() {
        return this.fileLenght;
    }

    /**
     * Get the requested file.
     *
     * @return Requested file.
     */
    public byte[] getFile() {
        return this.file;
    }

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the file request to the file system.
     *
     * @param path Path of the file.
     * @throws IOException File system error while opening the file.
     */
    public FileRequest(Path path) throws IOException {
        this.openFile(path);
    }

    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Check the file path.
     *
     * @param path Path to check.
     * @return True if the path exists and is readable.
     */
    private boolean checkFile(Path path) {
        return Files.exists(path) && !Files.notExists(path) && Files.isReadable(path);
    }

    /**
     * Set the file kind and extension.
     * Search in the FILE_KINDS if is a supported file, if it's it use those information other ways
     * it will use text/plain.
     *
     * @param path Path of the file.
     */
    private void setKindExtension(Path path) {
        String extension = path.toString();
        extension = extension.substring(extension.lastIndexOf(".") + 1);

        int kind = -1;
        for (int i = 0; i < FILE_KINDS.length; i++) {
            if (extension.equals(FILE_KINDS[i][2].toLowerCase())) {
                kind = i;
                i = FILE_KINDS.length;
            }
        }
        if (kind > 0) {
            this.fileExtension = FILE_KINDS[kind][1];
            this.fileKind = FILE_KINDS[kind][0];
        } else {
            this.fileExtension = "plain";
            this.fileKind = "text";
        }
    }

    /**
     * Open the file.
     *
     * @param path File to open.
     * @throws IOException File system error while opening the file.
     */
    public void openFile(Path path) throws IOException {
        if (checkFile(path)) {
            setKindExtension(path);

            this.file = Files.readAllBytes(path);
            this.fileLenght = file.length;
            this.status = HttpStatus.OK;
        } else {
            this.status = HttpStatus.NOT_FOUND;
        }
    }


    // ----------------------------------------------------------------------------- General Methods
    // --------------------------------------------------------------------------- Static Components

}
