package com.ryancat.greenhouseclient.annotation;

import com.ryancat.greenhouseclient.util.Constants;

/**
 * Http的注解，标注这个字段是请求还是响应
 * @author RyanHu
 *
 */
public @interface HttpAnnotation {
	String HttpType() default Constants.HTTP_REQUEST ;
}
