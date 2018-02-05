package com.wpy.base.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wpy.base.cache.PoolCache;
import com.wpy.base.cache.ScanPool;
import com.wpy.base.domain.User;
import com.wpy.base.util.QRCodeUtil;

@Controller("loginContorller")
@RequestMapping(value = "/login")
public class LoginContorller {
	
	private static String APPID="wx969fe1c1d234af78";

	@Autowired
	private HttpServletRequest req;

	@Autowired
	private HttpServletResponse resp;
	
	//��ά����ҳ
	public String index() {
	        try {
	            String uuid = UUID.randomUUID().toString();
//	            super.getRequest().setAttribute("uuid", uuid);
	            ScanPool pool = new ScanPool();
	            pool.setCreateTime(System.currentTimeMillis());
	            Map<String, ScanPool> map = new HashMap<String, ScanPool>(1);
	            map.put(uuid, pool);
	            PoolCache.cacheMap.put(uuid, pool);
	            pool = null;
	        } catch (Exception e) {
	        }
	        return "index";
	    }

	/**
	 * �ֻ���ɨ���ά��ִ�еķ���
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
//	@RequestMapping("/loginByQrGen")
//	protected void loginByQrGen() throws ServletException, IOException {
//		// ��ȡ��ά�������е�uuid
//		String uuid = req.getParameter("uuid");
//		// ͨ��Ӧ�û�ȡ�����uuid����
//		Map uuidMap = (Map) req.getAttribute("UUID_MAP");
//		// ���������û�����uuid������Ӧ���
//		if (uuidMap == null || !uuidMap.containsKey(uuid)) {
//			resp.getOutputStream().write("��ά�벻���ڻ���ʧЧ��".getBytes());
//			return;
//		}
//		// ����΢�Ŵ�����code����ȡ�û���openID
//		String code = req.getParameter("code");
//		try {
//			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=YOUR_APPID" + "&secret=YOUR_SECRTC"
//					+ "&grant_type=authorization_code" + "&code=" + code;
//			Gson gson = new Gson();
//			Map map = gson.fromJson(HttpUtil.get(url, "utf-8"), new TypeToken<Map>() {
//			}.getType());
//			Object openID = map.get("openid");
//			if (openID != null && !"".equals(openID)) {
//				// ͨ��openID��ȡuser����
//				User user = dao.getUserByOpenId(openID.toString());
//				if (user != null) {
//					// �����ѯ��ĳ��userӵ�и�openID�������õ�map������
//					uuidMap.put(uuid, user);
//					// �������ֻ���ɨ����
//					resp.getOutputStream().write("��½�ɹ���".getBytes());
//					return;
//				}
//			}
//			// ���û��openID���������ѯ����openID��Ӧ��user�������Ƴ���uuid������Ӧ���
//			uuidMap.remove(uuid);
//			resp.getOutputStream().write("�㻹δ�󶨣����ע΢�źŲ����˺ţ���ʹ��΢�ſͻ���ɨ�裡".getBytes());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * ��ȡuuid����ά��ͼƬ��ַ
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/showQrGen")
	protected void showQrGen() throws ServletException, IOException {
		// ����UUID�����
		UUID randomUUID = UUID.randomUUID();

		// ͨ��Ӧ�û�ȡ�����uuid����
		Map<String, User> map = (Map) req.getAttribute("UUID_MAP");
		if (map == null) {
			map = new HashMap<String, User>();
			req.setAttribute("UUID_MAP", map);
		}
		// ��uuid����map��
		map.put(randomUUID.toString(), null);

		// ��ά��ͼƬɨ��������
		String url = "http://192.168.1.104:8080/login?cmd=loginByQrGen&uuid=" + randomUUID;

		// ���ɶ�ά��ͼƬ
		ByteArrayOutputStream qrOut = QRCodeUtil.createQrGen(url);
		String fileName = randomUUID + ".jpg";
		OutputStream os = new FileOutputStream(new File(req.getRealPath("/temp"), fileName));
		os.write(qrOut.toByteArray());
		os.flush();
		os.close();

		// ����ҳ��json�ַ�����uuid������ѯ���ʱ�����Ĳ����� img����ҳ��ͼƬ��ʾ
		String jsonStr = "{\"uuid\":\"" + randomUUID + "\",\"img\":\"" + "/temp/" + fileName + "\"}";
		OutputStream outStream = resp.getOutputStream();
		outStream.write(jsonStr.getBytes());
		outStream.flush();
		outStream.close();

	}

	/**
	 * PC�˲��Ͻ�����ѯ���ķ���
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */

	@RequestMapping("/checkScan")
	protected void checkScan(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ��ȡҳ���uuid����
		String uuid = req.getParameter("uuid");
		// ͨ��Ӧ�û�ȡ�����uuid����
		Map map = (Map) req.getSession().getServletContext().getAttribute("UUID_MAP");
		if (map != null) {
			// ��ѯ��uuid�Ƿ���ڣ��Ҷ�ά���Ѿ���ɨ�貢ƥ�䵽�˺�
			if (map.containsKey(uuid)) {
				User user = (User) map.get(uuid);
				if (user != null) {
					// �Ӽ������Ƴ�
					map.remove(uuid);
					// ���õ�¼��Ϣ
					req.getSession().setAttribute("USER_IN_SESSION", user);
					resp.getOutputStream().write("ok".getBytes());
				} else {
					resp.getOutputStream().write("native".getBytes());
				}
			}
		}
	}

}
