package cn.smbms.tools;

public class Sigleton {
   private static Sigleton singleton;
   
   private Sigleton(){
	   //放置只执行一次的业务代码操作
   }
   public static class SingletonHelper{
	   private static final Sigleton INSTANCE=new Sigleton();
   }
   public static Sigleton getInstance(){
	   singleton=SingletonHelper.INSTANCE;
	   return singleton;
   }
   public static Sigleton test(){
	   return singleton;
   }
}
