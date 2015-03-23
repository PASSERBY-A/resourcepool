/**
 * 
 */
package com.hp.avmon.common.sqlgenerator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.hp.avmon.utils.Constants;
import com.hp.avmon.utils.DBUtils;

/**
 * @author muzh
 *
 */
@SuppressWarnings("rawtypes")
public class CreateSqlTools {
	
	private static String getTableName(Object obj) {
		String tableName = null;
		if (obj.getClass().isAnnotationPresent(TableAnnotation.class)) {
			tableName = obj.getClass().getAnnotation(TableAnnotation.class).tableName();
		}
		return tableName;

	}
	
	private static List<Field> getAnnoFieldList(Object obj) {

		List<Field> list = new ArrayList<Field>();
		Class superClass = obj.getClass().getSuperclass();

		while (true) {
			if (superClass != null) {
				Field[] superFields = superClass.getDeclaredFields();
				if (superFields != null && superFields.length > 0) {
					for (Field field : superFields) {
						if (field.isAnnotationPresent(FieldAnnotation.class)) {
							list.add(field);
						}
					}
				}
				superClass = superClass.getSuperclass();
			} else {
				break;
			}
		}

		Field[] objFields = obj.getClass().getDeclaredFields();
		if (objFields != null && objFields.length > 0) {
			for (Field field : objFields) {
				if (field.isAnnotationPresent(FieldAnnotation.class)) {
					list.add(field);
				}
			}
		}
		return list;

	}

	private static String getFieldValue(Object obj, Field field) {

		String value = null;
		String name = field.getName();
		String methodName = "get" + name.substring(0, 1).toUpperCase()
				+ name.substring(1);
		Method method = null;
		Object methodValue = null;
		try {
			method = obj.getClass().getMethod(methodName);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		if (method != null) {
			try {
				methodValue = method.invoke(obj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			if (methodValue != null) {
				value = methodValue.toString();
			} else {
				Class objSuperClass = obj.getClass().getSuperclass();
				while (true) {
					if (objSuperClass != null) {
						try {
							methodValue = method.invoke(objSuperClass);
						} catch (IllegalAccessException e) {
							System.out.println(">>>>>>>>>>>>>>>method" + method);
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							System.out.println(">>>>>>>>>>>>>>>method" + method);
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							System.out.println(">>>>>>>>>>>>>>>method" + method);
							e.printStackTrace();
						}
						if (methodValue != null) {
							value = methodValue.toString();
							break;
						} else {
							objSuperClass = objSuperClass.getSuperclass();
						}
					} else {
						break;
					}
				}
			}
		}
		return value;
	}

	public static String getInsertSql(Object obj,HashMap<String, String> fixedParams) {

		String insertSql = null;
		String tableName = getTableName(obj);

		if (tableName != null) {
			StringBuffer sqlStr = new StringBuffer("INSERT INTO ");
			StringBuffer valueStr = new StringBuffer(" VALUES (");

			List<Field> annoFieldList = getAnnoFieldList(obj);

			if (annoFieldList != null && annoFieldList.size() > 0) {
				sqlStr.append(tableName + " (");
				if (fixedParams != null && fixedParams.size() > 0) {
					Iterator<String> keyNames = fixedParams.keySet().iterator();
					while (keyNames.hasNext()) {
						String keyName = (String) keyNames.next();
						sqlStr.append(keyName + ",");
						valueStr.append(fixedParams.get(keyName) + ",");
					}
				}

				for (Field field : annoFieldList) {
					FieldAnnotation anno = field.getAnnotation(FieldAnnotation.class);
					if (!anno.pk()) {
						Object fieldValue = getFieldValue(obj, field);
						if (fieldValue != null) {
							if (fixedParams != null && fixedParams.size() > 0) {
								Iterator<String> keyNames = fixedParams.keySet().iterator();
								boolean nextFieldFlag = false;
								while (keyNames.hasNext()) {
									String keyName = (String) keyNames.next();
									if (anno.fieldName().equals(keyName)) {
										nextFieldFlag = true;
										break;
									}
								}
								if (nextFieldFlag) {
									break;
								}
							}

							if(DBUtils.isOracle()){
		                         //处理oracle保留字，类似user，insert时"USER"
	                            sqlStr.append("\"" + anno.fieldName().toUpperCase() + "\",");
							}
							else{
							    sqlStr.append(anno.fieldName() + ",");
							}
							
							switch (anno.fieldType()) {
								case NUMBER:
									valueStr.append(fieldValue + ",");
									break;
								case SYSDATE:
									String k = DBUtils.getDbSysdateKeyword();
									valueStr.append(k).append(",");
									break;	
								default:
									valueStr.append("'" + fieldValue + "',");
									break;
							}
						}
					}
				}

				insertSql = sqlStr.toString().substring(0, sqlStr.length() - 1)
					+ ")" + valueStr.toString().substring(0,valueStr.length() - 1) + ")";

			}

		}

		return insertSql;

	}
	
	public static String getInsertSql(Object obj) { 
		return getInsertSql(obj, null); 
	}
	
	public static String getUpdateSql(Object obj, boolean reqPk,HashMap<String, String> fixedParams) {

		String updateSql = null;
		String tableName = getTableName(obj);

		if (tableName != null) {
			List<Field> annoFieldList = getAnnoFieldList(obj);
			if (annoFieldList != null && annoFieldList.size() > 0) {

				StringBuffer sqlStr = new StringBuffer("UPDATE " + tableName);
				StringBuffer valueStr = new StringBuffer(" SET ");
				String whereStr = " WHERE ";

				if (fixedParams != null && fixedParams.size() > 0) {
					Iterator<String> keyNames = fixedParams.keySet().iterator();
					while (keyNames.hasNext()) {
						String keyName = (String) keyNames.next();
						valueStr.append(keyName + "=" + fixedParams.get(keyName) + ",");
					}
				}

				for (Field field : annoFieldList) {
					String fieldValue = getFieldValue(obj, field);

					if (fieldValue != null) {
						FieldAnnotation anno = field.getAnnotation(FieldAnnotation.class);

						if (!anno.pk()) {
							if (fixedParams != null && fixedParams.size() > 0) {
								boolean nextFieldFlag = false;
								Iterator<String> keyNames = fixedParams.keySet().iterator();

								while (keyNames.hasNext()) {
									String keyName = (String) keyNames.next();
									if (anno.fieldName().equals(keyName)) {
										nextFieldFlag = true;
										break;
									}
								}

								if (nextFieldFlag) {
									break;
								}

							}

//							valueStr.append(anno.fieldName() + "=");
							
							valueStr.append("" + anno.fieldName().toUpperCase() + "=");
							switch (anno.fieldType()) {
								case NUMBER:
									valueStr.append(fieldValue + ",");
									break;	
								case SYSDATE:
									String k = DBUtils.getDbSysdateKeyword();
									valueStr.append(k).append(",");
									break;
								default:
									valueStr.append("'" + fieldValue + "',");
									break;
							}
						} else {
							if (reqPk) {
								switch (anno.fieldType()) {
									case NUMBER:
										whereStr += anno.fieldName() + "=" + fieldValue;
										break;
									default:
										whereStr += anno.fieldName() + "='" + fieldValue + "'";
										break;
								}
							}
						}
					}
				}

				updateSql = sqlStr.toString() + valueStr.toString().substring(0,valueStr.length() - 1) + (reqPk ? whereStr : "");

			}
		}
		return updateSql;

	}
	
	public static String getUpdateSql(Object obj) { 
		return getUpdateSql(obj, true, null); 
	} 
	
//	public static void main(String args[]){
//		
//		ConstantDefBean netProxyCfg = new ConstantDefBean();
//		netProxyCfg.setName("amu");
//		netProxyCfg.setId("amu");
//		netProxyCfg.setType("xxx");
//
//		System.out.println(CreateSqlTools.getInsertSql(netProxyCfg)); 
//
//		System.out.println(CreateSqlTools.getUpdateSql(netProxyCfg)); 
//	}
}
