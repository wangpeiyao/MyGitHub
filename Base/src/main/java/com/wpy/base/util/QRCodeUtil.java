package com.wpy.base.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

/**
 * ��ά�빤����
 * 
 * @author WPY
 *
 */
public class QRCodeUtil {

	/**
	 * ��ȡURL��Ӧ�Ķ�ά��
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static ByteArrayOutputStream createQrGen(String url) throws IOException {
		// ��������ģ���ʹ��withCharset("UTF-8")����

		// ���ö�ά��url���ӣ�ͼƬ���250*250��JPG����
		return QRCode.from(url).withSize(250, 250).to(ImageType.JPG).stream();
	}

}
