package com.nixsolutions.service;

import java.io.IOException;

public interface CaptchaService {

	boolean verify(String captcha) throws IOException;
}
