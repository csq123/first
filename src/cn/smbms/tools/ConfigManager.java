package cn.smbms.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;




/**
 * 读取配置文件工具类-单例模式
 * @author Administrator
 *
 */
public class ConfigManager {
     private static Properties properties;
     //类加载时自动进行初始化：饿汉模式
     private static ConfigManager configManager=new ConfigManager();
     
     //私有构造器-读取配置文件操作
     private ConfigManager(){
    	 properties=new Properties();
 		String configFile = "database.properties";
 		InputStream is=ConfigManager.class.getClassLoader().getResourceAsStream(configFile);
 		try {
 			properties.load(is);
 			is.close();
 		} catch (IOException e) {
 			e.printStackTrace();
 		} 
     }
     //全局访问点
     /**
      * 
      * @return
      */
     /*public static synchronized ConfigManager getInstance(){
    	 if(configManager==null){
    		 configManager=new ConfigManager();
    	 }
    	 return configManager;
     }*/
     //饿汉模式
     public static  ConfigManager getInstance(){
    	 return configManager;
     }
     //获取相应的value值
     public String getValue(String key){
    	 
    	 return properties.getProperty(key);
     }
}
