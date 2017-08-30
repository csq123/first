package cn.smbms.test;

import cn.smbms.tools.Sigleton;

public  class  TestSingleton {
    
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		System.out.println("singleton test()---------->"+Sigleton.test());
		System.out.println("singleton getInstance()---------->"+Sigleton.getInstance());
		
	}
}

