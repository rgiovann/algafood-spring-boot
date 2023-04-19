//package com.algaworks.algafood.infraestructure.configuration;
//
//import java.lang.reflect.InvocationTargetException;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//

//
//   Useless modelmapper já mapeia classes, to fazendo um wrap do que já existe 
//

//@Component
//public class MyMapper {
//    private final ModelMapper modelMapper;
//
//    public MyMapper(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }
//
//	public <T, U> U mapEntity(T source, Class<U> destType) {
//		U dest = null;
//		try {
//			dest = destType.getDeclaredConstructor().newInstance();
//			modelMapper.map(source, dest);
//		} catch (InstantiationException | IllegalAccessException e) {
//			throw new RuntimeException(e);
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return dest;
//	}
//}
