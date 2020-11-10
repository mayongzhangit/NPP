package com.myz.npp.web.sdk;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.util.Base64;

/**
 * 对微信小程序用户加密数据的解密示例代码.
 *
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */
public class WXBizDataCrypt {
    private String appid;
	private String sessionKey;

	public WXBizDataCrypt (String appid, String sessionKey) {
		this.appid = appid;
		this.sessionKey = sessionKey;
	}


	/**
	 * 检验数据的真实性，并且获取解密后的明文.
	 * @param encryptedData string 加密的用户数据
	 * @param iv string 与用户数据一同返回的初始向量
     *
	 * @return string 成功0，失败返回对应的错误码
	 */
	/**
	 * 检验数据的真实性，并且获取解密后的明文.
	 *
	 * @param encryptedData string 加密的用户数据
	 * @param iv            string 与用户数据一同返回的初始向量
	 * @return data string 解密后的原文
	 */
	public String decryptData(String encryptedData, String iv) {
		if (StringUtils.length(sessionKey) != 24) {
			return "ErrorCode::$IllegalAesKey;";
		}
		// 对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
		byte[] aesKey = Base64.getDecoder().decode(sessionKey);

		if (StringUtils.length(iv) != 24) {
			return "ErrorCode::$IllegalIv;";
		}
		// 对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
		byte[] aesIV = Base64.getDecoder().decode(iv);

		// 对称解密的目标密文为 Base64_Decode(encryptedData)
		byte[] aesCipher = Base64.getDecoder().decode(encryptedData);

		try {
			byte[] resultByte = AESUtils.decrypt(aesCipher, aesKey, aesIV);
			return new String(resultByte);
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String java_openssl_encrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"),
				new IvParameterSpec(iv));
		String base64Str = Base64.getEncoder().encodeToString(cipher.doFinal(data));
		return base64Str;
	}


	public static void main(String[] args) throws Exception {

		String appid = "wx4f4bc4dec97d474b";
		String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";

		String encryptedData="CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";

		String iv = "r7BXXKkLb8qrSNn05n0qiA==";

		WXBizDataCrypt wx4f4bc4dec97d474b = new WXBizDataCrypt(appid, sessionKey);
		String s = wx4f4bc4dec97d474b.decryptData(encryptedData, iv);
		System.out.println(s);

		String s2 = wx4f4bc4dec97d474b.decryptData(encryptedData, iv);
		System.out.println(s2);
	}
}

