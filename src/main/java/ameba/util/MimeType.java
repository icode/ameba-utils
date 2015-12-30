package ameba.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author icode
 */
public class MimeType {
    private final static Map<String, String> contentTypes = new HashMap<>();

    static {
        LinkedProperties properties = LinkedProperties.create("mime-types.properties");
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            contentTypes.put((String) entry.getKey(), (String) entry.getValue());
        }
    }

    /**
     * @param extension the extension
     * @return the content type associated with <code>extension</code>.  If no association is found, this method will
     * return <code>text/plain</code>
     */
    public static String get(String extension) {
        return get(extension, "text/plain");
    }

    /**
     * @param extension the extension
     * @param defaultCt the content type to return if there is no known association for the specified extension
     * @return the content type associated with <code>extension</code> or if no associate is found, returns
     * <code>defaultCt</code>
     */
    public static String get(String extension, String defaultCt) {
        final String mime = contentTypes.get(extension);
        return mime == null ? defaultCt : mime;
    }

    /**
     * @param extension the extension
     * @return <code>true</code> if the specified extension has been registered otherwise, returns <code>false</code>
     */
    public static boolean contains(String extension) {
        return contentTypes.containsKey(extension);
    }

    /**
     * <p> Associates the specified extension and content type </p>
     *
     * @param extension   the extension
     * @param contentType the content type associated with the extension
     */
    public static void put(String extension, String contentType) {
        if (extension != null && extension.length() != 0
                && contentType != null && contentType.length() != 0) {

            contentTypes.put(extension, contentType);
        }
    }

    /**
     * @param fileName the filename
     * @return the content type associated with <code>extension</code> of the
     * given filename or if no associate is found, returns
     * <code>null</code>
     */
    public static String getByFilename(String fileName) {
        String extn = getExtension(fileName);
        if (extn != null) {
            return get(extn);
        } else {
            // no extension, no content type
            return null;
        }
    }

    /**
     * Get extension of file, without fragment id
     */
    private static String getExtension(String fileName) {
        // play it safe and get rid of any fragment id
        // that might be there
        int length = fileName.length();

        int newEnd = fileName.lastIndexOf('#');
        if (newEnd == -1) {
            newEnd = length;
        }
        int i = fileName.lastIndexOf('.', newEnd);
        if (i != -1) {
            return fileName.substring(i + 1, newEnd);
        } else {
            // no extension, no content type
            return null;
        }
    }
}
