package org.apache.causeway.wicketstubs;

import jakarta.servlet.http.Cookie;

import java.time.Duration;
import java.time.Instant;

public class WebResponse {
    public static final Duration MAX_CACHE_DURATION = Duration.ofDays(365L);

    public void sendRedirect(String encoding) {

    }

    public void disableCaching() {
        //FIXME
    }

    public void write(CharSequence filteredResponse) {
        //FIXME
    }

    protected String encodeURL(CharSequence url) {
        return url.toString();//FIXME
    }

    protected void setContentType(String mimeType) {
    }

    protected void setStatus(int sc) {
    }

    protected void sendError(int sc, String msg) {
    }

    protected void setContentLength(long length) {
    }

    protected String encodeRedirectURL(CharSequence url) {
    }

    protected Object getContainerResponse() {
    }

    protected void addCookie(Cookie cookie) {
    }

    protected void clearCookie(Cookie cookie) {
    }

    protected boolean isHeaderSupported() {
    }

    protected void setHeader(String name, String value) {
    }

    protected void addHeader(String name, String value) {
    }

    protected void setDateHeader(String name, Instant date) {
    }

    protected void flush() {

    }
}
