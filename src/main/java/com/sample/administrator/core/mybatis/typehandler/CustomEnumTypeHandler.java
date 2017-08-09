package com.sample.administrator.core.mybatis.typehandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class CustomEnumTypeHandler<E extends Enum<E>> extends EnumTypeHandler<E> {

	private Class<E> type;
	
	public CustomEnumTypeHandler(Class<E> type) {
		super(type);
		this.type = type;
	}
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		if (jdbcType == null) {
			ps.setString(i, parameter.name());
		} else {
			ps.setObject(i, parameter.name(), jdbcType.TYPE_CODE); // see r3589
		}
	}
	
	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String s = rs.getString(columnName);
		return s == null ? null : getCustomEnumResult(s);
	}
	
	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String s = rs.getString(columnIndex);
		return s == null ? null : getCustomEnumResult(s);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String s = cs.getString(columnIndex);
		return s == null ? null : getCustomEnumResult(s);
	}
	
	private E getCustomEnumResult(String codeValue) {
		try {
			Method method = null;
			
			for (Method item : type.getEnumConstants()[0].getClass().getMethods()) {
				if (item.isAnnotationPresent(StoredValue.class)) {
					method = item;
				}
			}
			
			if (method == null) {
				method = type.getEnumConstants()[0].getClass().getDeclaredMethod("getStoredValue");
			}
			
			for (E item : type.getEnumConstants()) {
				Object value = method.invoke(item);
				if (codeValue.equals(value)) {
					return item;
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
