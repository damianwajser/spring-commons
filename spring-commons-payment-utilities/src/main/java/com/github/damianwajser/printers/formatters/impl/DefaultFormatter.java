package com.github.damianwajser.printers.formatters.impl;

import com.github.damianwajser.printers.formatters.Formatter;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DefaultFormatter implements Formatter {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFormatter.class);

	public byte[] write(String text, int width, int height) throws WriterException, IOException {
		LOGGER.info("Will generate image code=[{}], width=[{}], height=[{}]", text, width, height);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
		MatrixToImageWriter.writeToStream(matrix, MediaType.IMAGE_PNG.getSubtype(), baos, new MatrixToImageConfig());
		return baos.toByteArray();
	}

	public String read(byte[] qr) throws NotFoundException, IOException {
		InputStream qrBytes = new ByteArrayInputStream(qr);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(qrBytes))));
		Result result = new MultiFormatReader().decode(binaryBitmap);
		return result.getText();
	}
}
