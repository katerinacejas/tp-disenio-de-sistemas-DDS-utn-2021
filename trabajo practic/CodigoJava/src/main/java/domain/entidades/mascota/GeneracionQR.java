package domain.entidades.mascota;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import net.glxn.qrgen.javase.QRCode;

public class GeneracionQR {

    public GeneracionQR(){

    }

    public String generarQR(int id, String mensaje) throws IOException {

        String barcodeText = mensaje + id; //TODO

        ByteArrayOutputStream stream = QRCode
                .from(barcodeText)
                .withSize(250, 250)
                .stream();
        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
        BufferedImage bImage = ImageIO.read(bis);

        ImageIO.write(bImage, "jpg", new File("src/main/resources/public/img/QR" + (String.valueOf(id)) + ".jpg"));

        return String.valueOf(id);
    }

    public String getQrInfo(String url) throws Exception {
        //String url = "https://news.bbc.co.uk";

        int imageSize = 200;
        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE,
                imageSize, imageSize);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "png", bos);
        String image = Base64.getEncoder().encodeToString(bos.toByteArray()); // base64 encode

        return image;
    }

}