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
	
	//二维码首页
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
	 * 手机端扫描二维码执行的方法
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
//	@RequestMapping("/loginByQrGen")
//	protected void loginByQrGen() throws ServletException, IOException {
//		// 获取二维码链接中的uuid
//		String uuid = req.getParameter("uuid");
//		// 通过应用获取共享的uuid集合
//		Map uuidMap = (Map) req.getAttribute("UUID_MAP");
//		// 如果集合内没有这个uuid，则响应结果
//		if (uuidMap == null || !uuidMap.containsKey(uuid)) {
//			resp.getOutputStream().write("二维码不存在或已失效！".getBytes());
//			return;
//		}
//		// 根据微信传来的code来获取用户的openID
//		String code = req.getParameter("code");
//		try {
//			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=YOUR_APPID" + "&secret=YOUR_SECRTC"
//					+ "&grant_type=authorization_code" + "&code=" + code;
//			Gson gson = new Gson();
//			Map map = gson.fromJson(HttpUtil.get(url, "utf-8"), new TypeToken<Map>() {
//			}.getType());
//			Object openID = map.get("openid");
//			if (openID != null && !"".equals(openID)) {
//				// 通过openID获取user对象
//				User user = dao.getUserByOpenId(openID.toString());
//				if (user != null) {
//					// 如果查询到某个user拥有该openID，则设置到map集合内
//					uuidMap.put(uuid, user);
//					// 并返回手机端扫描结果
//					resp.getOutputStream().write("登陆成功！".getBytes());
//					return;
//				}
//			}
//			// 如果没有openID参数，或查询不到openID对应的user对象，则移除该uuid，并响应结果
//			uuidMap.remove(uuid);
//			resp.getOutputStream().write("你还未绑定，请关注微信号并绑定账号！并使用微信客户端扫描！".getBytes());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 获取uuid及二维码图片地址
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/showQrGen")
	protected void showQrGen() throws ServletException, IOException {
		// 生成UUID随机数
		UUID randomUUID = UUID.randomUUID();

		// 通过应用获取共享的uuid集合
		Map<String, User> map = (Map) req.getAttribute("UUID_MAP");
		if (map == null) {
			map = new HashMap<String, User>();
			req.setAttribute("UUID_MAP", map);
		}
		// 把uuid放入map中
		map.put(randomUUID.toString(), null);

		// 二维码图片扫描后的链接
		String url = "http://192.168.1.104:8080/login?cmd=loginByQrGen&uuid=" + randomUUID;

		// 生成二维码图片
		ByteArrayOutputStream qrOut = QRCodeUtil.createQrGen(url);
		String fileName = randomUUID + ".jpg";
		OutputStream os = new FileOutputStream(new File(req.getRealPath("/temp"), fileName));
		os.write(qrOut.toByteArray());
		os.flush();
		os.close();

		// 返回页面json字符串，uuid用于轮询检查时所带的参数， img用于页面图片显示
		String jsonStr = "{\"uuid\":\"" + randomUUID + "\",\"img\":\"" + "/temp/" + fileName + "\"}";
		OutputStream outStream = resp.getOutputStream();
		outStream.write(jsonStr.getBytes());
		outStream.flush();
		outStream.close();

	}

	/**
	 * PC端不断进行轮询检查的方法
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */

	@RequestMapping("/checkScan")
	protected void checkScan(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取页面的uuid参数
		String uuid = req.getParameter("uuid");
		// 通过应用获取共享的uuid集合
		Map map = (Map) req.getSession().getServletContext().getAttribute("UUID_MAP");
		if (map != null) {
			// 查询该uuid是否存在，且二维码已经被扫描并匹配到账号
			if (map.containsKey(uuid)) {
				User user = (User) map.get(uuid);
				if (user != null) {
					// 从集合中移除
					map.remove(uuid);
					// 设置登录信息
					req.getSession().setAttribute("USER_IN_SESSION", user);
					resp.getOutputStream().write("ok".getBytes());
				} else {
					resp.getOutputStream().write("native".getBytes());
				}
			}
		}
	}

}
