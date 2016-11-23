package ameba.util;

import java.io.Serializable;
import java.net.URL;
import java.util.Comparator;

/**
 * <p>UrlExternalFormComparator class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public class UrlExternalFormComparator implements Comparator<URL>, Serializable
{
    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(URL url1, URL url2)
    {
        return url1.toExternalForm().compareTo(url2.toExternalForm());
    }
}
