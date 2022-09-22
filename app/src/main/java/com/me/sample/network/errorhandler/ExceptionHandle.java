package com.me.sample.network.errorhandler;

import androidx.core.net.ParseException;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

public class ExceptionHandle {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseThrowable handleException(Throwable throwable) {
        ResponseThrowable responseThrowable;
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            responseThrowable = new ResponseThrowable(throwable, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
            case UNAUTHORIZED:
                responseThrowable.message = "Unauthorized";
                break;
            case FORBIDDEN:
                responseThrowable.message = "Access FORBIDDEN";
                break;
            case NOT_FOUND:
                responseThrowable.message = "unfound";
                break;
            case REQUEST_TIMEOUT:
                responseThrowable.message = "time exceeded";
                break;
            case GATEWAY_TIMEOUT:
                responseThrowable.message = "GATEWAY_TIMEOUT";
                break;
            case INTERNAL_SERVER_ERROR:
                responseThrowable.message = "interval_error";
                break;
            case BAD_GATEWAY:
                responseThrowable.message = "wrong gateway";
                break;
            case SERVICE_UNAVAILABLE:
                responseThrowable.message = "paused service";
                break;
            default:
                responseThrowable.message = "http error";
                break;
            }
            return responseThrowable;
        } else if (throwable instanceof ServerException) {
            // 服务器异常
            ServerException resultException = (ServerException) throwable;
            responseThrowable = new ResponseThrowable(resultException, resultException.code);
            responseThrowable.message = resultException.message;
            return responseThrowable;
        } else if (throwable instanceof JsonParseException
                   || throwable instanceof JSONException
                   || throwable instanceof ParseException) {
            responseThrowable = new ResponseThrowable(throwable, ERROR.PARSE_ERROR);
            responseThrowable.message = "parsing error";
            return responseThrowable;
        } else if (throwable instanceof ConnectException) {
            responseThrowable = new ResponseThrowable(throwable, ERROR.NETWORK_ERROR);
            responseThrowable.message = "connection failed";
            return responseThrowable;
        } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
            responseThrowable = new ResponseThrowable(throwable, ERROR.SSL_ERROR);
            responseThrowable.message = "certification failed";
            return responseThrowable;
        } else if (throwable instanceof ConnectTimeoutException){
            responseThrowable = new ResponseThrowable(throwable, ERROR.TIMEOUT_ERROR);
            responseThrowable.message = "connection exceeded";
            return responseThrowable;
        } else if (throwable instanceof java.net.SocketTimeoutException) {
            responseThrowable = new ResponseThrowable(throwable, ERROR.TIMEOUT_ERROR);
            responseThrowable.message = "connection TimeOut";
            return responseThrowable;
        }
        else {
            responseThrowable = new ResponseThrowable(throwable, ERROR.UNKNOWN);
            responseThrowable.message = "Unknown Error";
            return responseThrowable;
        }
    }

    public class ERROR {
        public static final int UNKNOWN = 1000;
        public static final int PARSE_ERROR = 1001;
        public static final int NETWORK_ERROR = 1002;
        public static final int HTTP_ERROR = 1003;
        public static final int SSL_ERROR = 1005;
        public static final int TIMEOUT_ERROR = 1006;
    }

    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;

        public ResponseThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }
    }

    public static class ServerException extends RuntimeException {
        public int code;
        public String message;
    }
}