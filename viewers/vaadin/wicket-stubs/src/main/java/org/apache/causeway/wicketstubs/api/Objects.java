package org.apache.causeway.wicketstubs.api;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

public final class Objects {
    private static final int BIGDEC = 9;
    private static final int BIGINT = 6;
    private static final int BOOL = 0;
    private static final int BYTE = 1;
    private static final int CHAR = 2;
    private static final int DOUBLE = 8;
    private static final int FLOAT = 7;
    private static final int INT = 4;
    private static final int LONG = 5;
    private static final int MIN_REAL_TYPE = 7;
    private static final int NONNUMERIC = 10;
    private static final int SHORT = 3;
    private static final HashMap<Class<?>, Object> primitiveDefaults = Generics.newHashMap();

    public static BigDecimal bigDecValue(Object value) throws NumberFormatException {
        if (value == null) {
            return BigDecimal.valueOf(0L);
        } else {
            Class<?> c = value.getClass();
            if (c == BigDecimal.class) {
                return (BigDecimal)value;
            } else if (c == BigInteger.class) {
                return new BigDecimal((BigInteger)value);
            } else if (c.getSuperclass() == Number.class) {
                return new BigDecimal(((Number)value).doubleValue());
            } else if (c == Boolean.class) {
                return BigDecimal.valueOf((Boolean)value ? 1L : 0L);
            } else {
                return c == Character.class ? BigDecimal.valueOf((long)(Character)value) : new BigDecimal(stringValue(value, true));
            }
        }
    }

    public static BigInteger bigIntValue(Object value) throws NumberFormatException {
        if (value == null) {
            return BigInteger.valueOf(0L);
        } else {
            Class<?> c = value.getClass();
            if (c == BigInteger.class) {
                return (BigInteger)value;
            } else if (c == BigDecimal.class) {
                return ((BigDecimal)value).toBigInteger();
            } else if (c.getSuperclass() == Number.class) {
                return BigInteger.valueOf(((Number)value).longValue());
            } else if (c == Boolean.class) {
                return BigInteger.valueOf((Boolean)value ? 1L : 0L);
            } else {
                return c == Character.class ? BigInteger.valueOf((long)(Character)value) : new BigInteger(stringValue(value, true));
            }
        }
    }

    public static boolean booleanValue(Object value) {
        if (value == null) {
            return false;
        } else {
            Class<?> c = value.getClass();
            if (c == Boolean.class) {
                return (Boolean)value;
            } else if (c == Character.class) {
                return (Character)value != 0;
            } else if (value instanceof Number) {
                return ((Number)value).doubleValue() != 0.0;
            } else {
                return true;
            }
        }
    }

    public static int compareWithConversion(Object v1, Object v2) {
        int result;
        if (v1 == v2) {
            result = 0;
        } else {
            int t1 = getNumericType(v1);
            int t2 = getNumericType(v2);
            int type = getNumericType(t1, t2, true);
            switch (type) {
                case 6:
                    result = bigIntValue(v1).compareTo(bigIntValue(v2));
                    break;
                case 9:
                    result = bigDecValue(v1).compareTo(bigDecValue(v2));
                    break;
                case 10:
                    if (t1 == 10 && t2 == 10) {
                        if (v1 instanceof Comparable && v1.getClass().isAssignableFrom(v2.getClass())) {
                            result = ((Comparable)v1).compareTo(v2);
                            break;
                        }

                        String var10002 = v1.getClass().getName();
                        throw new IllegalArgumentException("invalid comparison: " + var10002 + " and " + v2.getClass().getName());
                    }
                case 7:
                case 8:
                    double dv1 = doubleValue(v1);
                    double dv2 = doubleValue(v2);
                    return dv1 == dv2 ? 0 : (dv1 < dv2 ? -1 : 1);
                default:
                    long lv1 = longValue(v1);
                    long lv2 = longValue(v2);
                    return lv1 == lv2 ? 0 : (lv1 < lv2 ? -1 : 1);
            }
        }

        return result;
    }

    public static <T> T convertValue(Object value, Class<T> toType) {
        Object result = null;
        if (value != null) {
            if (value.getClass().isArray() && toType.isArray()) {
                Class<?> componentType = toType.getComponentType();
                result = Array.newInstance(componentType, Array.getLength(value));
                int i = 0;

                for(int icount = Array.getLength(value); i < icount; ++i) {
                    Array.set(result, i, convertValue(Array.get(value, i), componentType));
                }
            } else {
                if (toType == Integer.class || toType == Integer.TYPE) {
                    result = (int)longValue(value);
                }

                if (toType == Double.class || toType == Double.TYPE) {
                    result = doubleValue(value);
                }

                if (toType == Boolean.class || toType == Boolean.TYPE) {
                    result = booleanValue(value) ? Boolean.TRUE : Boolean.FALSE;
                }

                if (toType == Byte.class || toType == Byte.TYPE) {
                    result = (byte)((int)longValue(value));
                }

                if (toType == Character.class || toType == Character.TYPE) {
                    result = (char)((int)longValue(value));
                }

                if (toType == Short.class || toType == Short.TYPE) {
                    result = (short)((int)longValue(value));
                }

                if (toType == Long.class || toType == Long.TYPE) {
                    result = longValue(value);
                }

                if (toType == Float.class || toType == Float.TYPE) {
                    result = (float)doubleValue(value);
                }

                if (toType == BigInteger.class) {
                    result = bigIntValue(value);
                }

                if (toType == BigDecimal.class) {
                    result = bigDecValue(value);
                }

                if (toType == String.class) {
                    result = stringValue(value);
                }
            }
        } else if (toType.isPrimitive()) {
            result = primitiveDefaults.get(toType);
        }

        return (T) result; // cast added - FIXME?
    }

    public static double doubleValue(Object value) throws NumberFormatException {
        if (value == null) {
            return 0.0;
        } else {
            Class<?> c = value.getClass();
            if (c.getSuperclass() == Number.class) {
                return ((Number)value).doubleValue();
            } else if (c == Boolean.class) {
                return (Boolean)value ? 1.0 : 0.0;
            } else if (c == Character.class) {
                return (double)(Character)value;
            } else {
                String s = stringValue(value, true);
                return s.length() == 0 ? 0.0 : Double.parseDouble(s);
            }
        }
    }

    public static boolean equal(Object a, Object b) {
        if (a == b) {
            return true;
        } else {
            return a != null && b != null && a.equals(b);
        }
    }

    public static int getNumericType(int t1, int t2, boolean canBeNonNumeric) {
        if (t1 == t2) {
            return t1;
        } else if (canBeNonNumeric && (t1 == 10 || t2 == 10 || t1 == 2 || t2 == 2)) {
            return 10;
        } else {
            if (t1 == 10) {
                t1 = 8;
            }

            if (t2 == 10) {
                t2 = 8;
            }

            if (t1 >= 7) {
                if (t2 >= 7) {
                    return Math.max(t1, t2);
                } else if (t2 < 4) {
                    return t1;
                } else {
                    return t2 == 6 ? 9 : Math.max(8, t1);
                }
            } else if (t2 >= 7) {
                if (t1 < 4) {
                    return t2;
                } else {
                    return t1 == 6 ? 9 : Math.max(8, t2);
                }
            } else {
                return Math.max(t1, t2);
            }
        }
    }

    public static int getNumericType(Object value) {
        if (value != null) {
            Class<?> c = value.getClass();
            if (c == Integer.class) {
                return 4;
            }

            if (c == Double.class) {
                return 8;
            }

            if (c == Boolean.class) {
                return 0;
            }

            if (c == Byte.class) {
                return 1;
            }

            if (c == Character.class) {
                return 2;
            }

            if (c == Short.class) {
                return 3;
            }

            if (c == Long.class) {
                return 5;
            }

            if (c == Float.class) {
                return 7;
            }

            if (c == BigInteger.class) {
                return 6;
            }

            if (c == BigDecimal.class) {
                return 9;
            }
        }

        return 10;
    }

    public static int getNumericType(Object v1, Object v2) {
        return getNumericType(v1, v2, false);
    }

    public static int getNumericType(Object v1, Object v2, boolean canBeNonNumeric) {
        return getNumericType(getNumericType(v1), getNumericType(v2), canBeNonNumeric);
    }

    public static boolean isEqual(Object object1, Object object2) {
        boolean result = false;
        if (object1 == object2) {
            result = true;
        } else if (object1 != null && object1.getClass().isArray()) {
            if (object2 != null && object2.getClass().isArray() && object2.getClass() == object1.getClass()) {
                result = Array.getLength(object1) == Array.getLength(object2);
                if (result) {
                    int i = 0;

                    for(int icount = Array.getLength(object1); result && i < icount; ++i) {
                        result = isEqual(Array.get(object1, i), Array.get(object2, i));
                    }
                }
            }
        } else {
            result = object1 != null && object2 != null && (compareWithConversion(object1, object2) == 0 || object1.equals(object2));
        }

        return result;
    }

    public static long longValue(Object value) throws NumberFormatException {
        if (value == null) {
            return 0L;
        } else {
            Class<?> c = value.getClass();
            if (c.getSuperclass() == Number.class) {
                return ((Number)value).longValue();
            } else if (c == Boolean.class) {
                return (Boolean)value ? 1L : 0L;
            } else {
                return c == Character.class ? (long)(Character)value : Long.parseLong(stringValue(value, true));
            }
        }
    }

    public static Number newInteger(int type, long value) {
        switch (type) {
            case 0:
            case 2:
            case 4:
                return (int)value;
            case 1:
                return (byte)((int)value);
            case 3:
                return (short)((int)value);
            case 5:
                return value;
            case 6:
            default:
                return BigInteger.valueOf(value);
            case 7:
                return (float)value;
            case 8:
                return (double)value;
        }
    }

    public static String stringValue(Object value) {
        return stringValue(value, false);
    }

    public static int hashCode(Object... obj) {
        if (obj != null && obj.length != 0) {
            int result = 37;

            for(int i = obj.length - 1; i > -1; --i) {
                result = 37 * result + (obj[i] != null ? obj[i].hashCode() : 0);
            }

            return result;
        } else {
            return 0;
        }
    }

    public static String stringValue(Object value, boolean trim) {
        String result;
        if (value == null) {
            result = "null";
        } else {
            result = value.toString();
            if (trim) {
                result = result.trim();
            }
        }

        return result;
    }

    public static <T> T defaultIfNull(T originalObj, T defaultObj) {
        return originalObj != null ? originalObj : defaultObj;
    }

    private Objects() {
    }

    static {
        primitiveDefaults.put(Boolean.TYPE, Boolean.FALSE);
        primitiveDefaults.put(Byte.TYPE, (byte)0);
        primitiveDefaults.put(Short.TYPE, Short.valueOf((short)0));
        primitiveDefaults.put(Character.TYPE, '\u0000');
        primitiveDefaults.put(Integer.TYPE, 0);
        primitiveDefaults.put(Long.TYPE, 0L);
        primitiveDefaults.put(Float.TYPE, 0.0F);
        primitiveDefaults.put(Double.TYPE, 0.0);
        primitiveDefaults.put(BigInteger.class, new BigInteger("0"));
        primitiveDefaults.put(BigDecimal.class, new BigDecimal(0.0));
    }
}
