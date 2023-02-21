package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//ファイルの保存用のディレクトリにアクセスできなかったり、アップロードされたファイルが受付最大値を超えている場合、
//MultipartExceptionが発生。「@RestContollerAdvice」を付与した例外ハンドクラスで処理するようにしたほうがいい
//MultipartExceptionが発生するのは、サイズ超過と決め打ちしてHTTPステータスステータスは413
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

		@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
		@ExceptionHandler(MultipartException.class)
		public String handleMultipart(MultipartException e) {
			//レスポンス作成
			return "ERROR999";
		}
}
