package org.apache.causeway.wicketstubs;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.causeway.wicketstubs.api.Args;

public abstract class Response {
    public Response() {
    }

    public abstract void write(CharSequence var1);

    public abstract void write(byte[] var1);

    public abstract void write(byte[] var1, int var2, int var3);

    public void close() {
    }

    public abstract String encodeURL(CharSequence var1);

    public void reset() {
    }

    public abstract Object getContainerResponse();

    public OutputStream getOutputStream() {
        return new StreamAdapter(this);
    }

    private static class StreamAdapter extends OutputStream {
        private final Response response;

        public StreamAdapter(Response response) {
            Args.notNull(response, "response");
            this.response = response;
        }

        public void write(int b) throws IOException {
            this.response.write(new byte[]{(byte) b});
        }

        public void write(byte[] b) throws IOException {
            this.response.write(b);
        }

        public void write(byte[] b, int off, int len) throws IOException {
            this.response.write(b, off, len);
        }

        public void close() throws IOException {
            super.close();
            this.response.close();
        }
    }
}
