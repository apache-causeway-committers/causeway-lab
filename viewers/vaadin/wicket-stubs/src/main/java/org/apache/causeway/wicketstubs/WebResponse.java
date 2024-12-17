package org.apache.causeway.wicketstubs;

import jakarta.servlet.http.Cookie;

import java.time.Duration;
import java.time.Instant;

public class WebResponse {
    public static final Duration MAX_CACHE_DURATION = Duration.ofDays(365L);

    public void sendRedirect(String encoding) {

    }

    public void disableCaching() {
    }

    public void write(CharSequence filteredResponse) {
    }

    protected String encodeURL(CharSequence url) {
        return url.toString();
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
        return null;
    }

    protected Object getContainerResponse() {
        return null;
    }

    protected void addCookie(Cookie cookie) {
    }

    protected void clearCookie(Cookie cookie) {
    }

    protected boolean isHeaderSupported() {
        return false;
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
