import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyUtils {
    /**
     *
     * @param is
     * @return Array of size 2
     */
    public static InputStream[] cloneInputStream(InputStream is) throws IOException {
        //Clone inputstream of request body
        //When reading InputStream, after finishing reading all, the next function that needs that inputstream, can't read,
        //because the inputstream reached the end.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(is.readAllBytes()); //he.getRequestBody() inputstream is now eof => useless
        baos.flush();

        InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
        InputStream is2 = new ByteArrayInputStream(baos.toByteArray());

        return new InputStream[]{is1, is2};
    }
}
