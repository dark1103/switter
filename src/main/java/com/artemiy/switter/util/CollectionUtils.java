package com.artemiy.switter.util;

import java.util.List;
import java.util.function.Function;

/**
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
public class CollectionUtils {

	public static <T,D> List<D> transformList(List<T> list, Function<T,D> mappingFunction) {
		return list.stream().map(mappingFunction).toList();
	}
}
