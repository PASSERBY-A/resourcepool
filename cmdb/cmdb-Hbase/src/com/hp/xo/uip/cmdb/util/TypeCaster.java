package com.hp.xo.uip.cmdb.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TypeCaster {
	/**
	 * 取得原始类对应的包装类
	 * @param t
	 * @return
	 */
	protected static Class<?> getWrappedType(Class<?> t) {
		if(!t.isPrimitive())
			return t;
		if(t.isAssignableFrom(int.class))
			return Integer.class;
		if(t.isAssignableFrom(char.class))
			return Character.class;
		if(t.isAssignableFrom(long.class))
			return Long.class;
		if(t.isAssignableFrom(double.class))
			return Double.class;
		if(t.isAssignableFrom(float.class))
			return Float.class;
		if(t.isAssignableFrom(byte.class))
			return Byte.class;
		if(t.isAssignableFrom(boolean.class))
			return Boolean.class;
		if(t.isAssignableFrom(short.class))
			return Short.class;
		return t;
	}
	
	protected static Class<?> getPrimitiveType(Class<?> t) {
		if(t.isPrimitive())
			return null;
		if(t.isAssignableFrom(Integer.class))
			return int.class;
		if(t.isAssignableFrom(Character.class))
			return char.class;
		if(t.isAssignableFrom(Long.class))
			return long.class;
		if(t.isAssignableFrom(Double.class))
			return double.class;
		if(t.isAssignableFrom(Float.class))
			return float.class;
		if(t.isAssignableFrom(Byte.class))
			return byte.class;
		if(t.isAssignableFrom(Boolean.class))
			return boolean.class;
		if(t.isAssignableFrom(Short.class))
			return short.class;
		return t;
	}
	
	/**
	 * 转换对象到指定类型
	 * @param t 目标类型
	 * @param v 需要转换的值
	 * @return
	 */
	public static Object cast(Class<?> t, Object v) {
		if(null == v)
			return null;
		t = getWrappedType(t);
		if(t.isInstance(v))
			return v;
		else { 
			//目标为日期类型
			if(Date.class.isAssignableFrom(t)) {
				if(v instanceof Number)
					return new Date(((Number)v).longValue());
				return stringToDate(v.toString());
			}
			//目标为字符串
			else if(String.class.isAssignableFrom(t)) {
				if(v instanceof Date)
					return dateToString((Date)v);
				else
					return v.toString();
			}
			//如果对象和目标类型同为Number的子类
			else if((Number.class.isAssignableFrom(t) && (v instanceof Number || v instanceof Date))) {
				Number nv;
				if(v instanceof Date)
					nv = ((Date)v).getTime();
				else
					nv = (Number)v;
				if(Integer.class.isAssignableFrom(t))
					return nv.intValue();
				else if(Double.class.isAssignableFrom(t))
					return nv.doubleValue();
				else if(Short.class.isAssignableFrom(t))
					return nv.shortValue();
				else if(Byte.class.isAssignableFrom(t))
					return nv.byteValue();
				else if(Float.class.isAssignableFrom(t))
					return nv.floatValue();
				else if(Long.class.isAssignableFrom(t))
					return nv.longValue();
			}
			//如果对象为字符串
			else if(v instanceof String) {
				String s = v.toString();
				if(Integer.class.isAssignableFrom(t))
					return Integer.valueOf(s);
				else if(Double.class.isAssignableFrom(t))
					return Double.valueOf(s);
				else if(Short.class.isAssignableFrom(t))
					return Short.valueOf(s);
				else if(Byte.class.isAssignableFrom(t))
					return Byte.valueOf(s);
				else if(Float.class.isAssignableFrom(t))
					return Float.valueOf(s);
				else if(Long.class.isAssignableFrom(t))
					return Long.valueOf(s);
				else if(Boolean.class.isAssignableFrom(t))
					return Boolean.valueOf(s);
				else if(Character.class.isAssignableFrom(t)) {
					if(s.length()>0)
						return s.charAt(0);
				}
			}
		}
		return t.cast(v);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T cast(Class<T> c, Object v, T def) {
		try {
			Object r = cast(c, v);
			if(c.isInstance(r))
				return (T)r;
			return def;
		}
		catch(Throwable e) {
			return def;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object v, T def) {
		try {
			if(null == def)
				return (T)v;
			Object r = cast(def.getClass(), v);
			if(null != r)
				return (T)r;
			return def;
		}
		catch(Throwable e) {
			return def;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object v, Class<T> c) {
			Object r = cast(c, v);
			return (T)r;
	}
	
	public static Number toNumber(Object value) {
		if(null == value)
			return null;
		BigDecimal d;
		if(value instanceof Date)
			d = new BigDecimal(((Date)value).getTime());
		else if(!(value instanceof Number)) {
			try {
				d = new BigDecimal(value.toString());
			}
			catch(Throwable e) {
				return null;
			}
		}
		else {
			if(value instanceof BigDecimal)
				d = (BigDecimal)value;
			else
				d = new BigDecimal(((Number)value).doubleValue());
		}
		if(Double.compare(d.doubleValue(),d.intValue()) == 0) {
			if(d.doubleValue()>Double.MAX_VALUE || d.doubleValue()<Double.MIN_VALUE)
				return d.doubleValue();
			else if(d.doubleValue()>Integer.MAX_VALUE || d.doubleValue()<Integer.MIN_VALUE)
				return d.longValue();
			else
				return d.intValue();
		}
		else
			return d.doubleValue();
	}
	
	public static Boolean toBoolean(Object value) {
		if(null == value)
			return false;
		if(value instanceof Boolean)
			return (Boolean)value;
		else if(value instanceof Number) 
			return ((Number)value).intValue() != 0;
		else {
			String s = value.toString();
			return ("true".equalsIgnoreCase(s) || 
					"1".equalsIgnoreCase(s) || 
					"ok".equalsIgnoreCase(s) ||
					"yes".equalsIgnoreCase(s));
		}
	}

	public static String dateToString(Date value) {
		if(null == value)
			return null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(value);
	}
	
	//初始化常见的日期字符串格式模板
	private static List<DateFormat> datePatterns;
	static {
		datePatterns = new ArrayList<DateFormat>();
		datePatterns.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		datePatterns.add(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
		datePatterns.add(new SimpleDateFormat("yyyyMMddHHmmss"));
		datePatterns.add(new SimpleDateFormat("yyyy-MM-dd"));
		datePatterns.add(new SimpleDateFormat("yyyy/MM/dd"));
		datePatterns.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
		datePatterns.add(new SimpleDateFormat("MM/dd/yy hh:mm aa",Locale.ENGLISH));
	}
	
	public static Date stringToDate(String value, String pattern) {
		if(null == value)
			return null;
		if(null == pattern)
			return stringToDate(value);
		DateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(value);
		} catch (ParseException e) {
			return stringToDate(value);
		}
	}
	
	public static Date stringToDate(String value) {
		if(null == value)
			return null;
		Date date = null;
		synchronized(datePatterns) {
			for(DateFormat format : datePatterns) {
				try {
					date = format.parse(value);
					break;
				} catch (ParseException e) {}
			}
		}
		return date;
	}
	
	public static long[] toArray(List<?> list, long def) {
		long[] array = new long[list.size()];
		for(int i=0; i<array.length; i++) {
			array[i] = cast(Long.class, list.get(i), def);
		}
		return array;
	}
	
	public static int[] toArray(List<?> list, int def) {
		int[] array = new int[list.size()];
		for(int i=0; i<array.length; i++) {
			array[i] = cast(Integer.class, list.get(i), def);
		}
		return array;
	}
	
	public static byte[] toArray(List<?> list, byte def) {
		byte[] array = new byte[list.size()];
		for(int i=0; i<array.length; i++) {
			array[i] = cast(Byte.class, list.get(i), def);
		}
		return array;
	}
	
	public static char[] toArray(List<?> list, char def) {
		char[] array = new char[list.size()];
		for(int i=0; i<array.length; i++) {
			array[i] = cast(Character.class, list.get(i), def);
		}
		return array;
	}
	
	public static short[] toArray(List<?> list, short def) {
		short[] array = new short[list.size()];
		for(int i=0; i<array.length; i++) {
			array[i] = cast(Short.class, list.get(i), def);
		}
		return array;
	}
	
	public static float[] toArray(List<?> list, float def) {
		float[] array = new float[list.size()];
		for(int i=0; i<array.length; i++) {
			array[i] = cast(Float.class, list.get(i), def);
		}
		return array;
	}
	
	public static double[] toArray(List<?> list, double def) {
		double[] array = new double[list.size()];
		for(int i=0; i<array.length; i++) {
			array[i] = cast(Double.class, list.get(i), def);
		}
		return array;
	}
	
	public static boolean[] toArray(List<?> list, boolean def) {
		boolean[] array = new boolean[list.size()];
		for(int i=0; i<array.length; i++) {
			array[i] = cast(Boolean.class, list.get(i), def);
		}
		return array;
	}
	
	public static String[] toArray(List<?> list, String def) {
		String[] array = new String[list.size()];
		for(int i=0; i<array.length; i++) {
			array[i] = cast(String.class, list.get(i), def);
		}
		return array;
	}
	
	public List<Long> toList(long[] array) {
		List<Long> result = new ArrayList<Long>(array.length);
		for(long v : array)
			result.add(v);
		return result;
	}
	
	public List<Integer> toList(int[] array) {
		List<Integer> result = new ArrayList<Integer>(array.length);
		for(int v : array)
			result.add(v);
		return result;
	}
	
	public List<Byte> toList(byte[] array) {
		List<Byte> result = new ArrayList<Byte>(array.length);
		for(byte v : array)
			result.add(v);
		return result;
	}
	
	public List<Character> toList(char[] array) {
		List<Character> result = new ArrayList<Character>(array.length);
		for(char v : array)
			result.add(v);
		return result;
	}
	
	public List<Short> toList(short[] array) {
		List<Short> result = new ArrayList<Short>(array.length);
		for(short v : array)
			result.add(v);
		return result;
	}
	
	public List<Float> toList(float[] array) {
		List<Float> result = new ArrayList<Float>(array.length);
		for(float v : array)
			result.add(v);
		return result;
	}
	
	public List<Double> toList(double[] array) {
		List<Double> result = new ArrayList<Double>(array.length);
		for(double v : array)
			result.add(v);
		return result;
	}
	
	public List<Boolean> toList(boolean[] array) {
		List<Boolean> result = new ArrayList<Boolean>(array.length);
		for(boolean v : array)
			result.add(v);
		return result;
	}
	
	public static boolean isBasicType(Object o) {
		if(null == o)
			return true;
		Class<?> c;
		if(o instanceof Class<?>) {
			c = (Class<?>)o;
		}
		else
			c = o.getClass();
		return Number.class.isAssignableFrom(c) || 
			String.class.isAssignableFrom(c) || 
			Date.class.isAssignableFrom(c) || 
			Boolean.class.isAssignableFrom(c) || 
			Character.class.isAssignableFrom(c);
	}
	
	public static boolean isNumber(Object o) {
		if(o instanceof Number)
			return true;
		try {
			new BigDecimal(o.toString());
			return true;
		}
		catch(Throwable e) {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static int compare(Object o1, Object o2) {
		if(o1 == o2)
			return 0;
		if(o1 == null)
			return -1;
		if(o2 == null)
			return 1;
		if(o1.getClass().isAssignableFrom(o2.getClass()) && o1 instanceof Comparable<?>)
			return ((Comparable<Object>)o1).compareTo(o2);
		else if(o2.getClass().isAssignableFrom(o1.getClass()) && o2 instanceof Comparable<?>) 
			return -((Comparable<Object>)o2).compareTo(o1);
		if((o1 instanceof Number || o2 instanceof Number) && (isNumber(o1) && isNumber(o2)))
			return new BigDecimal(o1.toString()).compareTo(new BigDecimal(o2.toString()));
		return o1.toString().compareTo(o2.toString());
	}
}
