package org.apache.causeway.wicketstubs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public final class AppendingStringBuffer implements Serializable, CharSequence {
    private static final long serialVersionUID = 1L;
    private static final AppendingStringBuffer NULL = new AppendingStringBuffer("null");
    private static final StringBuilder SB_NULL = new StringBuilder("null");
    private static final StringBuffer SBF_NULL = new StringBuffer("null");
    private char[] value;
    private int count;

    public AppendingStringBuffer() {
        this(16);
    }

    public AppendingStringBuffer(int length) {
        this.value = new char[length];
    }

    public AppendingStringBuffer(CharSequence str) {
        this(str.length() + 16);
        this.append((Object) str);
    }

    public int length() {
        return this.count;
    }

    public int capacity() {
        return this.value.length;
    }

    public void ensureCapacity(int minimumCapacity) {
        if (minimumCapacity > this.value.length) {
            this.expandCapacity(minimumCapacity);
        }

    }

    private void expandCapacity(int minimumCapacity) {
        int newCapacity = (this.value.length + 1) * 2;
        if (newCapacity < 0) {
            newCapacity = Integer.MAX_VALUE;
        } else if (minimumCapacity > newCapacity) {
            newCapacity = minimumCapacity;
        }

        char[] newValue = new char[newCapacity];
        System.arraycopy(this.value, 0, newValue, 0, this.count);
        this.value = newValue;
    }

    public void setLength(int newLength) {
        if (newLength < 0) {
            throw new StringIndexOutOfBoundsException(newLength);
        } else {
            if (newLength > this.value.length) {
                this.expandCapacity(newLength);
            }

            if (this.count < newLength) {
                while (this.count < newLength) {
                    this.value[this.count] = 0;
                    ++this.count;
                }
            } else {
                this.count = newLength;
            }

        }
    }

    public char charAt(int index) {
        if (index >= 0 && index < this.count) {
            return this.value[index];
        } else {
            throw new StringIndexOutOfBoundsException(index);
        }
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        if (srcBegin < 0) {
            throw new StringIndexOutOfBoundsException(srcBegin);
        } else if (srcEnd >= 0 && srcEnd <= this.count) {
            if (srcBegin > srcEnd) {
                throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
            } else {
                System.arraycopy(this.value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
            }
        } else {
            throw new StringIndexOutOfBoundsException(srcEnd);
        }
    }

    public void setCharAt(int index, char ch) {
        if (index >= 0 && index < this.count) {
            this.value[index] = ch;
        } else {
            throw new StringIndexOutOfBoundsException(index);
        }
    }

    public AppendingStringBuffer append(Object obj) {
        if (obj instanceof AppendingStringBuffer) {
            return this.append((AppendingStringBuffer) obj);
        } else if (obj instanceof StringBuilder) {
            return this.append((StringBuilder) obj);
        } else {
            return obj instanceof StringBuffer ? this.append(obj.toString()) : this.append(String.valueOf(obj));
        }
    }

    public AppendingStringBuffer append(String str) {
        if (str == null) {
            str = String.valueOf(str);
        }

        int len = str.length();
        int newcount = this.count + len;
        if (newcount > this.value.length) {
            this.expandCapacity(newcount);
        }

        str.getChars(0, len, this.value, this.count);
        this.count = newcount;
        return this;
    }

    public AppendingStringBuffer append(AppendingStringBuffer sb) {
        if (sb == null) {
            sb = NULL;
        }

        int len = sb.length();
        int newcount = this.count + len;
        if (newcount > this.value.length) {
            this.expandCapacity(newcount);
        }

        sb.getChars(0, len, this.value, this.count);
        this.count = newcount;
        return this;
    }

    public AppendingStringBuffer append(StringBuilder sb) {
        if (sb == null) {
            sb = SB_NULL;
        }

        int len = sb.length();
        int newcount = this.count + len;
        if (newcount > this.value.length) {
            this.expandCapacity(newcount);
        }

        sb.getChars(0, len, this.value, this.count);
        this.count = newcount;
        return this;
    }

    public AppendingStringBuffer append(char[] str) {
        int len = str.length;
        int newcount = this.count + len;
        if (newcount > this.value.length) {
            this.expandCapacity(newcount);
        }

        System.arraycopy(str, 0, this.value, this.count, len);
        this.count = newcount;
        return this;
    }

    public AppendingStringBuffer append(char[] str, int offset, int len) {
        int newcount = this.count + len;
        if (newcount > this.value.length) {
            this.expandCapacity(newcount);
        }

        System.arraycopy(str, offset, this.value, this.count, len);
        this.count = newcount;
        return this;
    }

    public AppendingStringBuffer append(boolean b) {
        int newcount;
        if (b) {
            newcount = this.count + 4;
            if (newcount > this.value.length) {
                this.expandCapacity(newcount);
            }

            this.value[this.count++] = 't';
            this.value[this.count++] = 'r';
            this.value[this.count++] = 'u';
            this.value[this.count++] = 'e';
        } else {
            newcount = this.count + 5;
            if (newcount > this.value.length) {
                this.expandCapacity(newcount);
            }

            this.value[this.count++] = 'f';
            this.value[this.count++] = 'a';
            this.value[this.count++] = 'l';
            this.value[this.count++] = 's';
            this.value[this.count++] = 'e';
        }

        return this;
    }

    public AppendingStringBuffer append(char c) {
        int newcount = this.count + 1;
        if (newcount > this.value.length) {
            this.expandCapacity(newcount);
        }

        this.value[this.count++] = c;
        return this;
    }

    public AppendingStringBuffer append(int i) {
        return this.append(String.valueOf(i));
    }

    public AppendingStringBuffer append(long l) {
        return this.append(String.valueOf(l));
    }

    public AppendingStringBuffer append(float f) {
        return this.append(String.valueOf(f));
    }

    public AppendingStringBuffer append(double d) {
        return this.append(String.valueOf(d));
    }

    public AppendingStringBuffer delete(int start, int end) {
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        } else {
            if (end > this.count) {
                end = this.count;
            }

            if (start > end) {
                throw new StringIndexOutOfBoundsException();
            } else {
                int len = end - start;
                if (len > 0) {
                    System.arraycopy(this.value, start + len, this.value, start, this.count - end);
                    this.count -= len;
                }

                return this;
            }
        }
    }

    public AppendingStringBuffer deleteCharAt(int index) {
        if (index >= 0 && index < this.count) {
            System.arraycopy(this.value, index + 1, this.value, index, this.count - index - 1);
            --this.count;
            return this;
        } else {
            throw new StringIndexOutOfBoundsException();
        }
    }

    public AppendingStringBuffer replace(int start, int end, String str) {
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        } else {
            if (end > this.count) {
                end = this.count;
            }

            if (start > end) {
                throw new StringIndexOutOfBoundsException();
            } else {
                int len = str.length();
                int newCount = this.count + len - (end - start);
                if (newCount > this.value.length) {
                    this.expandCapacity(newCount);
                }

                System.arraycopy(this.value, end, this.value, start + len, this.count - end);
                str.getChars(0, len, this.value, start);
                this.count = newCount;
                return this;
            }
        }
    }

    public String substring(int start) {
        return this.substring(start, this.count);
    }

    public CharSequence subSequence(int start, int end) {
        return this.substring(start, end);
    }

    public String substring(int start, int end) {
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        } else if (end > this.count) {
            throw new StringIndexOutOfBoundsException(end);
        } else if (start > end) {
            throw new StringIndexOutOfBoundsException(end - start);
        } else {
            return new String(this.value, start, end - start);
        }
    }

    public AppendingStringBuffer insert(int index, char[] str, int offset, int len) {
        if (index >= 0 && index <= this.count) {
            if (offset >= 0 && offset + len >= 0 && offset + len <= str.length) {
                if (len < 0) {
                    throw new StringIndexOutOfBoundsException(len);
                } else {
                    int newCount = this.count + len;
                    if (newCount > this.value.length) {
                        this.expandCapacity(newCount);
                    }

                    System.arraycopy(this.value, index, this.value, index + len, this.count - index);
                    System.arraycopy(str, offset, this.value, index, len);
                    this.count = newCount;
                    return this;
                }
            } else {
                throw new StringIndexOutOfBoundsException(offset);
            }
        } else {
            throw new StringIndexOutOfBoundsException();
        }
    }

    public AppendingStringBuffer insert(int offset, Object obj) {
        if (obj instanceof AppendingStringBuffer asb) {
            return this.insert(offset, asb.value, 0, asb.count);
        } else if (obj instanceof StringBuffer) {
            return this.insert(offset, (StringBuffer) obj);
        } else {
            return obj instanceof StringBuilder ? this.insert(offset, (StringBuilder) obj) : this.insert(offset, String.valueOf(obj));
        }
    }

    public AppendingStringBuffer insert(int offset, String str) {
        if (offset >= 0 && offset <= this.count) {
            if (str == null) {
                str = String.valueOf(str);
            }

            int len = str.length();
            int newcount = this.count + len;
            if (newcount > this.value.length) {
                this.expandCapacity(newcount);
            }

            System.arraycopy(this.value, offset, this.value, offset + len, this.count - offset);
            str.getChars(0, len, this.value, offset);
            this.count = newcount;
            return this;
        } else {
            throw new StringIndexOutOfBoundsException();
        }
    }

    public AppendingStringBuffer insert(int offset, StringBuilder str) {
        if (offset >= 0 && offset <= this.count) {
            if (str == null) {
                str = SB_NULL;
            }

            int len = str.length();
            int newcount = this.count + len;
            if (newcount > this.value.length) {
                this.expandCapacity(newcount);
            }

            System.arraycopy(this.value, offset, this.value, offset + len, this.count - offset);
            str.getChars(0, len, this.value, offset);
            this.count = newcount;
            return this;
        } else {
            throw new StringIndexOutOfBoundsException();
        }
    }

    public AppendingStringBuffer insert(int offset, StringBuffer str) {
        if (offset >= 0 && offset <= this.count) {
            if (str == null) {
                str = SBF_NULL;
            }

            int len = str.length();
            int newcount = this.count + len;
            if (newcount > this.value.length) {
                this.expandCapacity(newcount);
            }

            System.arraycopy(this.value, offset, this.value, offset + len, this.count - offset);
            str.getChars(0, len, this.value, offset);
            this.count = newcount;
            return this;
        } else {
            throw new StringIndexOutOfBoundsException();
        }
    }

    public AppendingStringBuffer insert(int offset, char[] str) {
        if (offset >= 0 && offset <= this.count) {
            int len = str.length;
            int newcount = this.count + len;
            if (newcount > this.value.length) {
                this.expandCapacity(newcount);
            }

            System.arraycopy(this.value, offset, this.value, offset + len, this.count - offset);
            System.arraycopy(str, 0, this.value, offset, len);
            this.count = newcount;
            return this;
        } else {
            throw new StringIndexOutOfBoundsException();
        }
    }

    public AppendingStringBuffer insert(int offset, boolean b) {
        return this.insert(offset, String.valueOf(b));
    }

    public AppendingStringBuffer insert(int offset, char c) {
        int newcount = this.count + 1;
        if (newcount > this.value.length) {
            this.expandCapacity(newcount);
        }

        System.arraycopy(this.value, offset, this.value, offset + 1, this.count - offset);
        this.value[offset] = c;
        this.count = newcount;
        return this;
    }

    public AppendingStringBuffer insert(int offset, int i) {
        return this.insert(offset, String.valueOf(i));
    }

    public AppendingStringBuffer insert(int offset, long l) {
        return this.insert(offset, String.valueOf(l));
    }

    public AppendingStringBuffer insert(int offset, float f) {
        return this.insert(offset, String.valueOf(f));
    }

    public AppendingStringBuffer insert(int offset, double d) {
        return this.insert(offset, String.valueOf(d));
    }

    public int indexOf(String str) {
        return this.indexOf(str, 0);
    }

    public int indexOf(String str, int fromIndex) {
        return indexOf(this.value, 0, this.count, str.toCharArray(), 0, str.length(), fromIndex);
    }

    static int indexOf(char[] source, int sourceOffset, int sourceCount, char[] target, int targetOffset, int targetCount, int fromIndex) {
        if (fromIndex >= sourceCount) {
            return targetCount == 0 ? sourceCount : -1;
        } else {
            if (fromIndex < 0) {
                fromIndex = 0;
            }

            if (targetCount == 0) {
                return fromIndex;
            } else {
                char first = target[targetOffset];
                int i = sourceOffset + fromIndex;
                int max = sourceOffset + (sourceCount - targetCount);

                while (true) {
                    while (i > max || source[i] == first) {
                        if (i > max) {
                            return -1;
                        }

                        int j = i + 1;
                        int end = j + targetCount - 1;
                        int k = targetOffset + 1;

                        do {
                            if (j >= end) {
                                return i - sourceOffset;
                            }
                        } while (source[j++] == target[k++]);

                        ++i;
                    }

                    ++i;
                }
            }
        }
    }

    public int lastIndexOf(String str) {
        return this.lastIndexOf(str, this.count);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return lastIndexOf(this.value, 0, this.count, str.toCharArray(), 0, str.length(), fromIndex);
    }

    static int lastIndexOf(char[] source, int sourceOffset, int sourceCount, char[] target, int targetOffset, int targetCount, int fromIndex) {
        int rightIndex = sourceCount - targetCount;
        if (fromIndex < 0) {
            return -1;
        } else {
            if (fromIndex > rightIndex) {
                fromIndex = rightIndex;
            }

            if (targetCount == 0) {
                return fromIndex;
            } else {
                int strLastIndex = targetOffset + targetCount - 1;
                char strLastChar = target[strLastIndex];
                int min = sourceOffset + targetCount - 1;
                int i = min + fromIndex;

                while (true) {
                    while (i < min || source[i] == strLastChar) {
                        if (i < min) {
                            return -1;
                        }

                        int j = i - 1;
                        int start = j - (targetCount - 1);
                        int k = strLastIndex - 1;

                        do {
                            if (j <= start) {
                                return start - sourceOffset + 1;
                            }
                        } while (source[j--] == target[k--]);

                        --i;
                    }

                    --i;
                }
            }
        }
    }

    public boolean startsWith(CharSequence prefix, int toffset) {
        char[] ta = this.value;
        int to = toffset;
        int po = 0;
        int pc = prefix.length();
        if (toffset >= 0 && toffset <= this.count - pc) {
            do {
                --pc;
                if (pc < 0) {
                    return true;
                }
            } while (ta[to++] == prefix.charAt(po++));

            return false;
        } else {
            return false;
        }
    }

    public boolean startsWith(CharSequence prefix) {
        return this.startsWith(prefix, 0);
    }

    public boolean endsWith(CharSequence suffix) {
        return this.startsWith(suffix, this.count - suffix.length());
    }

    public String toString() {
        return new String(this.value, 0, this.count);
    }

    public final char[] getValue() {
        return this.value;
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.value = (char[]) this.value.clone();
    }

    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        } else {
            int n;
            char[] v1;
            if (anObject instanceof AppendingStringBuffer) {
                AppendingStringBuffer anotherString = (AppendingStringBuffer) anObject;
                n = this.count;
                if (n == anotherString.count) {
                    v1 = this.value;
                    char[] v2 = anotherString.value;
                    int i = 0;

                    do {
                        if (n-- == 0) {
                            return true;
                        }
                    } while (v1[i] == v2[i++]);

                    return false;
                }
            } else if (anObject instanceof CharSequence) {
                CharSequence sequence = (CharSequence) anObject;
                n = this.count;
                if (sequence.length() == this.count) {
                    v1 = this.value;
                    int i = 0;

                    do {
                        if (n-- == 0) {
                            return true;
                        }
                    } while (v1[i] == sequence.charAt(i++));

                    return false;
                }
            }

            return false;
        }
    }

    public int hashCode() {
        int h = 0;
        if (h == 0) {
            int off = 0;
            char[] val = this.value;
            int len = this.count;

            for (int i = 0; i < len; ++i) {
                h = 31 * h + val[off++];
            }
        }

        return h;
    }

    public void clear() {
        this.count = 0;
    }
}
